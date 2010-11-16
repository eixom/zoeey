/*
 * MoXie (SysTem128@GMail.Com) 2010-6-29 15:40:45
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.OptionHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class OptionHelperTest {

    public OptionHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setArgs method, of class OptionHelper.
     */
    @Test
    public void testSetArgs() {
        System.out.println("setArgs");


        OptionHelper optHelper = new OptionHelper(new String[]{"-arg0", "-arg1", "val1", "--arg2", "val2"//
                    , "--arg3", "val3", "val5"//
                    , "--arg5"//
                    , "--arg6", "this is a long string\'\\\""//
                });

        assertTrue(optHelper.hasOpt("arg0"));
        assertTrue(optHelper.hasOpt(new String[]{"arg0", "arg1"}));
        assertEquals(optHelper.getOpt("arg1"), "val1");
        assertEquals(optHelper.getOptValList("arg1").toString(), "[val1]");
        assertEquals(optHelper.getOpt("arg2"), "val2");
        assertEquals(optHelper.getOptValList("arg3").toString(), "[val3, val5]");
        assertEquals(optHelper.getArg(8), "--arg5");
        assertEquals(optHelper.getOpt("arg6"), "this is a long string\'\\\"");
        assertEquals(optHelper.getArgCount(), 11);


        optHelper = new OptionHelper("-arg0 -arg1 val1 --arg2 val2 "
                + "--arg3 val3 val5 "
                + "--arg5 "
                + "--arg6 \"this is a long string\'\\\"\"   "
                + "");
        assertEquals(optHelper.getArg(8), "--arg5");
        assertEquals(optHelper.getOpt("arg6"), "this is a long string\'\\\"");
        assertEquals(optHelper.getOptValList("arg3").toString(), "[val3, val5]");
        assertTrue(optHelper.hasOpt("arg0"));
        assertTrue(optHelper.hasOpt(new String[]{"arg0", "arg1"}));
        assertEquals(optHelper.getOpt("arg1"), "val1");
        assertEquals(optHelper.getOptValList("arg1").toString(), "[val1]");
        assertEquals(optHelper.getOpt("arg2"), "val2");
        assertEquals(optHelper.getOptValList("arg3").toString(), "[val3, val5]");
        assertEquals(optHelper.getArg(8), "--arg5");
        assertEquals(optHelper.getOpt("arg6"), "this is a long string\'\\\"");
        assertEquals(optHelper.getArgCount(), 11);

    }
}
