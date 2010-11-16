/*
 * MoXie (SysTem128@GMail.Com) 2010-10-8 15:08:17
 * 
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ParamHandlerTest {

    public ParamHandlerTest() {
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
     * Test of getValue method, of class ParamHandler.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        String name = "action";
        Router router = new Router();
        router.add("/:action");
        ParamHandler handler = router.getHandler("/login");
        String expResult = "login";
        String result = handler.getValue(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getValueList method, of class ParamHandler.
     */
    @Test
    public void testGetValueList() {
        System.out.println("getValueList");
        String name = "items";
        Router router = new Router();
        router.add("/:items[,]");
        ParamHandler handler = router.getHandler("/1,2,3,5");
        List<String> result = handler.getValueList(name);
        assertArrayEquals(new String[]{"1", "2", "3", "5"}, result.toArray(new String[0]));
    }

    /**
     * Test of getMap method, of class ParamHandler.
     */
    @Test
    public void testGetMap() {
        System.out.println("getMap");
        String name = "others";
        Router router = new Router();
        router.add("/:others{-}");
        /**
         * 注意 和普通参数相同不定参数可以但键多值
         */
        ParamHandler handler = router.getHandler("/key1-val1-key2-val2-key2-val3");
        List<ParamEntry> list = handler.getList(name);
        assertEquals("[{\"value\":\"val1\",\"key\":\"key1\"}"
                + ",{\"value\":\"val2\",\"key\":\"key2\"}"
                + ",{\"value\":\"val3\",\"key\":\"key2\"}]"//
                , JsonHelper.encode(list));
        Map<String, String> map = handler.getMap(name);
        assertEquals(
                new HashMap<String, String>() {

                    {
                        put("key1", "val1");
                        put("key2", "val3");
                    }
                }, map);
    }
}
