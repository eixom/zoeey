/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.Md5;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class Md5Test {

    /**
     *
     */
    public Md5Test() {
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
     * Test of md5 method, of class Md5.
     */
    @Test
    public void testMd5() {
        System.out.println("md5 string");
        String data = "moxie";
        Md5 md5 = new Md5();
        String result = md5.encrypt(data);
        assertEquals(result, "289757c656c0a96f85c757fcbe8dec5f");
    }

    /**
     * Test of md5 method, of class Md5.
     * @throws FileNotFoundException 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testFileMd5() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        System.out.println("md5 file");
        String path = TestUtil.getResource("md5file_test.html");
        Md5 md5 = new Md5();
        File file = new File(path);
        String result = md5.encrypt(file);
        assertEquals(result, "b874a14d4a6b23b87d7efa12d19cacfd");
    }
}
