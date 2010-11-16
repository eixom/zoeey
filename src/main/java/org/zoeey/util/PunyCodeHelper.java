/*
 * MoXie (SysTem128@GMail.Com) 2010-3-25 23:50:18
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

/**
 * Punycode是一个根据RFC 3492标准而制定的编码系统,主要用於把域名从地方语言所采用的Unicode编码转换成为可用於DNS系统的编码。
 * 
 * RFC 3490 “在应用中实现国际化域名（IDNA）”；
 * RFC 3491 “域名准备（Nameprep）：国际化域名的字符串准备特征描述”；
 * RFC 3492 “Punycode码：应用中国际化域名（IDNA）统一码的引导串（Bootstring）编码”
 * http://www.ietf.org/rfc/rfc3490.txt
 * http://www.ietf.org/rfc/rfc3491.txt
 * http://www.ietf.org/rfc/rfc3492.txt
 * @author MoXie
 */
public class PunyCodeHelper {

    /**
     * ACE prefix
     */
    public final static String ACE_PREFIX = "xn--";
    /**
     * Parameter values for Punycode
     */
    private final static int BASE = 36;
    private final static int TMIN = 1;
    private final static int TMAX = 26;
    private final static int SKEW = 38;
    private final static int DAMP = 700;
    private final static int INITIAL_BIAS = 72;
    private final static int INITIAL_N = 0x80;
    private final static char DELIMITER = '-';

    /**
     * 编码
     * @param str Unicode 字符串
     * @return Punycode
     */
    public static String encode(String str) {
        int n = INITIAL_N;
        int delta = 0;
        int bias = INITIAL_BIAS;
        char ch = Character.UNASSIGNED;
        int len = str.length();
        StringBuilder strBuilder = new StringBuilder();
        for (int j = 0; j < len; j++) {
            ch = str.charAt(j);
            if (ch < 0x80) {
                strBuilder.append(ch);
            }
        }
        // number of basic code points
        int b_n = strBuilder.length();
        if (b_n > 0) {
            strBuilder.append(DELIMITER);
        }
        int h = b_n;
        int m, q, t;
        while (h < len) {
            m = Integer.MAX_VALUE;
            for (int j = 0; j < len; j++) {
                ch = str.charAt(j);
                if (ch >= n && ch < m) {
                    m = ch;
                }
            }

            if (m - n > (Integer.MAX_VALUE - delta) / (h + 1)) {
                return null; // return punycode_overflow;
            }
            delta = delta + (m - n) * (h + 1);
            n = m;

            for (int j = 0; j < len; j++) {
                ch = str.charAt(j);
                if (ch < n) {
                    if (++delta == 0) {
                        return null;
                    }
                }
                if (ch == n) {
                    q = delta;
                    for (int k = BASE;; k += BASE) {
                        t = k <= bias /* + tmin */ ? TMIN
                                : /* +tmin not needed */ k >= bias + TMAX ? TMAX : k - bias;
                        if (q < t) {
                            break;
                        }
                        strBuilder.append(encode_digit(t + (q - t) % (BASE - t)));
                        q = (q - t) / (BASE - t);
                    }

                    strBuilder.append(encode_digit(q));
                    bias = adapt(delta, h + 1, h == b_n);
                    delta = 0;
                    ++h;
                }
            }
            ++delta;
            ++n;
        }
        return strBuilder.toString();
    }

    /**
     * 解码
     * @param encoded Punycode字符串
     * @return Unicode 字符串
     */
    public static String decode(String encoded) {
        int n = INITIAL_N;
        int i = 0;
        int bias = INITIAL_BIAS;
        StringBuilder strBuilder = new StringBuilder();
        int len = encoded.length();
        int pos = encoded.lastIndexOf(DELIMITER);
        char ch;
        if (pos > 0) {
            for (int j = 0; j < pos; j++) {
                ch = encoded.charAt(j);
                if (ch >= 0x80) {
                    return null;
                }
                strBuilder.append(ch);
            }
            pos++;
        } else {
            pos = 0;
        }

        while (pos < len) {
            int oldi = i;
            int w = 1;

            for (int k = BASE;; k += BASE) {
                if (pos == len) {
                    return null;
                }
                ch = encoded.charAt(pos++);
                int digit = decode_digit(ch);
                if (digit > (Integer.MAX_VALUE - i) / w) {
                    return null;
                }
                i = i + digit * w;
                int t;
                t = k <= bias /* + tmin */ ? TMIN
                        : /* +tmin not needed */ k >= bias + TMAX ? TMAX : k - bias;
                if (digit < t) {
                    break;
                }
                w = w * (BASE - t);
            }
            bias = adapt(i - oldi, strBuilder.length() + 1, oldi == 0);

            if (i / (strBuilder.length() + 1) > Integer.MAX_VALUE - n) {
                return null;
            }

            n = n + i / (strBuilder.length() + 1);
            i = i % (strBuilder.length() + 1);
            strBuilder.insert(i, (char) n);
            i++;
        }
        return strBuilder.toString();
    }

    /**
     * Bias adaptation function (rfc3492 6.1)
     * @param delta
     * @param numpoints
     * @param first
     * @return
     */
    private final static int adapt(int delta, int numpoints, boolean first) {
        if (first) {
            delta = delta / DAMP;
        } else {
            delta = delta / 2;
        }

        delta = delta + (delta / numpoints);

        int k = 0;
        while (delta > ((BASE - TMIN) * TMAX) / 2) {
            delta = delta / (BASE - TMIN);
            k = k + BASE;
        }

        return k + ((BASE - TMIN + 1) * delta) / (delta + SKEW);
    }

    /**
     * the basic code point[Page 24]
     * @param d 
     * @return   the basic code point
     */
    private final static char encode_digit(int d) {
        return (char) (d + 22 + 75 * (d < 26 ? 1 : 0));
    }

    /**
     * the numeric value of a basic code[Page 24]
     * @param cp    
     * @return  the numeric value of a basic code
     */
    private final static int decode_digit(int cp) {
        return cp - 48 < 10 ? cp - 22 : cp - 65 < 26 ? cp - 65
                : cp - 97 < 26 ? cp - 97 : BASE;
    }
    private static final char dot = 0x002E;

    /**
     * 域名国际化
     * @param domain    本地域名
     * @return  国际化后的域名
     */
    public static String idna_encode(String domain) {
        int len = domain.length();
        StringBuilder strBuilder = new StringBuilder(len);
        char ch;

        for (int i = 0; i < len; i++) {
            ch = domain.charAt(i);
            switch (ch) {
                case 0x3002:
                case 0xFF0E:
                case 0xFF61:
                    ch = dot;
                    break;
            }
            strBuilder.append(ch);
        }
        domain = strBuilder.toString();
        String[] parts = StringHelper.split(domain, dot);
        String bunycode;
        strBuilder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i != 0) {
                strBuilder.append(dot);
            }
            bunycode = encode(parts[i]);
            if (bunycode.charAt(bunycode.length() - 1) == '-') {
                strBuilder.append(bunycode);
                strBuilder.deleteCharAt(strBuilder.length() - 1);
            } else {
                strBuilder.append(ACE_PREFIX);
                strBuilder.append(bunycode);
            }
        }
        return strBuilder.toString();
    }

    /**
     * 域名本地化
     * @param encoded   国际化的域名
     * @return  本地化后的域名
     */
    public static String idna_decode(String encoded) {
        StringBuilder strBuilder = new StringBuilder();
        String[] parts = StringHelper.split(encoded, dot);
        String str;
        for (int i = 0; i < parts.length; i++) {
            str = parts[i];
            if (str == null) {
                return null;
            }
            if (str.indexOf(ACE_PREFIX) == 0) {
                str = decode(str.substring(ACE_PREFIX.length()));
            } else {
                str = decode(str + '-');
            }
            if (str == null) {
                return null;
            }
            strBuilder.append(str);
            strBuilder.append(dot);
        }

        return strBuilder.substring(0, strBuilder.length() - 1);
    }
}
