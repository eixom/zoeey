/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.zdbcontrol;

import org.zoeey.zdo.zdbcontrol.ZDBControlNamedAble;
import org.zoeey.zdo.zdbcontrol.ZDBControlNamed;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import junit.framework.TestCase;
import org.zoeey.util.ArrayHelper;
import org.zoeey.util.JsonHelper;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZDBControlNamedTest extends TestCase {

    /**
     *
     * @param testName
     */
    public ZDBControlNamedTest(String testName) {
        super(testName);
    }

    /**
     * Test of setString method, of class ZDBControlStandard.
     */
    public void testSetString() {
        String sql = "select * "
                + "from article "
                + "where 1=1 "
                + "and title like  concat('%',concat(:title,'%')) " +//
                "and content like  '%:content%' " +//
                "and content like  '%\\:content2%' " +//
                "and id < :id " +//
                "and id_2 > :id "//
                ;
        Pattern sqlPattern = Pattern.compile("[^\\\\]:([a-zA-Z_0-9]+)");
        Matcher matcher = sqlPattern.matcher(sql);
        String[] result = new String[10];
        int i = 0;
        while (matcher.find()) {
            result[i++] = matcher.group(1);
        }
        String expResult = "select * "
                + "from article "
                + "where 1=1 "
                + "and title like  concat('%',concat(?,'%')) "
                + "and content like  '%?%' "
                + "and content like  '%\\:content2%' " + // Bug '%\\\\:content%' fix
                "and id < ? and id_2 > ? ";
        assertEquals(expResult, sql.replaceAll("([^\\\\]):[a-zA-Z_0-9]+", "$1?"));
        String[] expResult_1 = new String[]{"title", "content", "id", "id"};
        assertEquals(JsonHelper.encode(expResult_1), JsonHelper.encode(ArrayHelper.copyOf(result, i)));
    }

    /**
     *
     */
    public void testArras() {
        String param = "title";
        int cond = 2;
        for (int j = 0; j < cond; j++) {
            List uniqueKeys = new ArrayList();
            if (j == 0) {
                uniqueKeys.add("title");
                uniqueKeys.add("id");
                uniqueKeys.add("title");
                uniqueKeys.add("content");
            }
            int[] resultIdx = null;
            int[] indexs = new int[uniqueKeys.size()];
            Iterator iterator = uniqueKeys.iterator();
            int idx = 0;
            int i = 0;
            while (iterator.hasNext()) {

                if (param.equals(iterator.next())) {
                    indexs[i++] = idx;
                }
                idx++;
            }
            resultIdx = ArrayHelper.copyOf(indexs, i);
            if (resultIdx == null || resultIdx.length == 0) {
//            fail();
            }
            int[] expResult = null;
            if (j == 0) {
                expResult = new int[]{0, 2};
            } else if (j == 1) {
                expResult = new int[]{};
            }
            assertEquals(Arrays.toString(expResult), Arrays.toString(ArrayHelper.copyOf(resultIdx, i)));
        }

    }

    /**
     *
     * @throws Exception
     */
    public void testAddTitle() throws Exception {
        org.h2.Driver.load();
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:bookStore", "root", "");
        ZDBControlNamedAble zdbControl = new ZDBControlNamed(conn);
        // <editor-fold defaultstate="collapsed" desc="生成模拟数据。">
        boolean isDemo = true;
        boolean isExecuted = true;
        zdbControl.autoCommit();
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
        zdbControl.execute("CREATE TABLE T_Article(id INT PRIMARY KEY,"
                + "title VARCHAR(255),content VARCHAR(2200)) "); // 建表
        zdbControl.execute("CREATE SEQUENCE ARTICLE_SEQ_ID ");//

        if (isDemo) {
//            String sql = "insert into T_Article (id,title,content) values (NEXT VALUE FOR ARTICLE_SEQ_ID,?,?)"; // 
            String sql = "insert into T_Article (id,title,content) "
                    + "values (NEXT VALUE FOR ARTICLE_SEQ_ID,:title, :content )"; //
//            statem = conn.prepareStatement(sql);
            zdbControl.prepareStatement(sql);
            String content = "内容_";
            for (int i = 0; i < 3; i++) {
                content += content;
            }

            for (int idx = 1; idx < 500; idx++) {
//                zdb.setString(1, "demo 标题_" + idx);
//                zdb.setString(2, "demo 内容_" + content);
                zdbControl.setString("title", "demo 标题_" + idx);
                zdbControl.setString(":content", "demo 内容_" + content);
                zdbControl.addBatch();
            }
            zdbControl.executeBatch();
        }
        //</editor-fold>

        String search = "10";
        StringBuffer sqlBuffer = new StringBuffer("select id,title,content from T_Article ");
        if (search != null) {
            sqlBuffer.append("WHERE 1=1 "
                    + "and (title LIKE CONCAT(CONCAT('%',:title_forSearch),'%')  OR content LIKE CONCAT(CONCAT('%',:title_forSearch),'%')  ) ");
        }
        sqlBuffer.append("limit 2 OFFSET :rowStart ");
        zdbControl.prepareStatement(sqlBuffer.toString());
        /**
         * 单位置 前缀：非必须
         */
        zdbControl.setInt(":rowStart", 0);
        /**
         * exception test
         */
        if (false) {
            zdbControl.setInt("blah", 0);
        }
        if (false) {
            zdbControl.setInt("bla space h", 0);
        }
        if (search != null) {
            /**
             * 注意此参数的位置和sql语句中其出现的顺序同，依然可以执行
             * 多位置
             */
            zdbControl.setString("title_forSearch", search);
        }
        assertEquals(zdbControl.getUniqueKey(), "37a6259cc0c1dae299a7866489dff0bd");
        ResultSet rs = zdbControl.executeQuery();
        Map row = null;
        int i = 0;
        String suffix = null;
        while (rs.next()) {
            i++;
            /**
             * 使用时需要不同对象作为行数据
             */
            row = new HashMap(); // 
            row.put("id", rs.getString("id"));
            row.put("title", rs.getString("title"));
            row.put("content", rs.getString("content"));
            suffix = String.valueOf((int) Math.pow(10, i));
//            assertEquals("{content=demo 内容_内容_内容_内容_内容_内容_内容_内容_内容_, id=" + //
//                    suffix + ", title=demo 标题_" + suffix + "}", row.toString());
        }
        /**
         *  语句型参数绑定
         */
//        ParamentNamedParserAble parser = new ParamentNamedParser();
//        parser.convert("select id,title,content from T_Article WHERE id in (:ids) and id < :id ");
//        parser.bindSQL("ids", "1,2,3");
//        zdbControl.prepareStatement(parser);
//        zdbControl.setInt("id", 3);
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
//        assertEquals(i, 2);
//        assertEquals(row.toString(), "{content=demo 内容_内容_内容_内容_内容_内容_内容_内容_内容_, id=2, title=demo 标题_2}");
        /**
         * 清理数据
         */
        try {
            zdbControl.execute("DROP TABLE T_Article");
            zdbControl.execute("DROP SEQUENCE ARTICLE_SEQ_ID ");
        } catch (SQLException ex) {
            fail("execute 语句错误！");
        }
        /**
         * 关闭连接
         */
        zdbControl.close();
        conn.close();
    }
}
