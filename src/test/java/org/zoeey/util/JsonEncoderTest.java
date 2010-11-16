/*
 * MoXie (SysTem128@GMail.Com) 2009-4-25 10:03:03
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.BeanHelperTest.HanMeimei;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsonEncoderTest {

    /**
     *
     */
    public JsonEncoderTest() {
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
     * Test of getJson method, of class new JsonEncoder().
     */
    @Test
    public void testEncode() {
        System.out.println("encode");
        String result;
        // array
        String[] strs = {"MoXie", "special:\"\\\\/\\b\\f\\n\\r\\t"};
        result = JsonHelper.encode(strs);
        assertEquals(result, "[\"MoXie\",\"special:\\\"\\\\\\\\\\/\\\\b\\\\f\\\\n\\\\r\\\\t\"]");
        // list
        List<Object> list = new ArrayList<Object>();
        list.add("MoXie");
        list.add("special:\"\\\\/\\b\\f\\n\\r\\t");
        result = JsonHelper.encode(list);
        assertEquals(result, "[\"MoXie\",\"special:\\\"\\\\\\\\\\/\\\\b\\\\f\\\\n\\\\r\\\\t\"]");
        // collection or set
        HashSet<Object> set = new HashSet<Object>();
        set.add("MoXie");
        set.add("special:\"\\\\/\\b\\f\\n\\r\\t");
        result = JsonHelper.encode(set);
        assertEquals(result, "[\"special:\\\"\\\\\\\\\\/\\\\b\\\\f\\\\n\\\\r\\\\t\",\"MoXie\"]");
        // map
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("name", "MoXie");
        map.put("special", "\"\\\\/\\b\\f\\n\\r\\t");
        result = JsonHelper.encode(map);
        assertEquals(result, "{\"special\":\"\\\"\\\\\\\\\\/\\\\b\\\\f\\\\n\\\\r\\\\t\",\"name\":\"MoXie\"}");
        // mixed
        list.add(strs);
        list.add(set);
        list.add(map);
        result = JsonHelper.encode(list);
        assertEquals(result, "[\"MoXie\",\"special:\\\"\\\\\\\\\\/\\\\b\\\\f\\\\n\\\\r\\\\t\","
                + "[\"MoXie\",\"special:\\\"\\\\\\\\\\/\\\\b\\\\f\\\\n\\\\r\\\\t\"],"
                + "[\"special:\\\"\\\\\\\\\\/\\\\b\\\\f\\\\n\\\\r\\\\t\",\"MoXie\"],"
                + "{\"special\":\"\\\"\\\\\\\\\\/\\\\b\\\\f\\\\n\\\\r\\\\t\",\"name\":\"MoXie\"}]");
        result = JsonHelper.encode("中文");
        assertEquals(result, "\"\\u4e2d\\u6587\"");

        result = JsonHelper.encode("JavaBeanをMapに変換するユーティリティ");
        assertEquals(result, "\"JavaBean\\u3092Map\\u306b\\u5909\\u63db\\u3059\\u308b\\u30e6\\u30fc\\u30c6\\u30a3\\u30ea\\u30c6\\u30a3\"");

        result = JsonHelper.encode(new HashMap<String, String>() {

            {
                put("the\nkey", "the\nvalue");
            }
        });
        assertEquals(result, "{\"the\\nkey\":\"the\\nvalue\"}");

//      20013
//      25991
//      "\u4E2D\u6587"
        list = new ArrayList<Object>();
        list.add(true);
        list.add(1);
        list.add(1.5D);
        list.add('A');
        result = JsonHelper.encode(list);
        assertEquals(result, "[true,1,1.5,\"A\"]");
        int[] ints = new int[]{1, 3, 5, 7, 8, 10, 12};
//        TimeMeasurer tm = new TimeMeasurer();
//        tm.start();
//        for (int i = 0; i < 100000; i++) {
        result = JsonHelper.encode(ints);
//        }
//        System.out.println(tm.spend());
        /**
         * 10,000
         * 73
         * 100,000
         * 610
         */
        assertEquals(result, "[1,3,5,7,8,10,12]");
        /**
         *
         */
        Object obj = List.class;

        result = JsonHelper.encode(new HashMap<Object, Object>() {

            private final static long serialVersionUID = 0L;

            {

                put(HashMap.class, HashMap.class);
                put(List.class, List.class);
            }
        });
        if (result.indexOf("java.util.HashMap") < 0) {
            fail("json encode failed.");
        }
        HanMeimei hanMM = new BeanHelperTest.HanMeimei("HanMeimei", 20, true, "dress");
        assertEquals(new JsonEncoder().getJson(hanMM), "{\"age\":20"
                + ",\"isActive\":true,\"dress\":\"dress\",\"name\":\"HanMeimei\"}");

        assertEquals(new JsonEncoder().getJson(Planet.EARTH), "\"EARTH\"");


    }

    public static enum Planet {

        EARTH(5.976e+24, 6.37814e6),
        MARS(6.421e+23, 3.3972e6);
        private final double mass;   // in kilograms
        private final double radius; // in meters

        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }
        public static final double G = 6.67300E-11;

        double surfaceGravity() {
            return G * mass / (radius * radius);
        }

        double surfaceWeight(double otherMass) {
            return otherMass * surfaceGravity();
        }
    }
}
