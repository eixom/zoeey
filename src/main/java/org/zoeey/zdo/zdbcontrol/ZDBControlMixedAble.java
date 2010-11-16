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

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface ZDBControlMixedAble extends ZDBControlNamedAble {

    /**
     * 
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setString(int idx, String val) throws SQLException;

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setByte(int idx, byte val) throws SQLException;

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setClob(int idx, Clob val) throws SQLException;

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setDate(int idx, Date val) throws SQLException;

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setDouble(int idx, double val) throws SQLException;

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setFloat(int idx, float val) throws SQLException;

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setInt(int idx, int val) throws SQLException;

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setLong(int idx, long val) throws SQLException;

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setObject(int idx, Object val) throws SQLException;

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setJavaObject(int idx, Object val) throws SQLException;

    /**
     *
     * @param idx
     * @param val
     * @throws SQLException
     */
    void setShort(int idx, short val) throws SQLException;
 
}
