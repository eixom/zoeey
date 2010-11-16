/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.zoeey.common.ZObject;

/**
 * <pre>
 * 虚表 用于维护不定参数的对象查询
 * 特征
 * 表只存在 昵称名
 * 字段只使用 参数名
 * @see TableEntry#getNickName() 
 * @see FieldItem#getArgName()
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class AbstractTableEntry
        extends TableEntryBase
        implements AbstractTableEntryAble {


    /**
     * 新字段
     * @return
     */
    public static AbstractTableEntryAble newInstance() {
        return new AbstractTableEntry();
    }

    /**
     * 新字段
     * @param fieldName
     * @param type
     * @param value
     */
    public void newField(String fieldName, int type, Object value) {
        getFieldList().add(new FieldItem(fieldName, type, value));
    }

    public void newField(FieldItem field) {
        getFieldList().add(field);
    }

    /**
     * 新字符串字段
     * @param fieldName
     * @param value
     */
//    public void newString(String fieldName, String value) {
//        newField(fieldName, FieldItem.TYPE_STRING, new ZObject(value));
//    }
    /**
     * 新 数字字段
     * @param fieldName
     * @param value
     */
//    public void newNumber(String fieldName, int value) {
////        newField(fieldName, FieldItem.TYPE_NUMBER, new ZObject(value));
//        newField(fieldName, FieldItem.TYPE_NUMBER, new ZObject(value));
//    }
    /**
     * 新 空 字段
     * @param fieldName
     */
    public void newNull(String fieldName) {
//        newField(fieldName, FieldItem.TYPE_NULL, new ZObject(null));
        newField(fieldName, FieldItem.TYPE_NULL, null);
    }

    /**
     * 从Beans新增字段
     * 从一个实体对象
     * @param obj
     */
    public void fromBean(Object obj) {
        fromBean(obj, new Class[]{obj.getClass()});
    }

    /**
     * 从Beans新增字段，使用一个接口
     * 注意：接口为第一父级所有接口与本身所有
     * @param obj 包含有get方法的对象
     * @param clazzAble 
     */
    public void fromBean(Object obj, Class clazzAble) {
        List clazzList = new ArrayList();
        clazzList.add(clazzAble);
        Class[] interfaces = clazzAble.getInterfaces();
        if (interfaces != null && interfaces.length > 0 && clazzAble.isInterface()) {
            clazzList.addAll(Arrays.asList(interfaces));
        }
        fromBean(obj, (Class[]) clazzList.toArray(new Class[0]));
    }

    /**
     * 从Beans新增字段，使用多个Class内的方法
     * @param obj 用于使用的Bean对象
     * @param clazzs 元素的get，is方法将会从这里取出来
     */
    public void fromBean(Object obj, Class[] clazzs) {
        Class clazz = null;
        Method[] methods = null;
        Method method = null;
        List methodList = new ArrayList();
        for (int i = 0; i < clazzs.length; i++) {
            clazz = clazzs[i];
            methods = clazz.getDeclaredMethods();
            for (int j = 0; j < methods.length; j++) {
                method = methods[j];
                methodList.add(method);
            }
        }
        methods = (Method[]) methodList.toArray(new Method[0]);
        String name = null;
        try {
            for (int i = 0; i < methods.length; i++) {
                method = methods[i];
                name = method.getName();
                if (name.startsWith("get") // String Double Long Short Integer
                        || name.startsWith("is") // Boolean
                        ) {
                    name = name.replaceFirst("^(get|is)", "");
                    if (name.length() < 1) {
                        continue;
                    }
                    name = name.replaceFirst("^[A-Z]", name.substring(0, 1).toLowerCase());
                    newField(new FieldItem(name,
                            FieldItem.recognitionType(method.getReturnType()),
                            new ZObject(method.invoke(obj, new Object[0]))));
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }
}
