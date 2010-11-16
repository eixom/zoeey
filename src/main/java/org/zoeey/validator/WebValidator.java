/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.validator;

/**
 * 适用于Web开发的一些验证规则
 * @author MoXie(SysTem128@GMail.Com)
 */
public class WebValidator
        extends Validator {

    /**
     * 锁定创建
     */
    private WebValidator() {
    }

    /**
     * 检查标题长度（3到255个字符）
     * @param title 标题
     * @return
     */
    public static boolean isTitle(String title) {
        return isTitle(title, false);
    }

    /**
     * 检查标题长度
     * @param title 标题
     * @param allowEmpty true 0到255个字符，false 0到255个字符
     * @return 
     */
    public static boolean isTitle(String title, boolean allowEmpty) {
        if (title == null || title.length() < 1) {
            return allowEmpty;
        }
        return isStringFormat(title, 3, 255);
    }

    /**
     *
     * @param titles
     * @return
     */
    public static boolean isTitle(String[] titles, boolean allowEmpty) {
        if (titles == null || titles.length < 1) {
            return allowEmpty;
        }
        for (String title : titles) {
            if (!isTitle(title, allowEmpty)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 短码
     * @param code 
     * @param allowEmpty
     * @return
     */
    public static boolean isShortCode(String code, boolean allowEmpty) {
        return isStringFormat(code, allowEmpty ? 0 : 1, 200);
    }

    /**
     * 是否为内容
     * @param content
     * @return
     */
    public static boolean isContent(String content) {
        return isContent(content, false);
    }

    /**
     * 是否为内容
     * @param content
     * @param allowEmpty 是否允许为空
     * @return
     */
    public static boolean isContent(String content, boolean allowEmpty) {
        if (content != null && content.length() > 0) {
            return isStringFormat(content, 5, 4000);
        } else {
            return allowEmpty;
        }
    }

    /**
     *
     * @param contents
     * @return
     */
    public static boolean isContent(String[] contents, boolean allowEmpty) {
        if (contents == null || contents.length < 1) {
            return allowEmpty;
        }
        for (String content : contents) {
            if (!isContent(content, allowEmpty)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param message
     * @return
     */
    public static boolean isMessage(String message) {
        return isStringFormat(message, 1, 1000);
    }

    /**
     *
     * @param sn
     * @return
     */
    public static boolean isSn(String sn) {
        return isStringFormat(sn, 1, 56, "^[a-zA-Z0-9_]{1,56}$");
    }

    /**
     * 时间格式
     * 2008-08-08 08:08:08
     * @param time
     * @return
     */
    public static boolean isFormatTime(String time) {
        return isFormatTime(time, false);
    }

    /**
     * 时间格式
     * 2008-08-08 08:08:08
     * @param time
     * @param allowEmpty 是否允许为空
     * @return
     */
    public static boolean isFormatTime(String time, boolean allowEmpty) {
        if (time != null && time.length() > 0) {
            return isStringFormat(time, 1, 56, "^[\\d]{4}\\-[\\d]{2}\\-[\\d]{2}[\\s]+[\\d]{2}:[\\d]{2}:[\\d]{2}$");
        } else {
            return allowEmpty;
        }
    }

    /**
     * 时间格式
     * 2008-08-08 
     * @param date
     * @return
     */
    public static boolean isFormatDate(String date) {
        return isFormatDate(date, false);
    }

    /**
     * 时间格式
     * 2008-08-08
     * @param date
     * @param allowEmpty 是否允许为空
     * @return
     */
    public static boolean isFormatDate(String date, boolean allowEmpty) {
        if (date != null && date.length() > 0) {
            return isStringFormat(date, 1, 56, "^[\\d]{4}\\-[\\d]{2}$");
        } else {
            return allowEmpty;
        }
    }

    /**
     *
     * @param modSn
     * @return
     */
    public static boolean isModuleSn(String modSn) {
        return isSn(modSn);
    }

    /**
     * 帐户验证
     * @param account  以字母、数字和下划线组成且下划线不再两端
     * @return
     */
    public static boolean isAccount(String account) {
        return isStringPreg(account, "^[a-zA-Z0-9][a-zA-Z0-9_]{1,34}[a-zA-Z0-9]$");
    }

    /**
     *
     * @param action
     * @return
     */
    public static boolean isAction(String action) {
        return isSn(action);
    }

    /**
     *
     * @param action
     * @param actions
     * @return
     */
    public static boolean isAction(String action, String[] actions) {
        boolean isValid = false;
        if (isAction(action)) {
            if (actions != null) {
                for (String act : actions) {
                    if (action.equals(act)) {
                        isValid = true;
                        break;
                    }
                }
            }
        }
        return isValid;
    }

    /**
     * #336699
     * ^#[a-fA-F0-9]{1,6}$
     * @param color
     * @return
     */
    public static boolean isColor(String color) {
        return isStringPreg(color, "^#[a-fA-F0-9]{1,6}$");
    }

    /**
     * rgb(33,66,99)
     * RGB(33,66,99)
     * ^(?i)rgb\\([0-9]+,[0-9]+,[0-9]+\\)$
     * @param color
     * @return
     */
    public static boolean isRgbColor(String color) {
        return isStringPreg(color, "^(?i)rgb\\([0-9]+,[0-9]+,[0-9]+\\)$");
    }

    /**
     * @param obj
     * @param objs
     * @return
     */
    public static boolean isRefer(Object obj, Object[] objs) {
        boolean isValid = true;
        if (obj instanceof String || obj instanceof Integer) {
            if (objs != null) {
                for (Object o : objs) {
                    if (!o.equals(obj)) {
                        isValid = false;
                    }
                }
            } else {
                isValid = false;
            }
        } else {
            isValid = false;
        }
        return isValid;
    }

    /**
     * SysTem128@GMail.Com
     * ^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$"
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return isStringPreg(email, "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$");
    }

    /** 
     * 0 与 正整数验证
     * @param numStr
     * @param allowZero 
     * @param allowNegative
     * @return
     */
    public static boolean isIntegerString(String numStr, boolean allowZero, boolean allowNegative) {
        boolean isValid = false;
        isValid = isStringPreg(numStr, "^[+|\\-]?[0-9]{1,10}+$");
        if (isValid) {
            try {
                if (allowZero == false) {
                    isValid = isValid & (Integer.parseInt(numStr, 10) != 0);
                }
                if (allowNegative == false) {
                    isValid = isValid & (Integer.parseInt(numStr, 10) >= 0);
                }
            } catch (NumberFormatException ex) {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * 0 和 正整数 即自然数
     * @param numStr
     * @return
     */
    public static boolean isIntegerString(String numStr) {
        return isIntegerString(numStr, true, false);
    }

    /**
     * 正整数
     * @param numStr
     * @return
     */
    public static boolean isIntegerString_Pos(String numStr) {
        return isIntegerString(numStr, false, false);
    }

    /**
     * 整数：
     * 负整数 0 和 正整数
     * @param numStr
     * @return
     */
    public static boolean isIntegerString_Neg(String numStr) {
        return isIntegerString(numStr, true, true);
    }

    /**
     * 密码
     * @param psw  长度为6～36位的密码
     * @return
     */
    public static boolean isPassword(String psw) {
        return isPassword(psw, false);
    }

    /**
     * 密码
     * @param psw  长度为6～36位的密码
     * @param allowEmpty 是否允许为空
     * @return
     */
    public static boolean isPassword(String psw, boolean allowEmpty) {
        if (psw != null && psw.length() > 0) {
            return isStringFormat(psw, 6, 36);
        } else {
            return allowEmpty;
        }
    }
    /**
     * 必须包含数字
     */
    public final static int PSW_STRICT_TYPE_NUMBER = 1;
    /**
     * 必须包含字母
     */
    public final static int PSW_STRICT_TYPE_LETTER = 2;
    /**
     * 必须同时包含数字和字母
     */
    public final static int PSW_STRICT_TYPE_NUM_LET = 3;

    /**
     * 密码严格验证
     * 6 - 36 位
     * @see #PSW_STRICT_TYPE_NUMBER 全数字
     * @see #PSW_STRICT_TYPE_LETTER 全字母
     * @see #PSW_STRICT_TYPE_NUM_LET 必须包含数字和字母
     * @param psw
     * @param type
     * @return
     */
    public static boolean isPasswordStrict(String psw, int type) {
        boolean isValid = false;
        do {
            isValid = isStringFormat(psw, 6, 36);
            if (!isValid) {
                break;
            }
            if (type == PSW_STRICT_TYPE_NUMBER
                    || type == PSW_STRICT_TYPE_NUM_LET) {
                char ch;
                for (int i = 0; i < psw.length(); i++) {
                    ch = psw.charAt(i);
                    if (!(ch >= '0' || ch <= '9')) {
                        isValid = false;
                    }
                }

                if (!isValid) {
                    break;
                }
            }
            if (type == PSW_STRICT_TYPE_LETTER
                    || type == PSW_STRICT_TYPE_NUM_LET) {
                char ch;
                for (int i = 0; i < psw.length(); i++) {
                    ch = psw.charAt(i);
                    if (!(ch >= 'a' && ch < 'z') || !(ch >= 'A' && ch <= 'Z')) {
                        isValid = false;
                    }
                }

                if (!isValid) {
                    break;
                }
            }
        } while (false);
        return isValid;
    }

    /**
     *
     * @param shortBrief
     * @return
     */
    public static boolean isShortBrief(String shortBrief) {
        return isStringFormat(shortBrief, 0, 200);
    }

    /**
     *
     * @param state
     * @param states
     * @return
     */
    public static boolean isState(String state, int[] states) {
        boolean isValid = false;
        if (isIntegerString(state)) {
            Integer stateInteger = Integer.valueOf(state);
            if (states != null) {
                for (Integer str : states) {
                    if (stateInteger.equals(str)) {
                        isValid = true;
                    }
                }
            }
        }
        return isValid;
    }

    /**
     *
     * @param tagSn
     * @return
     */
    public static boolean isTagSn(String tagSn) {
        return isSn(tagSn);
    }

//    public StateBean isFileSafe(Properties prop, FileItem fi) {
//        return super.isFileSafe(prop, fi);
//    }
    /**
     *
     * @param obj
     * @param objs
     * @return
     */
    public static boolean isInArray(Object obj, Object[] objs) {
        boolean isValid = false;
        if (objs != null) {
            for (Object _obj : objs) {
                if (_obj.equals(obj)) {
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }

    /**
     * 字符串是否表布尔，不区分大小写
     * @param boolStr "true","false"
     * @return
     */
    public static boolean isBoolean(String boolStr) {
        return ("true".equalsIgnoreCase(boolStr) || "false".equalsIgnoreCase(boolStr));
    }
}
