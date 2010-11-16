/*
 * MoXie (SysTem128@GMail.Com) 2009-7-16 16:38:44
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common;

/**
 * 数组强制转换
 * @author MoXie
 */
public class WideCast {

    /**
     * 锁定创建
     */
    private WideCast() {
    }

    /**
     * Object 到 char 的转换
     * @param args  Object 数组
     * @return  char 数组
     */
    public static char[] castToChar(Object[] args) {
        char[] results = new char[args.length];
        int i = 0;
        for (Object arg : args) {
            results[i] = ZObject.conv(arg).toChar();
            i++;
        }
        return results;
    }

    /**
     * Object 到 byte 的转换
     * @param args  Object 数组
     * @return  byte 数组
     */
    public static byte[] castToByte(Object[] args) {
        byte[] results = new byte[args.length];
        int i = 0;
        for (Object arg : args) {
            results[i] = ZObject.conv(arg).toByte();
            i++;
        }
        return results;
    }

    /**
     * Object 到 short 的转换
     * @param args  Object 数组
     * @return  short 数组
     */
    public static short[] castToShort(Object[] args) {
        short[] results = new short[args.length];
        int i = 0;
        for (Object arg : args) {
            results[i] = ZObject.conv(arg).toShort();
            i++;
        }
        return results;
    }

    /**
     * Object 到 int 的转换
     * @param args  Object 数组
     * @return  int 数组
     */
    public static int[] castToInteger(Object[] args) {
        int[] results = new int[args.length];
        int i = 0;
        for (Object arg : args) {
            results[i] = ZObject.conv(arg).toInteger();
            i++;
        }
        return results;
    }

    /**
     * Object 到 long 的转换
     * @param args  Object 数组
     * @return  long 数组
     */
    public static long[] castToLong(Object[] args) {
        long[] results = new long[args.length];
        int i = 0;
        for (Object arg : args) {
            results[i] = ZObject.conv(arg).toLong();
            i++;
        }
        return results;
    }

    /**
     * Object 到 double 的转换
     * @param args  Object 数组
     * @return  double 数组
     */
    public static double[] castToDouble(Object[] args) {
        double[] results = new double[args.length];
        int i = 0;
        for (Object arg : args) {
            results[i] = ZObject.conv(arg).toDouble();
            i++;
        }
        return results;
    }
}
