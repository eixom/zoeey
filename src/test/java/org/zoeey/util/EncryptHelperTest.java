/*
 * MoXie (SysTem128@GMail.Com) 2010-7-3 15:39:40
 *
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: Apache License  Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.EncryptHelper;
import org.zoeey.common.TestUtil;
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
public class EncryptHelperTest {

    public EncryptHelperTest() {
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
     * Test of md5 method, of class EncryptHelper.
     */
    @Test
    public void testMd5_String() {
        System.out.println("md5");
        String str = "moxie";
        String expResult = "289757c656c0a96f85c757fcbe8dec5f";
        String result = EncryptHelper.md5(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of sha1 method, of class EncryptHelper.
     */
    @Test
    public void testSha1_String() {
        System.out.println("sha1");
        String str = "moxie";
        String expResult = "19ab80fdca0a20fe1df62ff9993c81f6da3454d2";
        String result = EncryptHelper.sha1(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of md5 method, of class EncryptHelper.
     */
    @Test
    public void testMd5_File() throws Exception {
        System.out.println("md5");
        String path = TestUtil.getResource("md5file_test.html");
        File file = new File(path);
        String expResult = "b874a14d4a6b23b87d7efa12d19cacfd";
        String result = EncryptHelper.md5(file);
        assertEquals(expResult, result);
    }

    /**
     * Test of sha1 method, of class EncryptHelper.
     */
    @Test
    public void testSha1_File() throws Exception {
        System.out.println("sha1");
        String path = TestUtil.getResource("md5file_test.html");
        File file = new File(path);
        String expResult = "636c0af710e8d91998de12f06a53afd6b92839c9";
        String result = EncryptHelper.sha1(file);
        assertEquals(expResult, result);
    }
}
