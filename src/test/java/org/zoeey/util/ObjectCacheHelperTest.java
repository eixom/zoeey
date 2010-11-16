/*
 * MoXie (SysTem128@GMail.Com) 2009-5-7 23:07:03
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.ObjectCacheHelper;
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
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ObjectCacheHelperTest {

    /**
     *
     */
    public ObjectCacheHelperTest() {
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
     * Test of put method, of class ObjectCacheHelper.
     */
    @Test
    public void testPut() {
        System.out.println("put");
        Map bigMap = new HashMap(10000);
        for (int idx = 0; idx < 100000; idx++) {
            bigMap.put(idx, idx);
        }
        ObjectCacheHelper.put(bigMap.getClass(), bigMap);
        ObjectCacheHelper.put("test", bigMap.getClass(), bigMap);
    }

    /**
     * Test of get method, of class ObjectCacheHelper.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        assertEquals(ObjectCacheHelper.<Map>get(HashMap.class).size(), 100000);
        assertEquals(ObjectCacheHelper.<Map>get("test", HashMap.class).size(), 100000);
    }

    /**
     * Test of remove method, of class ObjectCacheHelper.
     */
    @Test
    public void testRemove() {
    }

    /**
     * Test of removeAll method, of class ObjectCacheHelper.
     */
    @Test
    public void testRemoveAll() {
        System.out.println("removeAll");
        Object key = null;
        ObjectCacheHelper.removeAll();
    }
}
