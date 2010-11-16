/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.TimeMeasurer;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class TimeMeasurerTest extends TestCase {

    /**
     *
     */
    public TimeMeasurerTest() {
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

//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//    }
    /**
     *
     * @throws InterruptedException
     */
    @Test
    public void testAll() throws InterruptedException {
        System.out.println("TimeMeasurer");
        String name = "runTime";
        TimeMeasurer tm = new TimeMeasurer();
        for (int i = 0; i < 5; i++) {
            name = "runTime_" + i;
            tm.start(name);
            Thread.sleep(5);
            tm.stop(name);
            assertEquals(true, tm.spend(name) >= 5);
        }
        assertNotSame(tm.getSpendMapping(), "[]");
    }
}