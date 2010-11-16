/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.zdbcontrol;

import org.zoeey.zdo.zdbcontrol.ZDBControlMixedAble;
import org.zoeey.zdo.zdbcontrol.ZDBControlMixed;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZDBControlMixedTest extends TestCase {

    /**
     *
     * @param testName
     */
    public ZDBControlMixedTest(String testName) {
        super(testName);
    }

    /**
     *
     * @throws Exception
     */
    public void testAddTitle() throws Exception {
        
        if (true) {
            return;
        }
        org.h2.Driver.load();
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:bookStore", "root", "");
        ZDBControlMixedAble zdbControl = new ZDBControlMixed(conn, "cacheMe_");
        // <editor-fold defaultstate="collapsed" desc="生成模拟数据。">
        boolean isDemo = true;
//        Statement statemt = null;
//        PreparedStatement statem = null;
        boolean isExecuted = true;
//        conn.setAutoCommit(true);
        zdbControl.autoCommit();
//        statemt = conn.createStatement();

        try {
//            statemt.execute("DROP TABLE T_Article "); //
            zdbControl.execute("DROP TABLE T_Article "); //
        } catch (SQLException ex) {
        }
        try {
//            statemt.execute("DROP SEQUENCE ARTICLE_SEQ_ID ");//
            zdbControl.execute("DROP SEQUENCE ARTICLE_SEQ_ID ");//
        } catch (SQLException ex) {
        }
//        statemt.execute("CREATE TABLE T_Article(id INT PRIMARY KEY,title VARCHAR(255),content VARCHAR(2200)) "); // 建表
//        statemt.execute("CREATE SEQUENCE ARTICLE_SEQ_ID ");//
        zdbControl.execute("CREATE TABLE T_Article(id INT PRIMARY KEY," +
                "title VARCHAR(255),content VARCHAR(2200)) "); // 建表
        zdbControl.execute("CREATE SEQUENCE ARTICLE_SEQ_ID ");//

        if (isDemo) {
//            String sql = "insert into T_Article (id,title,content) values (NEXT VALUE FOR ARTICLE_SEQ_ID,?,?)"; //
            String sql = "insert into T_Article (id,title,content) " +
                    "values (NEXT VALUE FOR ARTICLE_SEQ_ID,:title, ? )"; //
//            statem = conn.prepareStatement(sql);
            zdbControl.prepareStatement(sql);
            String content = "内容_";
            for (int i = 0; i < 3; i++) {
                content += content;
            }
            for (int idx = 1; idx < 3; idx++) {
//                zdb.setString(1, "demo 标题_" + idx);
//                zdb.setString(2, "demo 内容_" + content);
                zdbControl.setString("title", "demo 标题_" + idx);
                zdbControl.setString(1, "demo 内容_" + content);
                zdbControl.addBatch();
            }
            zdbControl.executeBatch();
        }
        //</editor-fold>

        String search = "10";
        StringBuffer sqlBuffer = new StringBuffer("select id,title,content from T_Article ");
        if (search != null) {
            sqlBuffer.append("WHERE 1=1 " +
                    "and (title LIKE CONCAT(CONCAT('%',:title_forSearch),'%')  OR content LIKE CONCAT(CONCAT('%',:title_forSearch),'%')  )");
        }
        sqlBuffer.append("limit 2 OFFSET ? ");
        zdbControl.prepareStatement(sqlBuffer.toString());
//        zdb.setInt(":rowEnd", 0);    // 单位置 前缀：非必须
        zdbControl.setInt(1, 0);    // 单位置 前缀：非必须
        /**
         * exception test
         */
        if (false) {
            zdbControl.setInt("blah", 0);    // 单位置
        }
        if (false) {
            zdbControl.setInt("bla space h", 0);    // 单位置
        }
        if (search != null) {
            /**
             * 注意此参数的位置和sql语句中其出现的顺序同，依然可以执行
             */
            zdbControl.setString("title_forSearch", search); // 多位置
        }
        assertEquals(zdbControl.getUniqueKey(), "730cfd93800e0939523cf42a7c3238bf");
        ResultSet rs = zdbControl.executeQuery();
        Map row = new HashMap();
        int i = 0;
        String suffix = null;
        while (rs.next()) {
            i++;
//            row = new HashMap(); // 使用时需要不同对象作为行数据
            row.put("id", rs.getString("id"));   // 编号
            row.put("title", rs.getString("title"));    // 标题
            row.put("content", rs.getString("content"));    // 内容
            suffix = String.valueOf((int) Math.pow(10, i));
            assertEquals("{content=demo 内容_内容_内容_内容_内容_内容_内容_内容_内容_, id=" + suffix + ", title=demo 标题_" + suffix + "}", row.toString());
        }
        /**
         *  语句型参数绑定
         */
//        ParamentMixedParserAble parser = new ParamentMixedParser();
//        parser.convert("select id,title,content from T_Article WHERE id in (:ids) and id < :id");
//        parser.bindSQL("ids", "1,2");
////        zdbControl.prepareStatement(parser);
//        zdbControl.setInt("id", 2);
//        rs = zdbControl.executeQuery();
//        i = 0;
//        while (rs.next()) {
//            i++;
//            /**
//             * 使用时需要不同对象作为行数据
//             */
//            row = new HashMap(); //
//            row.put("id", rs.getString("id"));
//            row.put("title", rs.getString("title"));
//            row.put("content", rs.getString("content"));
//        }
//        assertEquals(i, 1);
//        assertEquals(row.toString(), "{content=demo 内容_内容_内容_内容_内容_内容_内容_内容_内容_, id=1, title=demo 标题_1}");
        try {
            zdbControl.execute("DROP TABLE T_Article");
            zdbControl.execute("DROP SEQUENCE ARTICLE_SEQ_ID ");
        } catch (SQLException ex) {
            fail("execute 语句错误！");
        }
        zdbControl.close();
        conn.close();
    }
}
