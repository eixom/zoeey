/*
 * MoXie (SysTem128@GMail.Com) 2009-8-9 13:10:33
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.EnvInfo;
import org.zoeey.util.FileHelper;
import org.zoeey.util.BinaryFileHelper;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
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
public class BinaryFileHelperTest {

    /**
     *
     */
    public BinaryFileHelperTest() {
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
        String tempDir = EnvInfo.getJavaIoTmpdir();
        FileHelper.tryDelete(new File(tempDir.concat("/zoeey_test/binaryFileHelper/")), true);
    }

    /**
     * Test of write method, of class BinaryFileHelper.
     * @throws Exception
     */
    @Test
    public void testWrite_File_byteArr() throws Exception {
        System.out.println("write");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File byteFile = new File(tempDir.concat("/zoeey_test/binaryFileHelper/textWrite/byteFile.bin"));
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6};
        BinaryFileHelper.write(byteFile, bytes);
        assertArrayEquals(BinaryFileHelper.read(byteFile), bytes);

    }

    /**
     * Test of append method, of class BinaryFileHelper.
     * @throws Exception
     */
    @Test
    public void testAppend() throws Exception {
        System.out.println("append");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File byteFile = new File(tempDir.concat("/zoeey_test/binaryFileHelper/textWrite/byteFile.bin"));
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6};
        BinaryFileHelper.write(byteFile, bytes);
        bytes = new byte[]{7, 8, 9, 10};
        BinaryFileHelper.append(byteFile, bytes);
        bytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(BinaryFileHelper.read(byteFile), bytes);
    }
}
