/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.container;

import org.zoeey.common.container.ObjectHolder;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
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
public class ObjectHolderTest {

    /**
     *
     */
    public ObjectHolderTest() {
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
     * Test of put method, of class ObjectContainer.
     */
    @Test
    public void testPut() {
        System.out.println("put");
        Object obj = "MoXie";
        ObjectHolder.put("name", obj);
    }

    /**
     * Test of get method, of class ObjectContainer.
     */
    @Test
    public void testGet_0args() {
        System.out.println("get");
        Object result = ObjectHolder.get("name", "");
        assertEquals(result, "MoXie");
    }

    /**
     * Test of get method, of class ObjectContainer.
     */
    @Test
    public void testGet_null() {
        System.out.println("get");
        String str = ObjectHolder.get("name", "");
        assertEquals(str, "MoXie");
    }

    /**
     * Test of clear method, of class ObjectContainer.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        ObjectHolder.clear();
    }

    /**
     * Test of clear method, of class ObjectContainer.
     */
    @Test
    public void testMultiThreadTest() {
        System.out.println("multiThreadTest");
        for (int i = 0; i < 500; i++) {
            MultiThreadTest test = new MultiThreadTest();
            test.start();
        }
    }

    /**
     * Test of finalize method, of class ObjectContainer.
     */
    @Test
    public void testFinalize() {
        System.out.println("finalize");
    }

    private static class MultiThreadTest extends Thread {

        @Override
        public void run() {
            try {
                long nanotime = System.nanoTime();
                ObjectHolder.put("nanotime", nanotime);
                sleep(500);
                long result = ObjectHolder.<Long>get("nanotime");
                Assert.assertEquals(result, nanotime);
            } catch (InterruptedException ex) {
                Logger.getLogger(ObjectHolderTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
