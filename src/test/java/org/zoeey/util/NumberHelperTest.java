/*
 * MoXie (SysTem128@GMail.Com) 2009-8-11 12:09:49
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.NumberHelper;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class NumberHelperTest {

    /**
     *
     */
    public NumberHelperTest() {
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
     * Test of baseConver method, of class NumberHelper.
     */
    @Test
    public void testBaseConver() {
        System.out.println("baseConver");
        /**
         * 16 进制转 10进制
         */
        String numStr = "a";
        int fromBase = 16;
        int toBase = 10;
        String expResult = "10";
        String result;//
        result = NumberHelper.baseConver(numStr, fromBase, toBase);
        assertEquals(expResult, result);
        /**
         * 10 进制转 36 进制
         */
        numStr = "36";
        fromBase = 10;
        toBase = 36;
        expResult = "10";
        result = NumberHelper.baseConver(numStr, fromBase, toBase);
        assertEquals(expResult, result);
        numStr = "363213";
        fromBase = 10;
        toBase = 36;
        expResult = "7s99";
        result = NumberHelper.baseConver(numStr, fromBase, toBase);
        assertEquals(expResult, result);
        /**
         * 3 进制转 6 进制
         */
        numStr = "122";
        fromBase = 3;
        toBase = 6;
        expResult = "25";
        result = NumberHelper.baseConver(numStr, fromBase, toBase);
        assertEquals(expResult, result);
        /**
         * 7 进制转 22 进制
         */
        numStr = "356";
        fromBase = 7;
        toBase = 22;
        expResult = "8c";
        result = NumberHelper.baseConver(numStr, fromBase, toBase);
        assertEquals(expResult, result);
        /**
         * 7 进制转 2 进制
         */
        numStr = "356";
        fromBase = 7;
        toBase = 2;
        expResult = "10111100";
        result = NumberHelper.baseConver(numStr, fromBase, toBase);
        assertEquals(expResult, result);
        /**
         * 36 进制转 16 进制
         */
        numStr = "zzzzzz";
        fromBase = 36;
        toBase = 16;
        expResult = "81bf0fff";
        result = NumberHelper.baseConver(numStr, fromBase, toBase);
        assertEquals(expResult, result);
    }

    /**
     * Test of any2dec method, of class NumberHelper.
     */
    @Test
    public void testDec2Any() {
        System.out.println("dec2Any");
        /**
         * 16进制
         */
        String expResult = "a";
        int toBase = 16;
        long number = 10;
        String result = NumberHelper.dec2Any(number, toBase);
        assertEquals(expResult, result);
        expResult = "36211653";
        toBase = 7;
        number = 3213213;
        result = NumberHelper.dec2Any(number, toBase);
        assertEquals(expResult, result);
        /**
         * 22 进制
         */
        expResult = "3feh";
        toBase = 22;
        number = 39529;
        result = NumberHelper.dec2Any(number, toBase);
        assertEquals(expResult, result);
        /**
         * 36 进制
         */
        expResult = "1wvcg";
        toBase = 36;
        number = 3213232;
        result = NumberHelper.dec2Any(number, toBase);
        assertEquals(expResult, result);
        /**
         * 36 进制
         */
        expResult = "zzzzzz";
        toBase = 36;
        number = 2176782335L;
        result = NumberHelper.dec2Any(number, toBase);
        assertEquals(expResult, result);
        /**
         * 62 进制
         */
        number = NumberHelper.any2Dec("ZZZZZZZZZ", 62);
        assertEquals(13537086546263551L, number);
        assertEquals(NumberHelper.dec2Any(number, 62), "ZZZZZZZZZ");
        /**
         * 62 进制
         */
        String strValue = NumberHelper.dec2Any(Long.MAX_VALUE, 62);
        assertEquals("aZl8N0y58M7", strValue);
        assertEquals(NumberHelper.any2Dec(strValue, 62), Long.MAX_VALUE);
        /**
         * 2 进制
         */
        assertEquals(NumberHelper.dec2Any(Long.MAX_VALUE, NumberHelper.MIN_RADIX),//
                "111111111111111111111111111111111111111111111111111111111111111");
        assertEquals(NumberHelper.any2Dec("111111111111111111111111111111111111111111111111111111111111111", 2),//
                Long.MAX_VALUE);
        /**
         * 2 进制
         * JDK 1.5
         */
        assertEquals(NumberHelper.dec2Any(Long.MIN_VALUE, NumberHelper.MIN_RADIX),//
                "-1000000000000000000000000000000000000000000000000000000000000000");
        assertEquals(NumberHelper.any2Dec("-1000000000000000000000000000000000000000000000000000000000000000", 2),//
                -Long.MIN_VALUE);
        assertEquals(NumberHelper.any2Dec("-1000000000000000000000000000000000000000000000000000000000000000", 2),//
                Long.MIN_VALUE);
        assertEquals(NumberHelper.any2Dec("-1000000000000000000000000000000000000000000000000000000000000000", 2),//
                -Long.MIN_VALUE);
        assertEquals(Long.MIN_VALUE, -Long.MIN_VALUE);
        /**
         * short url?
         */
        assertEquals(NumberHelper.dec2Any(56800235583L, 62), "ZZZZZZ");

    }

    /**
     * Test of any2dec method, of class NumberHelper.
     */
    @Test
    public void testAny2dec() {
        System.out.println("any2dec");
        /**
         * 16进制
         */
        String num = "a";
        int base = 16;
        long expResult = 10;
        long result = NumberHelper.any2Dec(num, base);
        assertEquals(expResult, result);
        num = "36211653";
        base = 7;
        expResult = 3213213;
        result = NumberHelper.any2Dec(num, base);
        assertEquals(expResult, result);
        /**
         * 22 进制
         */
        num = "3feh";
        base = 22;
        expResult = 39529;
        result = NumberHelper.any2Dec(num, base);
        assertEquals(expResult, result);
        /**
         * 36 进制
         */
        num = "1wvcg";
        base = 36;
        expResult = 3213232;
        result = NumberHelper.any2Dec(num, base);
        assertEquals(expResult, result);
    }
}
