/*
 * MoXie (SysTem128@GMail.Com) 2010-8-25 14:43:47
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: Apache License  Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.zoeey.ztpl.compiler;

import org.zoeey.ztpl.Ztpl;
import java.io.File;
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
public class CompileHelperTest {

    public CompileHelperTest() {
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
     * Test of genIdentifier method, of class CompileHelper.
     */
    @Test
    public void testGetPackName() {
        System.out.println("getPackName");
        String prefix = "pack_";
        File tplFile = new File("tpl/test/ztpl_compile_helper.log");
        String expResult = "pack_test_tpl";
        String result = CompileHelper.getPackName(prefix, tplFile);
        assertEquals(expResult, result);
    }

    /**
     * Test of genClassName method, of class CompileHelper.
     */
    @Test
    public void testGetClassName() {
        System.out.println("getClassName");
        String prefix = "class_";
        File tplFile = new File("tpl/test/ztpl_compile_helper.log");
        String expResult = "class_ztpl_compile_helper_log5d531b9966973c1bf97b090004e57727";
        String result = CompileHelper.getClassName(prefix, tplFile);
        assertEquals(expResult, result);
    }
}
