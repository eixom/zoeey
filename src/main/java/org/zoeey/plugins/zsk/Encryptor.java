/*
 * MoXie (SysTem128@GMail.Com) 2009-1-18 16:27:02
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins.zsk;

import java.io.IOException;
import org.zoeey.util.Base64Helper;
import org.zoeey.util.EncryptHelper;
import org.zoeey.util.RandomHelper;
import org.zoeey.util.StringHelper;

/**
 * <pre>
 * 基于密匙的加解密
 * 可以进行安全的二进制加密
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class Encryptor {

    /**
     * 私有密匙
     */
    private String privateKey = "ENCRYPTOR_J8767028183688889200L";
    /**
     * 密匙
     */
    private String key;
    /**
     * 协同密匙
     */
    private String companionKey;

    /**
     *
     * @param key 公有密匙
     */
    public Encryptor(String key) {
        this.key = EncryptHelper.md5(privateKey + key);
        this.companionKey = this.genCompanion(key);
    }

    /**
     * 设置私有密匙
     * @param privateKey
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * 制取取协同密匙
     * @param <type> $key
     * @return <type>
     */
    private String genCompanion(String key) {
        StringBuilder strBuilder = new StringBuilder();
        byte[] keys = key.getBytes();
        strBuilder.append(privateKey);
        for (byte _key : keys) {
            strBuilder.append(_key);
        }

        return strBuilder.toString();
    }

    /**
     * 异或加解密
     * @param Mixed $content
     * @param String $pKey
     * @return String
     */
    private String getXorBilateral(String content, String pKey) {
        int len = pKey.length();
        int count = content.length();
        char current;
        StringBuilder strBuilder = new StringBuilder();
        int kIdx = 0;
        int i = 0;
        for (i = 0; i < count; i++) {
            if (kIdx == len) {
                kIdx = 0;
            }
            current = content.charAt(i);
            strBuilder.append((char) (current ^ pKey.charAt(kIdx)));
            kIdx++;
        }
        return strBuilder.toString();
    }

    /**
     * 加密
     * @param content
     * @return String
     * @throws IOException 
     */
    public String encrypt(String content) throws IOException {
        String randKey = RandomHelper.toString(16);
        content = getXorBilateral(content, this.key);
        content = getXorBilateral(content, randKey);
        content = randKey + content;
        content = getXorBilateral(content, this.companionKey);
        content = Base64Helper.encode(content);
        return content;
    }

    /**
     * 解密
     * @param content
     * @return String
     * @throws IOException
     */
    public String decrypt(String content) throws IOException {
        content = Base64Helper.decode(content);
        content = getXorBilateral(content, this.companionKey);
        String randKey = StringHelper.subString(content, 0, 16);
        content = StringHelper.subString(content, 16, -1);
        content = getXorBilateral(content, randKey);
        content = getXorBilateral(content, this.key);
        return content;
    }
}
