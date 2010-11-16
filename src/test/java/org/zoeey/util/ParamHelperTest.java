/*
 * MoXie (SysTem128@GMail.Com) 2009-4-14 16:25:10
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.ParamHelper;
import org.zoeey.util.NumberHelper;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ParamHelperTest extends TestCase {

    /**
     *
     */
    public ParamHelperTest() {
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
    @Override
    public void setUp() {
    }

    /**
     *
     */
    @After
    @Override
    public void tearDown() {
    }

    /**
     * Test of genParam method, of class ParamHelper.
     */
    @Test
    public void testFeasibility() {
        int args = ParamHelper.AND_0 & ParamHelper.AND_2 & ParamHelper.AND_3;
        assertTrue(ParamHelper.contain(args, ParamHelper.AND_2));
        args = args ^ ~ParamHelper.AND_2;
        assertFalse(ParamHelper.contain(args, ParamHelper.AND_2));
        if (true) {
            return;
        }
        System.out.println(Long.toBinaryString(1 << 25));
        System.out.println(Long.toBinaryString(1 << 28));
        System.out.println(Long.toBinaryString(1 << 29));
        System.out.println(Long.toBinaryString(1 << 30));
        System.out.println(Long.toBinaryString((1 << 31) - 1));
        System.out.println(Long.toBinaryString(Long.MAX_VALUE));
        System.out.println(Long.toBinaryString(Integer.MAX_VALUE));
        System.out.println(Double.toHexString(Double.MAX_VALUE));
        for (int j = 0; j < 31; j++) {
//            System.out.println("public static final int PLUS_" + j + " = 0x" + NumberHelper.dec2Any(ParamHelper.genParamPlus(j), 16) + ";");
            System.out.println("public static final int OR_" + j + " = 0x" + NumberHelper.dec2Any(ParamHelper.genParamOr(j), 16) + ";");
        }
        if (true) {
            return;
        }

        int a = 0xff ^ 0x01;
        int b = 0xff ^ 0x02;
        int c = 0xff ^ 0x04;
        int d = 0xff ^ 0x08;
        int e = 0xff ^ 0x10;
        int f = 0xff ^ 0x20;
        int g = (a & b & c);// d | e;
        System.out.printf("%6s \n", Long.toBinaryString(g));
        System.out.println(g & (0xff ^ a));
        System.out.println(g & (0xff ^ b));
        System.out.println(g & (0xff ^ c));
        System.out.println(g & (0xff ^ d));
        System.out.println(g & (0xff ^ f));
        System.out.println(g & (0xff ^ e));
        System.out.printf("%6s \n", Long.toBinaryString(a));
        System.out.printf("%6s \n", Long.toBinaryString(b));
        System.out.printf("%6s \n", Long.toBinaryString(c));
        System.out.printf("%6s \n", Long.toBinaryString(d));
        System.out.printf("%6s \n", Long.toBinaryString(e));
    }

    /**
     * Test of genParam method, of class ParamHelper.
     */
    @Test
    public void testGenParam() {
        System.out.println("genParam");
        int a = ParamHelper.genParam(0);
        int b = ParamHelper.genParam(1);
        int c = ParamHelper.genParam(2);
        int d = ParamHelper.genParam(3);
        int e = ParamHelper.genParam(4);
        int j = ParamHelper.genParam(15);
    }

    /**
     *
     */
    @Test
    public void testContain() {
        System.out.println("contain");
        int a = ParamHelper.genParam(0);
        int b = ParamHelper.genParam(1);
        int c = ParamHelper.genParam(2);
        int d = ParamHelper.genParam(3);
        int j = ParamHelper.genParam(15);
        int h = ParamHelper.genParam(30);
        int args = a & b & c & h;
        assertTrue(ParamHelper.contain(args, a));
        args = ~args ^ a;
        assertFalse(ParamHelper.contain(args, a));
        assertTrue(ParamHelper.contain(args, b));
        assertTrue(ParamHelper.contain(args, c));
        assertTrue(ParamHelper.contain(args, h));
        assertFalse(ParamHelper.contain(args, d));
        assertFalse(ParamHelper.contain(args, j));

        args &= j;
        assertTrue(ParamHelper.contain(args, j));
    }

    /**
     *
     */
    @Test
    public void testGenParamPlus() {
        System.out.println("genParamPlus");
        int a = ParamHelper.genParamPlus(0);
        int b = ParamHelper.genParamPlus(1);
        int c = ParamHelper.genParamPlus(2);
        int d = ParamHelper.genParamPlus(3);
        int e = ParamHelper.genParamPlus(4);
        int j = ParamHelper.genParamPlus(15);
        assertEquals(Long.toBinaryString(a), "1");
        assertEquals(Long.toBinaryString(b), "10");
        assertEquals(Long.toBinaryString(c), "100");
        assertEquals(Long.toBinaryString(d), "1000");
        assertEquals(Long.toBinaryString(e), "10000");
        assertEquals(Long.toBinaryString(j), "1000000000000000");

    }

    /**
     *
     */
    @Test
    public void testContainPlus() {
        System.out.println("containPlus");
        int a = ParamHelper.genParamPlus(0);
        int b = ParamHelper.genParamPlus(1);
        int c = ParamHelper.genParamPlus(2);
        int d = ParamHelper.genParamPlus(3);
        int e = ParamHelper.genParamPlus(4);
        int j = ParamHelper.genParamPlus(15);
        int args = a + b + c;
        assertTrue(ParamHelper.containPlus(args, a));
        args = args - a;
        assertFalse(ParamHelper.containPlus(args, a));
        assertTrue(ParamHelper.containPlus(args, b));
        assertTrue(ParamHelper.containPlus(args, c));
        assertFalse(ParamHelper.containPlus(args, d));
        assertFalse(ParamHelper.containPlus(args, e));
        assertFalse(ParamHelper.containPlus(args, j));
        args += j;
        assertTrue(ParamHelper.containPlus(args, j));
    }

    /**
     *
     */
    @Test
    public void testContainOr() {
        System.out.println("containOr");
        int a = ParamHelper.genParamOr(0);
        int b = ParamHelper.genParamOr(1);
        int c = ParamHelper.genParamOr(2);
        int d = ParamHelper.genParamOr(3);
        int e = ParamHelper.genParamOr(4);
        int j = ParamHelper.genParamOr(15);
        int args = a | b | c;
        assertTrue(ParamHelper.containOr(args, a));
        args = args ^ a;
        assertFalse(ParamHelper.containOr(args, a));
        assertTrue(ParamHelper.containOr(args, b));
        assertTrue(ParamHelper.containOr(args, c));
        assertFalse(ParamHelper.containOr(args, d));
        assertFalse(ParamHelper.containOr(args, e));
        assertFalse(ParamHelper.containOr(args, j));
        args += j;
        assertTrue(ParamHelper.containPlus(args, j));
    }
    private static final int m = ParamHelper.genParam(0);
    private static final int o = ParamHelper.genParam(1);
    private static final int x = ParamHelper.genParam(2);
    private static final int i = ParamHelper.genParam(3);
    private static final int e = ParamHelper.genParam(4);

    private String printMyName(int name) {
        StringBuffer strBuffer = new StringBuffer(5);
        if (ParamHelper.contain(name, m)) {
            strBuffer.append("m");
        }
        if (ParamHelper.contain(name, o)) {
            strBuffer.append("o");
        }
        if (ParamHelper.contain(name, x)) {
            strBuffer.append("x");
        }
        if (ParamHelper.contain(name, i)) {
            strBuffer.append("i");
        }
        if (ParamHelper.contain(name, e)) {
            strBuffer.append("e");
        }
        return strBuffer.toString();
    }

    /**
     *
     */
    @Test
    public void testPrintMyName() {
        assertEquals(printMyName(m & x), "mx");
        assertEquals(printMyName(m & o), "mo");
        assertEquals(printMyName(e & m & o & x & i), "moxie"); // 这里是为了说明参数是没有顺序的。
    }
    private static final int _m = ParamHelper.genParamPlus(0);
    private static final int _o = ParamHelper.genParamPlus(1);
    private static final int _x = ParamHelper.genParamPlus(2);
    private static final int _i = ParamHelper.genParamPlus(3);
    private static final int _e = ParamHelper.genParamPlus(4);

    private String printMyNamePlus(int name) {
        StringBuffer strBuffer = new StringBuffer(5);
        if (ParamHelper.containPlus(name, _m)) {
            strBuffer.append("m");
        }
        if (ParamHelper.containPlus(name, _o)) {
            strBuffer.append("o");
        }
        if (ParamHelper.containPlus(name, _x)) {
            strBuffer.append("x");
        }
        if (ParamHelper.containPlus(name, _i)) {
            strBuffer.append("i");
        }
        if (ParamHelper.containPlus(name, _e)) {
            strBuffer.append("e");
        }
        return strBuffer.toString();
    }

    /**
     *
     */
    @Test
    public void testPrintMyNamePlus() {
        assertEquals(printMyNamePlus(_m + _x), "mx");
        assertEquals(printMyNamePlus(_m + _o), "mo");
        assertEquals(printMyNamePlus(_e + _m + _o + _x + _i), "moxie"); // 这里是为了说明参数是没有顺序的。

    }
}
