/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.Sha1;
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
public class Sha1Test {

    /**
     *
     */
    public Sha1Test() {
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
     * Test of compute method, of class Sha1.
     */
//    @Test
//    public void testCompute_byteArr() {
////        System.out.println("sha1 bytes");
////        byte[] data = "moxie".getBytes();
////        byte[] result = Sha1.sha1(data);
////        assertEquals(Sha1.byte2str(result), "289757c656c0a96f85c757fcbe8dec5f");
//    }
    /**
     * Test of sha1 method, of class Sha1.
     */
    @Test
    public void testSha1() {
        System.out.println("sha1 string");
        String data = "moxie";
        String result = (new Sha1()).encrypt(data);
        assertEquals(result, "19ab80fdca0a20fe1df62ff9993c81f6da3454d2");
    }

    /**
     * Test of sha1 method, of class Sha1.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testFileSha1() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
        System.out.println("sha1 file");
        String path = TestUtil.getResource("md5file_test.html");
        File file = new File(path);
        String result = (new Sha1()).encrypt(file);
        assertEquals(result, "636c0af710e8d91998de12f06a53afd6b92839c9");
    }
}









