/*
 * MoXie (SysTem128@GMail.Com) 2010-2-8 16:25:12
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common;

import org.zoeey.common.Status;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.ParamHelper;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class StatusTest {

    public StatusTest() {
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
     * Test of getBrief method, of class Status.
     */
    @Test
    public void testGetBrief() {
        System.out.println("getBrief");
        Status instance = new Status();
        String expResult = "文章读取成功！";
        instance.setBrief(expResult);
        String result = instance.getBrief();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBrief method, of class Status.
     */
    @Test
    public void testSetBrief() {
        System.out.println("setBrief");
        Status instance = new Status();
        String expResult = "文章读取成功！";
        instance.setBrief(expResult);
        String result = instance.getBrief();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Status.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Status instance = new Status();
        String expResult = "article.read.success";
        instance.setName(expResult);
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class Status.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        Status instance = new Status();
        String expResult = "article.read.success";
        instance.setName(expResult);
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSign method, of class Status.
     */
    @Test
    public void testGetSign() {
        System.out.println("getSign");
        Status instance = new Status();
        int sing_a = ParamHelper.AND_0;
        int sing_b = ParamHelper.AND_1;
        instance.setSign(sing_a & sing_b);
        int result = instance.getSign();
        assertTrue(ParamHelper.contain(result, sing_a));
        assertTrue(ParamHelper.contain(result, sing_b));
    }

    /**
     * Test of setSign method, of class Status.
     */
    @Test
    public void testSetSign() {
        System.out.println("setSign");
        Status instance = new Status();
        int sing_a = ParamHelper.AND_0;
        int sing_b = ParamHelper.AND_1;
        instance.setSign(sing_a & sing_b);
        int result = instance.getSign();
        assertTrue(ParamHelper.contain(result, sing_a));
        assertTrue(ParamHelper.contain(result, sing_b));
    }
}
