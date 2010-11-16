/*
 * MoXie (SysTem128@GMail.Com) 2009-4-17 16:10:23
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.ConditionsMapString;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ConditionsMapStringTest {

    /**
     *
     */
    public ConditionsMapStringTest() {
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
     * Test of active method, of class PropertiesString.
     */
    @Test
    public void testActive() {
        System.out.println("active");
        ConditionsMapString pStr = new ConditionsMapString();
        pStr.put("cd_1", "1_cd_1.l");
        pStr.put("cd_2", "2_cd_2.1");
        pStr.put("cd_1", "3_cd_1.2");
        pStr.put("cd_3", "4_cd_3.1");
        pStr.put("cd_3", "5_cd_3.2");
        pStr.put("cd_2", "6_cd_2.3");
        pStr.active("cd_1");
        assertEquals(pStr.toString(";"), "3_cd_1.2");
        pStr.active("cd_3");
        pStr.active("cd_2");
        assertEquals(pStr.toString(";"), "3_cd_1.2;5_cd_3.2;6_cd_2.3");
    }
}
