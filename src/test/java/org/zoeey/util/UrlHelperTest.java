/*
 * MoXie (SysTem128@GMail.Com) 2009-3-22 2:52:05
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
public class UrlHelperTest {

    /**
     *
     */
    public UrlHelperTest() {
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
     * Test of enURL method, of class StringEscaper.
     */
    @Test
    public void testEncode() {
        System.out.println("encode");
        String urlStr = "{name=\"MoXie\",all=true}";
        String expResult = "%7Bname%3D%22MoXie%22%2Call%3Dtrue%7D";
        String result = UrlHelper.encode(urlStr);
        assertEquals(expResult, result);
        //
        urlStr = "&name=MoXie&all=true";
        expResult = "%26name%3DMoXie%26all%3Dtrue";
        result = UrlHelper.encode(urlStr);
        assertEquals(expResult, result);
    }

    /**
     * Test of unURL method, of class StringEscaper.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        String urlStr = "%7Bname%3D%22MoXie%22%2Call%3Dtrue%7D";
        String expResult = "{name=\"MoXie\",all=true}";
        String result = UrlHelper.decode(urlStr);
        assertEquals(expResult, result);
        //
        urlStr = "%E8%8C%B6";
        expResult = "茶";
        result = UrlHelper.decode(urlStr);
        assertEquals(expResult, result);
        //
        urlStr = "茶";
        expResult = "茶";
        result = UrlHelper.decode(urlStr);
        assertEquals(expResult, result);
    }
}
