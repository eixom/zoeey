/*
 * MoXie (SysTem128@GMail.Com) 2009-5-25 2:27:07
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.zdbcontrol;

import org.zoeey.zdo.zdbcontrol.ParamentMixedParser;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.MemoryMeasurer;
import org.zoeey.util.TimeMeasurer;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ParamentMixedParserTest {

    /**
     *
     */
    public ParamentMixedParserTest() {
    }

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of convert method, of class ParamentMixedParser.
     */
    @Test
    public void testConvert() {
        System.out.println("indexListOf");
        ParamentMixedParser parser = new ParamentMixedParser();
        parser.convert("select * from article where id = :id and title = ?");
        List<String> sqlList = parser.listSql();
        assertEquals(sqlList.toString(), "[select * from article where id = , ?" +
                ",  and title = , ?, ]");
        assertEquals(parser.listIndexOf("id").toString(), "[1]");
        assertEquals(parser.listIndexOf(1).toString(), "[2]");
        assertEquals(parser.getNormalSql(), "select * from article where id = ? " +
                "and title = ?");
        /**
         * 
         */
        parser.convert("select * from article where id = :id " +
                "and title like '%:title%' and name like '%?%?'");
        sqlList = parser.listSql();
        assertEquals(sqlList.toString(), "[select * from article where id = , ?" +
                ",  and title like '%, ?, %' and name like '%, ?, %, ?, ']");
        assertEquals(parser.listIndexOf("id").toString(), "[1]");
        assertEquals(parser.listIndexOf("title").toString(), "[2]");
        assertEquals(parser.listIndexOf(1).toString(), "[3]");
        assertEquals(parser.listIndexOf(2).toString(), "[4]");
        assertEquals(parser.getNormalSql(), "select * from article where id = ? " +
                "and title like '%?%' and name like '%?%?'");
        /**
         * 容错性测试
         */
        parser.convert("select * from article where ?  id= :id " +
                "and title like '%::title:%' :$ :title :id :$");
        sqlList = parser.listSql();
        assertEquals(sqlList.toString(), "[select * from article where , ?" +
                ",   id= , ?,  and title like '%:, ?, :%' , ?,  , ?,  , ?,  , ?, ]");
        assertEquals(parser.listIndexOf("id").toString(), "[2, 6]");
        assertEquals(parser.listIndexOf("title").toString(), "[3, 5]");
        assertEquals(parser.listIndexOf("$").toString(), "[4, 7]");
        assertEquals(parser.listIndexOf(1).toString(), "[1]");
        assertEquals(parser.getNormalSql(), "select * from article " +
                "where ?  id= ? and title like '%:?:%' ? ? ? ?");
        /**
         * 转译测试
         */
        parser.convert("\\select * from article where id = '\\:id' and title like '%?\\??%'\\");
        sqlList = parser.listSql();
        assertEquals(sqlList.toString(), "[\\select * from article " +
                "where id = '\\:id' and title like '%, ?, \\?, ?, %'\\]");
        assertEquals(parser.listIndexOf("id").toString(), "[]");
        assertEquals(parser.listIndexOf(1).toString(), "[1]");
        assertEquals(parser.listIndexOf(2).toString(), "[2]");
        /**
         * 效率测试
         */
//        MemoryMeasurer mm = new MemoryMeasurer();
//        TimeMeasurer tm = new TimeMeasurer();
//        mm.start();
//        tm.start();
//        for (int i = 0; i < 100000; i++) {
//            parser.convert("select * from article where id = :id and title like '%::title:%' :$ :title :id :$");
//            sqlList = parser.listSql();
//            parser.listIndexOf("id");
//            parser.listIndexOf("title");
//            parser.listIndexOf("$").toString();
//            parser.listIndexOf(1).toString();
//        }
//        mm.stop();
//        tm.stop();
//        System.out.print(mm.spend());
//        System.out.print("b ");
//        System.out.print(tm.spend());
//        System.out.println("ms");
        /**
         * 10，000
         * 15256b 126ms
         * 13080b 107ms
         * 13080b 108ms
         * 100,000
         * 490880b 1034ms
         * 566224b 977ms
         * 548720b 991ms
         */
    }
}