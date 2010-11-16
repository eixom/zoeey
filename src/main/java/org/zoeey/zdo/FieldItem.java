/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import org.zoeey.common.ZObject;

/**
 * 字段项
 * 
 * @author MoXie(SysTem128@GMail.Com)
 */
public class FieldItem {

    /**
     * SQL
     */
    public static short TYPE_SQL = 0;
    /**
     * NULL
     */
    public static short TYPE_NULL = 1;
    /**
     * 字符串
     */
    public static short TYPE_STRING = 2;
    /**
     * 数字
     */
    public static short TYPE_NUMBER = 3;
    /**
     * 
     */
    /**
     * 字段名
     */
    private String name;
    /**
     * 参数名，默认和字段名相同
     */
    private String argName;
    /**
     * 参数类型
     * @see #TYPE_SQL
     * @see #TYPE_NULL
     * @see #TYPE_NUMBER
     * @see #TYPE_STRING
     */
    private int type;
    /**
     * 字段值
     * @see ZObject
     */
    private ZObject value;
    /**
     * 参数绑定顺序
     * 默认为0
     */
    private short order;
    /**
     * 是否参与活动
     */
    private boolean isActive = false;

    /**
     * 锁定创建
     */
    private FieldItem() {
    }

    /**
     *
     * @param name
     * @param type
     * @param value
     */
    public FieldItem(String name, int type, Object value) {
        this.name = name;
        this.argName = name;
        this.type = type;
        this.value = new ZObject(value);
        this.order = 0;
        this.isActive = true;
    }

    /**
     * 字段名称
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 字段名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 字段参数名
     * @return
     */
    public String getArgName() {
        return argName;
    }

    /**
     * 字段参数名
     * @param argName
     */
    public void setArgName(String argName) {
        this.argName = argName;
    }

    /**
     * 字段类型
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     * 字段类型
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 数据
     * @return
     */
    public ZObject getValue() {
        return value;
    }

    /**
     * 数据
     * @param value
     */
    public void setValue(Object value) {
        this.value = new ZObject(value);
    }

    /**
     * 参数绑定顺序
     * @return
     */
    public short getOrder() {
        return order;
    }

    /**
     * 参数绑定顺序
     * @param order
     */
    public void setOrder(short order) {
        this.order = order;
    }

    /**
     * 是否参与活动
     * @return
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * 停止活动
     */
    public void turnOff() {
        this.isActive = false;
    }

    /**
     * 开始活动
     */
    public void turnOn() {
        this.isActive = true;
    }

    /**
     * 识别类型
     * String Double Long Short  Integer Boolean
     * @param clazz
     * @return
     */
    public static short recognitionType(Class clazz) {
        return recognitionType(clazz.getName());
    }

    /**
     * 识别类型
     * String Double Long Short  Integer Boolean
     * @param clazz
     * @return
     */
    public static short recognitionType(String clazz) {
        //        clazz = java.lang.Double.class;
        //        clazz = java.lang.Long.class;
        //        clazz = java.lang.Integer.class;
        //        clazz = java.lang.Boolean.class;
        if (clazz.equals("java.lang.String")) {
            return TYPE_STRING;
        } else if (clazz.equals("java.lang.Double") //
                || clazz.equals("java.lang.Long") //
                || clazz.equals("java.lang.Integer") //
                || clazz.equals("java.lang.Boolean") //
                || clazz.equals("double") //
                || clazz.equals("long") //
                || clazz.equals("int") //
                || clazz.equals("boolean") //
                ) {
            return TYPE_NUMBER;
        } else {
            return TYPE_NULL;
        }
    }
}
