/*
 * MoXie (SysTem128@GMail.Com) 2009-4-14 16:20:19
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

/**
 * <pre>
 * 参数辅助类
 * 制取可使用运算符号相连接的参数。
 * 注意:拼结的参数并没有顺序。
 * ex.
 * <b>1:</b>
 * int a,b,c;
 * a = genParam(1);b=genParam(2),c=genParam(3);
 * getSomeThing(args){
 *  if (contain(args,a){
 *     //
 *  }
 * }
 * getSomeThing(a <b>&</b> b); // 拼结的参数并没有顺序。b & a 也是同样的效果。
 * 
 * <b>2:</b>
 * a = genParamPlus(1);b=genParamPlus(2),c=genParamPlus(3);
 * getSomeThing(args){
 *  if (containPlus(args,a){
 *     //
 *  }
 * }
 * getSomeThing(a <b>+</b> b); // 拼结的参数并没有顺序。b + a 也是同样的效果。
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ParamHelper {

    /**
     * 锁定创建
     */
    private ParamHelper() {
    }
    /**
     * 按位与
     */
    public static final int AND_0 = 0x7FFFFFFE;
    /**
     *
     */
    public static final int AND_1 = 0x7FFFFFFD;
    /**
     *
     */
    public static final int AND_2 = 0x7FFFFFFB;
    /**
     *
     */
    public static final int AND_3 = 0x7FFFFFF7;
    /**
     *
     */
    public static final int AND_4 = 0x7FFFFFEF;
    /**
     *
     */
    public static final int AND_5 = 0x7FFFFFDF;
    /**
     *
     */
    public static final int AND_6 = 0x7FFFFFBF;
    /**
     *
     */
    public static final int AND_7 = 0x7FFFFF7F;
    /**
     *
     */
    public static final int AND_8 = 0x7FFFFEFF;
    /**
     *
     */
    public static final int AND_9 = 0x7FFFFDFF;
    /**
     *
     */
    public static final int AND_10 = 0x7FFFFBFF;
    /**
     *
     */
    public static final int AND_11 = 0x7FFFF7FF;
    /**
     *
     */
    public static final int AND_12 = 0x7FFFEFFF;
    /**
     *
     */
    public static final int AND_13 = 0x7FFFDFFF;
    /**
     *
     */
    public static final int AND_14 = 0x7FFFBFFF;
    /**
     *
     */
    public static final int AND_15 = 0x7FFF7FFF;
    /**
     *
     */
    public static final int AND_16 = 0x7FFEFFFF;
    /**
     *
     */
    public static final int AND_17 = 0x7FFDFFFF;
    /**
     *
     */
    public static final int AND_18 = 0x7FFBFFFF;
    /**
     *
     */
    public static final int AND_19 = 0x7FF7FFFF;
    /**
     *
     */
    public static final int AND_20 = 0x7FEFFFFF;
    /**
     *
     */
    public static final int AND_21 = 0x7FDFFFFF;
    /**
     *
     */
    public static final int AND_22 = 0x7FBFFFFF;
    /**
     *
     */
    public static final int AND_23 = 0x7F7FFFFF;
    /**
     *
     */
    public static final int AND_24 = 0x7EFFFFFF;
    /**
     *
     */
    public static final int AND_25 = 0x7DFFFFFF;
    /**
     *
     */
    public static final int AND_26 = 0x7BFFFFFF;
    /**
     *
     */
    public static final int AND_27 = 0x77FFFFFF;
    /**
     *
     */
    public static final int AND_28 = 0x6FFFFFFF;
    /**
     *
     */
    public static final int AND_29 = 0x5FFFFFFF;
    /**
     *
     */
    public static final int AND_30 = 0x3FFFFFFF;
    /**
     * 算术和
     */
    public static final int PLUS_0 = 0x1;
    /**
     *
     */
    public static final int PLUS_1 = 0x2;
    /**
     *
     */
    public static final int PLUS_2 = 0x4;
    /**
     *
     */
    public static final int PLUS_3 = 0x8;
    /**
     *
     */
    public static final int PLUS_4 = 0x10;
    /**
     *
     */
    public static final int PLUS_5 = 0x20;
    /**
     *
     */
    public static final int PLUS_6 = 0x40;
    /**
     *
     */
    public static final int PLUS_7 = 0x80;
    /**
     *
     */
    public static final int PLUS_8 = 0x100;
    /**
     *
     */
    public static final int PLUS_9 = 0x200;
    /**
     *
     */
    public static final int PLUS_10 = 0x400;
    /**
     *
     */
    public static final int PLUS_11 = 0x800;
    /**
     *
     */
    public static final int PLUS_12 = 0x1000;
    /**
     *
     */
    public static final int PLUS_13 = 0x2000;
    /**
     *
     */
    public static final int PLUS_14 = 0x4000;
    /**
     *
     */
    public static final int PLUS_15 = 0x8000;
    /**
     *
     */
    public static final int PLUS_16 = 0x10000;
    /**
     *
     */
    public static final int PLUS_17 = 0x20000;
    /**
     *
     */
    public static final int PLUS_18 = 0x40000;
    /**
     *
     */
    public static final int PLUS_19 = 0x80000;
    /**
     *
     */
    public static final int PLUS_20 = 0x100000;
    /**
     *
     */
    public static final int PLUS_21 = 0x200000;
    /**
     *
     */
    public static final int PLUS_22 = 0x400000;
    /**
     *
     */
    public static final int PLUS_23 = 0x800000;
    /**
     *
     */
    public static final int PLUS_24 = 0x1000000;
    /**
     *
     */
    public static final int PLUS_25 = 0x2000000;
    /**
     *
     */
    public static final int PLUS_26 = 0x4000000;
    /**
     *
     */
    public static final int PLUS_27 = 0x8000000;
    /**
     *
     */
    public static final int PLUS_28 = 0x10000000;
    /**
     *
     */
    public static final int PLUS_29 = 0x20000000;
    /**
     *
     */
    public static final int PLUS_30 = 0x40000000;
    /**
     *
     */
    public static final int PLUS_31 = 0x80000000;

    /**
     * <pre>
     * 制取参数，按位与"&"连接。
     * 注意：作为参数时使用 '&' 进行连接,取消时 取反并异或。
     * int a,b,c,args;
     * a = genParam(1);b=genParam(2),c=genParam(3);
     * 加入 args = a & b
     * 取消 args = ~args ^ a;
     * </pre>
     * @see #contain(int, int)
     * @param i 0-30 (包含0和30)
     * @return
     */
    public static int genParam(int i) {
        i = i < 0 ? 0 : i;
        i = i > 30 ? 30 : i;
        return (1 << 31) - 1 ^ (1 << i);
    }

    /**
     * <pre>
     * 判断是否含有某参数
     * 注意：用于判断使用 '&' 进行连接的参数。
     * </pre>
     * @see #genParam(int)
     * @param args 参数集合
     * @param arg 需要进行判断的参数
     * @return
     */
    public static boolean contain(int args, int arg) {
        return (args & ((1 << 31) - 1 ^ arg)) == 0;
    }

    /**
     * <pre>
     * 制取参数，算数和"+"连接。
     * 注意：用于判断使用加号 '+' 进行连接的参数，取消用 '^'或'-' 号。
     * int a,b,c,args;
     * a = genParamPlus(1);b=genParamPlus(2),c=genParamPlus(3);
     * 加入 args = a + b
     * 取消 args = a - b
     * </pre>
     * @see #containPlus(int, int) 
     * @param i 0-31 (包含0和03)
     * @return
     */
    public static int genParamPlus(int i) {
        i = i < 0 ? 0 : i;
        i = i > 30 ? 30 : i;
        return 1 << i;
    }

    /**
     * <pre>
     * 判断是否含有某参数
     * </pre>
     * @see #genParamPlus(int)
     * @param args 参数集合
     * @param arg 需要进行判断的参数
     * @return
     */
    public static boolean containPlus(int args, int arg) {
        return (args & arg) != 0;
    }

    /**
     * <pre>
     * 制取参数，算数和"|"连接。
     * 注意：作为参数时使用加号 '|' 进行连接，取消时用 '^'或'-' 号。
     * int a,b,c,args;
     * a = genParamOr(1);b=genParamOr(2),c=genParamOr(3);
     * 加入 args = a + b
     * 取消 args = a - b
     * </pre>
     * @see #containOr(int, int)
     * @param i 0-31 (包含0和03)
     * @return
     */
    public static int genParamOr(int i) {
        return genParamPlus(i);
    }

    /**
     * <pre>
     * 判断是否含有某参数
     * </pre>
     * @see #genParamOr(int)
     * @param args 参数集合
     * @param arg 需要进行判断的参数
     * @return
     */
    public static boolean containOr(int args, int arg) {
        return containPlus(args, arg);
    }
}
