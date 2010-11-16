/*
 * MoXie (SysTem128@GMail.Com) 2009-12-2 10:07:51
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

import org.zoeey.loader.FieldMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.JsonHelper;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class FieldMapTest {

    /**
     *
     */
    public FieldMapTest() {
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
     * Test of getList method, of class FieldMap.
     */
    @Test
    public void testGetList() {
        System.out.println("getList");
        FieldMap<String, Object> fieldMap = new FieldMap<String, Object>();
        String expResult = null;
        fieldMap.put("keyA", new FieldMap<String, Object>() {

            private static final long serialVersionUID = 0L;

            {

                put("keyB", "value");
            }
        });
        expResult = "value";
        Object result = fieldMap.getMap("keyA").get("keyB");
        assertEquals(result, expResult);
        /**
         * null point
         */
        expResult = null;
        result = fieldMap.getMap("keyA").get("keyC");
        assertEquals(result, expResult);
    }

    /**
     * Test of getMap method, of class FieldMap.
     */
    @Test
    public void testGetMap() {
        System.out.println("getMap");
        FieldMap<String, Object> fieldMap = new FieldMap<String, Object>();
        String expResult = null;
        fieldMap.put("keyA", new FieldMap<String, Object>() {

            private static final long serialVersionUID = 0L;

            {

                put("keyB", "value");
            }
        });
        expResult = "{}";
        Object result = fieldMap.getMap("keyA").getMap("keyB");
        result = JsonHelper.encode(result);
        assertEquals(result, expResult);
        /**
         * empty
         */
        expResult = "{}";
        result = fieldMap.getMap("keyA").getMap("keyB");
        result = JsonHelper.encode(result);
        assertEquals(result, expResult);
    }
}
