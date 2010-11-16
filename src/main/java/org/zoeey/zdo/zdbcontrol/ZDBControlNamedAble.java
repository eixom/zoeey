/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.zdbcontrol;

import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface ZDBControlNamedAble extends ZDBControlAble {

    /**
     * 获取命名的参数列表
     * @return
     */
    public List<String> getParamList();

    /**
     * 设置参数
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setString(String param, String val) throws SQLException;

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setByte(String param, byte val) throws SQLException;

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setClob(String param, Clob val) throws SQLException;

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setDate(String param, Date val) throws SQLException;

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setDouble(String param, double val) throws SQLException;

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setFloat(String param, float val) throws SQLException;

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setInt(String param, int val) throws SQLException;

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setLong(String param, long val) throws SQLException;

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setObject(String param, Object val) throws SQLException;

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setJavaObject(String param, Object val) throws SQLException;

    /**
     *
     * @param param
     * @param val
     * @throws SQLException
     */
    public void setShort(String param, short val) throws SQLException;
}
