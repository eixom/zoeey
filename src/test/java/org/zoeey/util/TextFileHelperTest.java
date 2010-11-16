/*
 * MoXie (SysTem128@GMail.Com) 2009-8-9 12:17:18
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.EnvInfo;
import org.zoeey.util.TextFileHelper;
import org.zoeey.util.FileHelper;
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
public class TextFileHelperTest {

    /**
     *
     */
    public TextFileHelperTest() {
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
        FileHelper.tryDelete(new File(tempDir.concat("/zoeey_test/textFileHelper/")), true);
    }

    /**
     * Test of write method, of class TextFileHelper.
     * @throws Exception
     */
    @Test
    public void testWrite_File_String() throws Exception {
        System.out.println("write");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File textFile = new File(tempDir.concat("/zoeey_test/textFileHelper/textWrite/textFile.log"));
        String content = "Test of write method, of class TextFileHelper.";
        TextFileHelper.write(textFile, content);
        assertEquals(TextFileHelper.read(textFile), content);
    }

    /**
     * Test of append method, of class TextFileHelper.
     * @throws Exception
     */
    @Test
    public void testAppend_File_String() throws Exception {
        System.out.println("append");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File textFile = new File(tempDir.concat("/zoeey_test/textFileHelper/textWrite/textFile.log"));
        String content = "Test of write method, of class TextFileHelper.";
        TextFileHelper.write(textFile, content);
        TextFileHelper.append(textFile, "append content");
        assertEquals(TextFileHelper.read(textFile), content + "append content");
    }
}
