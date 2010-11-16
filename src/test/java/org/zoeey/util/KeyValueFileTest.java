/*
 * MoXie (SysTem128@GMail.Com) 2009-4-17 19:02:55
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import org.zoeey.loader.FieldMap;
import org.zoeey.loader.FieldMapper;
import org.zoeey.util.KeyValueFile.Item;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class KeyValueFileTest extends TestCase {

    /**
     *
     */
    public KeyValueFileTest() {
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
     * Test of parseList method, of class KeyValueFile.
     * @throws IOException
     */
    @Test
    public void testParseList() throws IOException {
        System.out.println("parseList");
        List<Item> itemList = KeyValueFile.toList("key = \"value1_\"+\"value2\"");
        StringBuilder strBuilder = new StringBuilder();
        for (Item item : itemList) {
            strBuilder.append(item.getKey());
            strBuilder.append(":");
            strBuilder.append(item.getValue());
        }
        assertEquals(strBuilder.toString(), "key:value1_value2");
        /**
         * 
         */
        itemList = KeyValueFile.toList("key = \"value1_\"key+\"value2\"");
        strBuilder = new StringBuilder();
        for (Item item : itemList) {
            strBuilder.append(item.getKey());
            strBuilder.append(":");
            strBuilder.append(item.getValue());
        }
        assertEquals(strBuilder.toString(), "key:value1_value2");

        String text;
        /**
         * 字符集设置错误可能造成解析错误
         */
        File file = new File(TestUtil.getResource("keyValueFileTest.kv"));
        text = TextFileHelper.read(file, "utf-8");
        //
        List<Item> list = KeyValueFile.toList(text);
        strBuilder = new StringBuilder();
        for (Item item : list) {
            strBuilder.append(item.getKey());
            strBuilder.append(":");
            strBuilder.append(item.getValue());
            strBuilder.append("\n");
        }
        Map<String, String> errorMap = new HashMap<String, String>();
        errorMap = KeyValueFile.toMap(text);
        assertEquals(errorMap.get("name").replaceAll("\\r|\\n", "") //
                , "丢失了后半个引号name = '这里将不同于期望值'name = ");
        if (true) {
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(KeyValueFile.toMap(text));
        FieldMap<String, Object> fieldMap = FieldMapper.toFieldMap(map);
        /**
         * 可连接特性测试
         */
        assertEquals(fieldMap.get("consequent"), "这是一句话的前半部分，这是后半部分：）这是最后一部分-_-");
        /**
         * 普通键值对测试
         */
        assertEquals(fieldMap.get("中文名"), "中文内容");
        assertEquals(fieldMap.get("中文名_2"), "中文内容_2");
        assertEquals(fieldMap.get("中文名_3"), "\\u4e2d\\u6587\\u5185\\u5bb9_3");
        assertEquals(fieldMap.get("中文名_4"), "中文内容_4_\\uG123");// 此处非16进制，直接输出。
        /**
         * 偏离字符集测试
         */
        assertTrue(//
                fieldMap.getMap("field").getMap("key_1").get("key_2_1").equals("FieldMapper_value_1\\uff01") //
                || fieldMap.getMap("field").getMap("key_1").get("key_2_1").equals("FieldMapper_value_1！"));
        // 特别注意，当kv文件内！转义为 \uff01，且使用单引号包围时不会被解析器解码为"！"。
        assertEquals(fieldMap.getMap("field").getMap("key_1").get("key_2_2"), "FieldMapper_value_2！");
        /**
         * 持久化测试
         */
        // map
        File tmpFile = File.createTempFile("keyValue", "storeTest.tmp");
        Map<String, String> storeMap = new HashMap<String, String>();
        storeMap.put("key1", "value");
        storeMap.put("key2", "va'lue");
        storeMap.put("key3", "va\"lue");
        storeMap.put("key5", "中文");

        KeyValueFile.store(tmpFile, storeMap);
        assertEquals(KeyValueFile.toMap(tmpFile).toString() //
                , "{key1=value, key3=va\"lue, key5=中文, key2=va'lue}");
        // list
        tmpFile = File.createTempFile("keyValue", "storeTest.tmp");
        List<Item> storeList = new ArrayList<Item>();
        storeList.add(new Item("key1", "value"));
        storeList.add(new Item("key2", "value1"));
        storeList.add(new Item("key2", "value2"));
        KeyValueFile.store(tmpFile, storeList);
        assertEquals(JsonHelper.encode(KeyValueFile.toList(tmpFile)) //
                , "[{\"key\":\"key1\",\"value\":\"value\"},{\"key\":\"key2\""
                + ",\"value\":\"value1\"},{\"key\":\"key2\",\"value\":\"value2\"}]");

    }
}
