/*
 * MoXie (SysTem128@GMail.Com) 2009-8-10 11:01:02
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zoeey.constant.EnvConstants;

/**
 * <pre>
 * Base64 解码
 * Wikipedia: <a href="http://en.wikipedia.org/wiki/Base64" >Base64</a>
 * </pre>
 * @author MoXie
 */
class Base64Decoder {

    /**
     * 对位表
     */
    private static byte alphabet_byte[] = new byte[256];

    static {
        for (int i = 0; i < 255; i++) {
            alphabet_byte[i] = -1;
        }
        for (int i = 0; i < Base64Encoder.alphabet.length; i++) {
            alphabet_byte[Base64Encoder.alphabet[i]] = (byte) i;
        }
    }

    /**
     * 对字符串进行解码
     * @param str
     * @return
     */
    public String decode(String str) {
        try {
            if (str == null) {
                return str;
            }
            return new String(decode(str.getBytes(EnvConstants.CHARSET)), EnvConstants.CHARSET);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Base64Decoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * 对位解码
     * @param pre
     * @param suf
     * @param pos
     * @return
     */
    private byte decode(byte pre, byte suf, int pos) {
        int outInt = 0;
        pre = alphabet_byte[pre & 0xFF];
        suf = alphabet_byte[suf & 0xFF];
        if (pos % 3 == 1) {
            pos = 1;
        } else if (pos % 3 == 2) {
            pos = 2;
        } else {
            pos = 3;
        }
        switch (pos) {
            case 1:
                outInt = (((pre << 2) & 0xFC) | ((suf >>> 4) & 3));
                break;
            case 2:
                outInt = (((pre << 4) & 0xF0) | ((suf >>> 2) & 0xF));
                break;
            case 3:
                outInt = (((pre << 6) & 0xC0) | (suf & 0x3F));
                break;
        }
        return (byte) outInt;

    }

    /**
     * 对字节序列解码
     * @param encBytes
     * @return
     */
    public byte[] decode(byte[] encBytes) {
        int encLen = encBytes.length;
        int origLen = (encBytes.length / 4) * 3;
        if (origLen < 4) {
            return encBytes;
        }
        int trim = 0;
        if (encBytes[encBytes.length - 1] == '=') {
            trim++;
        }
        if (encBytes[encBytes.length - 2] == '=') {
            trim++;
        }
        origLen -= trim;
        byte[] origBytes = new byte[origLen];
        int encIdx = 0;
        int origIdx = 0;
        int index = 0;
        while (index < encLen) {
            if (origIdx == origLen) {
                break;
            }
            origBytes[origIdx++] = decode(encBytes[encIdx], encBytes[++encIdx], index + 1);
            if (encIdx == encLen || encBytes[encIdx] == '=') {
                break;
            }
            if ((index + 1) % 3 == 0) {
                encIdx++;
            }
            index++;
        }
        return origBytes;
    }
}
