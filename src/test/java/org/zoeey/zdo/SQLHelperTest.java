/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import org.zoeey.zdo.SQLHelper;
import org.zoeey.zdo.FieldItem;
import java.sql.Types;
import junit.framework.TestCase;
import org.zoeey.common.ZObject;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class SQLHelperTest extends TestCase {

    /**
     *  
     * @param testName
     */
    FieldItem[] fieldItems;

    /**
     *
     * @param testName
     */
    public SQLHelperTest(String testName) {
        super(testName);
        fieldItems = new FieldItem[6];
        fieldItems[0] = new FieldItem("title_0", Types.VARCHAR, new ZObject(1));
        fieldItems[1] = new FieldItem("title_1", Types.VARCHAR, new ZObject(2));
        fieldItems[2] = new FieldItem("title_2", Types.VARCHAR, new ZObject(3));
        fieldItems[3] = new FieldItem("title_3", Types.VARCHAR, new ZObject(4));
        fieldItems[4] = new FieldItem("title_4", Types.VARCHAR, new ZObject(5));
        fieldItems[5] = new FieldItem("title_5", Types.VARCHAR, new ZObject(6));
    }

    /**
     * Test of setQuoter method, of class SQLHelper.
     */
    public void testSetQuoter() {
        System.out.println("setQuoter");
        char quoter = ' ';
        SQLHelper sqlHelper = new SQLHelper();
        sqlHelper.setQuoter(quoter);
    }

    /**
     * Test of removeQuoter method, of class SQLHelper.
     */
    public void testRemoveQuoter() {
        System.out.println("removeQuoter");
        SQLHelper sqlHelper = new SQLHelper();
        sqlHelper.removeQuoter();
    }

    /**
     * Test of joinFields_Comma method, of class SQLHelper.
     * @throws Exception 
     */
    public void testjoinFields_Comma()
            throws Exception {
        System.out.println("joinFields_Comma");
        String[] flipFields = {"title_0", "title_3"};
        SQLHelper sqlHelper = new SQLHelper();
        /**
         * 普通
         */
        String result = sqlHelper.joinFields_Comma(fieldItems);
        assertEquals("`title_0`,`title_1`,`title_2`,`title_3`,`title_4`,`title_5` ", result);
        /**
         * 剔除
         */
        result = sqlHelper.joinFields_Comma(fieldItems, flipFields);
        assertEquals("`title_1`,`title_2`,`title_4`,`title_5` ", result);
        /**
         * 剔除 + 带前缀
         */
        sqlHelper.setPrefix("article");
        result = sqlHelper.joinFields_Comma(fieldItems, flipFields);
        assertEquals("article.`title_1`,article.`title_2`,article.`title_4`,article.`title_5` ", result);
    }

    /**
     * Test of joinFields_ColonComma method, of class SQLHelper.
     * @
     */
    public void testjoinFields_ColonComma()  {
        System.out.println("joinFields_ColonComma");
        String[] flipFields = {"title_0", "title_3"};
        SQLHelper sqlHelper = new SQLHelper();
        /**
         * 普通
         */
        String result = sqlHelper.joinFields_ColonComma(fieldItems);
        assertEquals(":title_0,:title_1,:title_2,:title_3,:title_4,:title_5 ", result);
        /**
         * 剔除
         */
        result = sqlHelper.joinFields_ColonComma(fieldItems, flipFields);
        assertEquals(":title_1,:title_2,:title_4,:title_5 ", result);
        /**
         * 剔除 + 带前缀
         */
        sqlHelper.setPrefix("article");
        result = sqlHelper.joinFields_ColonComma(fieldItems, flipFields);
        assertEquals(":title_1,:title_2,:title_4,:title_5 ", result);
    }

    /**
     * Test of joinFields_And method, of class SQLHelper.
     * @
     */
    public void testjoinFields_And()  {
        System.out.println("joinFields_And");
        String[] flipFields = {"title_0", "title_3"};
        SQLHelper sqlHelper = new SQLHelper();
        /**
         * 普通
         */
        String result = sqlHelper.joinFields_And(fieldItems);
        assertEquals(//
                "AND `title_0` = :title_0 " +
                "AND `title_1` = :title_1 " +
                "AND `title_2` = :title_2 " +
                "AND `title_3` = :title_3 " +
                "AND `title_4` = :title_4 " +
                "AND `title_5` = :title_5 ", result);
        /**
         * 剔除
         */
        result = sqlHelper.joinFields_And(fieldItems, flipFields);

        assertEquals(//
                "AND `title_1` = :title_1 " +
                "AND `title_2` = :title_2 " +
                "AND `title_4` = :title_4 " +
                "AND `title_5` = :title_5 ", result);
        /**
         * 剔除 + 带前缀
         */
        sqlHelper.setPrefix("article");
        result = sqlHelper.joinFields_And(fieldItems, flipFields);

        assertEquals(//
                "AND article.`title_1` = :title_1 " +
                "AND article.`title_2` = :title_2 " +
                "AND article.`title_4` = :title_4 " +
                "AND article.`title_5` = :title_5 ", result);
    }

    /**
     * Test of joinFields_Or method, of class SQLHelper.
     * @
     */
    public void testjoinFields_Or()  {
        System.out.println("joinFields_Or");
        String[] flipFields = {"title_0", "title_3"};
        SQLHelper sqlHelper = new SQLHelper();
        /**
         * 普通
         */
        String result = sqlHelper.joinFields_Or(fieldItems);
        assertEquals(//
                "OR `title_0` = :title_0 " +
                "OR `title_1` = :title_1 " +
                "OR `title_2` = :title_2 " +
                "OR `title_3` = :title_3 " +
                "OR `title_4` = :title_4 " +
                "OR `title_5` = :title_5 ", result);
        /**
         * 剔除
         */
        result = sqlHelper.joinFields_Or(fieldItems, flipFields);

        assertEquals(//
                "OR `title_1` = :title_1 " +
                "OR `title_2` = :title_2 " +
                "OR `title_4` = :title_4 " +
                "OR `title_5` = :title_5 ", result);
        /**
         * 剔除 + 带前缀
         */
        sqlHelper.setPrefix("article");
        result = sqlHelper.joinFields_Or(fieldItems, flipFields);

        assertEquals(//
                "OR article.`title_1` = :title_1 " +
                "OR article.`title_2` = :title_2 " +
                "OR article.`title_4` = :title_4 " +
                "OR article.`title_5` = :title_5 ", result);
    }

    /**
     * Test of joinFields_EquColon method, of class SQLHelper.
     * @
     */
    public void testjoinFields_EquColon()  {
        System.out.println("joinFields_EquColon");
        String[] flipFields = {"title_0", "title_3"};
        SQLHelper sqlHelper = new SQLHelper();
        /**
         * 普通
         */
        String result = sqlHelper.joinFields_EquColon(fieldItems);
        assertEquals(//
                "`title_0` = :title_0," +
                "`title_1` = :title_1," +
                "`title_2` = :title_2," +
                "`title_3` = :title_3," +
                "`title_4` = :title_4," +
                "`title_5` = :title_5 ", result);
        /**
         * 剔除
         */
        result = sqlHelper.joinFields_EquColon(fieldItems, flipFields);

        assertEquals(//
                "`title_1` = :title_1," +
                "`title_2` = :title_2," +
                "`title_4` = :title_4," +
                "`title_5` = :title_5 ", result);
        /**
         * 剔除 + 带前缀
         */
        sqlHelper.setPrefix("article");
        result = sqlHelper.joinFields_EquColon(fieldItems, flipFields);

        assertEquals(//
                "article.`title_1` = :title_1," +
                "article.`title_2` = :title_2," +
                "article.`title_4` = :title_4," +
                "article.`title_5` = :title_5 ", result);
    }
}
