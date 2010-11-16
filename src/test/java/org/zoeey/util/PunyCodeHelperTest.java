/*
 * MoXie (SysTem128@GMail.Com) 2010-3-25 23:52:36
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author MoXie
 */
public class PunyCodeHelperTest extends TestCase {

    public PunyCodeHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of encode method, of class PunyCodeHelper.
     */
    @Test
    public void testEncode() {
        System.out.println("encode");
        String str;
        String expResult;
        String result;
        /**
         * 
         */
        str = "中文";
        expResult = "fiq228c";
        result = PunyCodeHelper.encode(str);
        assertEquals(expResult, result);
        /**
         *
         */
        str = "汉字";
        expResult = "p8s370b";
        result = PunyCodeHelper.encode(str);
        assertEquals(expResult, result);
        /**
         *
         */
        str = "---";
        expResult = "----";
        result = PunyCodeHelper.encode(str);
        assertEquals(expResult, result);
        /**
         *
         */
        str = "english";
        expResult = "english-";
        result = PunyCodeHelper.encode(str);
        assertEquals(expResult, result);
        /**
         * 交叉形
         */
        str = "中文abc中文abc";
        expResult = "abcabc-9v7id2194cea";
        result = PunyCodeHelper.encode(str);
        assertEquals(expResult, result);


    }

    /**
     * Test of decode method, of class PunyCodeHelper.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        String input = "fiq228c";
        String expResult = "中文";
        String result = PunyCodeHelper.decode(input);
        assertEquals(expResult, result);
        /**
         * 
         */
        input = "p8s370b";
        expResult = "汉字";
        result = PunyCodeHelper.decode(input);
        assertEquals(expResult, result);
        /**
         * 交叉形
         */
        input = "abcabc-9v7id2194cea";
        expResult = "中文abc中文abc";
        result = PunyCodeHelper.decode(input);
        assertEquals(expResult, result);
    }

    /**
     * Test of idna_encode method, of class PunyCodeHelper.
     */
    @Test
    public void testIdna_encode() {
        System.out.println("idna_encode");
        String domain = "中文。中文";
        String expResult = "xn--fiq228c.xn--fiq228c";
        String result = PunyCodeHelper.idna_encode(domain);
        assertEquals(expResult, result);
        /**
         * com后缀
         */
        domain = "中文。com";
        expResult = "xn--fiq228c.com";
        result = PunyCodeHelper.idna_encode(domain);
        assertEquals(expResult, result);
        /**
         * 交叉形
         */
        domain = "中文abc中文abc.中文";
        expResult = "xn--abcabc-9v7id2194cea.xn--fiq228c";
        result = PunyCodeHelper.idna_encode(domain);
        assertEquals(expResult, result);
        /**
         * - 号
         */
        domain = "中文abc中-文abc.中文";
        expResult = "xn--abc-abc-f43kd0980dfa.xn--fiq228c";
        result = PunyCodeHelper.idna_encode(domain);
        assertEquals(expResult, result);
        /**
         * 英文域名
         */
        domain = "zoeey.org";
        expResult = "zoeey.org";
        result = PunyCodeHelper.idna_encode(domain);
        assertEquals(expResult, result);

    }

    /**
     * Test of idna_decode method, of class PunyCodeHelper.
     */
    @Test
    public void testIdna_decode() {
        System.out.println("idna_decode");
        String punycode = "xn--fiq228c.xn--fiq228c";
        String expResult = "中文.中文";
        String result = PunyCodeHelper.idna_decode(punycode);
        assertEquals(expResult, result);
        /**
         * com后缀
         */
        punycode = "xn--fiq228c.com";
        expResult = "中文.com";
        result = PunyCodeHelper.idna_decode(punycode);
        assertEquals(expResult, result);
        /**
         * 交叉形
         */
        punycode = "xn--abcabc-9v7id2194cea.xn--fiq228c";
        expResult = "中文abc中文abc.中文";
        result = PunyCodeHelper.idna_decode(punycode);
        assertEquals(expResult, result);
        /**
         * - 号
         */
        punycode = "xn--abc-abc-f43kd0980dfa.xn--fiq228c";
        expResult = "中文abc中-文abc.中文";
        result = PunyCodeHelper.idna_decode(punycode);
        assertEquals(expResult, result);
        /**
         * 英文域名
         */
        punycode = "zoeey.org";
        expResult = "zoeey.org";
        result = PunyCodeHelper.idna_decode(punycode);
        assertEquals(expResult, result);

    }
}
