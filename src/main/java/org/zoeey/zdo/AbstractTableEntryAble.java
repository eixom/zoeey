/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;


/**
 * 一个可以随意构造的 TableEntry
 * @see AbstractTableEntry
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface AbstractTableEntryAble extends TableItemAble {

    /**
     * 新字段
     * @param fieldName
     */
    public void newField(FieldItem fieldName);

    /**
     * 新字段
     * @param fieldName
     * @param type
     * @param value
     */
    public void newField(String fieldName, int type, Object value);

    /**
     * 新字符串字段
     * @param fieldName
     * @param value
     */
//    public void newString(String fieldName, String value);
    /**
     * 新 数字字段
     * @param fieldName
     * @param value
     */
//    public void newNumber(String fieldName, int value);
    /**
     * 新 空 字段
     * @param fieldName
     */
//    public void newNull(String fieldName);
    /**
     * 从Beans新增字段
     * 从一个实体对象
     * @param obj
     */
    public void fromBean(Object obj);

    /**
     * 从Beans新增字段，使用一个接口
     * 注意：接口为第一父级所有接口与本身所有
     * @param obj
     * @param clazz
     */
    public void fromBean(Object obj, Class<?> clazz);

    /**
     * 从Beans新增字段，使用多个Class内的方法
     * @param obj 用于使用的Bean对象
     * @param clazz
     */
    public void fromBean(Object obj, Class<?>[] clazz);
}
