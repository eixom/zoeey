/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
/**
 * Change log:
 *     2009-06-03 - At version.57+ 参数分析方式从正则改变为Parament系列分析类。
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
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZDBControlMixed
        implements ZDBControlMixedAble {

    private Connection conn;
    private PreparedStatement pstmt;
    ParamentMixedParserAble parser;
    private boolean isUniqueId = false;
    private Map<Object, Object> uniqueKeyMap = null;

    /**
     *
     * @param conn
     */
    public ZDBControlMixed(Connection conn) {
        this(conn, null);
    }

    /**
     * 制造缓存键
     * @param conn
     * @param cacheKey
     */
    public ZDBControlMixed(Connection conn, String cacheKey) {
        this.conn = conn;
        this.isUniqueId = cacheKey == null ? false : true;
        this.pstmt = null;
        if (isUniqueId) {
            uniqueKeyMap = new HashMap<Object, Object>();
            uniqueKeyMap.put(cacheKey, cacheKey);
        }
    }

    /**
     * 初始化SQL
     * @param sql
     * @see ZDBControlAble ZDB数据库操作器接口
     */
    private void parseSql(String psql) {
        parser = new ParamentMixedParser();
        parser.convert(psql);
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
     * 由查询语句和查询参数制取的标识
     * @return  查询标识
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
     * 为问号绑定参数
     * @param index
     * @param val
     * @param type
     * @throws SQLException
     */
    public void bindValue(int index, Object val, Integer type) throws SQLException {
        updateUniqueKey(index, val);
        List<Integer> indexList = parser.listIndexOf(index);
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
        for (int _index : indexList) {
            pstmt.setObject(_index, val, type);
        }
    }

    /**
     * 绑定参数
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setString(int idx, String val) throws SQLException {
        bindValue(idx, val, Types.VARCHAR);
    }

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setInt(int idx, int val) throws SQLException {
        bindValue(idx, Integer.valueOf(val), Types.INTEGER);

    }

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setDate(int idx, Date val) throws SQLException {
        bindValue(idx, val, Types.DATE);

    }

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setObject(int idx, Object val) throws SQLException {
        bindValue(idx, val, null);
    }

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setJavaObject(int idx, Object val) throws SQLException {
        bindValue(idx, val, Types.JAVA_OBJECT);
    }

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setByte(int idx, byte val) throws SQLException {
        bindValue(idx, Byte.valueOf(val), Types.BINARY);
    }

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setShort(int idx, short val) throws SQLException {
        bindValue(idx, Short.valueOf(val), Types.SMALLINT);
    }

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setClob(int idx, Clob val) throws SQLException {
        bindValue(idx, val, Types.CLOB);
    }

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setFloat(int idx, float val) throws SQLException {
        bindValue(idx, Float.valueOf(val), Types.FLOAT);
    }

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setDouble(int idx, double val) throws SQLException {
        bindValue(idx, Double.valueOf(val), Types.DOUBLE);
    }

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    public void setLong(int idx, long val) throws SQLException {
        bindValue(idx, Long.valueOf(val), Types.BIGINT);
    }

    /**
     *
     * @return
     */
    public PreparedStatement getPreparedStatement() {
        return pstmt;
    }

    /**
     * 与Named 相同的部分
     */
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
        updateUniqueKey(psql, psql);
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
        updateUniqueKey(sql, sql);
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
    public void closePreparedStatement() throws SQLException {
        if (pstmt != null) {
            pstmt.close();
            pstmt = null;
        }
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public boolean isPreparedStatement() throws SQLException {
        if (pstmt == null //|| pstmt.isClosed() // jdk 1.6
                ) {
            return false;
        }
        return true;
    }

    /**
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        closePreparedStatement();
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

    public boolean isClosed() throws SQLException {
        return conn.isClosed();
    }
}
