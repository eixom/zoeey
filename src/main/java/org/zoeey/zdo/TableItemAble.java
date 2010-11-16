/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

/**
 * 基础表单元
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface TableItemAble {

    /**
     *
     * @return
     */
    public FieldItem[] getFields();

    /**
     *
     * @return
     */
    public int getFieldSize();

    /**
     *
     * @return
     */
    public String getName();

    /**
     *
     * @return
     */
    public String getNickName();

    /**
     *
     * @param nickName
     */
    public void setNickName(String nickName);

    /**
     *
     * @return
     */
    public FieldItem getPrimeKey();

    /**
     *
     * @param name
     * @param value
     */
    public void setPrimeKey(String name, String value);
}
