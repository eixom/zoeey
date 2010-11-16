/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.validator;

/**
 * 验证器
 * @author MoXie(SysTem128@GMail.Com)
 */
public class Validator {

    /**
     * 锁定创建
     */
    protected Validator() {
    }

    /**
     * 限制长度和规则的字符串
     * @param str
     * @param min
     * @param max
     * @param regex
     * @return
     */
    public static boolean isStringFormat(String str, int min, int max, String regex) {
        boolean isValid = true;
        if (str != null) {
            int strLength = str.length();
            if (min > 0) {
                if (strLength < min) {
                    isValid = false;
                }
            }
            if (max > 0) {
                if (strLength > max) {
                    isValid = false;
                }
            }
            if (regex != null && regex.length() > 0) {
                if (!str.matches(regex)) {
                    isValid = false;
                }
            }
        } else {
            isValid = false;
        }
        return isValid;
    }

    /**
     * 限制最小和最大长度的字符串
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean isStringFormat(String str, int min, int max) {
        return isStringFormat(str, min, max, null);
    }

    /**
     * 只限制最小长度的字符串
     * @param str
     * @param min
     * @return
     */
    public static boolean isStringMin(String str, int min) {
        return isStringFormat(str, min, 0);
    }

    /**
     * 只限制最大长度的字符串
     * @param str
     * @param max
     * @return
     */
    public static boolean isStringMax(String str, int max) {
        return isStringFormat(str, 0, max);
    }

    /**
     * 正则限制
     * @param str
     * @param regex
     * @return
     */
    public static boolean isStringPreg(String str, String regex) {
        return isStringFormat(str, 0, 0, regex);
    }

    /**
     *
     * @param obj
     * @param objs
     * @return
     */
    public static boolean isInArray(Object obj, Object[] objs) {
        boolean isValid = false;
        if (obj != null && objs.length > 0) {
            for (Object _obj : objs) {
                if (obj.equals(_obj)) {
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }
}
