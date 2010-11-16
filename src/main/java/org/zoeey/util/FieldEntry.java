package org.zoeey.util;

import java.util.Map;

/**
 * 字段
 * @param <K>
 * @param <V>
 */
public class FieldEntry<K, V> implements Map.Entry<K, V> {

    private K key;
    private V value;

    public FieldEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        return this.value = value;
    }
}
