/*
 * MoXie (SysTem128@GMail.Com) 2009-4-11 17:27:31
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.TimeFormatHelper;
import java.util.Date;
import java.util.TimeZone;
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
public class TimeFormatHelperTest {

    /**
     *
     */
    public TimeFormatHelperTest() {
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
    private static String pattern = "yyyy-MM-dd HH:mm:ss";
    private static long millisTime = 1239438840786L;
    private static long millisTimeNomill = 1239438840000L;

    /**
     * Test of format method, of class TimeFormatHelper.
     */
    @Test
    public void testFormat_long_String() {
        System.out.println("format");
        String expResult = "2009-04-11 16:34:00";
        String result = TimeFormatHelper.format(millisTime, pattern);
        assertEquals(expResult, result);


    }

    /**
     * Test of format method, of class TimeFormatHelper.
     */
    @Test
    public void testFormat_Date_String() {
        System.out.println("format");
        Date date = new Date();
        date.setTime(millisTime);
        String expResult = "2009-04-11 16:34:00";
        String result = TimeFormatHelper.format(date, pattern);
        assertEquals(expResult, result);


    }

    /**
     * Test of strToTime method, of class TimeFormatHelper.
     */
    @Test
    public void testStrToTime_String() {
        System.out.println("strToTime");
        String timeStr = "2009-04-11 16:34:00";
        long expResult = millisTimeNomill;
        long result = TimeFormatHelper.strToTime(timeStr);
        assertEquals(expResult, result);


    }

    /**
     * Test of strToTime method, of class TimeFormatHelper.
     */
    @Test
    public void testStrToTime_String_String() {
        System.out.println("strToTime");
        String timeStr = "2009-04-11 16:34:00";
        String format = pattern;
        long expResult = millisTimeNomill;
        long result = TimeFormatHelper.strToTime(timeStr, format);
        assertEquals(expResult, result);


    }

    /**
     * Test of strToTime method, of class TimeFormatHelper.
     */
    @Test
    public void testStrToTime_3args() {
        System.out.println("strToTime");
        String timeStr = "2009-04-11 16:34:00";
        String format = pattern;
        TimeZone timeZone = TimeZone.getDefault();
        long expResult = millisTimeNomill;
        long result = TimeFormatHelper.strToTime(timeStr, format, timeZone);
        assertEquals(expResult, result);


    }
}