/*
 * MoXie (SysTem128@GMail.Com) 2009-4-11 18:03:21
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zoeey.constant.EnvConstants;

/**
 * <pre>
 * sha 字符串、文件等
 * 注意: MessageDigest 不支持SHA产生的异常只会被记录起来。
 *      此类与其他加密类代码有冗余部分，为独立实现做准备。
 * <a href="http://www.faqs.org/rfcs/rfc3174" >RFC3174 - US Secure Hash Algorithm 1 (SHA1)</a>
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public final class Sha1 {

    /**
     *
     */
    public Sha1() {
    }

    private String bin2hex(byte[] bytes) {
        return new BigInteger(1, bytes).toString(16);
    }

    /**
     * 取得SHA1摘要实例
     * @return
     * @throws NoSuchAlgorithmException
     */
    private MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA");
    }

    /**
     * 取得字节数组SHA1摘要
     * @param bytes
     * @return
     */
    public byte[] encrypt(byte[] bytes) {
        byte[] _bytes = null;
        try {
            MessageDigest md = getMessageDigest();
            md.update(bytes);
            _bytes = md.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Sha1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return _bytes;
    }

    /**
     * 取得字符串SHA1摘要
     * @param str
     * @return   全小写字母，str为null时返回null。
     */
    public String encrypt(String str) {
        if (str == null) {
            return null;
        }
        return bin2hex(encrypt(str.getBytes()));
    }

    /**
     * 取得文件的SHA1摘要
     * @param file 文件对象
     * @return
     * @throws java.io.IOException
     */
    public String encrypt(File file) throws IOException {
        return encrypt(new FileInputStream(file));
    }

    /**
     * 取得输入流SHA1摘要
     * @param is 文件对象
     * @return
     * @throws java.io.IOException
     */
    public String encrypt(InputStream is) throws IOException {
        byte[] _bytes = null;
        try {
            MessageDigest digest = getMessageDigest();
            byte[] buffer = new byte[EnvConstants.BUFFER_BYTE_SIZE];
            int read = 0;
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            _bytes = digest.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Sha1.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (is != null) {
                is.close();
                is = null;
            }
        }
        return bin2hex(_bytes);
    }
}
