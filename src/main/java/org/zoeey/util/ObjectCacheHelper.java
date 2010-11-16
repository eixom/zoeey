/*
 * MoXie (SysTem128@GMail.Com) 2009-5-7 22:47:44
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.zoeey.constant.EnvConstants;

/**
 * <pre>
 * 带有可控域的对象缓存。
 * 维持与程序相同的全生命周期。
 * 注意：在remove前重复或覆盖缓存将被忽略.
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ObjectCacheHelper {

    /**
     * 默认域
     * ex.
     *  private static String CACHE_DOMAIN = "org.zoeey.util.ReflectCacheHelper";
     */
    private static final String DEFAULT_DOMAIN = "org.zoeey.util.ObjectCacheHelper";
    /**
     *  
     */
    private static Map<Object, Map<Object, Object>> cacheMap =
            new ConcurrentHashMap<Object, Map<Object, Object>>(EnvConstants.CONCURRENT_CAPACITY_SIZE);

    /**
     * 锁定创建
     */
    private ObjectCacheHelper() {
    }

    /**
     * <pre>
     * 使用默认域的自定义键进行获取
     * ex.
     * get("keyName")
     * 等同于
     * get("org.zoeey.util.ObjectCacheHelper","keyName");
     * </pre>
     * @param <V> 返回对象类型
     * @param key 
     * @return 无值返回 null
     */
    public static <V> V get(Object key) {
        return ObjectCacheHelper.<V>get(DEFAULT_DOMAIN, key);
    }

    /**
     * <pre>
     * 获取自定义域对应键的值
     * ex.
     * get("org.zoeey.xyz","keyName")
     * </pre>
     * @param <V> 返回对象类型
     * @param domain 域
     * @param key 键
     * @return 无值返回 null
     */
    public static <V> V get(Object domain, Object key) {
        return ObjectCacheHelper.<V>get(domain, key, null);
    }

    /**
     * <pre>
     * 获取自定义域对应键的值,并自定义为空时的返回值
     * ex.
     * get("org.zoeey.xyz","keyName",new HashMap())
     * </pre>
     * @param <V> 返回对象类型
     * @param domain 域
     * @param key 键
     * @param nullReturn 获取值为空时返回此值
     * @return 为空时返回 nullReturn
     */
    @SuppressWarnings("unchecked")
    public static <V> V get(Object domain, Object key, Object nullReturn) {
        Map<Object, Object> subMap = cacheMap.get(domain);
        if (subMap == null) {
            return (V) nullReturn;
        }
        V returnV = (V) subMap.get(key);
        if (returnV == null) {
            return (V) nullReturn;
        }
        return returnV;
    }

    /**
     * 制取域
     * @param domain
     * @return 为空时返回新的Map
     */
    private static Map<Object, Object> getMap(Object domain) {
        Map<Object, Object> subMap = cacheMap.get(domain);
        if (subMap == null) {
            return new ConcurrentHashMap<Object, Object>(EnvConstants.CONCURRENT_CAPACITY_SIZE);
        }
        return subMap;
    }

    /**
     * <pre>
     * 在默认域新增键值
     * ex.
     * put("keyName","bigValue");
     * 等同于
     * put("org.zoeey.util.ObjectCacheHelper"，"keyName","bigValue");
     * </pre>
     * @param key   不可为空
     * @param value 需要缓存的对象
     */
    public static void put(Object key, Object value) {
        put(DEFAULT_DOMAIN, key, value);
    }

    /**
     * <pre>
     * 在自定义域新增键值
     * ex.
     * put("org.zoeey.xyz","keyName","bigValue");
     * </pre>
     * @param domain 自定义的域
     * @param key   不可为空
     * @param value 需要缓存的对象
     */
    public static void put(Object domain, Object key, Object value) {
        Map<Object, Object> subMap = getMap(domain);
        subMap.put(key, value);
        cacheMap.put(domain, subMap);
    }

    /**
     * <pre>
     * 清除默认域值
     * ex.
     * remove("keyName");
     * 等同于
     * remove("org.zoeey.util.ObjectCacheHelper","keyName");
     * </pre>
     * @param key 键
     */
    public static void remove(Object key) {
        cacheMap.remove(key);
    }

    /**
     * <pre>
     * 清除自定义域值
     * ex.
     * remove("org.zoeey.xyz","keyName");
     * </pre>
     * @param domain 自定义的域
     * @param key 键
     */
    public static void remove(Object domain, Object key) {
        Map<Object, Object> subMap = getMap(domain);
        subMap.remove(key);
        cacheMap.put(domain, subMap);
    }

    /**
     * 清理所有缓存
     */
    public static synchronized void removeAll() {
        cacheMap = new ConcurrentHashMap<Object, Map<Object, Object>>();
    }

    /**
     * <pre>
     * 清理域所有缓存
     * ex.
     * removeAll("org.zoeey.util.ObjectCacheHelper");
     * 等同于
     * removeDefaultDomainAll();
     * </pre>
     * @param domain 域名称
     */
    public static void removeAll(Object domain) {
        cacheMap.remove(domain);
    }

    /**
     * 清理默认域所有缓存
     */
    public static void removeDefaultDomainAll() {
        cacheMap.remove(DEFAULT_DOMAIN);
    }
}
