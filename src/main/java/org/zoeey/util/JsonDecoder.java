/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * $log-new: 处理当"["、"{"等,在临近字符串尾部未闭合时出现的越界异常问题。 $
 * $log-new: 释放对象创建权限(改为public)，取消无参数构造函数、取消setJsonStr方法。 $
 * $log-new: 释放用户访问验证信息的权限。 $
 * $log-new: 取消泛型参数，返回Object。将强制转换权转交给用户。 $
 * $log-history:$
 */
package org.zoeey.util;

import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.zoeey.common.ZObject;

/**
 * Json解码<br />
 * 提供单行(//)和多行(/* *\/)两种注释类型。<br />
 * <a href="http://www.json.org/">www.json.org</a><br />
 * ex.<br />
 * 1.<br />
 *  jsonStr = "[  1 , 2 , 3 , 5 ]";<br />
 *  result = JsonDecoder.&lt;String&gt;decode(jsonStr);<br />
 * 2.<br />
 *  jsonStr = "{\"key_1\":\"value_1\",\"key_2\":\"value_2\"}";<br />
 *  decoder.setJsonStr(jsonStr);<br />
 *  result = decoder.decode();<br />
 *  vali = decoder.getValidator(); // 验证器可以取得相关的验证信息<br />
 * 注意：本类提供了对八进制和十六进制数字的支持，且允许注释。
 *      但为严格执行规范，请勿轻易传入。
 * @author MoXie(SysTem128@GMail.Com)
 */
public final class JsonDecoder {

    /**
     * Json 字符串
     */
    private String jsonStr;
    /**
     * 验证器
     */
    private Validator vali;
    /**
     * 索引位置
     */
    private int index;
    /**
     * Json 字符串长度
     */
    private int length;

    /**
     * Json解码
     * @param jsonStr
     */
    public JsonDecoder(String jsonStr) {
        length = jsonStr.length();
        this.jsonStr = jsonStr;
        vali = new Validator();
        index = 0;
    }

    /**
     * <pre>
     * 验证信息
     * 注意：在decode前取得的验证信息均为初始信息
     * </pre>
     */
    public static class Validator {

        private boolean pass = true;
        private int index = -1;

        /**
         * 是否通过验证
         * @return
         */
        public boolean isPass() {
            return pass;
        }

        /**
         * 第一次错误出现大致位置,无错误则返回 -1
         *
         * @return
         */
        public int getIndex() {
            return index;
        }

        /**
         * 标记错误
         * @param index
         */
        private void disPass(int index) {
            if (this.index == -1) {
                this.index = index;
            }
            pass = false;
        }
    }

    /**
     * <pre>
     * 注释处理
     * 提供单行(//)和多行(/* *\/)两种注释类型
     * </pre>
     */
    private void skipCommon() {
        char ch = jsonStr.charAt(++index);
        if (ch == '/') {
            for (index++; index < length; index++) {
                ch = jsonStr.charAt(index);
                if (ch == '\n' || ch == '\r') {
                    break;
                }
            }
        } else if (ch == '*') {
            boolean isEndReady = false;
            for (index++; index < length; index++) {
                ch = jsonStr.charAt(index);
                if (ch == '*') {
                    isEndReady = true;
                    continue;
                }
                if (isEndReady) {
                    isEndReady = false;
                    if (ch == '/') {
                        break;
                    }
                }
            }
        } else {
            vali.disPass(index);
        }
    }

    /**<pre>
     * 剔除空白元素
     * 结构间隔内均可同时使用空白符和注释。便于控制结构。
     * </pre>
     */
    private void skipBlank() {
        char ch = 0;
        for (; index < length; index++) {
            ch = jsonStr.charAt(index);
            if (Character.isWhitespace(ch)) {
                continue;
            } else if (ch == '/') {
                skipCommon();
                continue;
            } else {
                break;
            }
        }
    }

    /**
     * <pre>
     * 【键】值表达式
     * </pre>
     * @param strBuilder
     */
    private void seekKey(StringBuilder strBuilder) {
        seekString(strBuilder);
    }

    /**
     * <pre>
     * 验证字符串
     * 注意：起始必须为双引号。
     * </pre>
     * @param it
     * @return
     */
    private void seekString(StringBuilder strBuilder) {
        char ch = 0;
        boolean isEscape = false;
        ch = jsonStr.charAt(index);
        if (ch != '"') {
            vali.disPass(index);
        } else {
            while (true) {
                index++;
                if (index >= length) {
                    break;
                }
                ch = jsonStr.charAt(index);
                if (isEscape) {
                    isEscape = false;
                    seekEscape(strBuilder);
                } else {
                    if (ch == '\\') {
                        isEscape = true;
                        continue;
                    }
                    if (ch == '"') {
                        break;
                    } else {
                        strBuilder.append(ch);
                    }
                }
                if (!vali.isPass()) {
                    break;
                }
            }
        }
        if (ch != '"') {
            vali.disPass(index);
            strBuilder.setLength(0);
        }
    }

    /**
     * 验证十六进制字母
     * @param ch
     * @return
     */
    private boolean isHex(char ch) {
        if ((ch >= 'a' && ch <= 'f') || ch >= 'A' && ch <= 'F') {
            return true;
        }
        return false;
    }

    /**
     * 验证八进制字符
     * @param ch
     * @return
     */
    private boolean isOctal(char ch) {
        if ((ch >= '0' && ch <= '7')) {
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * 验证数字
     * +123 -123 +1.23 -1.23 
     * </pre>
     * @param it
     * @return
     */
    private int seekNumber(StringBuilder strBuilder) {
        char ch = 0;
        int dotCount = 0;
        int type = 1;// 0 int,1 long，2 float,3 double,
        char tmpCh = 0;
        int eIndex = -1;
        boolean isHex = false;
        boolean isOctal = false;
        boolean isDot = true;
        if (jsonStr.charAt(index) == '+') {
            index++;
        } else if (jsonStr.charAt(index) == '-') {
            index++;
            strBuilder.append('-');
        }
        if (length >= index + 2) {
            tmpCh = jsonStr.charAt(index + 1);
            if (jsonStr.charAt(index) == '0') {
                if ((tmpCh == 'x' || tmpCh == 'X')) {
                    isHex = true;
                    isDot = false;
                    isOctal = false;
                    index += 2;
                    strBuilder.append("0x");
                } else if (Character.isDigit(tmpCh)) {
                    isHex = false;
                    isDot = false;
                    isOctal = true;
                    strBuilder.append('0');
                }
            }
        }
        do {
            if (index >= length) {
                break;
            }
            ch = jsonStr.charAt(index);
            if (Character.isDigit(ch)) {
                strBuilder.append(ch);
            } else if (isDot && ch == '.') {
                type = 3;
                dotCount++;
                if (dotCount > 1) {
                    vali.disPass(index);
                    break;
                }
                strBuilder.append(ch);
            } else if (isHex && isHex(ch)) {
                strBuilder.append(ch);
            } else if (isOctal && isOctal(ch)) {
                strBuilder.append(ch);
            } else if (!isHex && !isOctal && eIndex == -1 && (ch == 'e' || ch == 'E')) {
                strBuilder.append(ch);
            } else {
                index--;
                break;
            }
            index++;
        } while (true);
        return type;
    }

    /**
     * 寻求Map
     * @return
     */
    private Map<Object, Object> seekMap() {
        String key = null;
        char ch = 0;
        Object value;
        StringBuilder strBuilder = new StringBuilder();
        Map<Object, Object> map = new LinkedHashMap<Object, Object>();
        for (index++; index < length; index++) {
            if (!vali.isPass()) {
                break;
            }
            skipBlank();
            ch = jsonStr.charAt(index);
            if (ch != '}') {
                strBuilder = new StringBuilder();
                seekKey(strBuilder); // {"_key":"value"}
                index++;
                skipBlank();
                ch = jsonStr.charAt(index);
                if (!vali.isPass()) {
                    break;
                }
                if (ch != ':') {
                    vali.disPass(index);
                    break;
                }
                key = strBuilder.toString();
                strBuilder.setLength(0);
                index++;
                skipBlank();
                value = decode();
                if (vali.isPass()) {
                    map.put(key, value);// {"key":"_value"}
                } else {
                    break;
                }
                index++;
                skipBlank();
                ch = jsonStr.charAt(index);
                if (ch == ',') {
                    continue;
                } else if (ch == '}') {
                    break;
                } else {
                    vali.disPass(index);
                }
            }
        }
        if (ch != '}') {
            vali.disPass(index);
        }
        return map;
    }

    /**
     * 寻求List
     * @return
     */
    private List<Object> seekList() {
        char ch = 0;
        List<Object> list = new ArrayList<Object>();
        Object value;
        for (index++; index < length; index++) {
            ch = jsonStr.charAt(index);
            if (ch != ']') {
                skipBlank();
                value = decode();
                if (vali.isPass()) {
                    list.add(value); // ["_value"]
                } else {
                    break;
                }
                ch = jsonStr.charAt(++index);
                skipBlank();
                ch = jsonStr.charAt(index);
                if (ch == ',') {
                    continue;
                } else if (ch == ']') {
                    break;
                } else {
                    vali.disPass(index);
                    break;
                }
            }
        }
        if (ch != ']') {
            vali.disPass(index);
        }
        return list;
    }
    /**
     * 可转义字符
     */
    private static final char[] escs = {'"', '/', '\\', 'b', 'f', 'n', 'r', 't', 'u'};

    // sorted
    /**
     * 寻求可转义字符
     * @param strBuilder
     */
    private void seekEscape(StringBuilder strBuilder) {
        char ch = jsonStr.charAt(index);
        /**
         * 非必须转义字符被识别为本身
         * 本方法保留较为严格的验证
         */
        if (Arrays.binarySearch(escs, ch) < 0) {
            vali.disPass(index);
        }
        // unicode
        if (vali.isPass()) {
            if (ch == 'u') {
                seekUnicode(strBuilder);
            } else {
                strBuilder.append(StringHelper.toEscapedVal(ch));
            }
        }
        // not valied return "\\uXXXX"
        if (false) {
            if (!vali.isPass()) {
                strBuilder.setLength(0);
            }
        }
    }

    /**
     * 寻求unicode表达式
     * @param strBuilder
     */
    private void seekUnicode(StringBuilder strBuilder) {
        char ch = 0;
        boolean isU = true;
        int i = 0;
        char[] chars = new char[4];
        int start = index;
        while (i < 4) {
            ch = jsonStr.charAt(++index);
            if (CharacterIterator.DONE == ch) {
                isU &= false;
                break;
            }
            if ((ch > 47 && ch < 58) // 0-9
                    || (ch > 64 && ch < 71) // A-F
                    || (ch > 96 && ch < 103) // a-f
                    ) {
                isU &= true;
            } else {
                isU &= false;
            }
            chars[i] = ch;
            i++;
        }
        // 不是unicode 直接输出 \\uXXXX
        if (!isU) {
            index = start;
            ch = 0;
            strBuilder.append("\\u");
            while (i > 0) {
                i--;
                strBuilder.append(jsonStr.charAt(++index));
            }
        } else {
            strBuilder.append((char) Integer.parseInt(new String(chars), 16));
        }
        chars = null;
    }

    /**
     * 主转换方法
     * @return
     */
    public Object decode() {

        if (!vali.isPass()) {
            return null;
        }

        Object obj = null; // for return
        try {
            char ch;
            StringBuilder strBuilder;
            skipBlank();
            ch = jsonStr.charAt(index);
            boolean isPass = false;
            int numType = 0;
            switch (ch) {
                case '\"':
                    strBuilder = new StringBuilder();
                    seekString(strBuilder);  // "...."
                    obj = strBuilder.toString();
                    strBuilder.setLength(0);
                    isPass = true;
                    break;
                case '[':
                    obj = seekList();
                    isPass = true;
                    break;
                case '{':
                    obj = seekMap();
                    isPass = true;
                    break;
                case 'n':
                case 'N':
                    isPass = true;
                    if (length - index > 2 && //
                            Character.toLowerCase(jsonStr.charAt(++index)) == 'u'
                            && Character.toLowerCase(jsonStr.charAt(++index)) == 'l'
                            && Character.toLowerCase(jsonStr.charAt(++index)) == 'l') {
                        obj = null;
                    } else {
                        vali.disPass(index);
                    }
                    break;
                case 't':
                case 'T':
                    isPass = true;
                    if (length - index > 2 && //
                            Character.toLowerCase(jsonStr.charAt(++index)) == 'r'
                            && Character.toLowerCase(jsonStr.charAt(++index)) == 'u'
                            && Character.toLowerCase(jsonStr.charAt(++index)) == 'e') {
                        obj = Boolean.TRUE;
                    } else {
                        vali.disPass(index);
                    }
                    break;
//            }
                case 'f':
                case 'F':
                    isPass = true;
                    if (length - index > 3 && //
                            Character.toLowerCase(jsonStr.charAt(++index)) == 'a'
                            && Character.toLowerCase(jsonStr.charAt(++index)) == 'l'
                            && Character.toLowerCase(jsonStr.charAt(++index)) == 's'
                            && Character.toLowerCase(jsonStr.charAt(++index)) == 'e'//
                            ) {
                        obj = Boolean.FALSE;
                    } else {
                        vali.disPass(index);
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '-':
                case '+':
                case '.':
                    strBuilder = new StringBuilder();
                    numType = seekNumber(strBuilder);
                    obj = strBuilder.toString();
                    if (numType == 1) {
                        obj = new ZObject(obj).toLong();
                    } else if (numType == 3) {
                        obj = new ZObject(obj).toDouble();
                    }

                    strBuilder.setLength(0);
                    isPass = true;
                    break;
            }
            if (!isPass) {
                vali.disPass(index);
            }
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            vali.disPass(index);
        }
        return obj;
    }

    /**
     * <pre>
     * 获取验证器
     * 可以得到错误信息（是否出错，第一次出错的大致位置）
     * </pre>
     * @return
     */
    public Validator getValidator() {
        return vali;
    }

    /**
     * 解码
     * @param jsonStr Json字符串
     * @return
     */
    public static Object decode(String jsonStr) {
        return new JsonDecoder(jsonStr).decode();
    }
}
