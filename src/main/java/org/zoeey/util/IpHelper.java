/*
 * MoXie (SysTem128@GMail.Com) 2009-4-11 13:50:41
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.common.ZObject;

/**
 * <pre>
 * ip转换辅助类
 * 208.77.188.166 -> 3494755494L/-800211802L
 * -800211802L/3494755494L -> 208.77.188.166
 * Ip.v4
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class IpHelper {

    /**
     * 锁定创建
     */
    private IpHelper() {
    }

    /**
     * 转换为Long型（实为有符整型）。兼容Php ip2long。在数据库中可能需要使用signed int(11)存储。
     * 注意：仅支持IPv4
     * @param ipStr 例如 127.0.0.1
     * @return  转换错误时返回 0
     */
    public static long toLong(String ipStr) {
        return toLong(ipStr, true);
    }

    /**
     * 转换为Long型（实为有符整型）。
     * 注意：仅支持IPv4
     * @param ipStr 例如 127.0.0.1
     * @param isSign <b>true</b> 在数据库中可能需要使用 signed int(11)存储,兼容Php ip2long。。
     *                <b>false</b> 在数据库中可能需要使用 unsigned int(11)存储。
     * @return  转换错误时返回 0
     */
    public static long toLong(String ipStr, boolean isSign) {
        long ipLong = 0L;
        do {
            if (ipStr == null) {
                break;
            }
            String[] ipStrs = StringHelper.split(ipStr, '.');
            if (ipStrs.length != 4) {
                break;
            }
            // 6 << 2 = 24,4 << 2 = 16,2 << 2 = 8
            // 3 << 3 == 24 ,1 << 24 == 256 * 256 * 256
            // ip[0]*256*256*256 + ip[1]*256*256 + ip[2]*256 + ip[3] 24 16 8;
            long part = 0L;
            ipLong = 0L;
            int i = 3;
            try {
                for (int j = 0; j < 4; j++) {
                    part = ZObject.conv(ipStrs[j]).toInteger();
                    if (part < 0 || part > 255) {
                        ipLong = 0;
                        break;
                    }
                    ipLong += part << (i << 3);
                    i--;
                }
            } catch (NumberFormatException ex) {
                ipLong = 0L;
            }
            if (ipLong > 0xFFFFFFFFL) {
                ipLong = 0;
            }
        } while (false);
        if (isSign) {
            return (int) ipLong;
        }
        return ipLong;
    }

    /**
     * <pre>
     * 转换为字符串型
     * 注意：仅支持IPv4
     * </pre>
     * @param ipLong
     * @return  无法转换时返回空字符串
     */
    public static String toString(long ipLong) {
        StringBuilder strBuilder = new StringBuilder();
        /**
         * fixedbug: ipLong 为负数时无法转换
         */
        if (ipLong < 0) {
            ipLong = ipLong + (1L << 32);
        }
        if (ipLong > 0L && ipLong < (1L << 32)) {
            for (int i = 3; i >= 0; i--) {
                strBuilder.append((ipLong >> (i << 3)) & 255);
                if (i > 0) {
                    strBuilder.append('.');
                }
            }
        }
        return strBuilder.toString();
    }
}
