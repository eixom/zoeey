/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import org.zoeey.zdo.zdbcontrol.ZDBControlNamedAble;
import org.zoeey.zdo.zdbcontrol.ZDBControlNamed;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 用于生成模拟数据测试Control
 * @author MoXie(SysTem128@GMail.Com)
 */
public class DemoData {

    private Connection conn = null;

    /**
     *
     * @throws SQLException
     */
    public DemoData() throws SQLException {
        this("");
    }
    ZDBControlNamedAble zdb = null;

    /**
     *
     * @param name
     * @throws SQLException
     */
    public DemoData(String name) throws SQLException {
        org.h2.Driver.load();
        conn = DriverManager.getConnection("jdbc:h2:mem:demo_" + name, "root", "");
        zdb = new ZDBControlNamed(conn);
        // <editor-fold defaultstate="collapsed" desc="生成模拟数据。">
        boolean isDemo = true;
        zdb.autoCommit();
        try {
//            statemt.execute("DROP TABLE T_Article "); //
            zdb.execute("DROP TABLE T_Article "); //
        } catch (SQLException ex) {
        }
        try {
//            statemt.execute("DROP SEQUENCE ARTICLE_SEQ_ID ");//
            zdb.execute("DROP SEQUENCE ARTICLE_SEQ_ID ");//
        } catch (SQLException ex) {
        }
//        statemt.execute("CREATE TABLE T_Article(id INT PRIMARY KEY,title VARCHAR(255),content VARCHAR(2200)) "); // 建表
//        statemt.execute("CREATE SEQUENCE ARTICLE_SEQ_ID ");//
        zdb.execute("CREATE TABLE T_Article(id INT PRIMARY KEY," +
                "title VARCHAR(255),content VARCHAR(2200),editTime int(11)) "); // 建表
        zdb.execute("CREATE SEQUENCE ARTICLE_SEQ_ID ");//

        if (isDemo) {
//            String sql = "insert into T_Article (id,title,content) values (NEXT VALUE FOR ARTICLE_SEQ_ID,?,?)"; //
            String sql = "insert into T_Article (id,title,content) " +
                    "values (NEXT VALUE FOR ARTICLE_SEQ_ID,:title, :content )"; //
//            statem = conn.prepareStatement(sql);
            zdb.prepareStatement(sql);
            String content = "内容_";
            for (int i = 0; i < 3; i++) {
                content += content;
            }

            for (int idx = 1; idx < 500; idx++) {
//                zdb.setString(1, "demo 标题_" + idx);
//                zdb.setString(2, "demo 内容_" + content);
                zdb.setString("title", "demo 标题_" + idx);
                zdb.setString(":content", "demo 内容_" + content);
                zdb.addBatch();
            }
            zdb.executeBatch();
        }
    //</editor-fold>
    }

    /**
     *
     * @return
     */
    public Connection getConn() {
        return conn;
    }

    /**
     *
     * @return
     */
    public ZDBControlNamedAble getZDB() {
        return zdb;
    }

    /**
     *
     */
    public void destory() {
        try {
            zdb.execute("DROP TABLE T_Article");
            zdb.execute("DROP SEQUENCE ARTICLE_SEQ_ID ");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
