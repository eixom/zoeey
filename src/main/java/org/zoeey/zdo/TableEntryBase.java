/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.zoeey.common.ZObject;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public abstract class TableEntryBase
        implements TableItemAble {

    /**
     * 表名称
     */
    private String tableName = null;
    /**
     * 表昵称
     */
    private String nickName = null;
    /**
     * 字段列表
     */
    private List fields = null;
    /**
     * 主键
     */
    private FieldItem primeKey = null;

    /**
     *
     */
    protected TableEntryBase() {
        fields = new ArrayList();
    }

    /**
     * 获取字段列表
     * @return
     */
    public FieldItem[] getFields() {
        return (FieldItem[]) fields.toArray(new FieldItem[0]);
    }

    /**
     * 获取字段数
     * @return 字段总数
     */
    public int getFieldSize() {
        return fields.size();
    }

    /**
     * 获取字段列表
     * @return
     */
    public List getFieldList() {
        return fields;
    }

    /**
     * 载入table
     * @param table
     */
    public void setTable(TableItemAble table) {
        setFields(table.getFields());
    }

    /**
     * 载入字段
     * @param fields
     */
    public void setFields(FieldItem[] fields) {
//        this.fields = fields;
        this.fields.clear();
        FieldItem field = null;
        for (int i = 0; i <
                fields.length; i++) {
            field = fields[i];
            this.fields.add(field);
        }
//        this.fields.addAll(Arrays.asList(fields));

    }

    /**
     *
     * @param tableName
     */
    protected void setName(String tableName) {
        if (nickName == null) {
            nickName = tableName;
        }
        this.tableName = tableName;
    }

    /**
     * 获取实体名
     *
     * @see #getNickName()
     * @return 昵称名
     */
    public String getName() {
        return tableName;
    }

    /**
     *  获取昵称名
     * @return 昵称名
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * <pre>
     * 设置表昵称
     * 虚表并无对应表实体昵称咱做定位使用
     * @see #getName()  同此
     * </pre>
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     *
     * @param name
     * @param value
     */
    public void setPrimeKey(String name, String value) {
        this.primeKey = new FieldItem(name, Types.VARCHAR, ZObject.conv(value));
    }

    /**
     *
     * @return
     */
    public FieldItem getPrimeKey() {
        return primeKey;
    }
}
