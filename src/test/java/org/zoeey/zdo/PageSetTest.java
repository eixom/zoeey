/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import org.zoeey.zdo.PageSet;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class PageSetTest extends TestCase {

    /**
     *
     */
    public PageSetTest() {
    }

    /**
     *
     */
    public void testMain() {
        System.out.println("PageSet");
        /**
         * between 包含左右端
         * SELECT * FROM `article` WHERE id between 0 and 3;
         * SELECT * FROM `article` WHERE id between 1 and 3;
         * +----+-------+
         * | id | title |
         * +----+-------+
         * |  1 | 111   |
         * |  2 | 222   |
         * |  3 | 333   |
         * +----+-------+
         * limit 不包含最前端
         * SELECT * FROM `article` LIMIT 1,3;
         * +----+-------+
         * | id | title |
         * +----+-------+
         * |  2 | 222   |
         * |  3 | 333   |
         * |  4 | 444   |
         * +----+-------+
         * SELECT * FROM `article` LIMIT 0,3;
         * +----+-------+
         * | id | title |
         * +----+-------+
         * |  1 | 111   |
         * |  2 | 222   |
         * |  3 | 333   |
         * +----+-------+
         */
        /**
         * 记录数为0时的一些特殊情况
         */
        PageSet pageSet = new PageSet(0, 3, 1);
        /**
         * true
         */
        assertTrue(pageSet.isFirst());
        assertTrue(pageSet.isLast());
        assertFalse(pageSet.hasList());
        /**
         * false
         */
        assertFalse(pageSet.hasNext());
        assertFalse(pageSet.hasPrev());
        /**
         * 记录数为 0 时 当前页和页码列均只有 1 。
         * 起始列与终止列均为 0 
         */
        assertEquals(pageSet.current(), 1);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 0);
        assertEquals(pageSet.getStartRow(), 0);
        assertEquals(pageSet.getEndRow(), 0);
        assertEquals(pageSet.getRecordCount(), 0);
        Assert.assertArrayEquals(pageSet.siblings(0), new int[]{1});
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{1});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{1});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1});
        /**
         * 
         */
        pageSet = new PageSet(1, 3, 1);
        assertTrue(pageSet.isFirst());
        assertTrue(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertFalse(pageSet.hasNext());
        assertFalse(pageSet.hasPrev());
        assertEquals(pageSet.current(), 1);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 0);
        assertEquals(pageSet.getStartRow(), 1);
        assertEquals(pageSet.getEndRow(), 1);
        assertEquals(pageSet.getRecordCount(), 1);
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{1});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1});
        /**
         * 
         */
        pageSet = new PageSet(3, 3, 1);
        assertTrue(pageSet.isFirst());
        assertTrue(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertFalse(pageSet.hasNext());
        assertFalse(pageSet.hasPrev());
        assertEquals(pageSet.current(), 1);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 0);
        assertEquals(pageSet.getStartRow(), 1);
        assertEquals(pageSet.getEndRow(), 3);
        assertEquals(pageSet.getRecordCount(), 3);
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{1});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{1});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1});
        /**
         * 
         */
        pageSet = new PageSet(4, 3, 2);
        assertFalse(pageSet.isFirst());
        assertTrue(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertFalse(pageSet.hasNext());
        assertTrue(pageSet.hasPrev());
        assertEquals(pageSet.current(), 2);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 3);
        assertEquals(pageSet.getStartRow(), 4);
        assertEquals(pageSet.getEndRow(), 4);
        assertEquals(pageSet.getRecordCount(), 4);
//        Assert.assertArrayEquals(pageSet.siblings(0), new int[]{2});
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{2});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{1, 2});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1, 2});
        /**
         *
         */
        pageSet = new PageSet(5, 3, 2);
        assertFalse(pageSet.isFirst());
        assertTrue(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertFalse(pageSet.hasNext());
        assertTrue(pageSet.hasPrev());
        assertEquals(pageSet.current(), 2);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 3);
        assertEquals(pageSet.getStartRow(), 4);
        assertEquals(pageSet.getEndRow(), 5);
        assertEquals(pageSet.getRecordCount(), 5);
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{2});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{1, 2});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1, 2});
        /**
         * 信息总量与单页信息数整除
         */
        pageSet = new PageSet(6, 3, 2);
        assertFalse(pageSet.isFirst());
        assertTrue(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertFalse(pageSet.hasNext());
        assertTrue(pageSet.hasPrev());
        assertEquals(pageSet.current(), 2);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 3);
        assertEquals(pageSet.getStartRow(), 4);
        assertEquals(pageSet.getEndRow(), 6);
        assertEquals(pageSet.getRecordCount(), 6);
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{2});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{1, 2});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1, 2});
        /**
         * 页码越过最大
         */
        pageSet = new PageSet(6, 3, 3);
        assertFalse(pageSet.isFirst());
        assertTrue(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertFalse(pageSet.hasNext());
        assertTrue(pageSet.hasPrev());
        assertEquals(pageSet.current(), 2);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getStartRow(), 4);
        assertEquals(pageSet.getEndRow(), 6);
        assertEquals(pageSet.getRecordCount(), 6);
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{2});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{1, 2});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1, 2});
        /**
         * 当前页靠近两端
         */
        pageSet = new PageSet(7, 3, 2);
        assertFalse(pageSet.isFirst());
        assertFalse(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertTrue(pageSet.hasNext());
        assertTrue(pageSet.hasPrev());
        assertEquals(pageSet.current(), 2);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 3);
        assertEquals(pageSet.getStartRow(), 4);
        assertEquals(pageSet.getEndRow(), 6);
        assertEquals(pageSet.getRecordCount(), 7);
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{2});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{1, 2, 3});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1, 2, 3});
        /**
         * 当前页在中间
         */
        pageSet = new PageSet(30, 3, 5);
        assertFalse(pageSet.isFirst());
        assertFalse(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertTrue(pageSet.hasNext());
        assertTrue(pageSet.hasPrev());
        assertEquals(pageSet.current(), 5);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 12);
        assertEquals(pageSet.getStartRow(), 13);
        assertEquals(pageSet.getEndRow(), 15);
        assertEquals(pageSet.getRecordCount(), 30);
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{5});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{3, 4, 5, 6, 7});
        Assert.assertArrayEquals(pageSet.siblings(6), new int[]{3, 4, 5, 6, 7, 8});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        /**
         * 当前页靠近前端
         */
        pageSet = new PageSet(30, 3, 3);
        assertFalse(pageSet.isFirst());
        assertFalse(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertTrue(pageSet.hasNext());
        assertTrue(pageSet.hasPrev());
        assertEquals(pageSet.current(), 3);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 6);
        assertEquals(pageSet.getStartRow(), 7);
        assertEquals(pageSet.getEndRow(), 9);
        assertEquals(pageSet.getRecordCount(), 30);
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{3});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{1, 2, 3, 4, 5});
        Assert.assertArrayEquals(pageSet.siblings(6), new int[]{1, 2, 3, 4, 5, 6});
        Assert.assertArrayEquals(pageSet.siblings(7), new int[]{1, 2, 3, 4, 5, 6, 7});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        /**
         * 当前页靠近后端
         */
        pageSet = new PageSet(30, 3, 8);
        assertFalse(pageSet.isFirst());
        assertFalse(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertTrue(pageSet.hasNext());
        assertTrue(pageSet.hasPrev());
        assertEquals(pageSet.current(), 8);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 21);
        assertEquals(pageSet.getStartRow(), 22);
        assertEquals(pageSet.getEndRow(), 24);
        assertEquals(pageSet.getRecordCount(), 30);
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{8});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{6, 7, 8, 9, 10});
        Assert.assertArrayEquals(pageSet.siblings(6), new int[]{5, 6, 7, 8, 9, 10});
        Assert.assertArrayEquals(pageSet.siblings(7), new int[]{4, 5, 6, 7, 8, 9, 10});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        /**
         * 当前页靠近后端
         */
        pageSet = new PageSet(30, 3, 10);
        assertFalse(pageSet.isFirst());
        assertTrue(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertFalse(pageSet.hasNext());
        assertTrue(pageSet.hasPrev());
        assertEquals(pageSet.current(), 10);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 27);
        assertEquals(pageSet.getStartRow(), 28);
        assertEquals(pageSet.getEndRow(), 30);
        assertEquals(pageSet.getRecordCount(), 30);
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{10});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{6, 7, 8, 9, 10});
        Assert.assertArrayEquals(pageSet.siblings(6), new int[]{5, 6, 7, 8, 9, 10});
        Assert.assertArrayEquals(pageSet.siblings(7), new int[]{4, 5, 6, 7, 8, 9, 10});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        /**
         * 当前页靠近后端
         */
        pageSet = new PageSet(30, 3, 9);
        assertFalse(pageSet.isFirst());
        assertFalse(pageSet.isLast());
        assertTrue(pageSet.hasList());
        assertTrue(pageSet.hasNext());
        assertTrue(pageSet.hasPrev());
        assertEquals(pageSet.current(), 9);
        assertEquals(pageSet.first(), 1);
        assertEquals(pageSet.getOffset(), 24);
        assertEquals(pageSet.getStartRow(), 25);
        assertEquals(pageSet.getEndRow(), 27);
        assertEquals(pageSet.getRecordCount(), 30);
        Assert.assertArrayEquals(pageSet.siblings(1), new int[]{9});
        Assert.assertArrayEquals(pageSet.siblings(5), new int[]{6, 7, 8, 9, 10});
        Assert.assertArrayEquals(pageSet.siblings(6), new int[]{5, 6, 7, 8, 9, 10});
        Assert.assertArrayEquals(pageSet.siblings(7), new int[]{4, 5, 6, 7, 8, 9, 10});
        Assert.assertArrayEquals(pageSet.all(), new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
    }
}
