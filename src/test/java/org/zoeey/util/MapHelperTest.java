/*
 * MoXie (SysTem128@GMail.Com) 2009-8-16 12:18:26
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.MapHelper;
import org.zoeey.util.JsonHelper;
import java.util.HashMap;
import java.util.Map;
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
public class MapHelperTest {

    /**
     *
     */
    public MapHelperTest() {
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
     * Test of keyToLowerCase method, of class MapHelper.
     */
    @Test
    public void testKeyToLowerCase() {
        System.out.println("keyToLowerCase");
        Map<String, String> map = new HashMap<String, String>();
        map.put("NAME", "MoXie");
        map.put("EMAIL", "SysTem128@GMail.Com");

        Map<String, String> result = MapHelper.keyToLowerCase(map);
        assertEquals("{\"email\":\"SysTem128@GMail.Com\",\"name\":\"MoXie\"}",//
                JsonHelper.encode(result));
    }

    /**
     * Test of keyToUpperCase method, of class MapHelper.
     */
    @Test
    public void testKeyToUpperCase() {
        System.out.println("keyToUpperCase");
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "MoXie");
        map.put("email", "SysTem128@GMail.Com");
        Map<String, String> result = MapHelper.keyToUpperCase(map);
        assertEquals("{\"NAME\":\"MoXie\",\"EMAIL\":\"SysTem128@GMail.Com\"}",//
                JsonHelper.encode(result));
    }
}
