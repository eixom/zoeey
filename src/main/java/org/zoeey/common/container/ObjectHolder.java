/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.container;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象容器
 * @author MoXie(SysTem128@GMail.Com)
 */
public final class ObjectHolder {

    /**
     * 修改实现为更可靠的 ThreadLocal
     */
    private static ThreadLocal<Map<String, Object>> localVar = new ThreadLocal<Map<String, Object>>();

    /**
     * 锁定创建
     */
    private ObjectHolder() {
    }

    /**
     * 判断是否存入过
     */
    private static boolean exist() {
        if (localVar.get() != null) {
            return true;
        }
        return false;
    }

    /**
     * 存入对象
     * @param map   对象映射表
     */
    private static void put(Map<String, Object> map) {
        localVar.set(map);
    }

    /**
     * 拿出对象
     * @return 值为空时返回 null
     */
    private static Map<String, Object> get() {
        return localVar.get();
    }

    /**
     * 存入一个对象
     * @param key   存储键
     * @param obj   存入对象
     */
    public static void put(String key, Object obj) {
        Map<String, Object> map = null;
        if (exist()) {
            map = get();
        } else {
            map = new HashMap<String, Object>();
        }
        map.put(key, obj);
        put(map);
    }

    /**
     * 取得对象
     * @param <T>   返回值数据类型
     * @param key   存储键
     * @param defaultValue 默认值
     * @return 如未Put任何数据则返回 newObj
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, T defaultValue) {
        Map<String, Object> map = get();
        if (map != null) {
            return (T) map.get(key);
        }
        return defaultValue;
    }

    /**
     * 取得对象
     * @param <T>   返回值数据类型
     * @param key   存储键
     * @return  未找到时返回 null
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        Map<String, Object> map = get();
        if (map != null) {
            return (T) map.get(key);
        }
        return null;
    }

    /**
     * 清理当前会话数据<br />
     * 特别注意：在每次会话完成时务必执行，以避免数据被共享
     */
    public static void clear() {
        if (localVar != null) {
            localVar.remove();
        }
    }
}
