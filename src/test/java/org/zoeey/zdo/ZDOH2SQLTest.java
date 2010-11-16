/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import org.zoeey.zdo.samples.SampleArticleTableEntry;
import junit.framework.TestCase;
import org.zoeey.common.ZObject;
import org.zoeey.util.TimeHelper;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZDOH2SQLTest extends TestCase {

    /**
     *
     * @param testName
     */
    public ZDOH2SQLTest(String testName) {
        super(testName);
    }

    /**
     * Test of contact method, of class ZDOH2SQL.
     */
    public void testConnect() {
    }

    /**
     * Test of bindParams method, of class ZDOH2SQL.
     */
    public void testBindParams() {
    }

    /**
     * Test of contactTable method, of class ZDOH2SQL.
     */
    public void testConnectTable() {
    }

    /**
     * Test of contactTables method, of class ZDOH2SQL.
     */
    public void testConnectTables() {
    }

    /**
     * Test of useTpl method, of class ZDOH2SQL.
     */
    public void testUseTpl() {
    }

    /**
     * Test of getLastSql method, of class ZDOH2SQL.
     */
    public void testGetLastSql() {
    }

    /**
     * Test of insert method, of class ZDOH2SQL.
     * @throws Exception
     */
    public void testCreate_String() throws Exception {
        System.out.println("create");
        DemoData demoData = new DemoData("ZDOH2SQL");
        ZDOAble zdo = new ZDOH2SQL(demoData.getConn());
        int effectRowCount = 0;
        long time = TimeHelper.time();
        /**
         * 单对象存储
         */
        /**
         * 数据表
         */
        SampleArticleTableEntry table = SampleArticleTableEntry.newInstance();
        table.getId().turnOff();
        table.setPrimeKey("id", "NEXT VALUE FOR ARTICLE_SEQ_ID ");
        table.setTitle("标题灰常党");
        table.setContent("内容灰常虚~");
        table.setEditTime(time);
        effectRowCount = zdo.contactTable(table).insert();
        assertEquals(effectRowCount, 1);
        /**
         * 虚拟表
         */
        AbstractTableEntryAble absTable = AbstractTableEntry.newInstance();
        absTable.setPrimeKey("id", "NEXT VALUE FOR ARTICLE_SEQ_ID");
        absTable.setNickName("T_Article");
        absTable.newField("title", FieldItem.TYPE_STRING, new ZObject("标题灰常党"));
        absTable.newField("content", FieldItem.TYPE_STRING, new ZObject("内容灰常虚~"));
        absTable.newField("editTime", FieldItem.TYPE_NUMBER, new ZObject(time));
        effectRowCount = zdo.contactTable(absTable).insert();
        assertEquals(effectRowCount, 1);
        //
        absTable = AbstractTableEntry.newInstance();
        absTable.setNickName("T_Article");
        absTable.setPrimeKey("id", "NEXT VALUE FOR ARTICLE_SEQ_ID");
        absTable.newField("title", FieldItem.TYPE_STRING, new ZObject("标题灰常党"));
//        absTable.newString("content", "内容灰常虚~");
//        absTable.newNumber("editTime", (int) time);
        
        effectRowCount = zdo.contactTable(absTable).insert();
        assertEquals(effectRowCount, 1);
        /**
         * 转换自Beans的虚表
         */
        absTable = AbstractTableEntry.newInstance();
        absTable.setNickName("T_Article");
        ArticleBeanAddAble article = new ArticleBeansBase(); // 这是一个通过接口实现的可伸缩的Bean
        absTable.setPrimeKey("id", "NEXT VALUE FOR ARTICLE_SEQ_ID");
        article.setTitle("标题灰常党");
        article.setContent("内容灰常虚～");
        article.setEditTime(time);
        absTable.fromBean(article, ArticleBeanAddAble.class);
        effectRowCount = zdo.contactTable(absTable).insert();
        assertEquals(effectRowCount, 1);
        //        /**
        //         * 绑定多个表对象
        //         */
        //        effectRowCount = zdo.contactTable(table).contactTable(table.sampleData()[0]).insert();
        //        assertEquals(effectRowCount, 6);
        demoData.destory();

//        int expResult = 0;
//        int result = zdo.insert(prefix);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class ZDOH2SQL.
     * @throws Exception
     */
    public void testCreate_0args() throws Exception {
    }

    /**
     * Test of lastInsertId method, of class ZDOH2SQL.
     */
    public void testLastInsertId() {
    }

    /**
     * Test of read method, of class ZDOH2SQL.
     */
    public void testRead() {
    }

    /**
     * Test of readList method, of class ZDOH2SQL.
     */
    public void testReadList() {
    }

    /**
     * Test of update method, of class ZDOH2SQL.
     */
    public void testUpdate() {
    }

    /**
     * Test of del method, of class ZDOH2SQL.
     */
    public void testDel() {
    }
}
