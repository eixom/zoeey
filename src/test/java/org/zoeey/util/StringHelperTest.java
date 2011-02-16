/*
 * MoXie (SysTem128@GMail.Com) 2009-3-11 10:20:49
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

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
public class StringHelperTest {

    /**
     *
     */
    public StringHelperTest() {
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
     * Test of Trim method, of class StringHelper.
     * @throws Exception
     */
    @Test
    public void testTrim() throws Exception {
        System.out.println("trim");
        //
        String str = " 中文 \t";
        String expResult = "中文";
        String result = StringHelper.trim(str);
        assertEquals(expResult, result);
        //
        str = "\t 中文 ";
        expResult = "中文 ";
        result = StringHelper.ltrim(str);
        assertEquals(expResult, result);
        //
        str = " 中文\t ";
        expResult = " 中文";
        result = StringHelper.rtrim(str);
        assertEquals(expResult, result);
        //
        str = "\t中文 \t";
        expResult = "\t中文";
        result = StringHelper.rtrim(str);
        assertEquals(expResult, result);
        //
        str = "//123";
        expResult = "123";
        result = StringHelper.ltrim(str, new char[]{'/'}); 
        //
        str = "//123//";
        expResult = "123";
        result = StringHelper.trim(str, new char[]{'/'});
        assertEquals(expResult, result);
        //
        str = "/a123aa/";
        expResult = "123";
        result = StringHelper.trim(str, new char[]{'/', 'a'});
        assertEquals(expResult, result);
    }

    /**
     * Test of iconv method, of class StringHelper.
     * @throws Exception
     */
    @Test
    public void testSpilt() throws Exception {
        System.out.println("spilt");
        String str = "aa,bb,cc";
        String[] strs = StringHelper.split(str, ',');
        assertEquals("[\"aa\",\"bb\",\"cc\"]", JsonHelper.encode(strs));
        str = ",bb,cc";
        strs = StringHelper.split(str, ',');
        assertEquals("[\"\",\"bb\",\"cc\"]", JsonHelper.encode(strs));
        str = "aa,bb,";
        strs = StringHelper.split(str, ',');
        assertEquals("[\"aa\",\"bb\",\"\"]", JsonHelper.encode(strs));
        str = ",bb,";
        strs = StringHelper.split(str, ',');
        assertEquals("[\"\",\"bb\",\"\"]", JsonHelper.encode(strs));
        str = ",,";
        strs = StringHelper.split(str, ',');
        assertEquals("[\"\",\"\",\"\"]", JsonHelper.encode(strs));
        str = "aa";
        strs = StringHelper.split(str, ',');
        assertEquals("[\"aa\"]", JsonHelper.encode(strs));
    }

    /**
     * Test of iconv method, of class StringHelper.
     * @throws Exception
     */
//    @Test
//    public void testIconv() throws Exception {
//        System.out.println("iconv");
//        String fromCharset = "utf-8";
//        String toCharset = "gb2312";
//        String str = "中文";
//        Logger.getAnonymousLogger().log(Level.OFF, JsonHelper.encode(str.getBytes("utf-8")));
//        String result = StringHelper.iconv(fromCharset, toCharset, str);
//        Logger.getAnonymousLogger().log(Level.OFF, JsonHelper.encode(result.getBytes()));
//        result = StringHelper.iconv(toCharset, fromCharset, result);
//        for (Byte bt : "中文".getBytes("gb2312")) {
//            System.out.println(bt);
//            System.out.println(Integer.toHexString(bt));
//        }
//    }
    /**
     * Test of firstToLowerCase method, of class StringHelper.
     */
    @Test
    public void testFirstToLowerCase() {
        System.out.println("firstToLowerCase");
        String str = "MoXie";
        String expResult = "moXie";
        String result = StringHelper.firstToLowerCase(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of firstToUpperCase method, of class StringHelper.
     */
    @Test
    public void testFirstToUpperCase() {
        System.out.println("firstToUpperCase");
        String str = "moxie";
        String expResult = "Moxie";
        String result = StringHelper.firstToUpperCase(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of enRegex method, of class StringEscaper.
     */
    @Test
    public void testEnRegex() {
        System.out.println("enRegex");
        String str = "[]{}?=";
        String expResult = "\\[\\]\\{\\}\\?=";
        String result = StringHelper.regexEscape(str);
        assertEquals(expResult, result);
    }

    /**
     *
     */
    @Test
    public void testSubString() {
        System.out.println("subString");
        String str = "MoXie";
        String expResult = "MoXie";
        String result = StringHelper.subString(str, -70, 500);
        assertEquals(expResult, result);
        str = "MoXie";
        expResult = "";
        result = StringHelper.subString(str, 500, -1);
        assertEquals(expResult, result);
        str = "MoXie";
        expResult = "MoXie";
        result = StringHelper.subString(str, 0, -1);
        assertEquals(expResult, result);
    }

    /**
     *
     */
    @Test
    public void testUtf8_encode() {
        System.out.println("utf8_encode");
        String str = "中文abc";
        String expResult = "\\u4e2d\\u6587abc";
        String result = StringHelper.utf8_literal(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class StringHelper.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        String str = "";
        boolean expResult = true;
        boolean result = StringHelper.isEmpty(str);
        assertEquals(expResult, result);
        //
        str = null;
        expResult = true;
        result = StringHelper.isEmpty(str);
        assertEquals(expResult, result);
        str = "MoXie";
        expResult = false;
        result = StringHelper.isEmpty(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of format method, of class StringHelper.
     */
    @Test
    public void testFormat() {
        System.out.println("format");
        String str = "";
        Object[] args = null;
        String expResult = "";
        String result = StringHelper.format(str, args);
        assertEquals(expResult, result);
        str = "%s : %s";
        args = new String[]{"key", "value"};
        expResult = "key : value";
        result = StringHelper.format(str, args);
        assertEquals(expResult, result);
    }

    /**
     * Test of split method, of class StringHelper.
     */
    @Test
    public void testSplit() {
        System.out.println("split");
        String str = "a b c";
        char sep = ' ';
        String[] expResult = new String[]{"a", "b", "c"};
        String[] result = StringHelper.split(str, sep);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of replace method, of class StringHelper.
     */
    @Test
    public void testReplace() {
        System.out.println("replace");
        String source = "this is a string";
        String search = "s";
        String replace = "q";
        String expResult = "thiq iq a qtring";
        String result = StringHelper.replace(source, search, replace);
        assertEquals(expResult, result);
    }

    /**
     * Test of replaceIgnoreCase method, of class StringHelper.
     */
    @Test
    public void testReplaceIgnoreCase() {
        System.out.println("replaceIgnoreCase");
        String source = "this is a string";
        String search = "Is";
        String replace = "q";
        String expResult = "thq q a string";
        String result = StringHelper.replaceIgnoreCase(source, search, replace);
        assertEquals(expResult, result);
        //
        source = "this is a string";
        search = "Is\\s";
        replace = "q";
        expResult = source;
        result = StringHelper.replaceIgnoreCase(source, search, replace);
        assertEquals(expResult, result);
        //
        source = "this is a string$";
        search = "Ng$";
        replace = "q";
        expResult = "this is a striq";
        result = StringHelper.replaceIgnoreCase(source, search, replace);
        assertEquals(expResult, result);
        //
        source = "^this is a string";
        search = "^Th";
        replace = "q";
        expResult = "qis is a string";
        result = StringHelper.replaceIgnoreCase(source, search, replace);
        assertEquals(expResult, result);
    }

    /**
     * Test of repeat method, of class StringHelper.
     */
    @Test
    public void testRepeat() {
        System.out.println("repeat");
        String str = "ha";
        int count = 3;
        String expResult = "hahaha";
        String result = StringHelper.repeat(str, count);
        assertEquals(expResult, result);
    }

    /**
     * Test of reverse method, of class StringHelper.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        String str = "abcdefg";
        String expResult = "gfedcba";
        String result = StringHelper.reverse(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeFirst method, of class StringHelper.
     */
    @Test
    public void testRemoveFirst() {
        System.out.println("removeFirst");
        String str = "apple orange apple orange";
        String needRemoved = "orange";
        String expResult = "apple  apple orange";
        String result = StringHelper.removeFirst(str, needRemoved);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeAll method, of class StringHelper.
     */
    @Test
    public void testRemoveAll() {
        System.out.println("removeAll");
        String str = "apple orange apple orange";
        String needRemoved = "orange";
        String expResult = "apple  apple ";
        String result = StringHelper.removeAll(str, needRemoved);
        assertEquals(expResult, result);
    }

    /**
     * Test of regexEscape method, of class StringHelper.
     */
    @Test
    public void testRegexEscape() {
        System.out.println("regexEscape");
        String str = ".{}[]-";
        String expResult = "\\.\\{\\}\\[\\]\\-";
        String result = StringHelper.regexEscape(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of utf8_literal method, of class StringHelper.
     */
    @Test
    public void testUtf8_literal() {
        System.out.println("utf8_literal");
        String str = "\r\t\n";
        String expResult = "\\r\\t\\n";
        String result = StringHelper.utf8_literal(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of toEscapedVal method, of class StringHelper.
     */
    @Test
    public void testToEscapedVal() {
        System.out.println("toEscapedVal");
        char ch = 't';
        char expResult = '\t';
        char result = StringHelper.toEscapedVal(ch);
        assertEquals(expResult, result);
    }

    /**
     * Test of replaceFirst method, of class StringHelper.
     */
    @Test
    public void testReplaceFirst() {
        System.out.println("replaceFirst");
        String source = "this is a string";
        String search = "is";
        String replace = "q";
        String expResult = "thq is a string";
        String result = StringHelper.replaceFirst(source, search, replace);
        assertEquals(expResult, result);
    }

    /**
     * Test of replaceFirstIgnoreCase method, of class StringHelper.
     */
    @Test
    public void testReplaceFirstIgnoreCase() {
        System.out.println("replaceFirstIgnoreCase");
        String source = "this is a string";
        String search = "Is";
        String replace = "q";
        String expResult = "thq is a string";
        String result = StringHelper.replaceFirstIgnoreCase(source, search, replace);
        assertEquals(expResult, result);

    }
}
