/*
 * MoXie (SysTem128@GMail.Com) 2009-3-20 17:52:08
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
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class FileHelperTest {

    /**
     *
     */
    public FileHelperTest() {
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
        FileHelper.tryDelete(new File(tempDir.concat("/zoeey_test/fileHelper/")), true);
    }

    /**
     * Test of move method, of class FileHelper.
     * @throws Exception 
     */
    @Test
    public void testMove() throws Exception {
        System.out.println("move");

        String tempDir = EnvInfo.getJavaIoTmpdir();
        File original = new File(tempDir.concat("/zoeey_test/fileHelper/move/original.log"));
        File target = new File(tempDir.concat("/zoeey_test/fileHelper/move/target.log"));
        String content = "Test of copy method, of class FileHelper.";
        FileHelper.tryDelete(original);
        FileHelper.tryDelete(target);
        assertTrue(!original.exists());
        assertTrue(!target.exists());
        FileHelper.tryCreate(original);
        assertTrue(original.exists());
        TextFileHelper.write(original, content);
        FileHelper.move(original, target);
        assertTrue(!original.exists());
        assertTrue(target.exists());
        assertEquals(TextFileHelper.read(target), content);
        FileHelper.tryDelete(target);
        assertTrue(!target.exists());
    }

    /**
     * Test of copy method, of class FileHelper.
     * @throws Exception
     */
    @Test
    public void testCopy() throws Exception {
        System.out.println("copy");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File original = new File(tempDir.concat("/zoeey_test/fileHelper/copy/original.log"));
        File target = new File(tempDir.concat("/zoeey_test/fileHelper/copy/target.log"));
        String content = "Test of copy method, of class FileHelper.";
        /**
         * 
         */
        FileHelper.tryDelete(original);
        FileHelper.tryDelete(target);
        assertTrue(!original.exists());
        assertTrue(!target.exists());
        FileHelper.tryCreate(original);
        assertTrue(original.exists());
        TextFileHelper.write(original, content);
        /**
         * 
         */
        FileHelper.copy(original, target);
        assertTrue(original.exists());
        assertTrue(target.exists());
        assertEquals(TextFileHelper.read(target), content);
        FileHelper.tryDelete(original);
        FileHelper.tryDelete(target);
        assertTrue(!original.exists());
        assertTrue(!target.exists());
    }

    /**
     * Test of tryMakeDirs method, of class FileHelper.
     */
    @Test
    public void testTryMakeDirs() {
        System.out.println("tryMakeDirs");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File dir = new File(tempDir.concat("/zoeey_test/fileHelper/tryMakDirs/"));
//        FileHelper.tryDelete(dir);
        dir.delete();
        assertTrue(!dir.exists());
        FileHelper.tryMakeDirs(dir);
        assertTrue(dir.isDirectory());
        assertTrue(dir.exists());
        FileHelper.tryDelete(dir);
        assertTrue(dir.exists());
        dir.delete();
        assertTrue(!dir.exists());
    }

    /**
     * Test of touch method, of class FileHelper.
     */
    @Test
    public void testTouch() throws IOException, InterruptedException {
        System.out.println("touch");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File file = new File(tempDir.concat("/zoeey_test/fileHelper/touch/touch.log"));
        TextFileHelper.write(file, "touch me");
        long lastModified = file.lastModified();
        Thread.sleep(100);
        FileHelper.touch(file);
        if (file.lastModified() <= lastModified) {
            fail("touch invalided");
        }
        // dir
        file = new File(tempDir.concat("/zoeey_test/fileHelper/touch/touchdir"));
        FileHelper.tryMakeDirs(file);
        lastModified = file.lastModified();
        Thread.sleep(100);
        FileHelper.touch(file);
        if (file.lastModified() <= lastModified) {
            fail("touch invalided");
        }
    }

    /**
     * Test of tryCreate method, of class FileHelper.
     * @throws Exception
     */
    @Test
    public void testTryCreate() throws Exception {
        System.out.println("tryCreate");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File file = new File(tempDir.concat("/zoeey_test/fileHelper/tryCreate/createdFile.log"));
        FileHelper.tryDelete(file);
        assertTrue(!file.exists());
        FileHelper.tryCreate(file);
        assertTrue(file.exists());
        FileHelper.tryDelete(file);
        assertTrue(!file.exists());
    }

    /**
     * Test of listFiles method, of class FileHelper.
     * @throws Exception
     */
    @Test
    public void testListFiles_3args() throws Exception {
        System.out.println("listFiles");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File logDir = null;
        logDir = new File(tempDir.concat("/zoeey_test/fileHelper/listFile/"));
        FileHelper.tryDelete(logDir, true); // 清理文件
        FileHelper.tryMakeDirs(logDir);
        assertTrue(logDir.exists());
        File logFile = null;
        logFile = new File(tempDir.concat("/zoeey_test/fileHelper/listFile/listFileA.log"));
        FileHelper.tryCreate(logFile);
        assertTrue(logFile.exists());
        logFile = new File(tempDir.concat("/zoeey_test/fileHelper/listFile/listFileB.log"));
        FileHelper.tryCreate(logFile);
        assertTrue(logFile.exists());
        logFile = new File(tempDir.concat("/zoeey_test/fileHelper/listFile/listFileC.log"));
        FileHelper.tryCreate(logFile);
        logFile = new File(tempDir.concat("/zoeey_test/fileHelper/listFile/childDir/listFileD.log"));
        FileHelper.tryCreate(logFile);
        assertTrue(logFile.exists());
        /**
         * 无过滤器 无递归
         */
        boolean recusive = false;
        List<String> expResult = new ArrayList<String>();
        expResult.add("listFileA.log");
        expResult.add("listFileB.log");
        expResult.add("listFileC.log");
        List<File> result = FileHelper.listFiles(logDir, recusive, null);
        List<String> fileNameList = new ArrayList<String>();
        for (File file : result) {
            fileNameList.add(file.getName());
        }
        assertEquals(expResult, fileNameList);
        /**
         * 无过滤器 有递归
         */
        recusive = true;
        expResult = new ArrayList<String>();
        expResult.add("listFileA.log");
        expResult.add("listFileB.log");
        expResult.add("listFileC.log");
        expResult.add("listFileD.log");
        result = FileHelper.listFiles(logDir, recusive, null);
        fileNameList = new ArrayList<String>();
        for (File file : result) {

            fileNameList.add(file.getName());
        }
        assertEquals(expResult, fileNameList);
        /**
         * 有过滤器
         */
        FileFilter fileFilter = new FileFilter() {

            public boolean accept(File file) {
                return !file.getName().endsWith("B.log");
            }
        };
        expResult.remove(1);
        result = FileHelper.listFiles(logDir, recusive, fileFilter);
        fileNameList = new ArrayList<String>();
        for (File file : result) {
            fileNameList.add(file.getName());
        }
        assertEquals(expResult, fileNameList);
        FileHelper.tryDelete(logDir, true); // 清理文件
    }

    /**
     * Test of tryDelete method, of class FileHelper.
     * @throws IOException
     */
    @Test
    public void testTryDelete_File() throws IOException {
        System.out.println("tryDelete");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File fLog = new File(tempDir.concat("/adir/f.log"));
        FileHelper.tryCreate(fLog);
        assertTrue(fLog.exists());
        FileHelper.tryDelete(fLog);
        assertTrue(!fLog.exists());

    }

    /**
     * Test of tryDelete method, of class FileHelper.
     * @throws IOException
     */
    @Test
    public void testTryDelete_File_boolean() throws IOException {
        System.out.println("tryDelete");
        String tempDir = EnvInfo.getJavaIoTmpdir();
        File mainDir = new File(tempDir.concat("/zoeey_test/fileHelper/tryDelete/"));
        File childDir = new File(tempDir.concat("/zoeey_test/fileHelper/tryDelete/childDir/"));
        File aLog = new File(tempDir.concat("/zoeey_test/fileHelper/tryDelete/a.log"));
        File bLog = new File(tempDir.concat("/zoeey_test/fileHelper/tryDelete/childDir/b.log"));
        File cLog = new File(tempDir.concat("/zoeey_test/fileHelper/tryDelete/childDir/c.log"));
        File dLog = new File(tempDir.concat("/zoeey_test/fileHelper/tryDelete/childDir/d.log"));
        FileHelper.tryCreate(aLog);
        FileHelper.tryCreate(bLog);
        FileHelper.tryCreate(cLog);
        FileHelper.tryCreate(dLog);
        assertTrue(mainDir.exists());
        assertTrue(childDir.exists());
        assertTrue(aLog.exists());
        assertTrue(bLog.exists());
        assertTrue(cLog.exists());
        assertTrue(dLog.exists());
        boolean recursive = false;
        FileHelper.tryDelete(dLog, recursive);
        assertTrue(mainDir.exists());
        assertTrue(childDir.exists());
        assertTrue(aLog.exists());
        assertTrue(bLog.exists());
        assertTrue(cLog.exists());
        assertTrue(!dLog.exists());
        recursive = true;
        FileHelper.tryDelete(mainDir, recursive);
        assertTrue(mainDir.exists());
        assertTrue(!childDir.exists());
        assertTrue(!aLog.exists());
        assertTrue(!bLog.exists());
        assertTrue(!cLog.exists());
        assertTrue(!dLog.exists());

    }

    /**
     * Test of getAbsolutePath method, of class FileHelper.
     */
    @Test
    public void testGetAbsolutePath() {
        System.out.println("getAbsolutePath");
        String relativePath = "../../index.html";
        String absoluteDir = "d:/webRoot/script/pages/";
        int replacePartCount = -1;
        String expResult = "d:/webRoot/index.html";
        String result = FileHelper.getAbsolutePath(relativePath, absoluteDir, replacePartCount);
        assertEquals(expResult, result);
    }

    /**
     * Test of backToslash method, of class FileHelper.
     */
    @Test
    public void testBackToslash() {
        System.out.println("backToslash");
        String fileStr = "d:/somedir//webRoot/special/script/\\//\\///\\\\////edit.js";
        String expResult = "d:/somedir/webRoot/special/script/edit.js";
        String result = FileHelper.backToslash(fileStr);
        assertEquals(result, expResult);
    }
    /**
     * 
     */
    private static char[] FILE_UNSAFE_CHARS = new char[]{'"', '\'', '*', '/', ':', '<', '>', '?', '\\', '|',};

    /**
     *
     */
    @Test
    public void testFileNameEscape() {
        System.out.println("fileNameEscape");
        if (true) {
            Arrays.sort(FILE_UNSAFE_CHARS);
            StringBuffer strBuffer = new StringBuffer(FILE_UNSAFE_CHARS.length);
            for (int i = 0; i < FILE_UNSAFE_CHARS.length; i++) {
                strBuffer.append(FILE_UNSAFE_CHARS[i]);
            }
//            System.out.println(strBuffer.toString());
        }
        String fileStr = "d:/somedir//webRoot/special/script/\\//\\///\\\\////edit.js / \\ : * ? \" ' < > |";
        String expResult = "d--somedir--webRoot-special-script--------------edit.js - - - - - - - - - -";
        String result = FileHelper.fileNameEscape(fileStr, '-', false);
        assertEquals(result, expResult);
        fileStr = "d:/somedir//webRoot/special/script/\\//\\///\\\\////edit.js / \\ : * ? \" ' < > |";
        expResult = "d--somedir--webRoot-special-script--------------edit.js - - - - - - - - - -";

        for (int i = 0; i < FILE_UNSAFE_CHARS.length; i++) {
            result = FileHelper.fileNameEscape(fileStr, FILE_UNSAFE_CHARS[i], false);
            assertEquals(result, expResult);
        }
        fileStr = "d:/  somedir//web    Root/spe    cial/script/    \\//\\///\\\\////edit.js / \\ : * ? \" ' < > |";
        expResult = "d--somedir--webRoot-special-script--------------edit.js----------";

        for (int i = 0; i < FILE_UNSAFE_CHARS.length; i++) {
            result = FileHelper.fileNameEscape(fileStr, FILE_UNSAFE_CHARS[i], true);
            assertEquals(result, expResult);
        }
    }
}
