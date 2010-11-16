/*
 * MoXie (SysTem128@GMail.Com) 2009-4-23 12:40:56
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

import java.util.Collection;
import java.util.Map;

/**
 * 字段映射表接口
 * @param <K>
 * @param <V>
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface FieldMapAble<K, V> extends Map<K, V> {

    /**
     * 获取值集合
     * 注意：如字段对应非值列表则返回空集合（<b>非null</b>）
     * @param key
     * @return
     */
    Collection<V> getList(K key);

    /**
     * <pre>
     * 获取字段对应哈希表
     * 注意：如字段对应非哈希表则返回 new FieldMap();
     * </pre>
     * @param key
     * @return
     */
    FieldMap<K, V> getMap(K key);
}
