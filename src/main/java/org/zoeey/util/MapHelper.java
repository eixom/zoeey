/*
 * MoXie (SysTem128@GMail.Com) 2009-8-16 12:09:38
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.Map;

/**
 *
 * @author MoXie
 */
public class MapHelper {

    /**
     * 锁定创建
     */
    private MapHelper() {
    }

    /**
     * 转换键为小写
     * @param <T>
     * @param map
     * @return
     */
    public static <T> Map<String, T> keyToLowerCase(Map<String, T> map) {
        if (map != null) {
            T value;
            String[] keys = map.keySet().toArray(new String[0]);
            for (String key : keys) {
                value = map.get(key);
                map.remove(key);
                map.put(key.toLowerCase(), value);
            }
        }
        return map;
    }

    /**
     * 转换键为大写
     * @param <T>
     * @param map
     * @return
     */
    public static <T> Map<String, T> keyToUpperCase(Map<String, T> map) {
        if (map != null) {
            T value;
            String[] keys = map.keySet().toArray(new String[0]);
            for (String key : keys) {
                value = map.get(key);
                map.remove(key);
                map.put(key.toUpperCase(), value);
            }
        }
        return map;
    }
}
