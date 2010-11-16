/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import java.sql.ResultSet;

/**
 *
 * CRUD
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface ZDOAble {

    /**
     * 新增
     * @return 新增数量
     * @throws Exception
     */
    public int insert() throws Exception;

    /**
     * 新增单元自增主键值
     * @return
     */
    public String lastInsertId();

    /**
     * 读取单数据
     * @return 数据集
     * @throws Exception
     */
    public ResultSet read() throws Exception;

    /**
     * 读取集
     * 注意这里没有给出设置起始列和结束列
     * @return 数据集
     * @throws Exception
     */
    public ResultSet readList() throws Exception;

    /**
     *  修改
     * @return 影响列数
     * @throws Exception
     */
    public int update() throws Exception;

    /**
     * 删除
     * @return 影响列数
     * @throws Exception
     */
    public int del() throws Exception;

    /**
     *
     * @param table
     * @return
     */
    public ZDOAble contactTable(TableItemAble table);

}
