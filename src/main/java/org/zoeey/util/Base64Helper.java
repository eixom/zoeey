/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.IOException;

/**
 * Base64 编码解码<br />
 * Wikipedia: <a href="http://en.wikipedia.org/wiki/Base64" >Base64</a><br />
 * RFC: <a href="http://tools.ietf.org/html/rfc3548" >rfc3548</a>
 * @author MoXie(SysTem128@GMail.Com)
 */
public final class Base64Helper {

    /**
     * 锁定创建
     */
    private Base64Helper() {
    }

    /**
     * 编码
     * @param bytes
     * @return
     */
    public static String encode(byte[] bytes) {
        return new Base64Encoder().encode(bytes);
    }

    /**
     * 编码
     * @param str
     * @return
     */
    public static String encode(String str) {
        return new Base64Encoder().encode(str);
    }

    /**
     * 解码为字符串
     * @param str base64字符串
     * @return
     * @throws IOException
     */
    public static String decode(String str) throws IOException {
        return new Base64Decoder().decode(str);
    }

    /**
     * 解码为字节数组
     * @param bytes
     * @return
     * @throws IOException
     */
    public static byte[] decode(byte[] bytes) throws IOException {
        return new Base64Decoder().decode(bytes);
    }
}
