/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
/**
 * Change log:
 *     2009-06-02 - At version.57+ 参数分析方式从正则改变为Parament系列分析类。
 */
package org.zoeey.zdo.zdbcontrol;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zoeey.util.EncryptHelper;
import org.zoeey.util.JsonHelper;

/**
 * <pre>
 * ZDB 标准操作组件。
 * 可作为数据库操作辅助工具。
 * 只可以使用参数名形式进行值绑定
 *  参数名：
 *    由冒号":"起始，可使用字符数字和$等符合Java变量命名规定的字符。
 *    可以由纯数字组成，但不建议这样做。
 *    select title,content from t_article where id = :id
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZDBControlNamed
        implements ZDBControlNamedAble {

    private Connection conn;
    private PreparedStatement pstmt;
    ParamentNamedParserAble parser;
    private boolean isUniqueId = false;
    private Map uniqueKeyMap = null;

    /**
     * 命名参数形式的数据库操作工具
     * @param conn
     */
    public ZDBControlNamed(Connection conn) {
        this(conn, false);
    }

    /**
     * <pre>
     * 命名参数形式的数据库操作工具，并可与SQL语句和参数有关的唯一键。
     * 唯一键可当做数据缓存键。
     * </pre>
     * @param conn
     * @param isUniqueId 是否制取唯一键
     */
    public ZDBControlNamed(Connection conn, boolean isUniqueId) {
        this.conn = conn;
        this.isUniqueId = isUniqueId;
        this.pstmt = null;
        if (isUniqueId) {
            uniqueKeyMap = new HashMap();
        }
    }

    /**
     * 初始化SQL
     * @param sql
     * @see ZDBControlAble ZDB数据库操作器接口
     */
    private void parseSql(String psql) {
        parser = new ParamentNamedParser();
        parser.convert(psql);
        updateUniqueKey(0, psql);
    }

    /**
     * 获取命名的参数列表
     * @return
     */
    public List<String> getParamList() {
        return parser.getParamList();
    }

    private String getSql() {
        return parser.getNormalSql();
    }

    private void updateUniqueKey(Object key, Object val) {
        if (!isUniqueId) {
            return;
        }
        if (uniqueKeyMap != null) {
            uniqueKeyMap.put(key, val);
        }
    }

    /**
     *
     * @return
     */
    public String getUniqueKey() {
        return EncryptHelper.md5(JsonHelper.encode(uniqueKeyMap));
    }

    /**
     *
     * @param param
     * @param val
     * @param type
     * @throws SQLException
     */
    public void bindValue(String param, Object val, Integer type) throws SQLException {
        if (param.charAt(0) == ':') {
            param = param.substring(1);
        }
        updateUniqueKey(param, val);
        List<Integer> indexList = parser.listIndexOf(param);
        int size = indexList.size();
        if (size == 0) {
            return;
        }
        if (type == null) {
            pstmt.setObject(indexList.get(0), val);
            return;
        }
        if (size == 1) {
            pstmt.setObject(indexList.get(0), val, type);
            return;
        }
        for (int index : indexList) {
            pstmt.setObject(index, val, type);
        }
    }

    /**
     *
     * @return
     */
    public PreparedStatement getPreparedStatement() {
        return pstmt;
    }

    /**
     * 获取数据库链接
     * @return
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     *
     * @throws SQLException
     */
    public void beginTransaction() throws SQLException {
        conn.setAutoCommit(false);
    }

    /**
     *
     * @throws SQLException
     */
    public void autoCommit() throws SQLException {
        conn.setAutoCommit(true);
    }

    /**
     *
     * @param savepoint
     * @throws SQLException
     */
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        conn.releaseSavepoint(savepoint);
    }

    /**
     *
     * @throws SQLException
     */
    public void commit() throws SQLException {
        conn.commit();
    }

    /**
     *
     * @throws SQLException
     */
    public void rollback() throws SQLException {
        conn.rollback();
    }

    /**
     *
     * @param savepoint
     * @throws SQLException
     */
    public void rollback(Savepoint savepoint) throws SQLException {
        conn.rollback(savepoint);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public Statement createStatement() throws SQLException {
        return conn.createStatement();
    }

    /**
     *
     * @param psql
     * @throws SQLException
     */
    public void prepareStatement(String psql) throws SQLException {
        parseSql(psql);
        pstmt = conn.prepareStatement(getSql());
    }

    /**
     *
     * @throws SQLException
     */
    public void addBatch() throws SQLException {
        pstmt.addBatch();
    }

    /**
     *
     * @throws SQLException
     */
    public void executeBatch() throws SQLException {
        pstmt.executeBatch();
    }

    /**
     *
     * @throws SQLException
     */
    public void clearBatch() throws SQLException {
        pstmt.clearBatch();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery() throws SQLException {
        return pstmt.executeQuery();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public ResultSet getGeneratedKeys() throws SQLException {
        return pstmt.getGeneratedKeys();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public int executeUpdate() throws SQLException {
        return pstmt.executeUpdate();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public boolean execute() throws SQLException {
        return pstmt.execute();
    }

    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public boolean execute(String sql) throws SQLException {
        return conn.prepareStatement(sql).execute();
    }

    /**
     * 设置参数
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setString(String param, String val) throws SQLException {
        bindValue(param, val, Types.VARCHAR);
    }

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setInt(String param, int val) throws SQLException {
        bindValue(param, Integer.valueOf(val), Types.INTEGER);

    }

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setDate(String param, Date val) throws SQLException {
        bindValue(param, val, Types.DATE);

    }

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setObject(String param, Object val) throws SQLException {
        bindValue(param, val, null);
    }

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setJavaObject(String param, Object val) throws SQLException {
        bindValue(param, val, Types.JAVA_OBJECT);
    }

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setByte(String param, byte val) throws SQLException {
        bindValue(param, Byte.valueOf(val), Types.BINARY);
    }

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setShort(String param, short val) throws SQLException {
        bindValue(param, Short.valueOf(val), Types.SMALLINT);
    }

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setClob(String param, Clob val) throws SQLException {
        bindValue(param, val, Types.CLOB);
    }

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setFloat(String param, float val) throws SQLException {
        bindValue(param, Float.valueOf(val), Types.FLOAT);
    }

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setDouble(String param, double val) throws SQLException {
        bindValue(param, Double.valueOf(val), Types.DOUBLE);
    }

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setLong(String param, long val) throws SQLException {
        bindValue(param, Long.valueOf(val), Types.BIGINT);
    }

    /**
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        if (pstmt != null) {
            pstmt.close();
            pstmt = null;
        }
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

    public void closePreparedStatement() throws SQLException {
        if (pstmt != null) {
            pstmt.close();
            pstmt = null;
        }
    }

    public boolean isPreparedStatement() throws SQLException {
        if (pstmt == null // || pstmt.isClosed() // jdk 1.6
                ) {
            return false;
        }
        return true;
    }

    public boolean isClosed() throws SQLException {
        return conn.isClosed();
    }
}
