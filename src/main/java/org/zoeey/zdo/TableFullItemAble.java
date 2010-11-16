/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

/**
 * 完整表单元
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface TableFullItemAble extends TableItemAble {

    /**
     * 新增
     * @return SQL语句
     */
    public String create();

    /**
     * 新增单元自增主键值
     * @return SQL语句
     */
    public String lastInsertId();

    /**
     * 读取
     * @return SQL语句
     */
    public String read();

    /**
     * 读取
     * @return SQL语句
     */
    public String readList();

    /**
     *  修改
     * @return SQL语句
     */
    public String update();

    /**
     * 删除
     * @return SQL语句
     */
    public String del();
}
