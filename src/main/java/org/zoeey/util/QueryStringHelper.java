/*
 * MoXie (SysTem128@GMail.Com) 2009-6-23 17:30:15
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.zoeey.constant.EnvConstants;

/**
 * QueryString 分析工具
 * 示例：
 *      以 ? 起始：      ?name=moxie&name=system128#top
 *      以 & 起始：      &name=moxie&name=system128#top
 *      直接以键起始：     name=moxie&name=system128#top
 *      项重复情况       name=moxie&name=system128#top
 *          
 * @author MoXie
 */
public class QueryStringHelper {

    /**
     * 索引
     */
    private int index;
    /**
     * 总长
     */
    private int length;
    /**
     * QueryString
     */
    private String queryString;
    /**
     * # 与其后的内容
     */
    private String hash;
    /**
     * 字段列表
     */
    private List<Entry<String, String>> fieldList;

    /**
     * 使用默认字符集解码
     * @param queryString
     */
    public QueryStringHelper(String queryString) {
        this(queryString, EnvConstants.CHARSET);
    }

    /**
     * 使用指定字符集解码
     * @param queryString
     * @param charset
     */
    public QueryStringHelper(String queryString, String charset) {
        this.queryString = queryString;
        fieldList = new ArrayList<Entry<String, String>>();
        parse(charset);
    }

    /**
     * 进入分析
     * @param charset
     */
    private void parse(String charset) {
        length = queryString.length();
        char ch;
        String key;
        String value;
        index = 0;
        do {
            if (index >= length) {
                break;
            }
            ch = queryString.charAt(index);
            if (ch == '&' || (index == 0 && ch != '#')) {
                if (index == 0 && ch != '?' && ch != '&') {
                    index--;
                }
                key = parseKey();
                /**
                 * fixedbug: key=value&key 异常
                 */
                if (index >= length) {
                    break;
                }
                ch = queryString.charAt(index);
                if (ch != '=') {
                    break;
                }
                value = parseValue();
                /**
                 * fixed: key decode
                 */
                fieldList.add(new FieldEntry(UrlHelper.decode(key, charset), UrlHelper.decode(value, charset)));
            } else if (ch == '#') {
                this.hash = parseHash();
            } else {
                break;
            }
        } while (true);

    }

    /**
     * 析出键
     * @return
     */
    private String parseKey() {
        StringBuilder strBuilder = new StringBuilder();
        char ch;
        index++;
        do {
            if (index >= length) {
                break;
            }
            ch = queryString.charAt(index);
            if (ch == '=') {
                break;
            }
            strBuilder.append(ch);
            index++;
        } while (true);
        return strBuilder.toString();
    }

    /**
     * 析出值
     * @return
     */
    private String parseValue() {
        StringBuilder strBuilder = new StringBuilder();
        char ch;
        index++;
        do {
            if (index >= length) {
                break;
            }
            ch = queryString.charAt(index);
            if (ch == '&' || ch == '#') {
                break;
            }
            strBuilder.append(ch);
            index++;
        } while (true);
        return strBuilder.toString();
    }

    /**
     * 析出 hash
     * @return
     */
    private String parseHash() {
        StringBuilder strBuilder = new StringBuilder();
        char ch;
        do {
            if (index >= length) {
                break;
            }
            ch = queryString.charAt(index);
            strBuilder.append(ch);
            index++;
        } while (true);
        return strBuilder.toString();
    }

    /**
     * 获取 hash (#与其后的字符)
     * @return
     */
    public String getHash() {
        return hash;
    }

    /**
     * 获取字段列表
     * @return
     */
    public List<Entry<String, String>> getFieldList() {
        return fieldList;
    }

    /**
     * <pre>
     * 获取Map
     * 注意：重复字段不会被覆盖，即重复字段会被忽略
     * </pre>
     * @return
     */
    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (Entry<String, String> field : fieldList) {
            if (map.containsKey(field.getKey())) {
                continue;
            }
            map.put(field.getKey(), field.getValue());
        }
        return map;
    }

    /**
     * <pre>
     * 获取Map
     * 注意：重复字段不会被覆盖，即重复字段会被忽略
     * </pre>
     * @param str
     * @see #getMap()
     * @return
     */
    public static Map<String, String> toMap(String str) {
        return (new QueryStringHelper(str)).getMap();
    }

    /**
     * 获取字段列表
     * @param str
     * @see #getFieldList() 
     * @return
     */
    public static List<Entry<String, String>> toFieldList(String str) {
        return (new QueryStringHelper(str)).getFieldList();
    }

    /**
     * <pre>
     * 获取Map
     * 注意：重复字段不会被覆盖，即重复字段会被忽略
     * </pre>
     * @param str
     * @param charset
     * @see #getMap()
     * @return
     */
    public static Map<String, String> toMap(String str, String charset) {
        return (new QueryStringHelper(str, charset)).getMap();
    }

    /**
     * 获取字段列表
     * @param str
     * @param charset
     * @see #getFieldList()
     * @return
     */
    public static List<Entry<String, String>> getEntryList(String str, String charset) {
        return (new QueryStringHelper(str, charset)).getFieldList();
    }

    /**
     * 获取字段列表
     * @param str
     * @see #getFieldList()
     * @return
     */
    public static List<Entry<String, String>> getEntryList(String str) {
        return getEntryList(str, EnvConstants.CHARSET);
    }
}
