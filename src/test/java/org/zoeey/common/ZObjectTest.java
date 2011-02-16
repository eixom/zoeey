/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common;

import junit.framework.TestCase;
import org.zoeey.util.TimeMeasurer;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZObjectTest extends TestCase {

    /**
     *
     * @param testName
     */
    public ZObjectTest(String testName) {
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

    /**
     * Test of toString method, of class ZObject.
     */
    public void testToString() {
        System.out.println("toString");
        ZObject instance = new ZObject("i'm String");
        String expResult = "i'm String";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toInt method, of class ZObject.
     */
    public void testToInt() {
        System.out.println("toInt");
        ZObject instance = new ZObject("I'm string");
        int expResult = 0;
        int result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         *
         */
        instance = new ZObject("123I'm string");
        expResult = 123;
        result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         *
         */
        instance = new ZObject("123.5I'm string");
        expResult = 123;
        result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         *
         */
        Object obj = null;
        instance = new ZObject(obj);
        expResult = 0;
        result = instance.toInteger();
        assertEquals(expResult, result);
    }

    /**
     * Test of toInteger method, of class ZObject.
     */
    public void testToInteger() {
        System.out.println("toInteger");
        ZObject instance = new ZObject("I'm string");
        Integer expResult = new Integer(0);
        Integer result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         *
         */
        instance = new ZObject("123I'm string");
        expResult = new Integer(123);
        result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         *
         */
        instance = new ZObject("123.5I'm string");
        expResult = new Integer(123);
        result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         *
         */
        instance = new ZObject("+123I'm string");
        expResult = new Integer(123);
        result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         *
         */
        instance = new ZObject("-123.5I'm string");
        expResult = new Integer(-123);
        result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         *  scientific notation
         */
        instance = new ZObject("-123e-5I'm string");
        expResult = new Integer(-123);
        result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         * hex
         */
        instance = new ZObject("0x123I'm string");
        expResult = new Integer(0x123);
        result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         *
         */
        instance = new ZObject("+0x123I'm string");
        expResult = new Integer(0x123);
        result = instance.toInteger();
        assertEquals(expResult, result);
        /**
         *
         */
        instance = new ZObject("-0x123I'm string");
        expResult = new Integer(-0x123);
        result = instance.toInteger();
        assertEquals(expResult, result);

    }

    /**
     * Test of toDouble method, of class ZObject.
     */
    public void testToFloat() {
        System.out.println("toFloat");
        ZObject instance = new ZObject("a String~");
        float expResult = 0.0F;
        float result = instance.toFloat();
        assertTrue(expResult == result);
        /**
         *
         */
        instance = new ZObject("+a String~");
        result = instance.toFloat();
        assertTrue(expResult == result);
        /**
         *
         */
        instance = new ZObject("+11.01a String~");
        expResult = 11.01F;
        result = instance.toFloat();
        assertTrue(expResult == result);
        /**
         *
         */
        instance = new ZObject("+11.a String~");
        expResult = 11.0F;
        result = instance.toFloat();
        assertTrue(expResult == result);
        /**
         *
         */
        Object obj = null;
        instance = new ZObject(obj);
        expResult = 0F;
        result = instance.toFloat();
        assertEquals(expResult, result);
    }

    /**
     * Test of toDouble method, of class ZObject.
     */
    public void testToDouble() {
        System.out.println("toDouble");
        ZObject instance = new ZObject("a String~");
        double expResult = 0.0D;
        double result = instance.toDouble();
        assertTrue(expResult == result);
        //
        instance = new ZObject("+a String~");
        result = instance.toDouble();
        assertTrue(expResult == result);
        //
        instance = new ZObject("+11.01a String~");
        expResult = 11.01D;
        result = instance.toDouble();
        assertTrue(expResult == result);
        //
        instance = new ZObject("+11.a String~");
        expResult = 11.0D;
        result = instance.toDouble();
        assertTrue(expResult == result);
        //
        instance = new ZObject("+6.1E5String~");
        expResult = 610000.0;
        result = instance.toDouble();
        assertTrue(expResult == result);
        //
        instance = new ZObject("+6.1E-5String~");
        expResult = 0.000061D;
        result = instance.toDouble();
        assertTrue(expResult == result);
        //
        instance = new ZObject("+6.1E+5String~");
        expResult = 610000.0D;
        result = instance.toDouble();
        assertTrue(expResult == result);
        //
        instance = new ZObject("+6.1E+6000String~");
        expResult = 0.0D;
        result = instance.toDouble();
        assertTrue(expResult == result);
        //
        instance = new ZObject("+String~");
        expResult = 0.0D;
        result = instance.toDouble();
        assertTrue(expResult == result);
        //
        instance = new ZObject("-String~");
        expResult = 0.0D;
        result = instance.toDouble();
        assertTrue(expResult == result);
        //
        instance = new ZObject("-0xString~");
        expResult = 0.0D;
        result = instance.toDouble();
        assertTrue(expResult == result);
        /**
         *
         */
        Object obj = null;
        instance = new ZObject(obj);
        expResult = 0D;
        result = instance.toDouble();
        assertEquals(expResult, result);

        if (true) {
            return;
        }
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        for (int i = 0; i < 100000; i++) {
            new ZObject("+6.1E+5String~").toDouble();
        }
        /**
         * 100000
         * 569ms
         * 374ms
         * 523ms
         */
        System.out.println(tm.spend());
        tm.clear();
    }

    /**
     * Test of toBoolean method, of class ZObject.
     */
    public void testToBoolean() {
        System.out.println("toBoolean");
        ZObject instance = new ZObject();
        boolean expResult = false;
        boolean result = instance.toBoolean();
        assertEquals(expResult, result);
        //
        instance = new ZObject("a String");
        expResult = true;
        result = instance.toBoolean();
        assertEquals(expResult, result);
        //
        instance = new ZObject("true");
        expResult = true;
        result = instance.toBoolean();
        assertEquals(expResult, result);
        //
        instance = new ZObject("false");
        expResult = false;
        result = instance.toBoolean();
        assertEquals(expResult, result);
        //
        instance = new ZObject("0");
        expResult = false;
        result = instance.toBoolean();
        assertEquals(expResult, result);
        //
        Object obj = null;
        instance = new ZObject(obj);
        expResult = false;
        result = instance.toBoolean();
        assertEquals(expResult, result);
    }

    /**
     * Test of toIntString method, of class ZObject.
     */
    public void testToIntString() {
        System.out.println("toIntString");
        ZObject instance = new ZObject("I'm string");
        String expResult = "0";
        String result = instance.toIntString();
        assertEquals(expResult, result);
        instance = new ZObject("123I'm string");
        expResult = "123";
        result = instance.toIntString();
        assertEquals(expResult, result);
        instance = new ZObject("123.5I'm string");
        expResult = "123";
        result = instance.toIntString();
        assertEquals(expResult, result);

    }

    /**
     * Test of toLong method, of class ZObject.
     */
    public void testToLong() {
        System.out.println("toLong");
        ZObject instance = new ZObject("1111111aString");
        long expResult = 1111111L;
        long result = instance.toLong();
        assertEquals(expResult, result);
        //
        instance = new ZObject("+1111111aString");
        expResult = +1111111L;
        result = instance.toLong();
        assertEquals(expResult, result);
        // max
        instance = new ZObject(Long.MAX_VALUE + "11111111111aString");
        expResult = 0;
        result = instance.toLong();
        assertTrue(expResult == result);

    }

    /**
     * Test of toShort method, of class ZObject.
     */
    public void testToShort() {
        System.out.println("toShort");
        ZObject instance = new ZObject();
        short expResult = 0;
        short result = instance.toShort();
        assertEquals(expResult, result);
    }

    /**
     * Test of toShort method, of class ZObject.
     */
    public void testToByte() {
        System.out.println("toByte");
        ZObject instance = new ZObject();
        byte expResult = 0;
        byte result = instance.toByte();
        assertEquals(expResult, result);
    }

    /**
     *
     */
    public void testPhp() {

        boolean expBoolean = true;
        //
        ZObject zoTRUE = new ZObject(Boolean.TRUE);
        //
        ZObject zoFALSE = new ZObject(Boolean.FALSE);
        //
        ZObject zo1 = new ZObject(1);
        //
        ZObject zo0 = new ZObject(0);
        //
        ZObject zo_1 = new ZObject(-1);
        //
        ZObject zoStr1 = new ZObject("1");
        //
        ZObject zoStr0 = new ZObject("0");
        //
        ZObject zoStr_1 = new ZObject("-1");
        //
        Object obj = null;
        ZObject zoNULL = new ZObject(obj);
        //
        ZObject zoARRAY = new ZObject(Boolean.FALSE);
        //
        ZObject zoStrPHP = new ZObject("php");
        // TRUE: TRUE FALSE TRUE FALSE TRUE TRUE FALSE TRUE FALSE FALSE TRUE
        assertTrue(zoTRUE.toBoolean() == zoTRUE.toBoolean());
        assertFalse(zoTRUE.toBoolean() == zoFALSE.toBoolean());
        assertTrue(zoTRUE.toBoolean() == zo1.toBoolean());
        assertFalse(zoTRUE.toBoolean() == zo0.toBoolean());
        assertTrue(zoTRUE.toBoolean() == zo_1.toBoolean());
        assertTrue(zoTRUE.toBoolean() == zoStr1.toBoolean());
        assertFalse(zoTRUE.toBoolean() == zoStr0.toBoolean());
        assertTrue(zoTRUE.toBoolean() == zoStr_1.toBoolean());
        assertFalse(zoTRUE.toBoolean() == zoNULL.toBoolean());
        assertFalse(zoTRUE.toBoolean() == zoARRAY.toBoolean());
        assertTrue(zoTRUE.toBoolean() == zoStrPHP.toBoolean());
        // FALSE: FALSE TRUE FALSE TRUE FALSE FALSE TRUE FALSE TRUE TRUE FALSE
        assertFalse(zoFALSE.toBoolean() == zoTRUE.toBoolean());
        assertTrue(zoFALSE.toBoolean() == zoFALSE.toBoolean());
        assertFalse(zoFALSE.toBoolean() == zo1.toBoolean());
        assertTrue(zoFALSE.toBoolean() == zo0.toBoolean());
        assertFalse(zoFALSE.toBoolean() == zo_1.toBoolean());
        assertFalse(zoFALSE.toBoolean() == zoStr1.toBoolean());
        assertTrue(zoFALSE.toBoolean() == zoStr0.toBoolean());
        assertFalse(zoFALSE.toBoolean() == zoStr_1.toBoolean());
        assertTrue(zoFALSE.toBoolean() == zoNULL.toBoolean());
        assertTrue(zoFALSE.toBoolean() == zoARRAY.toBoolean());
        assertFalse(zoFALSE.toBoolean() == zoStrPHP.toBoolean());
        // 1 TRUE FALSE TRUE FALSE FALSE TRUE FALSE FALSE FALSE FALSE FALSE
//        assertFalse(zo1.toBoolean() == zoTRUE.toBoolean());
//        assertTrue(zo1.toBoolean() == zoFALSE.toBoolean());
//        assertFalse(zo1.toBoolean() == zo1.toBoolean());
//        assertTrue(zo1.toBoolean() == zo0.toBoolean());
//        assertFalse(zo1.toBoolean() == zo_1.toBoolean());
//        assertFalse(zo1.toBoolean() == zoStr1.toBoolean());
//        assertTrue(zo1.toBoolean() == zoStr0.toBoolean());
//        assertFalse(zo1.toBoolean() == zoStr_1.toBoolean());
//        assertTrue(zo1.toBoolean() == zoNULL.toBoolean());
//        assertTrue(zo1.toBoolean() == zoARRAY.toBoolean());
//        assertFalse(zo1.toBoolean() == zoStrPHP.toBoolean());
        //
    }

    /**
     *
     */
    public void testToType_const() {
        ZObject instance;
        /**
         * 
         */
        short expResult_short = 0;
        short result_short;
        instance = new ZObject();
        result_short = instance.toType(ZObject.TO_TYPE_SHORT);
        assertEquals(expResult_short, result_short);
        /**
         * int
         */
        int expResult_int = 0;
        int result_int;
        instance = new ZObject();
        result_int = instance.toType(ZObject.TO_TYPE_INT);
        assertEquals(expResult_int, result_int);
    }

    /**
     *
     */
    public void testConv() {
        System.out.println("testConv");
        assertEquals(ZObject.conv(1).toIntString(), "1");
        assertEquals(ZObject.conv("123,132abc").toIntString(), "123");
        assertEquals(ZObject.conv("123").toIntString(), "123");
        assertEquals(ZObject.conv("     123").toIntString(), "123");
        assertEquals(ZObject.conv("abc123").toIntString(), "0");
        assertEquals(ZObject.conv("26").toIntString(), "26"); // decimal
        assertEquals(ZObject.conv("032").toIntString(), "26"); // octal
        assertEquals(ZObject.conv("0x1a").toIntString(), "26"); // hexadecimal
    }
}
