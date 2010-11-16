/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.zdbcontrol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface ZDBControlAble {

    /**
     * 获取数据库链接
     * @return
     */
    public Connection getConnection();

    /**
     *
     * @param psql
     * @throws SQLException
     */
    public void prepareStatement(String psql) throws SQLException;

    /**
     *
     * @return
     */
    public PreparedStatement getPreparedStatement();

    /**
     *
     * @param savepoint
     * @throws SQLException
     */
    public void releaseSavepoint(Savepoint savepoint) throws SQLException;

    /**
     *
     * @throws SQLException
     */
    public void rollback() throws SQLException;

    /**
     *
     * @param savepoint
     * @throws SQLException
     */
    public void rollback(Savepoint savepoint) throws SQLException;

    /**
     *
     * @throws SQLException
     */
    public void addBatch() throws SQLException;

    /**
     *
     * @throws SQLException
     */
    public void autoCommit() throws SQLException;

    /**
     *
     * @throws SQLException
     */
    public void beginTransaction() throws SQLException;

    /**
     *
     * @throws SQLException
     */
    public void clearBatch() throws SQLException;

    /**
     *
     * @throws SQLException
     */
    public void closePreparedStatement() throws SQLException;

    /**
     *
     * @return 
     * @throws SQLException
     */
    public boolean isPreparedStatement() throws SQLException;

    /**
     *
     * @throws SQLException
     */
    public void close() throws SQLException;

    /**
     *
     * @return
     * @throws SQLException
     */
    public boolean isClosed() throws SQLException;

    /**
     *
     * @throws SQLException
     */
    public void commit() throws SQLException;

    /**
     *
     * @return
     * @throws SQLException
     */
    public Statement createStatement() throws SQLException;

    /**
     *
     * @return
     * @throws SQLException
     */
    public boolean execute() throws SQLException;

    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public boolean execute(String sql) throws SQLException;

    /**
     *
     * @throws SQLException
     */
    public void executeBatch() throws SQLException;

    /**
     *
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery() throws SQLException;
    /**
     *
     * @return
     * @throws SQLException
     */
    public ResultSet getGeneratedKeys() throws SQLException;

    /**
     *
     * @return
     * @throws SQLException
     */
    public int executeUpdate() throws SQLException;

    /**
     *
     * @return
     */
    public String getUniqueKey();
}
