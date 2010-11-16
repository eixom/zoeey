/*
 * MoXie (SysTem128@GMail.Com) 2009-5-24 23:20:16
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.zdbcontrol;

import org.zoeey.zdo.zdbcontrol.ParamentNamedParser;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.MemoryMeasurer;
import org.zoeey.util.TimeMeasurer;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ParamentNamedParserTest extends TestCase {

    /**
     *
     */
    public ParamentNamedParserTest() {
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
     * Test of indexListOf method, of class StandardParamsParser.
     */
    @Test
    public void testIndexListOf() {
        System.out.println("indexListOf");
        ParamentNamedParser parser = new ParamentNamedParser();
        parser.convert("select * from article where id =:id ");
        List<String> sqlList = parser.listSql();
        assertEquals(sqlList.toString(), "[select * from article where id =, ?,  ]");
        assertEquals(parser.listIndexOf("id").toString(), "[1]");
        assertEquals(parser.getNormalSql(), "select * from article where id =? ");
        /**
         *
         */
        parser.convert("select * from article where id = :id and title like '%:title%'");
        sqlList = parser.listSql();
        assertEquals(sqlList.toString(), "[select * from article where id = , ?,  " +
                "and title like '%, ?, %']");
        assertEquals(parser.listIndexOf("id").toString(), "[1]");
        assertEquals(parser.listIndexOf("title").toString(), "[2]");
        assertEquals(parser.getNormalSql(), "select * from article where id = ? and title like '%?%'");
        /**
         * 容错性测试
         */
        parser.convert("select * from article where id = :id and title like '%::title:%' :$ :title :id :$");
        sqlList = parser.listSql();
        assertEquals(sqlList.toString(), "[select * from article where id = , ?,  and title like '%:, ?, :%' , ?,  , ?,  , ?,  , ?, ]");
        assertEquals(parser.listIndexOf("id").toString(), "[1, 5]");
        assertEquals(parser.listIndexOf("title").toString(), "[2, 4]");
        assertEquals(parser.listIndexOf("$").toString(), "[3, 6]");
        /**
         * 转译测试
         */
        parser.convert("\\select * from article where id = '\\:id' " +
                "and title like '%?\\??%'  \\:title :title :id\\");
        sqlList = parser.listSql();
        assertEquals(sqlList.toString(), "[\\select * from article " +
                "where id = '\\:id' and title like '%?\\??%'  \\:title , ?,  , ?, \\]");
        assertEquals(parser.listIndexOf("id").toString(), "[2]");
        assertEquals(parser.listIndexOf("title").toString(), "[1]");

        /**
         * 效率测试
         */
//        MemoryMeasurer mm = new MemoryMeasurer();
//        TimeMeasurer tm = new TimeMeasurer();
//        mm.start();
//        tm.start();
//        for (int i = 0; i < 10000; i++) {
//           parser.convert("select * from article where id = :id and title like '%::title:%' :$ :title :id :$");
//            sqlList = parser.listSql();
//            parser.listIndexOf("id");
//            parser.listIndexOf("title");
//            parser.listIndexOf("$").toString();
//        }
//        mm.stop();
//        tm.stop();
//        System.out.print(mm.spend());
//        System.out.print("b ");
//        System.out.print(tm.spend());
//        System.out.println("ms");
        /**
         * 10，000
         * 509856b 106ms
         * 489896b 100ms
         * 489920b 135ms
         * 100,000
         * 163848b 908ms
         * 163848b 1046ms
         * 163888b 885ms
         */
    }
}
