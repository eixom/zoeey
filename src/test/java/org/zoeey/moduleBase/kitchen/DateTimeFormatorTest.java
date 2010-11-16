/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.moduleBase.kitchen;

import org.zoeey.util.TimeFormatHelper;
import java.util.Date;
import junit.framework.TestCase;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class DateTimeFormatorTest extends TestCase {

    /**
     *
     * @param testName
     */
    public DateTimeFormatorTest(String testName) {
        super(testName);
    }

    /**
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     *
     * @throws Exception
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    private String pattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * Test of timeFormat method, of class DateTimeFormator.
     */
    public void testTimeFormat_long_String() {
        System.out.println("timeFormat");
        long time = System.currentTimeMillis();

        String result = TimeFormatHelper.format(time, pattern);
        if (result == null) {
            fail("The test case is a prototype.");
        }
        System.out.println("time:" + time);
        System.out.println("pattern:" + pattern);
        System.out.println("result:" + result);
    }

    /**
     * Test of timeFormat method, of class DateTimeFormator.
     */
    public void testTimeFormat_Date_String() {
        System.out.println("timeFormat");
        Date _date = new Date();
        String result = TimeFormatHelper.format(_date, pattern);
        System.out.println("result：" + result);
    }
}
