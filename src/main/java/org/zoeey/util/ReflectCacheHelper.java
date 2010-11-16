/*
 * MoXie (SysTem128@GMail.Com) 2009-5-9 15:07:43
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 反射辅助工具
 * 
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ReflectCacheHelper {

    /**
     * 类注解表
     */
    private Map<Class<? extends Annotation>, Annotation> annotationMap;
    /**
     * 方法表
     */
    private Map<String, Method> methodMap;
    /**
     * 方法注解表
     */
    private Map<Method, Map<Class<? extends Annotation>, Annotation>> methodAnnotationMap;
    /**
     * 字段表
     */
    private Map<String, Field> fieldMap;
    /**
     * 字段注解表
     */
    private Map<Field, Map<Class<? extends Annotation>, Annotation>> fieldAnnotationMap;
    /**
     * 缓存域名称
     */
    private static final String CACHE_DOMAIN = "org.zoeey.util.ReflectCacheHelper";

    /**
     * 锁定创建
     */
    private ReflectCacheHelper(Class<?> clazz) {
        // 类注解表
        annotationMap = new HashMap<Class<? extends Annotation>, Annotation>();
        // 方法表
        methodMap = new HashMap<String, Method>();
        //  方法注解表
        methodAnnotationMap = new HashMap<Method, Map<Class<? extends Annotation>, Annotation>>();
        // 字段表
        fieldMap = new HashMap<String, Field>();
        // 字段注解表
        fieldAnnotationMap = new HashMap<Field, Map<Class<? extends Annotation>, Annotation>>();
        /**
         * getMethods 与 getDeclaredMethods 的选择:
         * 前者会给出该方法所有共有方法,而后者只给出当前类所定义的公有方法。
         * 为保证数据完整性，遂采用前者
         */
        /**
         * 方法表
         * 方法注解表
         */
        Map<Class<? extends Annotation>, Annotation> methodAnnotation;
        for (Method method : clazz.getMethods()) {
            methodMap.put(method.getName(), method);
            //
            methodAnnotation = new HashMap<Class<? extends Annotation>, Annotation>();
            for (Annotation annot : method.getAnnotations()) {
                methodAnnotation.put(annot.annotationType(), annot);
            }
            methodAnnotationMap.put(method, methodAnnotation);
        }
        /**
         * 类注解表
         */
        for (Annotation annot : clazz.getAnnotations()) {
            annotationMap.put(annot.annotationType(), annot);
        }
        /**
         * 字段表
         */
        Map<Class<? extends Annotation>, Annotation> fieldAnnotation;
        for (Field field : clazz.getFields()) {
            fieldAnnotation = new HashMap<Class<? extends Annotation>, Annotation>();
            for (Annotation annot : field.getAnnotations()) {
                fieldAnnotation.put(annot.annotationType(), annot);
            }
            fieldAnnotationMap.put(field, fieldAnnotation);
            fieldMap.put(field.getName(), field);
        }
    }

    /**
     * 获取 ReflectCacheHelper 对象。
     * @param clazz
     * @return
     */
    public static ReflectCacheHelper get(Class<?> clazz) {
        ReflectCacheHelper reflectCache = ObjectCacheHelper.<ReflectCacheHelper>get(CACHE_DOMAIN, clazz);
        if (reflectCache == null) {
            synchronized (ReflectCacheHelper.class) {
                // 避免double check提示
                ReflectCacheHelper _cache = ObjectCacheHelper.<ReflectCacheHelper>get(CACHE_DOMAIN, clazz);
                if (_cache == null) {
                    _cache = new ReflectCacheHelper(clazz);
                    ObjectCacheHelper.put(CACHE_DOMAIN, clazz, _cache); // cache
                }
                reflectCache = _cache;
            }
        }
        return reflectCache;
    }

    /**
     * 获取方法表，方法名为键，方法为值
     * @return 方法表
     */
    public Map<String, Method> getMethodMap() {
        return methodMap;
    }

    /**
     * 获取类注解表
     * @return 类注解表
     */
    public Map<Class<? extends Annotation>, Annotation> getAnnotationMap() {
        return annotationMap;
    }

    /**
     * 获取方法注解表
     * @return 方法注解表
     */
    public Map<Method, Map<Class<? extends Annotation>, Annotation>> getMethodAnnotationMap() {
        return methodAnnotationMap;
    }

    /**
     * 获取类字段列表
     * @return  类字段列表
     */
    public Map<String, Field> getFieldMap() {
        return fieldMap;
    }

    /**
     * 获取字段注解表
     * @return
     */
    public Map<Field, Map<Class<? extends Annotation>, Annotation>> getFieldAnnotationMap() {
        return fieldAnnotationMap;
    }

    /**
     * 获取声明指定注解的方法
     * @param <T>   注解类型
     * @param annotationType    注解类
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Annotation> Map<Method, T> getAnnotationPresentedMethodMap(Class<T> annotationType) {
        Map<Method, T> annotMap = new HashMap<Method, T>();
        Map<Method, Map<Class<? extends Annotation>, Annotation>> _annotMap = getMethodAnnotationMap();
        for (Entry<Method, Map<Class<? extends Annotation>, Annotation>> entry : _annotMap.entrySet()) {
            if (entry.getValue().containsKey(annotationType)) {
                annotMap.put(entry.getKey(), (T) entry.getValue().get(annotationType));
            }
        }
        return annotMap;
    }

    /**
     * 获取方法的注解
     * @param method    方法实体
     * @return  方法未定义时返回 null
     */
    public Annotation[] getMethodAnnotations(Method method) {
        Map<Class<? extends Annotation>, Annotation> annotMap = methodAnnotationMap.get(method);
        return annotMap == null ? null : annotMap.values().toArray(new Annotation[annotMap.size()]);
    }

    /**
     * 获取方法的注解
     * @param methodName    方法名
     * @return  方法未定义时返回 null
     */
    public Annotation[] getMethodAnnotations(String methodName) {
        Method method = methodMap.get(methodName);
        if (method == null) {
            return null;
        }
        return getMethodAnnotations(method);
    }

    /**
     * 获取指定方法的注解
     * @param method    方法实体
     * @return
     */
    public Map<Class<? extends Annotation>, Annotation> getMethodAnnotationMap(Method method) {
        return methodAnnotationMap.get(method);
    }

    /**
     * 获取指定方法的注解
     * @param methodName    方法名
     * @return
     */
    public Map<Class<? extends Annotation>, Annotation> getMethodAnnotationMap(String methodName) {
        return methodAnnotationMap.get(methodMap.get(methodName));
    }

    /**
     * 获取指定方法的注解类
     * @param method    方法
     * @return
     */
    @SuppressWarnings("unchecked")
    public Class<? extends Annotation>[] getMethodAnnotationTypes(Method method) {
        Collection<Annotation> anntColl = methodAnnotationMap.get(method).values();
        Iterator<Annotation> annotIterator = anntColl.iterator();
        Class<? extends Annotation>[] anntClasses = (Class<? extends Annotation>[]) new Class<?>[anntColl.size()];
        int i = 0;
        while (annotIterator.hasNext()) {
            anntClasses[i++] = annotIterator.next().annotationType();
        }
        return anntClasses;
    }

    /**
     * 获取指定方法的注解类
     * @param methodName    方法名
     * @return  方法未定义时返回 null
     */
    public Class<? extends Annotation>[] getMethodAnnotationTypes(String methodName) {
        Method method = methodMap.get(methodName);
        return method == null ? null : getMethodAnnotationTypes(method);
    }

    /**
     * 使用方法名前缀来获取方法列表
     * @param prefix    前缀，大小写敏感
     * @return 方法列表，前缀为空时返回空列表
     */
    public List<Method> getMethodByPrefix(String... prefix) {
        List<Method> methodList = new ArrayList<Method>();
        if (prefix == null) {
            return methodList;
        }
        for (Entry<String, Method> entry : methodMap.entrySet()) {
            for (int i = 0; i < prefix.length; i++) {
                if (entry.getKey().startsWith(prefix[i])) {
                    if (!methodList.contains(entry.getValue())) {
                        methodList.add(entry.getValue());
                    }
                }
            }
        }
        return methodList;
    }

    /**
     * 获取类成员取值方法<br />
     * 例如：getName() 方法对应 name 字段，isIsActive() 方法对应 isActive 字段
     *      
     * @return
     */
    public Map<String, Method> getGetMap() {
        List<Method> methodList = getMethodByPrefix("get", "is");
        Map<String, Method> map = new HashMap<String, Method>();
        if (methodList == null) {
            return map;
        }
        String fieldName = null;
        int len = 0;
        for (Method method : methodList) {
            if (method.getParameterTypes().length > 0) {
                continue;
            }
            fieldName = method.getName();
            if (fieldName.startsWith("is")) {
                /**
                 * isIsActive() -> isActive
                 */
                len = 2;
            } else {
                /**
                 * getName() -> name
                 */
                len = 3;
            }
            fieldName = StringHelper.subString(fieldName, len, -1);
            fieldName = StringHelper.firstToLowerCase(fieldName);
            map.put(fieldName, method);
        }
        return map;
    }

    /**
     * 获取类成员设置方法<br />
     * 例如：setName() 方法对应 name 字段,setIsActive() 方法对应 isActive 字段。
     * @return  
     */
    public Map<String, Method> getSetMap() {
        List<Method> methodList = getMethodByPrefix("set");
        Map<String, Method> map = new HashMap<String, Method>();
        String fieldName = null;
        for (Method method : methodList) {
            /**
             * getName() -> name
             */
            fieldName = StringHelper.subString(method.getName(), 3, -1);
            fieldName = StringHelper.firstToLowerCase(fieldName);
            map.put(fieldName, method);
        }
        return map;
    }

    /**
     * 获取声明指定注解的字段
     * @param <T>   注解类型
     * @param annotationType    注解类
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Annotation> Map<Field, T> getAnnotationPresentedFieldMap(Class<T> annotationType) {
        Map<Field, T> annotMap = new HashMap<Field, T>();
        Map<Field, Map<Class<? extends Annotation>, Annotation>> _annotMap = getFieldAnnotationMap();
        for (Entry<Field, Map<Class<? extends Annotation>, Annotation>> entry : _annotMap.entrySet()) {
            if (entry.getValue().containsKey(annotationType)) {
                annotMap.put(entry.getKey(), (T) entry.getValue().get(annotationType));
            }
        }
        return annotMap;
    }

    /**
     * 获取字段的注解
     * @param field    字段实体
     * @return  字段未定义时返回 null
     */
    public Annotation[] getFieldAnnotations(Field field) {
        Map<Class<? extends Annotation>, Annotation> annotMap = fieldAnnotationMap.get(field);
        return annotMap == null ? null : annotMap.values().toArray(new Annotation[annotMap.size()]);
    }

    /**
     * 获取字段的注解
     * @param fieldName    字段名
     * @return  字段未定义时返回 null
     */
    public Annotation[] getFieldAnnotations(String fieldName) {
        Field field = fieldMap.get(fieldName);
        if (field == null) {
            return null;
        }
        return getFieldAnnotations(field);
    }

    /**
     * 获取指定字段的注解
     * @param field    字段实体
     * @return
     */
    public Map<Class<? extends Annotation>, Annotation> getFieldAnnotationMap(Field field) {
        return fieldAnnotationMap.get(field);
    }

    /**
     * 获取指定字段的注解
     * @param fieldName    字段名
     * @return
     */
    public Map<Class<? extends Annotation>, Annotation> getFieldAnnotationMap(String fieldName) {
        return fieldAnnotationMap.get(fieldMap.get(fieldName));
    }

    /**
     * 获取指定字段的注解类
     * @param field    字段
     * @return
     */
    @SuppressWarnings("unchecked")
    public Class<? extends Annotation>[] getFieldAnnotationTypes(Field field) {
        Collection<Annotation> anntColl = fieldAnnotationMap.get(field).values();
        Iterator<Annotation> annotIterator = anntColl.iterator();
        Class<? extends Annotation>[] anntClasses = (Class<? extends Annotation>[]) new Class<?>[anntColl.size()];
        int i = 0;
        while (annotIterator.hasNext()) {
            anntClasses[i++] = annotIterator.next().annotationType();
        }
        return anntClasses;
    }

    /**
     * 获取指定字段的注解类
     * @param fieldName    字段名
     * @return  字段未定义时返回 null
     */
    public Class<? extends Annotation>[] getFieldAnnotationTypes(String fieldName) {
        Field field = fieldMap.get(fieldName);
        return field == null ? null : getFieldAnnotationTypes(field);
    }

    /**
     * 使用字段名前缀来获取字段列表
     * @param prefix    前缀，大小写敏感
     * @return 字段列表，前缀为空时返回空列表
     */
    public List<Field> getFieldByPrefix(String... prefix) {
        List<Field> fieldList = new ArrayList<Field>();
        if (prefix == null) {
            return fieldList;
        }
        for (Entry<String, Field> entry : fieldMap.entrySet()) {
            for (int i = 0; i < prefix.length; i++) {
                if (entry.getKey().startsWith(prefix[i])) {
                    if (!fieldList.contains(entry.getValue())) {
                        fieldList.add(entry.getValue());
                    }
                }
            }
        }
        return fieldList;
    }
}
