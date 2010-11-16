/*
 * MoXie (SysTem128@GMail.Com) 2009-4-21 16:10:23
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

import org.zoeey.loader.FieldMap;
import org.zoeey.loader.FieldMapper;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class FieldMapperTest extends TestCase {

    /**
     *
     */
    public FieldMapperTest() {
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
     * Test of parseMap method, of class ValueMapper.
     */
    @Test
    public void testToFieldMap() {
        System.out.println("toFieldMap");
//        MemoryMeasurer mm = new MemoryMeasurer();
//        mm.start();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name[key_1][key_2][1]", "aa");
        map.put("name[key_1][key_2][2]", "bb");
        FieldMap<String, Object> fieldMap = FieldMapper.toFieldMap(map);
//        mm.stop();
        assertEquals(fieldMap.getMap("name").getMap("key_1").getList("key_2").toString(), "[bb, aa]");
//        System.out.println(mm.spend());
        // 565600
        // 565552
        // 565600

    }

    /**
     * Test of parseMap method, of class ValueMapper.
     */
    @Test
    public void testFieldToMap() {
        System.out.println("fieldToMap");

        String fieldName;
        String fieldValue;
//        TimeMeasurer tm = new TimeMeasurer();
//        tm.start();
        fieldName = "name[key_1][key_2]";
        fieldValue = "value";
        FieldMap<String, Object> map = new FieldMap<String, Object>();
//        for (int idx = 0; idx < 100000; idx++) {
        FieldMapper.fieldToMap(map, fieldName + "[key_3_1]", fieldValue);
        FieldMapper.fieldToMap(map, fieldName + "[key_3_2]", fieldValue);
//        }
//        Map<String, Map<String, Map<String, String>>> _map = map;
        assertEquals(map.getMap("name").getMap("key_1").getMap("key_2").get("key_3_1"), "value");
        assertEquals(map.toString(), "{name={key_1={key_2={key_3_2=value, key_3_1=value}}}}");
        FieldMapper.fieldToMap(map, fieldName + "[key_3_1][]", 1);
        FieldMapper.fieldToMap(map, fieldName + "[key_3_1][]", 2);
        FieldMapper.fieldToMap(map, fieldName + "[key_3_1][]", 3);
        assertEquals(map.getMap("name").getMap("key_1").getMap("key_2").getList("key_3_1").toString(),//
                "[3, 2, value, 1]");
//        tm.stop();
//        System.out.println(tm.spend());
        // regexp
        // 1254
        // 1485
        // 1375
        // new StringBuffer
        // 670
        // 708
        // setLength(0)
        // 723
        // 1000 []
        // 739
        // 564
        // 1000 reg
        // 2165
        // 1943
        // 241
        // 242
        // 122
        // 123

    }
//    @Test
//    public void testisNum() {
//        System.out.println("isNum");
//        System.out.println(ValueMapper.isNum(""));
//        System.out.println(ValueMapper.isNum("00"));
//        System.out.println(ValueMapper.isNum("11"));
//        System.out.println(ValueMapper.isNum("111a"));
//        System.out.println(ValueMapper.isNum("11 2a"));
//    }
}
