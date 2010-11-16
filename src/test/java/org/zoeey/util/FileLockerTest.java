/*
 * MoXie (SysTem128@GMail.Com) 2009-4-17 10:45:22
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.EnvInfo;
import org.zoeey.util.TextFileHelper;
import org.zoeey.util.FileLocker;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class FileLockerTest extends TestCase {

    /**
     *
     */
    public FileLockerTest() {
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
     * Test of lockWrite method, of class FileLocker.
     * @throws NoSuchMethodException
     */
    @Test
    public void testWriteLock() throws NoSuchMethodException, IOException, InterruptedException, Throwable {
        System.out.println("writeLock");
        List<TestRunnable> trList = new ArrayList<TestRunnable>();
        for (int i = 0; i < 5; i++) { // 50*10=10~11s 5*2=1.0~1.5s
            for (int idx = 0; idx < 2; idx++) {
            TextFileHelper.write((new File(EnvInfo.getTempDir() + "/file_locker/" + idx + "")), idx + "");
////            System.out.println(EnvInfo.getTempDir());
                trList.add(new FileReadThread(new File(EnvInfo.getTempDir() + "/file_locker/" + idx + ".log")));
                trList.add(new FileWriteThread(new File(EnvInfo.getTempDir() + "/file_locker/" + idx + ".log")));
                trList.add(new FileReadThread(new File(EnvInfo.getTempDir() + "/file_locker/" + idx + ".log")));

            }
        }
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trList.toArray(new TestRunnable[0]));
        mttr.runTestRunnables();
    }
    private static List<String> list = new ArrayList<String>();

    public synchronized static void check(String str) {
        if (list.contains(str) && (str.startsWith("w_") || str.startsWith("/w_"))) {
            fail("重写:" + str);
        }
        if (str.startsWith("w_")) {
            if (list.contains("r_" + str.substring(2)) || list.contains("/r_" + str.substring(2))) {
                fail("读取锁未解除 1！");
            }
        }
        if (str.startsWith("/w_")) {
            if (list.contains("r_" + str.substring(3)) || list.contains("/r_" + str.substring(3))) {
                fail("读取锁未解除 2！");
            }
            if (!list.contains(str.substring(1))) {
                fail("锁未开启 1！");
            } else {
                list.remove(str.substring(1));
                return;
            }
        }
        if (str.startsWith("r_")) {
            if (list.contains("w_" + str.substring(2)) || list.contains("/w_" + str.substring(2))) {
                fail("写入锁未解除 1！");
            }
        }
        if (str.startsWith("/r_")) {
            if (list.contains("w_" + str.substring(3)) || list.contains("/w_" + str.substring(3))) {
                fail("写入锁未解除 2！");
            }
            if (!list.contains(str.substring(1))) {
                fail("锁未开启 2！");
            } else {
                list.remove(str.substring(1));
                return;
            }
        }
        list.add(str);
    }

    private static class FileWriteThread extends TestRunnable {

        private File file;

        public FileWriteThread(File file) {
            this.file = file;
        }

        public void runTest() {
            try {
                FileLocker flock = new FileLocker(file);
                try {
                    flock.lockWrite();
                    FileLockerTest.check("w_" + file.getName() + "");
                    Thread.sleep(200);
                    FileLockerTest.check("/w_" + file.getName() + "");
                } finally {
                    flock.releaseWrite();
                }
            } catch (Exception ex) {
            }
        }
    }

    private static class FileReadThread extends TestRunnable {

        private File file;

        public FileReadThread(File file) {
            this.file = file;
        }

        public void runTest() {
            try {
                FileLocker flock = new FileLocker(file);
                try {
                    flock.lockRead();
                    FileLockerTest.check("r_" + file.getName() + "");
                    Thread.sleep(20);
                    FileLockerTest.check("/r_" + file.getName() + "");
                } finally {
                    flock.releaseRead();
                }




            } catch (Exception ex) {
            }
        }
    }
}
