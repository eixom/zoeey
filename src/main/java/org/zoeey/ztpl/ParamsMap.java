/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 22:27:11
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl;

import java.util.Collection;
import java.util.HashMap;
import org.zoeey.common.ZObject;

/**
 * 模板参数映射表
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ParamsMap extends HashMap<String, Object> {

    private static final long serialVersionUID = -2028788835166238853L;

    /**
     * 获取值集合
     * 注意：如字段对应非值列表则返回空集合（<b>非null</b>）
     * @param key
     * @return
     */
    public Collection<Object> getList(String key) {
        return getMap(key).values();
    }

    /**
     * <pre>
     * 获取字段对应哈希表
     * 注意：如字段对应非表则返回 new FieldMap();
     * </pre>
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public ParamsMap getMap(String key) {
        Object fieldObj = get(key);
        if (fieldObj instanceof ParamsMap) {
            return (ParamsMap) fieldObj;
        } else {
            return new ParamsMap();
        }
    }

    /**
     *
     * @param key
     * @return
     */
    public ZObject getZObject(String key) {
        return new ZObject(get(key));
    }
}
