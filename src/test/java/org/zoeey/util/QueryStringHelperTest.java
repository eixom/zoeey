/*
 * MoXie (SysTem128@GMail.Com) 2009-6-23 17:58:11
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.JsonEncoder;
import org.zoeey.util.QueryStringHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
public class QueryStringHelperTest extends TestCase {

    /**
     *
     */
    public QueryStringHelperTest() {
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
     * Test of getHash method, of class QueryStringHelper.
     */
    @Test
    public void testGetHash() {
        System.out.println("getHash");
        QueryStringHelper queryStringHelper = new QueryStringHelper("?name=moxie#top");
        assertEquals(queryStringHelper.getHash(), "#top");
        queryStringHelper = new QueryStringHelper("#top");
        assertEquals(queryStringHelper.getHash(), "#top");
    }

    /**
     * Test of toList method, of class QueryStringHelper.
     */
    @Test
    public void testToList() {
        System.out.println("getFieldList");
        QueryStringHelper queryStringHelper = new QueryStringHelper("?name=moxie&name=system128#top");
        List list = new ArrayList();
        for (Entry field : queryStringHelper.getFieldList()) {
            list.add(field.getKey());
            list.add(field.getValue());
        }
        assertEquals(JsonEncoder.encode(list), "[\"name\",\"moxie\",\"name\",\"system128\"]");
    }

    /**
     * Test of toMap method, of class QueryStringHelper.
     */
    @Test
    public void testFieldMap() {
        System.out.println("getFieldMap");
        QueryStringHelper queryStringHelper = new QueryStringHelper("?name=moxie&name=system128#top");
        assertEquals(JsonEncoder.encode(queryStringHelper.getMap()), "{\"name\":\"moxie\"}");
    }

    /**
     *
     */
    @Test
    public void testToMap() {
        System.out.println("toMap");
        String urlStr = "first=value&arr[]=foo+bar&arr[]=baz#123";
        String expResult = "{\"arr[]\":\"foo bar\",\"first\":\"value\"}";
        Map params = QueryStringHelper.toMap(urlStr);
        assertEquals(JsonEncoder.encode(params), expResult);
    }
}
