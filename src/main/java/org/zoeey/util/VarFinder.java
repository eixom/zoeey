/*
 * MoXie (SysTem128@GMail.Com) 2009-4-24 1:57:46
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 键值查找器
 * ex.
 * varFinder.from(mapA).or(mapC).or(mapB).find("key_1").or("key_2").or()
 *          .find("key_2").from(mapA).or(mapB)
 *          .done("default_value");
 * 可用于在多个Map域查找值，当第一次查找到时以后的查找操作将会被忽略。
 * </pre>
 * @param <K> 
 * @param <V>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class VarFinder<K, V> {

    /**
     * 最终值
     */
    private V _value = null;
    /**
     * 默认值
     */
    private V _defaultValue = null;
    /**
     * 键值项列表
     */
    private List<Item<K, V>> itemList;
    /**
     * 最后载入的Map
     */
    private Map<K, V> latestMap = null;
    /**
     * 最后载入的键
     */
    private K latestKey = null;

    /**
     * 键值查找器
     */
    public VarFinder() {
        itemList = new ArrayList<Item<K, V>>();
    }

    /**
     * 新增键
     * @param key
     */
    private void addKey(K key) {
        if (_value != null) {
            return;
        }
        latestKey = key;
        Item item;
        int itemIndex = itemList.size() - 1;
        for (int i = itemIndex; i >= 0; i--) {
            item = itemList.get(i);
            if (item.getKey() == null) {
                item.setKey(key);
            } else {
                break;
            }
        }
        item = new Item(key, latestMap);
        itemList.add(item);
    }

    /**
     * 新增Map
     * @param key
     */
    private void addMap(Map map) {
        if (_value != null) {
            return;
        }
        latestMap = map;
        Item item;
        int itemIndex = itemList.size() - 1;
        for (int i = itemIndex; i >= 0; i--) {
            item = itemList.get(i);
            if (item.getMap() == null) {
                item.setMap(map);
            } else {
                break;
            }
        }
        item = new Item(latestKey, map);
        itemList.add(item);
    }

    private void find() {
        if (_value != null) {
            return;
        }
        Iterator<Item<K, V>> itemIterator = itemList.iterator();
        Item<K, V> item;
        Map<K, V> map;
        while (itemIterator.hasNext()) {
            item = itemIterator.next();
            map = item.getMap();
            if (map != null) {
                _value = map.get(item.getKey());
            }
            if (_value != null) {
                break;
            }
        }
    }

    /**
     * 新增查找键
     * @param key
     * @return
     */
    public VarFinder find(K key) {
        addKey(key);
        return this;
    }

    /**
     * 新增查找键
     * @param key
     * @return
     */
    public VarFinder or(K key) {
        addKey(key);
        return this;
    }

    /**
     * 新增查找范围
     * @param map
     * @return
     */
    public VarFinder or(Map map) {
        addMap(map);
        return this;
    }

    /**
     * 新增查找范围
     * @param map
     * @return
     */
    public VarFinder from(Map<K, V> map) {
        addMap(map);
        return this;
    }

    /**
     * 设置默认值
     * @param value
     * @return
     */
    public VarFinder setDefault(V value) {
        _defaultValue = value;
        return this;
    }

    /**
     *  获取结果
     * @param defaultValue 默认值
     * @return
     */
    public V done(V defaultValue) {
        _defaultValue = defaultValue;
        return done();
    }

    /**
     * 获取结果,此操作最后包含{@link #reset()}
     * @return
     */
    public V done() {
        find();
        V returnValue = (_value == null) ? _defaultValue : _value;
        reset();
        return returnValue;
    }

    /**
     * 隔离查找范围
     * @return
     */
    public VarFinder or() {
        find();
        latestKey = null;
        latestMap = null;
        itemList.clear();
        return this;
    }

    /**
     * 重置查找，可用在重利用对象前，{@link #done()} 已包含此操作
     * @return
     */
    public VarFinder reset() {
        latestKey = null;
        latestMap = null;
        _value = null;
        _defaultValue = null;
        itemList.clear();
        return this;
    }

    /**
     * 清理查找器，清理后当前对象则不可用
     * @return
     */
    public VarFinder clear() {
        reset();
        itemList = null;
        return this;
    }

    /**
     * 键值项
     * @param <K>
     * @param <V>
     */
    private static class Item<K, V> {

        private K key = null;
        private Map<K, V> map = null;

        public Item(K key, Map<K, V> map) {
            this.key = key;
            this.map = map;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public Map<K, V> getMap() {
            return map;
        }

        public void setMap(Map<K, V> map) {
            this.map = map;
        }
    }
}
