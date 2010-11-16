/*
 * MoXie (SysTem128@GMail.Com) 2009-4-11 16:28:23
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.TimeHelper;
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
public class TimeHelperTest {

    /**
     *
     */
    public TimeHelperTest() {
    }
    private static TimeHelper timeHelper = null;

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
//        long millisTime = System.currentTimeMillis();
        long millisTime = 1239438840786L;
        timeHelper = new TimeHelper(millisTime);

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
     * Test of monthStart method, of class TimeHelper.
     */
    @Test
    public void testMonthStart() {
        System.out.println("monthStart");
        long expResult = 1238515200000L;
        long result = timeHelper.monthStart();
        assertEquals(expResult, result);
    }

    /**
     * Test of monthEnd method, of class TimeHelper.
     */
    @Test
    public void testMonthEnd() {
        System.out.println("monthEnd");
        long expResult = 1241107200000L;
        long result = timeHelper.monthEnd();
        assertEquals(expResult, result);
    }

    /**
     * Test of weekStart method, of class TimeHelper.
     */
    @Test
    public void testWeekStart() {
        System.out.println("weekStart");
        long expResult = 1238860800000L;
        long result = timeHelper.weekStart();
        assertEquals(expResult, result);
    }

    /**
     * Test of weekEnd method, of class TimeHelper.
     */
    @Test
    public void testWeekEnd() {
        System.out.println("weekEnd");
        long expResult = 1239465600000L;
        long result = timeHelper.weekEnd();
        assertEquals(expResult, result);
    }

    /**
     * Test of today method, of class TimeHelper.
     */
    @Test
    public void testToday() {
        System.out.println("today");
        long expResult = 1239408000000L;
        long result = timeHelper.today();
        assertEquals(expResult, result);
    }

    /**
     * Test of tomorrow method, of class TimeHelper.
     */
    @Test
    public void testTomorrow() {
        System.out.println("tomorrow");
        long expResult = 1239494400000L;
        long result = timeHelper.tomorrow();
        assertEquals(expResult, result);
    }

    /**
     * Test of yesterday method, of class TimeHelper.
     */
    @Test
    public void testYesterday() {
        System.out.println("yesterday");
        long expResult = 1239321600000L;
        long result = timeHelper.yesterday();
        assertEquals(expResult, result);
    }
}