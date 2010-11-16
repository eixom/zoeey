/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import org.zoeey.zdo.AbstractTableEntry;
import org.zoeey.zdo.AbstractTableEntryAble;
import org.zoeey.zdo.ZDOMySQL;
import org.zoeey.zdo.FieldItem;
import org.zoeey.zdo.samples.SampleArticleTableEntry;
import junit.framework.TestCase;
import org.zoeey.common.ZObject;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZDOMySQLTest extends TestCase {

    /**
     *
     * @param testName
     */
    public ZDOMySQLTest(String testName) {
        super(testName);
    }

    /**
     * Test of contact method, of class ZDOMySQL.
     */

//    public void testConnect() {
//        System.out.println("contact");
//        TableEntryAble table = null;
//        ZDOMySQL instance = null;
//        ZDOMySQL expResult = null;
//        ZDOMySQL result = instance.contactTable(table);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of useTpl method, of class ZDOMySQL.
//     */
//    public void testUseTpl() {
//        System.out.println("useTpl");
//        String sqlTpl = "";
//        ZDOMySQL instance = null;
//        ZDOMySQL expResult = null;
//        ZDOMySQL result = instance.useTpl(sqlTpl);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLastSql method, of class ZDOMySQL.
//     */
//    public void testGetLastSql() {
//        System.out.println("getLastSql");
//        ZDOMySQL instance = null;
//        String expResult = "";
//        String result = instance.getLastSql();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of insert method, of class ZDOMySQL.
     * @throws Exception
     */
    public void testCreate_String() throws Exception {
        System.out.println("create");
        
        if (true) {
            return;
        }
        String prefix = "";
        DemoData demoData = new DemoData("ZDOMySQL");
        ZDOMySQL zdo = new ZDOMySQL(demoData.getConn());
        int effectRowCount = 0;
        SampleArticleTableEntry table = SampleArticleTableEntry.newInstance();
        /**
         * 单对象存储
         */
        table.setTitle("标题");
        table.setContent("内容");
        table.setEditTime(System.currentTimeMillis() / 1000);
        AbstractTableEntryAble absTable = AbstractTableEntry.newInstance();
        absTable.setNickName("T_Article");
        absTable.newField("title", FieldItem.TYPE_STRING, new ZObject("标题"));
        absTable.newField("content", FieldItem.TYPE_STRING, new ZObject("内容"));

        effectRowCount = zdo.contactTable(absTable).insert();
        System.out.println(effectRowCount);
//        assertEquals(effectRowCount, 1);
//        /**
//         * 绑定多个表对象
//         */
//        effectRowCount = zdo.contactTable(table).contactTable(table.sampleData()[0]).insert();
//        assertEquals(effectRowCount, 6);
        demoData.destory();

//        int expResult = 0;
//        int result = zdo.insert(prefix);
//        assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    /**
     * Test of insert method, of class ZDOMySQL.
     */
//    public void testCreate_0args() throws Exception {
//        System.out.println("insert");
//        ZDOMySQL instance = null;
//        int expResult = 0;
//        int result = instance.insert();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of lastInsertId method, of class ZDOMySQL.
     */
//    public void testLastInsertId() {
//        System.out.println("lastInsertId");
//        ZDOMySQL instance = null;
//        String expResult = "";
//        String result = instance.lastInsertId();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of read method, of class ZDOMySQL.
     */
//    public void testRead() {
//        System.out.println("read");
//        ZDOMySQL instance = null;
//        ResultSet expResult = null;
//        ResultSet result = instance.read();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of update method, of class ZDOMySQL.
     */
//    public void testUpdate() {
//        System.out.println("update");
//        ZDOMySQL instance = null;
//        int expResult = 0;
//        int result = instance.update();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of del method, of class ZDOMySQL.
     */
//    public void testDel() {
//        System.out.println("del");
//        ZDOMySQL instance = null;
//        int expResult = 0;
//        int result = instance.del();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
