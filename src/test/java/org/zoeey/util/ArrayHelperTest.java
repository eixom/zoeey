/*
 * MoXie (SysTem128@GMail.Com) 2009-7-27 11:28:12
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.ArrayHelper;
import org.zoeey.util.JsonHelper;
import java.util.Map;
import junit.framework.TestCase;
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
public class ArrayHelperTest extends TestCase {

    /**
     *
     */
    public ArrayHelperTest() {
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
     * Test of sizeOf method, of class ArrayHelper.
     */
    @Test
    public void testSizeOf() {
        System.out.println("sizeOf");
        int[] ints = new int[]{1, 3, 5};
        int result = ArrayHelper.sizeOf(ints);
        assertEquals(result, 3);
        ints = null;
        result = ArrayHelper.sizeOf(ints);
        assertEquals(result, 0);
    }

    /**
     * Test of merge method, of class ArrayHelper.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");
        int[][] args = new int[][]{new int[]{1, 3, 5}, new int[]{2, 4, 6}};
        int[] result = ArrayHelper.merge(args);
        assertArrayEquals(result, new int[]{1, 3, 5, 2, 4, 6});
        result = ArrayHelper.merge(new int[]{1, 3, 5}, new int[]{3, 6, 9});
        assertArrayEquals(result, new int[]{1, 3, 5, 3, 6, 9});
    }

    /**
     * Test of diff method, of class ArrayHelper.
     */
    @Test
    public void testDiff() {
        System.out.println("diff");
        int[] intsa = new int[]{1, 2, 3, 5};
        int[] intsb = new int[]{3, 5, 6, 8};
        int[] result = ArrayHelper.diff(intsa, intsb);
        assertArrayEquals(result, new int[]{1, 2, 6, 8});
        /**
         *
         */
        result = ArrayHelper.diff(intsa, intsa);
        assertArrayEquals(result, new int[]{});
        /**
         *
         */
        intsa = new int[]{1, 2, 3, 5};
        intsb = new int[]{11, 12};
        result = ArrayHelper.diff(intsa, intsb);
        assertArrayEquals(result, new int[]{1, 2, 3, 5, 11, 12});
        /**
         *
         */
        intsa = new int[]{11, 12};
        intsb = new int[]{1, 2, 3, 5};
        result = ArrayHelper.diff(intsa, intsb);
        assertArrayEquals(result, new int[]{11, 12, 1, 2, 3, 5});
    }

    /**
     * Test of combine method, of class ArrayHelper.
     */
    @Test
    public void testCombine() {
        System.out.println("combine");
        String[] intsa = new String[]{"a", "b", "c", "d"};
        Integer[] intsb = new Integer[]{3, 5, 6, 8};
        Map<String, Integer> result = ArrayHelper.combine(intsa, intsb);
        assertEquals(JsonHelper.encode(result), "{\"d\":8,\"a\":3,\"c\":6,\"b\":5}");

        intsb = new Integer[]{3, 5, 6, 8, 10, 20};
        result = ArrayHelper.combine(intsa, intsb);
        assertEquals(JsonHelper.encode(result), "{\"d\":8,\"a\":3,\"c\":6,\"b\":5}");

        intsb = new Integer[]{3, 5};
        result = ArrayHelper.combine(intsa, intsb);
        assertEquals(JsonHelper.encode(result), "{\"d\":null,\"a\":3,\"c\":null,\"b\":5}");

        intsb = null;
        result = ArrayHelper.combine(intsa, intsb);
        assertEquals(JsonHelper.encode(result), "{\"d\":null,\"a\":null,\"c\":null,\"b\":null}");
    }

    /**
     * Test of copyOf method, of class ArrayHelper.
     */
    @Test
    public void testCopyOf() {
        System.out.println("copyOf");
        int[] arr = new int[]{1, 3, 5};
        int[] result = ArrayHelper.copyOf(arr);
        assertArrayEquals(result, arr);
    }

    /**
     * Test of rand method, of class ArrayHelper.
     * 本测试使用了未测试的 {@link ArrayHelper#inArray(int[], int[])} 函数。
     * 所以尽在 {@link ArrayHelper#inArray(int[], int[])} 测试有效时可信。
     */
    @Test
    public void testRandom() {
        System.out.println("random");
        int[] arr = new int[]{1, 3, 5, 6, 7, 98};
        int[] result = ArrayHelper.random(arr, 3);
        assertTrue(ArrayHelper.inArray(arr, result));

        arr = new int[]{1, 3, 5, 6, 7, 98};
        result = ArrayHelper.random(arr, 6);
        assertTrue(ArrayHelper.inArray(arr, result));

        result = ArrayHelper.random(arr);
        assertTrue(ArrayHelper.inArray(arr, result));
    }

    /**
     *
     */
    @Test
    public void testShuffle() {
        System.out.println("shuffle");
        int[] arr = new int[]{1, 3, 5, 6, 7, 98};
        int[] result = ArrayHelper.shuffle(arr);
        assertTrue(ArrayHelper.inArray(arr, result));
    }

    /**
     * Test of inArray method, of class ArrayHelper.
     */
    @Test
    public void testInArray_intArr_int() {
        System.out.println("inArray int");
        int[] arr = new int[]{1, 2, 3, 5, 0};
        int search = 3;
        boolean expResult = true;
        boolean result = ArrayHelper.inArray(arr, search);
        assertEquals(expResult, result);
        search = 31;
        expResult = false;
        result = ArrayHelper.inArray(arr, search);
        assertEquals(expResult, result);
    }

    /**
     * Test of inArray method, of class ArrayHelper.
     * 此测试失败时请注意 {@link #testRand}
     */
    @Test
    public void testInArray_intArr_intArr() {
        System.out.println("inArray int[]");
        int[] arr = new int[]{1, 2, 3, 5, 0};
        int[] search = new int[]{2, 3};
        boolean expResult = true;
        boolean result = ArrayHelper.inArray(arr, search);
        assertEquals(expResult, result);
        search = new int[]{2, 3, 7};
        expResult = false;
        result = ArrayHelper.inArray(arr, search);
        assertEquals(expResult, result);
    }

    /**
     * Test of search method, of class ArrayHelper.
     */
    @Test
    public void testSearch() {
        System.out.println("search");
        int[] arr = new int[]{1, 2, 3, 5, 0};
        int search = 3;
        int expResult = 2;
        int result = ArrayHelper.search(arr, search);
        assertEquals(expResult, result);
        search = 31;
        expResult = -1;
        result = ArrayHelper.search(arr, search);
        assertEquals(expResult, result);
    }

    /**
     * 
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        int[] arr = new int[]{1, 2, 3, 5, 7};
        int[] result = ArrayHelper.reverse(arr);
        assertArrayEquals(result, new int[]{7, 5, 3, 2, 1});
    }

    /**
     *
     */
    @Test
    public void testJoin() {
        System.out.println("join");
        int[] arr = new int[]{1, 2, 3, 5, 7};
        String result = ArrayHelper.join(arr, ",|");
        assertEquals(result, "1,|2,|3,|5,|7");
    }

    /**
     *
     */
    @Test
    public void testSum() {
        System.out.println("sum");
        int[] arr = new int[]{1, 2, 3, 5, 7};
        int result = ArrayHelper.sum(arr);
        assertEquals(result, 18);
    }

    /**
     * Test of sizeOf method, of class ArrayHelper.
     */
    @Test
    public void testSizeOf_ObjectArr() {
        System.out.println("sizeOf");
        Object[] arr = new Object[]{new String("1"), new Integer(2), new Double(3)};
        int result = ArrayHelper.sizeOf(arr);
        assertEquals(result, 3);
        arr = null;
        result = ArrayHelper.sizeOf(arr);
        assertEquals(result, 0);
    }

    /**
     * Test of merge method, of class ArrayHelper.
     */
    @Test
    public void testMerge_ObjectArrArr() {
        System.out.println("merge");
        Object[][] arrs = new Object[][]{new Object[]{new ObjForArrayHelper("MoXie", 1), //
                new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)},//
            new Object[]{new ObjForArrayHelper("MoXie", 7), //
                new ObjForArrayHelper("SysTem128", 8), new ObjForArrayHelper("Zoeey", 9)}
        };
        Object[] expResult = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3),//
            new ObjForArrayHelper("MoXie", 7), //
            new ObjForArrayHelper("SysTem128", 8), new ObjForArrayHelper("Zoeey", 9)};
        Object[] result = ArrayHelper.merge(arrs);
        assertArrayEquals(expResult, result);

        result = ArrayHelper.merge(new Object[]{new ObjForArrayHelper("MoXie", 1), //
                    new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)},//
                new Object[]{new ObjForArrayHelper("MoXie", 7), //
                    new ObjForArrayHelper("SysTem128", 8), new ObjForArrayHelper("Zoeey", 9)});
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of diff method, of class ArrayHelper.
     */
    @Test
    public void testDiff_ObjectArr_ObjectArr() {
        System.out.println("diff");
        Object[] intsa = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)};
        Object[] intsb = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey2", 9)};
        Object[] result = ArrayHelper.diff(intsa, intsb);
        assertArrayEquals(result, new Object[]{new ObjForArrayHelper("Zoeey", 3),//
                    new ObjForArrayHelper("Zoeey2", 9)});
        /**
         *
         */
        result = ArrayHelper.diff(intsa, intsa);
        assertArrayEquals(result, new Object[]{});
        /**
         *
         */
        intsb = new Object[]{new ObjForArrayHelper("Zoeey", 5),//
                    new ObjForArrayHelper("Zoeey2", 9)};
        result = ArrayHelper.diff(intsa, intsb);
        assertArrayEquals(result, new Object[]{new ObjForArrayHelper("MoXie", 1), //
                    new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3),//
                    new ObjForArrayHelper("Zoeey", 5), new ObjForArrayHelper("Zoeey2", 9)
                });
        /**
         *
         */
        intsa = new Object[]{new ObjForArrayHelper("Zoeey", 5), new ObjForArrayHelper("Zoeey2", 9)};
        intsb = new Object[]{new ObjForArrayHelper("MoXie", 1), //
                    new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)};
        result = ArrayHelper.diff(intsa, intsb);
        assertArrayEquals(result, new Object[]{new ObjForArrayHelper("Zoeey", 5), new ObjForArrayHelper("Zoeey2", 9),//
                    new ObjForArrayHelper("MoXie", 1), new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3),});
    }

    /**
     * Test of copyOf method, of class ArrayHelper.
     */
    @Test
    public void testCopyOf_ObjectArr() {
        System.out.println("copyOf");
        Object[] arr = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)};
        Object[] expResult = arr;
        Object[] result = ArrayHelper.copyOf(arr);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of random method, of class ArrayHelper.
     */
    @Test
    public void testRandom_ObjectArr() {
        System.out.println("random");
        Object[] arr = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)};
        Object[] expResult = arr.clone();
        Object[] result = ArrayHelper.random(arr);
        assertTrue(ArrayHelper.inArray(expResult, result));
    }

    /**
     * Test of shuffle method, of class ArrayHelper.
     */
    @Test
    public void testShuffle_ObjectArr() {
        System.out.println("shuffle");
        Object[] arr = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)};
        Object[] expResult = arr.clone();
        Object[] result = ArrayHelper.shuffle(arr);
        assertEquals(result.length, 3);
        assertTrue(ArrayHelper.inArray(expResult, result));
    }

    /**
     * Test of random method, of class ArrayHelper.
     */
    @Test
    public void testRandom_ObjectArr_int() {
        System.out.println("random");
        Object[] arr = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)};
        Object[] expResult = arr.clone();
        Object[] result = ArrayHelper.random(arr, 2);
        assertEquals(result.length, 2);
        result = ArrayHelper.random(arr, 20);
        assertEquals(result.length, 3);
        assertTrue(ArrayHelper.inArray(expResult, result));
    }

    /**
     * Test of inArray method, of class ArrayHelper.
     */
    @Test
    public void testInArray_ObjectArr_Object() {
        System.out.println("inArray");
        Object[] arr = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)};
        Object search = new ObjForArrayHelper("Zoeey", 3);
        boolean expResult = true;
        boolean result = ArrayHelper.inArray(arr, search);
        assertEquals(expResult, result);
    }

    /**
     * Test of inArray method, of class ArrayHelper.
     */
    @Test
    public void testInArray_ObjectArr_ObjectArr() {
        System.out.println("inArray");
        Object[] arr = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)};
        Object[] search = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("Zoeey", 3)};
        boolean expResult = true;
        boolean result = ArrayHelper.inArray(arr, search);
        assertEquals(expResult, result);

//        boolean isAllowed = ArrayHelper.inArray(new String[]{"application/msword"//
//                    , "application/pdf"//
//                    , "application/vnd.openxmlformats-officedocument.wordprocessingml.document"//
//                    , "text/plain"}, "application/msword");//
//        System.out.println(isAllowed);

    }

    /**
     * Test of search method, of class ArrayHelper.
     */
    @Test
    public void testSearch_ObjectArr_Object() {
        System.out.println("search");
        Object[] arr = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)};
        Object search =
                new ObjForArrayHelper("SysTem128", 2);
        int expResult = 1;
        int result = ArrayHelper.search(arr, search);
        assertEquals(expResult, result);
    }

    /**
     * Test of reverse method, of class ArrayHelper.
     */
    @Test
    public void testReverse_ObjectArr() {
        System.out.println("reverse");
        Object[] arr = new Object[]{new ObjForArrayHelper("MoXie", 1), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("Zoeey", 3)};
        Object[] expResult = new Object[]{new ObjForArrayHelper("Zoeey", 3), //
            new ObjForArrayHelper("SysTem128", 2), new ObjForArrayHelper("MoXie", 1)};
        Object[] result = ArrayHelper.reverse(arr);
        assertArrayEquals(expResult, result);
    }
}
