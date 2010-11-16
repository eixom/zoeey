/*
 * MoXie (SysTem128@GMail.Com) 2009-4-12 14:01:20
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

/**
 * <pre>
 * Json编码与解码工具
 * <a href="http://www.json.org/">www.json.org</a>
 * <pre>
 * @see  JsonEncoder
 * @see  JsonDecoder
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsonHelper {

    /**
     * 锁定创建
     */
    private JsonHelper() {
    }

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
     * @param obj
     * @return
     */
    public static String encode(Object obj) {
        return new JsonEncoder().getJson(obj);
    }

    /**
     * <pre>
     * 从Json字符串中分析数据
     * JsonHelper.&lt;String&gt;decode(jsonStr);
     * 
     * </pre>
     * @param jsonStr Json字符串
     * @return
     * @see JsonDecoder
     */
    public static Object decode(String jsonStr) {
        return JsonDecoder.decode(jsonStr);
    }
}
