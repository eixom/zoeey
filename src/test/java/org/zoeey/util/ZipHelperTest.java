/*
 * MoXie (SysTem128@GMail.Com) 2009-8-4 9:58:19
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.io.FileFilter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class ZipHelperTest {

    /**
     *
     */
    public ZipHelperTest() {
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
        File dir = new File(TestUtil.getUnZipDir());
        FileHelper.tryDelete(dir, true);
        dir = new File(TestUtil.getZipDir());
        FileHelper.tryDelete(dir, true);
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
     * Test of zip method, of class ZipHelper.
     * @throws Exception
     */
    @Test
    public void testZip_File_File() throws Exception {
        System.out.println("zip file");
        String dir = TestUtil.getZipFilesDir();
        String zipFilePath = TestUtil.getZipFile("/zip file.zip");
        File zipFile = new File(zipFilePath);
        ZipHelper.zip(new File(dir.concat("/bug.png")), zipFile, new FileFilter() {

            public boolean accept(File file) {
                return !file.getAbsolutePath().endsWith(".svn") // 剔除 svn 目录
                        && !file.getAbsolutePath().endsWith(".csv"); // 剔除 csv 目录
            }
        });
        assertTrue(zipFile.length() > 0);
    }

    /**
     * Test of zip method, of class ZipHelper.
     * @throws Exception
     */
    @Test
    public void testZip_Dir_File() throws Exception {
        System.out.println("zip dir");
        String dir = TestUtil.getZipFilesDir();
        String zipFilePath = TestUtil.getZipFile("/zip dir.zip");

        File zipFile = new File(zipFilePath);
        ZipHelper.zip(new File(dir), zipFile, new FileFilter() {

            public boolean accept(File file) {
                return !file.getAbsolutePath().endsWith(".svn") // 剔除 svn 目录
                        && !file.getAbsolutePath().endsWith(".csv"); // 剔除 csv 目录
            }
        });
        assertTrue(zipFile.length() > 0);
    }

    /**
     * Test of zip method, of class ZipHelper.
     * @throws Exception
     */
    @Test
    public void testUnZip_File() throws Exception {
        System.out.println("unzip dir");

        String zipFilePath = TestUtil.getZipFile("/zip dir.zip");
        File dir = new File(TestUtil.getUnZipDir());
        File zipFile = new File(zipFilePath);

        ZipHelper.unzip(zipFile, dir);
        assertTrue(FileHelper.listFiles(dir).size() > 0);
    }
}
