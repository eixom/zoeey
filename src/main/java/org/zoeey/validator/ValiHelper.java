/*
 * MoXie (SysTem128@GMail.Com) 2009-7-31 8:38:13
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.validator;

import org.zoeey.validator.annotations.MsgSn;

/**
 *
 * @author MoXie
 */
public class ValiHelper {

    /**
     * 锁定创建
     */
    private ValiHelper() {
    }

    /**
     *
     * @param msgSn
     * @param strs
     * @return
     */
    public static String pickMsgSn(MsgSn msgSn, String... strs) {
        if (strs != null) {
            String str;
            for (int i = 0; i < strs.length; i++) {
                str = strs[i];
                if (str != null && str.length() > 0) {
                    return str;
                }
            }
        }
        if (msgSn != null && msgSn.value().length() > 0) {
            return msgSn.value();
        }
        return null;
    }

    /**
     *
     * @param msgSn
     * @param strs
     * @return
     */
    public static String pickMsgNative(MsgSn msgSn, String... strs) {
        if (strs != null) {
            String str;
            for (int i = 0; i < strs.length; i++) {
                str = strs[i];
                if (str != null && str.length() > 0) {
                    return str;
                }
            }
        }
        if (msgSn != null && msgSn.value().length() > 0) {
            return msgSn.msgNative();
        }
        return null;
    }

    /**
     *
     * @param msgSn
     * @param signs
     * @return
     */
    public static int pickSign(MsgSn msgSn, int... signs) {
        if (signs != null) {
            int in;
            for (int i = 0; i < signs.length; i++) {
                in = signs[i];
                if (in > 0) {
                    return in;
                }
            }
        }
        if (msgSn != null && msgSn.sign() > 0) {
            return msgSn.sign();
        }
        return 0;
    }
}
