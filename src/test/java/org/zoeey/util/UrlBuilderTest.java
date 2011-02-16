/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class UrlBuilderTest {

    /**
     *
     */
    public UrlBuilderTest() {
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
     * Test of build method, of class UrlBuilder.
     */
    @Test
    public void testBuild_Map() {
        System.out.println("build");
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("", "length0");
        params.put("null", null);
        params.put("title", "_title");
        params.put("name", "MoXie");
        String expResult = "&=length0&null=&title=_title&name=MoXie";
        String result = UrlBuilder.build(params);
        assertEquals(expResult, result);

    }

    /**
     * Test of build method, of class UrlBuilder.
     */
    @Test
    public void testBuild_Map_StringArr() {
        System.out.println("build");
        Map params = new LinkedHashMap();
        params.put("", "length0");
        params.put("null", null);
        params.put("title", "_title");
        params.put("name", "MoXie");
        String[] except = {"name"};
        String expResult = "&=length0&null=&title=_title";
        String result = UrlBuilder.build(params.entrySet(), except);
        assertEquals(expResult, result);


    }

    /**
     * Test of build method, of class UrlBuilder.
     */
    @Test
    public void testBuild_List_String() {
        System.out.println("build");
        List<String> params = new ArrayList<String>();
        params.add(null);
        params.add("");
        params.add("title");
        params.add("name");
        String numeric_prefix = "param_";
        String expResult = "&param_0=&param_1=&param_2=title&param_3=name";
        String result = UrlBuilder.buildList(params, numeric_prefix);
        assertEquals(expResult, result);



    }

    /**
     * Test of build method, of class UrlBuilder.
     */
    @Test
    public void testBuild_3args() {
        System.out.println("buildList");
        List params = new ArrayList();
        params.add("title");
        params.add(null);
        params.add("name");
        params.add("");
        String separater = "/";
        boolean isSepAsPrefx = false;
        String expResult = "title//name/";
        String result = UrlBuilder.buildList(params, separater, isSepAsPrefx);
        assertEquals(expResult, result);

        isSepAsPrefx = true;
        expResult = "/title//name/";
        result = UrlBuilder.buildList(params, separater, isSepAsPrefx);
        assertEquals(expResult, result);
    }
}
