/*
 * MoXie (SysTem128@GMail.Com) 2009-9-3 16:12:37
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.RandomHelper;
import org.zoeey.util.NumberHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class RandomHelperTest {

    /**
     *
     */
    public RandomHelperTest() {
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
     *
     */
    @Test
    public void testToInt() {
        System.out.println("toInt");

        for (int i = 0; i < 100; i++) {
            assertTrue(RandomHelper.toInt(100, 50) >= 50 //
                    && RandomHelper.toInt(50, 100) <= 100 //
                    && RandomHelper.toInt(100, 100) == 100);
        }
    }

    /**
     *
     */
    @Test
    public void testToString_2arg() {
        System.out.println("toString_2arg");
        for (int i = 0; i < 10; i++) {
            assertTrue(RandomHelper.toString(5, 9).length() > 4 //
                    && RandomHelper.toString(5, 9).length() < 10);
        }
    }

    /**
     *
     */
    @Test
    public void testToString_int() {
        System.out.println("toString_int");

        for (int i = 0; i < 10; i++) {
            assertTrue(RandomHelper.toString(5).length() == 5);
        }
    }

    /**
     *
     */
    @Test
    public void testToTimeString() {
        System.out.println("toTimeString");
        String time;
        for (int i = 0; i < 100; i++) {
            time = RandomHelper.toTimeString(62);
            assertTrue(NumberHelper.any2Dec(time, 62) <= System.currentTimeMillis());
        }
    }
}
