/*
 * MoXie (SysTem128@GMail.Com) 2009-7-3 8:05:23
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 信息摘要
 * 常用于信息签名、加密
 * @author MoXie
 */
public class EncryptHelper {

    /**
     * 锁定创建
     */
    private EncryptHelper() {
    }

    /**
     * <a href="http://www.faqs.org/rfcs/rfc1321" >RFC1321 - The MD5 Message-Digest Algorithm</a>
     * @param bytes
     * @return
     */
    public static byte[] md5(byte[] bytes) {
        return (new Md5()).encrypt(bytes);
    }

    /**
     * <a href="http://www.faqs.org/rfcs/rfc3174" >RFC3174 - US Secure Hash Algorithm 1 (SHA1)</a>
     * @param bytes
     * @return
     */
    public static byte[] sha1(byte[] bytes) {
        return (new Sha1()).encrypt(bytes);
    }

    /**
     * <a href="http://www.faqs.org/rfcs/rfc1321" >RFC1321 - The MD5 Message-Digest Algorithm</a>
     * @param str
     * @return
     */
    public static String md5(String str) {
        return (new Md5()).encrypt(str);
    }

    /**
     * <a href="http://www.faqs.org/rfcs/rfc3174" >RFC3174 - US Secure Hash Algorithm 1 (SHA1)</a>
     * @param str
     * @return
     */
    public static String sha1(String str) {
        return (new Sha1()).encrypt(str);
    }

    /**
     * <a href="http://www.faqs.org/rfcs/rfc1321" >RFC1321 - The MD5 Message-Digest Algorithm</a>
     * @param file 
     * @return
     * @throws IOException
     */
    public static String md5(File file) throws IOException {
        return (new Md5()).encrypt(file);
    }

    /**
     * <a href="http://www.faqs.org/rfcs/rfc3174" >RFC3174 - US Secure Hash Algorithm 1 (SHA1)</a>
     * @param file
     * @return
     * @throws IOException
     */
    public static String sha1(File file) throws IOException {
        return (new Sha1()).encrypt(file);
    }

    /**
     * <a href="http://www.faqs.org/rfcs/rfc3174" >RFC3174 - US Secure Hash Algorithm 1 (SHA1)</a>
     * @param is
     * @return
     * @throws IOException
     */
    public static String md5(InputStream is) throws IOException {
        return (new Sha1()).encrypt(is);
    }

    /**
     * <a href="http://www.faqs.org/rfcs/rfc3174" >RFC3174 - US Secure Hash Algorithm 1 (SHA1)</a>
     * @param is
     * @return
     * @throws IOException
     */
    public static String sha1(InputStream is) throws IOException {
        return (new Sha1()).encrypt(is);
    }
}
