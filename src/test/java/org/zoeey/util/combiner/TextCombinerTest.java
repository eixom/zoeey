/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner;

import java.io.File;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import org.zoeey.util.DirInfo;
import org.zoeey.util.FileHelper;
import org.zoeey.util.TextFileHelper;
import org.zoeey.util.TimeMeasurer;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class TextCombinerTest extends TestCase {

    /**
     *
     */
    public TextCombinerTest() {
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
     * Test of write method, of class TextCombiner.
     */
    public static final String currentDir = DirInfo.getClassDir(TextCombinerTest.class);
//    public static final String currentDir = "D:/UploadTest/fileWrite/";

    /**
     *
     * @throws Exception
     */
    @Test
    public void testWrite() throws Exception {
        File fileOrigin = new File(TestUtil.getResource("jsLoader").concat("/combiner/testWriteOrigin.js"));
        File file = new File(TestUtil.getResource("jsLoader").concat("/combiner/testWrite.js"));
        TextCombiner txtComb = new TextCombiner();
        txtComb.setCharset("utf-8");
        txtComb.setBoundary("\n/* {file}文件: {fileName} ，{/file} 写入时间：{date} */\n");
        TextFileHelper.write(fileOrigin, "/* fileOrigin */");
        txtComb.concat(fileOrigin);
        txtComb.concat("中文");
        txtComb.concat("english");
        txtComb.write(file);
        String jsContent = TextFileHelper.read(file);
        assertTrue(jsContent.indexOf("中文") > -1);
        assertTrue(jsContent.indexOf("english") > -1);
        assertTrue(jsContent.indexOf("fileOrigin") > -1);
        assertTrue(jsContent.indexOf("testWriteOrigin.js") > -1);
        txtComb.clear();
        FileHelper.tryDelete(file, false);
        file = null;
        txtComb = null;
    }

    /**
     *
     * @throws Exception
     */
    public void testWriteThreads() throws Exception {
//        Thread thread;
//        int i = 0;
        TimeMeasurer tm = new TimeMeasurer();
        tm.start("thread");
        if (true) {
            return;
        }
        /**
         * 1000枚
         * 30k 
         * 初写入平均值 5235
         * 覆写入平均值 sy 8162 lock 7562
         * 384k
         * 初写入平均值 7785
         * 覆写入平均值 9572
         */
//        try {
//            if (false) {
//                for (i = 0; i < 100; i++) {
//                    thread = new ThreadTester();
//                    thread.start();
//                }
//                Thread.sleep(2000);
//            }
//        } finally {
//            System.out.println(currentDir + "fileGen/");
//            FileHelper.tryDelete(new File(currentDir + "fileGen/"), true);
//            System.out.println("i:" + i);
//        }
        tm.stop("thread");
        System.out.println(tm.spend("thread"));
    }
}
