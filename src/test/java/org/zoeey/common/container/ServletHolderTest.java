/*
 * MoXie (SysTem128@GMail.Com) 2010-2-8 14:49:01
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.container;

import org.zoeey.common.container.ServletHolder;
import org.zoeey.common.container.ServletResource;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.RandomHelper;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class ServletHolderTest {

    public ServletHolderTest() {
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
     * Test of set method, of class ServletHolder.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        ServletResource resource = new ServletResource();
        ServletHolder.set(resource);
    }

    /**
     * Test of get method, of class ServletHolder.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        ServletResource expResult = new ServletResource();
        ServletHolder.set(expResult);
        ServletResource result = ServletHolder.get();
        assertEquals(expResult, result);
    }

    /**
     * Test of clear method, of class ServletHolder.
     */
    @Test
    public void testMultiThreadTest() throws Exception, Throwable {
        System.out.println("multiThreadTest");
        List<TestRunnable> trList = new ArrayList<TestRunnable>();
        for (int i = 0; i < 15; i++) {
            trList.add(new MultiThreadTest());
        }
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trList.toArray(new TestRunnable[0]));
        mttr.runTestRunnables();
    }

    /**
     * Test of clear method, of class ServletHolder.
     */
    @Test
    public void testMultiThread() {
        System.out.println("clear");
        ServletResource resource = new ServletResource();
        ServletHolder.set(resource);
        ServletHolder.clear();
        assertEquals(ServletHolder.get(), null);
    }

    private static class MultiThreadTest extends TestRunnable {

        @Override
        public void runTest() {
            try {
                Long nanotime = System.nanoTime();
                ServletResource resource = new ServletResource();
                resource.set("nanotime", nanotime);
                ServletHolder.set(resource);
                resource = null;
                Thread.sleep(RandomHelper.toInt(1, 500));
                Long result = (Long) ServletHolder.get().get("nanotime");
                Assert.assertEquals(result, nanotime);
                ServletHolder.clear();
            } catch (InterruptedException ex) {
                Logger.getLogger(ObjectHolderTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
