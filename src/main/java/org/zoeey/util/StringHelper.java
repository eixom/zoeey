/*
 * MoXie (SysTem128@GMail.Com) 2009-3-11 10:06:36
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串帮助类
 * change-log:
 *      09-11-26 16:20  新增自定义字符剔除函数。
 * 
 * @author MoXie(SysTem128@GMail.Com)
 */
public final class StringHelper {

    /**
     * 锁定创建
     */
    private StringHelper() {
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0) ? true : false;
    }

    /**
     * 格式话输出
     * @param str
     * @param args
     * @return
     */
    public static String format(String str, Object... args) {
        return (str == null || str.length() == 0) ? str : String.format(str, args);
    }

    /**
     * 剔除字符串两侧空格
     * @param str
     * @return
     */
    public static String trim(String str) {
        return ltrim(rtrim(str));
    }

    /**
     * 剔除字符串两侧字符
     * @param str
     * @param trimChars 需要剔除的字符
     * @return
     */
    public static String trim(String str, char[] trimChars) {
        return ltrim(rtrim(str, trimChars), trimChars);
    }

    /**
     * 切分字符串
     * @param str
     * @param sep
     * @return  无法切割时返回 0 长数组
     */
    public static String[] split(String str, char sep) {
        if (str == null) {
            return new String[0];
        }
        int length = str.length();
        char ch = 0;
        List<String> strList = new ArrayList<String>();
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            ch = str.charAt(i);
            if (ch == sep) {
                strList.add(strBuilder.toString());
                strBuilder = new StringBuilder();
            } else {
                strBuilder.append(ch);
            }
        }
        strList.add(strBuilder.toString());
        strBuilder = null;
        return strList.toArray(new String[strList.size()]);
    }

    /**
     * 剔除字符串右侧空格
     * @param str
     * @return
     */
    public static String rtrim(String str) {
        if (str != null) {
            char[] chars = str.toCharArray();
            StringBuilder strBuilder = new StringBuilder();
            char ch;
            boolean isTrimed = false;
            for (int i = chars.length - 1; i >= 0; i--) {
                ch = chars[i];
                if (!isTrimed) {
                    if (Character.isWhitespace(ch)) {
                        continue;
                    } else {
                        isTrimed = true;
                    }
                }
                strBuilder.append(ch);
            }
            strBuilder.reverse();
            str = strBuilder.toString();
        }
        return str;
    }

    /**
     * 剔除字符串右侧字符
     * @param str
     * @param trimChars 需要剔除的字符
     * @return
     */
    public static String rtrim(String str, char[] trimChars) {
        if (str != null && trimChars != null && trimChars.length > 0) {
            char[] chars = str.toCharArray();
            StringBuilder strBuilder = new StringBuilder();
            char ch;
            boolean isTrimed = false;
            for (int i = chars.length - 1; i >= 0; i--) {
                ch = chars[i];
                if (!isTrimed) {
                    if (ArrayHelper.inArray(trimChars, ch)) {
                        continue;
                    } else {
                        isTrimed = true;
                    }
                }
                strBuilder.append(ch);
            }
            strBuilder.reverse();
            str = strBuilder.toString();
        }
        return str;
    }

    /**
     * 剔除字符串左侧空格
     * @param str
     * @return
     */
    public static String ltrim(String str) {
        if (str != null) {
            char[] chars = str.toCharArray();
            StringBuilder strBuilder = new StringBuilder();
            char ch;
            boolean isTrimed = false;
            for (int i = 0; i < chars.length; i++) {
                ch = chars[i];
                if (!isTrimed) {
                    if (Character.isWhitespace(ch)) {
                        continue;
                    } else {
                        isTrimed = true;
                    }
                }
                strBuilder.append(ch);
            }
            str = strBuilder.toString();
        }
        return str;
    }

    /**
     * 剔除字符串左侧字符
     * @param str
     * @param trimChars 需要剔除的字符
     * @return
     */
    public static String ltrim(String str, char[] trimChars) {
        return ltrim(str, trimChars, 0);
    }

    /**
     * 剔除字符串左侧字符
     * @param str
     * @param trimChars 需要剔除的字符
     * @param limit    剔除个数限制 0 则为不限制
     * @return
     */
    public static String ltrim(String str, char[] trimChars, int limit) {
        if (str != null && trimChars != null && trimChars.length > 0) {
            char[] chars = str.toCharArray();
            StringBuilder strBuilder = new StringBuilder();
            char ch;
            boolean isTrimed = false;
            for (int i = 0; i < chars.length; i++) {
                ch = chars[i];
                if (!isTrimed) {
                    if (ArrayHelper.inArray(trimChars, ch)) {
                        if (i >= limit) {
                            isTrimed = true;
                        }
                        continue;
                    } else {
                        isTrimed = true;
                    }
                }
                strBuilder.append(ch);
            }
            str = strBuilder.toString();
        }
        return str;
    }

    /**
     * 文本替换
     * @param source 原始字符串
     * @param search 替换字符
     * @param replace 替换目标字符
     * @return
     */
    public static String replace(String source, String search, String replace) {
        source = (source == null) ? "" : source;
        if (search == null) {
            return source;
        }
        replace = replace == null ? "" : replace;
        return Pattern.compile(Pattern.quote(search)).matcher(source).replaceAll(replace);
    }

    /**
     * 文本替换
     * @param source 原始字符串
     * @param search 替换字符
     * @param replace 替换目标字符
     * @return
     */
    public static String replaceFirst(String source, String search, String replace) {
        source = (source == null) ? "" : source;
        if (search == null) {
            return source;
        }
        replace = replace == null ? "" : replace;
        return Pattern.compile(Pattern.quote(search)).matcher(source).replaceFirst(replace);
    }

    /**
     * 文本替换，大小写不敏感
     * @param source 原始字符串
     * @param search 替换字符
     * @param replace 替换目标字符
     * @return
     */
    public static String replaceIgnoreCase(String source, String search, String replace) {
        source = (source == null) ? "" : source;
        if (search == null) {
            return source;
        }
        replace = replace == null ? "" : replace;
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("((?i)");
        strBuilder.append(Pattern.quote(search));
        strBuilder.append(")");
        return Pattern.compile(strBuilder.toString()).matcher(source).replaceAll(replace);
    }

    /**
     * 文本替换，大小写不敏感
     * @param source 原始字符串
     * @param search 替换字符
     * @param replace 替换目标字符
     * @return
     */
    public static String replaceFirstIgnoreCase(String source, String search, String replace) {
        source = (source == null) ? "" : source;
        if (search == null) {
            return source;
        }
        replace = replace == null ? "" : replace;
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("((?i)");
        strBuilder.append(Pattern.quote(search));
        strBuilder.append(")");
        return Pattern.compile(strBuilder.toString()).matcher(source).replaceFirst(replace);
    }

    /**
     * 重复某字符串
     * @param str
     * @param count
     * @return 当str为null时返回null。
     */
    public static String repeat(String str, int count) {
        if (str == null) {
            return null;
        }
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            strBuilder.append(str);
        }
        return strBuilder.toString();
    }

    /**
     * 颠倒字符串
     * @param str
     * @return
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * 剔除第一次出现的某字符串
     * @param str
     * @param needRemoved
     * @return 当str为null时返回null。
     */
    public static String removeFirst(String str, String needRemoved) {
        if (str == null) {
            return null;
        }
        if (needRemoved == null) {
            return str;
        }
        return str.replaceFirst(regexEscape(needRemoved), "");
    }

    /**
     * 剔除出现的某字符串
     * @param str
     * @param needRemoved
     * @return 当str为null时返回null。
     */
    public static String removeAll(String str, String needRemoved) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(regexEscape(needRemoved), "");
    }

    /**
     * <pre>
     * 字符集转换
     * Convert string to requested character encoding
     *
     * </pre>
     * @param fromCharset 来源字符集 如：ISO-8859-1
     * @param toCharset 目标字符集
     * @param str   需要转换的字符串
     * @return
     * @throws java.io.UnsupportedEncodingException
     * @throws CharacterCodingException
     */
    public static String iconv(String fromCharset, String toCharset, String str)
            throws CharacterCodingException, UnsupportedEncodingException {
        if (fromCharset.equals(toCharset)) {
            return str;
        }
//        ByteBuffer buf = ByteBuffer.wrap(str.getBytes(toCharset));
//        /**
//         * decode
//         */
//        CharBuffer cbuf = Charset.forName(toCharset).decode(buf);
//        /**
//         * encode
//         */
//        buf = Charset.forName(toCharset).encode(cbuf);
//        byte[] bytes = new byte[buf.limit()];
//        System.arraycopy(buf.array(), 0, bytes, 0, bytes.length);
//        return new String(bytes, toCharset);
        return new String(str.getBytes(fromCharset), toCharset);
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String firstToLowerCase(String str) {
        if (str == null || str.length() < 1) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String firstToUpperCase(String str) {
        String lowFirstStr = str.substring(0, 1).toUpperCase();
        return lowFirstStr + str.substring(1);
    }

    /**
     * 正则转义
     * 注意：与 Pattern.quote()和Matcher.quoteReplacement(str)的区别
     * @see java.util.regex.Matcher#quoteReplacement(java.lang.String)
     * @see java.util.regex.Pattern#quote(java.lang.String)
     * @param str 需要转义的字符串
     * @return
     */
    public static String regexEscape(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        StringCharacterIterator iterator = new StringCharacterIterator(str);
        char _char = iterator.current();
        while (_char != CharacterIterator.DONE) {
            if (_char == '.') {
                result.append("\\.");
            } else if (_char == ':') {
                result.append("\\:");
            } else if (_char == ')') {
                result.append("\\)");
            } else if (_char == '^') {
                result.append("\\^");
            } else if (_char == '$') {
                result.append("\\$");
            } else if (_char == '\\') {
                result.append("\\\\");
            } else if (_char == '{') {
                result.append("\\{");
            } else if (_char == '}') {
                result.append("\\}");
            } else if (_char == '[') {
                result.append("\\[");
            } else if (_char == ']') {
                result.append("\\]");
            } else if (_char == '(') {
                result.append("\\(");
            } else if (_char == '?') {
                result.append("\\?");
            } else if (_char == '*') {
                result.append("\\*");
            } else if (_char == '+') {
                result.append("\\+");
            } else if (_char == '&') {
                result.append("\\&");
            } else if (_char == '-') {
                result.append("\\-");
            } else {
                result.append(_char);
            }
            _char = iterator.next();
        }
        return result.toString();
    }

    /**
     * 
     * <pre>
     * 子字符串，柔化字符串处理。
     * 上下标越界皆以“界”为准。
     * end = -1 则是截取到最终。
     * 如：
     * subString("MoXie",-50,100);
     * 等同于
     * subString("MoXie",0,4)
     *
     * </pre>
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String subString(String str, int start, int end) {
        int strLen = str.length();
        start = start > strLen ? strLen : start;
        //
        start = start > 0 ? start : 0;
        //
        if (end == -1) {
            end = strLen;
        } else {
            end = end > strLen ? strLen : end;
        }
        //
        return str.substring(start, end);
    }

    /**
     * <pre>
     * 照字面输出
     * Json.org 说明的需要转义的的字符
     * \"   \\  \/  \b  \f  \n  \r  \t \ u four-hex-digits
     * </pre>
     * @param str
     * @return 
     * @see  StringHelper#toUnicode(java.lang.String)
     **/
    public static String utf8_literal(String str) {
        StringBuilder strBuilder = new StringBuilder(str.length());
        CharacterIterator it = new StringCharacterIterator(str);
        for (char ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
            switch (ch) {
                case '"':
                    strBuilder.append("\\\"");
                    break;
                case '\\':
                    strBuilder.append("\\\\");
                    break;
                case '/':
                    strBuilder.append("\\/");
                    break;
                case '\b':
                    strBuilder.append("\\b");
                    break;
                case '\f':
                    strBuilder.append("\\f");
                    break;
                case '\n':
                    strBuilder.append("\\n");
                    break;
                case '\r':
                    strBuilder.append("\\r");
                    break;
                case '\t':
                    strBuilder.append("\\t");
                    break;
                default:
                    if (ch < 26 || ch > 126) {
                        toUnicode(strBuilder, ch);
                    } else {
                        strBuilder.append(ch);
                    }
                    break;
            }
        }
        return strBuilder.toString();
    }
    private static final char[] hexs = "0123456789abcdef".toCharArray();

    /**
     * <pre>
     * Json.org 说明的需要转义的的字符
     * \"   \\  \/  \b  \f  \n  \r  \t \ u four-hex-digits
     * </pre>
     * @see  StringHelper#toUnicode(java.lang.String)
     **/
    private static void toUnicode(StringBuilder strBuilder, char ch) {
        /**
         * standard unicode format
         */
        strBuilder.append("\\u");
        for (int i = 4; i > 0; i--) {
            strBuilder.append(hexs[(ch & 0xf000) >> 12]);
            ch <<= 4;
        }
    }

    /**
     * 转化字母为转义后的值
     * @param ch
     * @return
     */
    public static char toEscapedVal(char ch) {
        if (ch == 't') {
            ch = '\t';
        } else if (ch == 'r') {
            ch = '\r';
        } else if (ch == 'n') {
            ch = '\n';
        } else if (ch == 'f') {
            ch = '\f';
        } else if (ch == 't') {
            ch = '\t';
        } else if (ch == 'b') {
            ch = '\b';
        }
        return ch;
    }
}
