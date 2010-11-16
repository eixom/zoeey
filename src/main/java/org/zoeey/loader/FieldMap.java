/*
 * MoXie (SysTem128@GMail.Com) 2009-4-23 5:30:56
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

import java.util.Collection;
import java.util.HashMap;

/**
 * 字段映射表
 * @param <K>
 * @param <V>
 * @see ValueMapper
 * @author MoXie(SysTem128@GMail.Com)
 */
public class FieldMap<K, V> extends HashMap<K, V>
        implements FieldMapAble<K, V> {

    private static final long serialVersionUID = -2028788835166238853L;

    /**
     * 获取值集合
     * 注意：如字段对应非值列表则返回空集合（<b>非null</b>）
     * @param key
     * @return
     */
    public Collection<V> getList(K key) {
        return getMap(key).values();
    }

    /**
     * <pre>
     * 获取字段对应映射表
     * 注意：如字段对应非Map则返回 new FieldMap();
     * </pre>
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public FieldMap<K, V> getMap(K key) {
        Object fieldObj = get(key);
        if (FieldMap.class.isInstance(fieldObj)) {
            return (FieldMap<K, V>) fieldObj;
        } else {
            return new FieldMap<K, V>();
        }
    }
}
