/*
 * MoXie (SysTem128@GMail.Com) 2009-4-12 14:02:39
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;
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
public class JsonHelperTest extends TestCase {

    /**
     *
     */
    public JsonHelperTest() {
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
     * Test of encode method, of class JsonHelper.
     * @throws UnsupportedEncodingException
     * @throws CharacterCodingException
     */
    @Test
    public void testEncode() throws UnsupportedEncodingException, CharacterCodingException {
        System.out.println("encode");
//        JsonEncoderTest jet = new JsonEncoderTest();
//        TimeMeasurer tm = new TimeMeasurer();
//        tm.start();
//        for (int i = 0; i < 10000; i++) {
//            jet.testEncode();
//        }
//        tm.stop();
//        System.out.println(tm.spend());
        /**
         * 10,000 769ms
         * 10,000 722ms
         * 100,000 5606ms
         * 100,000 5583ms
         */
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testDecode() throws IOException {
        JsonDecoderTest jdt = new JsonDecoderTest();
        jdt.testDecode_String();
        //        jsonStr = TextFileHelper.read(new File(DirInfo.getClassDir(KeyValueFileTest.class)//
//                .replace("build/classes", "test") + "JsonDecoderTestTM.json"));
//        TimeMeasurer tm = new TimeMeasurer();
//        MemoryMeasurer mm = new MemoryMeasurer();
//        mm.start();
//        tm.start();
//        result = "";
//        for (int i = 0; i < 10000; i++) {
//            result = JsonHelper.decode(jsonStr);
//        }
//        System.out.println(result);
//        tm.stop();
//        System.out.println(tm.spend());
//        mm.stop();
//        System.out.println(mm.spend());
        /**
         * 0.1
         * 10,000 2745ms 488288
         * 10,000 2743ms 489600
         * 10,000 2772ms 488288
         * 100,000 20571ms
         * 0.2
         * 10,000 1645ms 388016
         * 10,000 1667ms 388016
         * 10,000 1774ms 388016
         * 20行中文注释
         * 10,000 1447ms 52008
         * 10,000 1512ms 52008
         * 10,000 1491ms 52008
         *
         * 各类型分析平均耗时
         * 45 行注释 163ms
         * 20套 单数字 529ms
         * 20套 全数字 528ms
         * 20套 true 451ms
         * 20套 false 497ms
         * -------------------------------
         * 20套 全字母 751ms
         * 优化一节for -> while 618ms/586ms/627ms
         *
         */
    }
}
