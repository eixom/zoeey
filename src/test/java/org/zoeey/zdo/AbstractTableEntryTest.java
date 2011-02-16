/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import junit.framework.TestCase;
import org.zoeey.dataBean.SampleLeaderBeanAble;
import org.zoeey.dataBean.SamplePersonBean;
import org.zoeey.dataBean.SamplePersonBeanAble;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class AbstractTableEntryTest extends TestCase {

    /**
     *
     * @param testName
     */
    public AbstractTableEntryTest(String testName) {
        super(testName);
    }

    /**
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     *
     * @throws Exception
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of setNickName method, of class AbstractTableEntry.
     */
    public void testAll() {
        System.out.println("absTable");
        AbstractTableEntryAble absTable = AbstractTableEntry.newInstance();
        absTable.setNickName("Article");
//        instance.newString("title", "titleValue");
//        instance.newNumber("counter", 10);
//        instance.newNumber("counter", 101);
        SamplePersonBean person = new SamplePersonBean();
        person.setName("MoXie");
        absTable.fromBean(person, SampleLeaderBeanAble.class);
        absTable.fromBean(person, SamplePersonBeanAble.class);

        FieldItem[] fields = absTable.getFields();
        FieldItem fieldEntry = null;
//        System.out.println(fields.length);
//        if (true) {
//            return;
//        }
        String expResult = "leader : 3 - true - leader|name : 2 - MoXie - name|id : 3 - 0 - id|age : 3 - 0 - age|name : 2 - MoXie - name|id : 3 - 0 - id|age : 3 - 0 - age|";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            fieldEntry = fields[i];
            result.append(fieldEntry.getName());
            result.append(" : ");
            result.append(fieldEntry.getType());
            result.append(" - ");
            result.append(fieldEntry.getValue());
            result.append(" - ");
            result.append(fieldEntry.getArgName());
            result.append("|");
        }
        assertEquals(expResult, result.toString());
    }
}
