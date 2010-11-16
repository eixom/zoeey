/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import org.zoeey.zdo.zdbcontrol.ZDBControlNamed;
import org.zoeey.zdo.zdbcontrol.ZDBControlNamedAble;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZDOMySQL implements ZDOAble {

    /**
     * 链接
     */
    private Connection conn = null;
    private ZDBControlNamedAble zdb = null;
    /**
     * 生成的SQL语句
     */
    private String lastSql = null;
    private String lastConnect = null;
    /**
     * 当前装载的表
     */
    private TableItemAble table = null;
    private SQLHelper helper = null;

    /**
     * 初始化conn
     * @param conn
     */
    public ZDOMySQL(Connection conn) {
        this.conn = conn;
        helper = new SQLHelper();
        zdb = new ZDBControlNamed(conn);
        helper.setQuoter('`');
    }

    /**
     *
     * @param query
     * @return
     */
    public ZDOAble contact(String query) {
        return this;
    }

    /**
     *
     * @param table
     * @return
     */
    public ZDOAble bindParams(TableItemAble table) {
        return this;
    }

    /**
     *
     * @param table
     * @return
     */
    public ZDOAble contactTable(TableItemAble table) {
        this.table = table;
        return this;
    }

    /**
     *
     * @param tables
     * @return
     */
    public ZDOAble contactTables(TableItemAble[] tables) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 自动生成语句
     * @return
     */
    private ZDOAble autoTpl(FieldItem[] entrys) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * <pre>
     * 使用模板创建查询
     * 例如： SELECT `id`,`title`,`content` FROM `article` WHERE `id` = :id
     * id为参数
     * 重复绑定参数无效
     * </pre>
     *
     * @param sqlTpl
     * @return
     */
    public ZDOAble useTpl(String sqlTpl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return
     */
    public String getLastSql() {
        return lastSql;
    }

    /**
     *
     * @param prefix
     * @return
     * @throws SQLException
     */
    public int insert(String prefix) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int insert() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String lastInsertId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResultSet read() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResultSet readList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int del() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
