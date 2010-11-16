/*
 * MoXie (SysTem128@GMail.Com) 2009-4-29 0:23:36
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.TextFileHelper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import static org.junit.Assert.*;
import org.zoeey.util.JsonDecoder.Validator;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsonDecoderTest {

    /**
     *
     */
    public JsonDecoderTest() {
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
     * Test of getValidator method, of class JsonDecoder.
     */
    @Test
    public void testGetValidator() {
        System.out.println("getValidator");
        String jsonStr;
        Validator vali;
        JsonDecoder decoder = null;
        jsonStr = "[1, 2, 3, 5]";
        decoder = new JsonDecoder(jsonStr);
        decoder.decode();
        vali = decoder.getValidator();
        assertEquals(vali.isPass(), true);
        assertEquals(vali.getIndex(), -1);
        //
        jsonStr = "[1, 2, 3 5]";
        decoder = new JsonDecoder(jsonStr);
        decoder.decode();
        vali = decoder.getValidator();
        assertEquals(vali.isPass(), false);
        assertEquals(vali.getIndex(), 9);
        //
        jsonStr = "{\"key_1\":\"value_1\", }";
        decoder = new JsonDecoder(jsonStr);
        decoder.decode();
        vali = decoder.getValidator();
        assertEquals(vali.isPass(), true);
        assertEquals(vali.getIndex(), -1);
        //
        jsonStr = "{\"key_1\":\"value_1\",\"key_2\" \"value_2\", }";
        decoder = new JsonDecoder(jsonStr);
        decoder.decode();
        vali = decoder.getValidator();
        assertEquals(vali.isPass(), false);
        assertEquals(vali.getIndex(), 27);
        //
        jsonStr = "\"abcdefg";
        decoder = new JsonDecoder(jsonStr);
        decoder.decode();
        vali = decoder.getValidator();
        assertEquals(vali.isPass(), false);
        assertEquals(vali.getIndex(), 8);

        /**
         * index error test
         */
        decoder = new JsonDecoder("{]");
        decoder.decode();
        vali = decoder.getValidator();
        assertFalse(vali.isPass());
        assertEquals(vali.getIndex(), 1);
    }

    /**
     * Test of decode method, of class JsonDecoder.
     * @throws IOException
     */
    @Test
    public void testDecode_String() throws IOException {

        System.out.println("decode");
        Object result;
        String jsonStr;//
        jsonStr = "\"moxie\"";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "moxie");
        jsonStr = "1.23";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "1.23");
        jsonStr = "true";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "true");
        jsonStr = "false";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "false");
        jsonStr = "{\"key_01\":\"string_value\",\"key_2\":\"string_value_2\""
                + ",\"key_3\":123}";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "{key_01=string_value, key_2=string_value_2, key_3=123}");
        jsonStr = "[1,2,3,5]";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "[1, 2, 3, 5]");
        jsonStr = "[{\"id\":123,\"title\":\"title_1\"}"
                + ",{\"id\":213,\"title\":\"title_2\"}"
                + ",{\"id\":321,\"title\":\"title_3\"}"
                + ",[1,2,3,4,5,{\"id\":567,\"title\":\"title_5\"}]]";
        result =
                JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "[{id=123, title=title_1}, {id=213, title=title_2}, {id=321, title=title_3}, [1, 2, 3, 4, 5, {id=567, title=title_5}]]");
        jsonStr = "[\"MoXie\",\"special:\\\"\\\\\\\\\\/\\\\b,\\\\f\\\\n"
                + "\\\\r\\\\t\",[\"MoXie\",\"special:\\\"\\\\\\\\\\/\\\\b"
                + "\\\\f\\\\n\\\\r\\\\t\"],[\"MoXie\",\"special:\\\"\\\\\\\\\\/"
                + "\\\\b\\\\f\\\\n\\\\r\\\\t\"],{\"name\":\"MoXie\",\"special\":"
                + "\"\\\"\\\\\\\\\\/\\\\b\\\\f\\\\n\\\\r\\\\t\"}]";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "[MoXie, special:\"\\\\/\\b,\\f\\n\\r\\t, [MoXie, special:\"\\\\/\\b\\f\\n\\r\\t], [MoXie, special:\"\\\\/\\b\\f\\n\\r\\t], {name=MoXie, special=\"\\\\/\\b\\f\\n\\r\\t}]");
        // blank
        jsonStr = " \"moxie\" ";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "moxie");
        jsonStr = " 1.23 ";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "1.23");
        /**
         * json 并不允许16进制数字
         */
        jsonStr = " 0x3f ";
        assertEquals(JsonDecoder.decode(jsonStr), 63L);
        //
        jsonStr = " true ";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "true");
        jsonStr = " false ";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "false");
        jsonStr = "{ \"key_01\" : \"string_value\" , \"key_2\" : \"string_value_2\" "
                + ", \"key_3\" : 123 } ";
        result = JsonDecoder.decode(jsonStr);
        // 数组
        jsonStr = "[ /**/ 1 , 2 , 3 , 5 ]";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "[1, 2, 3, 5]");
        jsonStr = "[ /**/ null]";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "[null]");
        jsonStr = "[ /**/ ]";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "[]");
        // 对象
        jsonStr = "{}";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "{}");
        jsonStr = "{:}";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "{}");
        jsonStr = "{\"key\":}";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "{}");
        jsonStr = "{\"\":\"value\"}";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "{=value}");
        jsonStr = "{ \"id\" : 2 , \"item\" : { \"key\" : \"value_2\" } , \"title\" : \"title_2\" , \"isShow\" : true , \"isNew\" : false , \"rate\" :2.2345 }";
        result = JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "{id=2, item={key=value_2}, title=title_2, isShow=true, isNew=false, rate=2.2345}");
        jsonStr = TextFileHelper.read(new File(
                TestUtil.getResource("json/JsonDecoderTest.json") //
                ));
        result = JsonDecoder.decode(jsonStr);
        JsonDecoder.decode(jsonStr);
        assertEquals(result.toString(), "[{id=1, item={key=value_1}, title=title_1"
                + ", isShow=true, isNew=false, rate=1.2345}, {id=2, item={key=value_2}"
                + ", title=title_2, isShow=true, isNew=false, rate=2.2345}"
                + ", {id=3, item={key=value_3}, title=title_3, isShow=true"
                + ", isNew=false, rate=3.2345}, {id=5, item={key=value_5}"
                + ", title=title_5, isShow=true, isNew=false, rate=5.2345}]");
        @SuppressWarnings("unchecked")
        List<Map<String, String>> entryList = (List<Map<String, String>>) JsonDecoder.decode(jsonStr);

        assertEquals(entryList.toString(), "[{id=1, item={key=value_1}, title=title_1"
                + ", isShow=true, isNew=false, rate=1.2345}, {id=2, item={key=value_2}"
                + ", title=title_2, isShow=true, isNew=false, rate=2.2345}"
                + ", {id=3, item={key=value_3}, title=title_3, isShow=true"
                + ", isNew=false, rate=3.2345}, {id=5, item={key=value_5}"
                + ", title=title_5, isShow=true, isNew=false, rate=5.2345}]");

        assertEquals(JsonDecoder.decode("1.3E5"), 130000.0);
        /**
         * octal
         */
        assertEquals(JsonDecoder.decode("26"), 26L); // decimal
        assertEquals(JsonDecoder.decode("032"), 26L); // octal
        assertEquals(JsonDecoder.decode("0x1a"), 26L); // hexadecimal
    }
}
