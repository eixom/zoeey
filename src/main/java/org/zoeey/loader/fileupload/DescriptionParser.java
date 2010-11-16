/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.zoeey.util.StringHelper;

/**
 * 字段头解析
 * <a href="http://www.ietf.org/rfc/rfc1867.txt">Form-based File Upload in HTML</a>
 * @author MoXie(SysTem128@GMail.Com)
 */
final class DescriptionParser {

    /**
     * 索引
     */
    private int idx = 0;
    /**
     * 全长
     */
    private int length = 0;
    /**
     * 源文
     */
    private String source = null;
    /**
     * 小写键名
     */
    private boolean keyToLow = false;
    /**
     * 允许转译的字符
     */
    private static final char[] escs = {'"', '/', '\\', 'b', 'f', 'n', 'r', 't', 'u'};

    /**
     * 字段头解析
     * @param source
     * @param keyToLow
     */
    private DescriptionParser(String source, boolean keyToLow) {
        this.source = source;
        this.keyToLow = keyToLow;
        idx = 0;
        length = source.length();
    }

    /**
     * 寻找键值对
     * @param paramMap
     */
    private void seek(Map<String, String> paramMap) {
        skipBlank();
        String key = null;
        String value = null;
        for (; idx < length; idx++) {
            key = seekKey();
            skipBlank();
            value = seekValue();
            if (key.length() > 0 && value != null) {
                if (keyToLow) {
                    paramMap.put(key.toLowerCase(), value);
                } else {
                    paramMap.put(key, value);
                }
            }
        }
    }

    /**
     * 剔除空白符
     */
    private void skipBlank() {
        char ch = 0;
        for (; idx < length; idx++) {
            ch = source.charAt(idx);
            if (Character.isWhitespace(ch)) {
                continue;
            } else {
                break;
            }
        }
    }

    /**
     * 寻找键名
     * @return
     */
    private String seekKey() {
        char ch = 0;
        StringBuilder strBuilder = new StringBuilder();
        for (; idx < length; idx++) {
            ch = source.charAt(idx);
            if (Character.isJavaIdentifierPart(ch) || ch == '-') {
                strBuilder.append(ch);
            } else {
                skipBlank();
                if (idx == length) {
                    break;
                }
                ch = source.charAt(idx);
                if (ch == '=' || ch == ':') {
                    idx++;
                    break;
                } else {
                    strBuilder = new StringBuilder();
                    if (Character.isJavaIdentifierPart(ch)) {
                        strBuilder.append(ch);
                    }
                }
            }
        }
        return strBuilder.toString();
    }

    /**
     * 寻找值
     * @return
     */
    private String seekValue() {
        char ch = 0;
        StringBuilder strBuilder = new StringBuilder();
        int i = 0;
        short mod = -1; // 0 end with " 1 end width ' 2 end with whitespace
        char endChar = 0;
        boolean isEscape = false;
        if (idx == length) {
            return null;
        }
        doloop:
        for (; idx < length; idx++) {
            ch = source.charAt(idx);
            if (i++ == 0) {
                switch (ch) {
                    case '"':
                        mod = 0;
                        endChar = '"';
                        break;
                    case '\'':
                        mod = 1;
                        endChar = '\'';
                        break;
                    default:
                        mod = 2;
                        strBuilder.append(ch);
                        break;
                }
                continue;
            } else {
                switch (mod) {
                    case 0:
                    case 1:
                        if (!isEscape && ch == '\\') {
                            isEscape = true;
                            continue doloop;
                        }
                        if (!isEscape && ch == endChar) {
                            break doloop;
                        }
                        if (isEscape) {
                            isEscape = false;
                            if (Arrays.binarySearch(escs, ch) > -1) {
                                strBuilder.append(StringHelper.toEscapedVal(ch));
                            } else {
                                strBuilder.append('\\');
                                strBuilder.append(ch);
                            }
                            continue doloop;
                        }
                        strBuilder.append(ch);
                        isEscape = false;
                        break;
                    case 2:
                        if (Character.isWhitespace(ch) || ch ==';') {
                            break doloop;
                        }
                        strBuilder.append(ch);
                        break;
                }
                isEscape = false;
            }
        }
        return strBuilder.toString();
    }

    /**
     * 分析出参数,键名保持原貌
     * @param source
     * @return
     */
    public static Map<String, String> getParam(String source) {
        return getParam(source, false);
    }

    /**
     * 分析出参数
     * @param source 需要分析的内容
     * @param keyToLow  是否将键转换为小写
     * @return
     */
    public static Map<String, String> getParam(String source, boolean keyToLow) {
        Map<String, String> paramMap = new HashMap<String, String>();
        DescriptionParser parser = new DescriptionParser(source, keyToLow);
        parser.seek(paramMap);
        parser = null;
        return paramMap;
    }
}
