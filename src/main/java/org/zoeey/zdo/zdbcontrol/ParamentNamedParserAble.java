/*
 * MoXie (SysTem128@GMail.Com) 2009-5-24 22:26:51
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.zdbcontrol;

import java.util.List;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface ParamentNamedParserAble {

    /**
     * 分析
     * @param sql
     */
    public void convert(String sql);

    /**
     * Sql 与 参数占位符 的分离片段
     * @return
     */
    public List<String> listSql();

    /**
     * 
     * @param str
     * @return
     */
    public List<Integer> listIndexOf(String str);

    /**
     * 获取一般的SQL
     * @return
     */
    public String getNormalSql();

    /**
     * 绑定SQL语句
     * @param str
     * @param sql
     * @return 绑定是否成功
     */
    public boolean bindSQL(String str, String sql);

    /**
     * 获取命名的参数列表
     * @return
     */
    public List<String> getParamList();
}
