/*
 * MoXie (SysTem128@GMail.Com) 2009-4-17 12:06:23
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.ConditionsString;
import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ConditionsStringTest extends TestCase {

    /**
     *
     */
    public ConditionsStringTest() {
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
    @Override
    public void setUp() {
    }

    /**
     *
     */
    @After
    @Override
    public void tearDown() {
    }

    /**
     * Test of add method, of class ConditionString.
     * @throws IOException
     */
    @Test
    public void testAdd() throws IOException {
        System.out.println("add");
        ConditionsString cStr;
//        TimeMeasurer tm =  new TimeMeasurer();
//        tm.start();
//        for (int i = 0; i < 100000; i++) {
        cStr = new ConditionsString();
        cStr.put("cd_1", "1_cd_1.l");
        cStr.put("cd_2", "2_cd_2.1");
        cStr.put("cd_1", "3_cd_1.2");
        cStr.put("cd_3", "4_cd_3.1");
        cStr.put("cd_3", "5_cd_3.2");
        cStr.put("cd_2", "6_cd_2.3");
        cStr.active("cd_1");
        assertEquals(cStr.toString(";"), "1_cd_1.l;3_cd_1.2");
        cStr.active("cd_3");
        cStr.active("cd_2");
        assertEquals(cStr.toString(";"), "1_cd_1.l;2_cd_2.1;3_cd_1.2;4_cd_3.1;5_cd_3.2;6_cd_2.3");
        String conditionText = "cd_1=\"1_cd_1.l\"   cd_2=\"2_cd_2.1\"   cd_1='3_cd_1.2' cd_3='4_cd_3.1' "
                + "cd_3='5_cd_3.2' cd_2='6_cd_2.3'";
        cStr = new ConditionsString();
        cStr.fromString(conditionText);
        cStr.active("cd_1");
        assertEquals(cStr.toString(";"), "1_cd_1.l;3_cd_1.2");
        cStr.active("cd_3");
        cStr.active("cd_2");
        assertEquals(cStr.toString(";"), "1_cd_1.l;2_cd_2.1;3_cd_1.2;4_cd_3.1;5_cd_3.2;6_cd_2.3");
        cStr = new ConditionsString();
        cStr.fromFile(new File(TestUtil.getResource("conditionString_test.csql")));
        cStr.active("cd_1");
        assertEquals(cStr.toString(";"), "1_cd_1.l;3_cd_1.2");
        cStr.active("cd_3");
        cStr.active("cd_2");
        assertEquals(cStr.toString(";"), "1_cd_1.l;2_cd_2.1;3_cd_1.2;4_cd_3.1;5_cd_3.2;6_cd_2.3");
// }
        assertEquals(cStr.toString(";",//
                ConditionsString.TOSTR_SPACES_TOONE & ConditionsString.TOSTR_SKIP_LFCR),//
                "1_cd_1.l;2_cd_2.1;3_cd_1.2;4_cd_3.1;5_cd_3.2;6_cd_2.3");
//        tm.stop();
//        System.out.println(tm.getSpend());
        // 1130,1233
    }
}
