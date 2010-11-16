/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <pre>
 * 将对象转换为 Json字符串。
 * 可包含类型有：
 * String，Short,Integer,Doubel，Boolean，Character，Number
 * Class直接返回Class名。
 * 其他类型会被认为是Bean转换为Map类型后进行编码
 * 并提供相关容器的自动转换（可任意嵌套）：
 * Array,Map,Collection(List,Set)
 * 这里并不建议使用Beans，请将Beans转换为Map等进行使用。
 * Json<a href="http://www.json.org/" >http://www.json.org/</a>
 * </pre>
 * @return
 */
public final class JsonEncoder {

    /**
     * 
     */
    private StringBuilder strBuilder;

    /**
     *
     */
    public JsonEncoder() {
    }

    /**
     * 转换
     * @param obj
     * @return
     */
    public String getJson(Object obj) {
        strBuilder = new StringBuilder();
        this.switcher(obj);
        return strBuilder.toString();
    }

    /**
     * 转换
     * @param obj
     * @return
     */
    public static String encode(Object obj) {
        return new JsonEncoder().getJson(obj);
    }

    /**
     * 字符型使用双引号括起来
     * @param strBuilder
     * @param str
     */
    private void add(String str) {
        strBuilder.append('\"');
        strBuilder.append(StringHelper.utf8_literal(str));
        strBuilder.append('\"');
    }

    /**
     * 布尔型直接转换为文字
     * @param strBuilder
     * @param bol
     */
    private void add(Boolean bol) {
        strBuilder.append(bol ? "true" : "false");
    }

    /**
     * Map的Key在不包含特殊字符的情况下可以不加双引号，但是Php的json_decode无法识别。
     * @param strBuilder
     * @param str
     */
    private void addKey(String str) {
//            boolean isMatch = str.matches("[a-zA-Z]+[a-zA-Z0-9]*");
//            if (!isMatch) {
        strBuilder.append('\"');
        strBuilder.append(StringHelper.utf8_literal(str));
        strBuilder.append('\"');
    }

    /**
     * 整形直接转换为文字
     * @param strBuilder
     * @param inte
     */
    private void add(Integer inte) {
        strBuilder.append(inte);
    }

    /**
     * 数字型直接输出
     * @param strBuilder
     * @param num
     */
    private void add(Number num) {
        strBuilder.append(num);
    }

    /**
     * null 直接输出 null。
     * @param strBuilder
     */
    private void addNull() {
        strBuilder.append("null");
    }

    /**
     * 键值对
     * @param strBuilder
     * @param iterator
     */
    private void addMap(Map<?, ?> map) {
        int i = 0;
        Object val = null;
        for (Entry<?, ?> entry : map.entrySet()) {
            if (i != 0) {
                strBuilder.append(',');
            }
            addKey(String.valueOf(entry.getKey()));
            strBuilder.append(':');
            val = entry.getValue();
            if (!map.equals(val)) {
                switcher(val);
            }
            i++;
        }
    }

    /**
     * 值列
     * @param strBuilder
     * @param iterator
     */
    private void addCollection(Collection<?> coll) {
        int i = 0;
        for (Object entry : coll) {
            if (i != 0) {
                strBuilder.append(',');
            }
            switcher(entry);
            i++;
        }
    }

    /**
     * 根据不同的类型进行分派填充
     * @param strBuilder
     * @param obj
     */
    private void switcher(Object obj) {
        do {
            if (obj == null) {
                addNull();
                break;
            }
            if (obj instanceof String) {
                add(obj.toString());
                break;
            }
            if (obj instanceof Integer || obj instanceof Short) {
                add(Integer.valueOf(obj.toString()));
                break;
            }
            if (obj instanceof Double) {
                add(Double.valueOf(obj.toString()));
                break;
            }
            /**
             * bugfixed: Boolean.TRUE == obj -> equals
             */
            if (obj instanceof Boolean) {
                add(Boolean.TRUE.equals(obj));
                break;
            }
            if (obj instanceof Character) {
                add(obj.toString());
                break;
            }
            if (obj instanceof Number) {
                add((Number) obj);
                break;
            }
            int i = 0;

            if (obj.getClass().isArray()) {
                strBuilder.append('[');
                int arSize = Array.getLength(obj);
                Object _o;
                while (i < arSize) {
                    _o = Array.get(obj, i);
                    if (i != 0) {
                        strBuilder.append(',');
                    }
                    switcher(_o);
                    i++;
                }
                strBuilder.append(']');
                break;
            }
            if (obj instanceof Map<?, ?>) {
                strBuilder.append('{');
                Map<?, ?> map = (Map<?, ?>) obj;
                addMap(map);
                strBuilder.append('}');
                break;
            }
            if (obj instanceof Collection<?>) {
                // collection  
                Collection<?> col = (Collection<?>) obj;
                strBuilder.append('[');
                addCollection(col);
                strBuilder.append(']');
                break;
            }
            // enum 仅做参考性支持，并不提供decode
            if (obj.getClass().isEnum()) {
                add(((Enum<?>) obj).name());
                break;
            }
            if (obj instanceof Class<?>) {
                add(((Class<?>) obj).getName());
                break;
            }
            // 其他Object转换为Map
            {
                switcher(BeanHelper.toMapIgnoreClass(obj));
                break;
            }
        } while (false);
    }
}
