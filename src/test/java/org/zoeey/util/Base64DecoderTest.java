/*
 * MoXie (SysTem128@GMail.Com) 2009-8-10 13:55:47
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.Base64Decoder;
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
public class Base64DecoderTest {

    /**
     *
     */
    public Base64DecoderTest() {
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
     * Test of decode method, of class Base64Decoder.
     */
    @Test
    public void testDecode_String() {
        System.out.println("decode");
        String str = "TW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZQ==";
        Base64Decoder decoder = new Base64Decoder();
        String expResult = "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie" +
                "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie" +
                "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie" +
                "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie" +
                "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie";
        String result = decoder.decode(str);
        assertEquals(expResult, result);
        try {
            /**
             * 容错性检验
             */
            decoder.decode("");
            decoder.decode("1");
            decoder.decode("a");
            decoder.decode("c");
            decoder.decode("=");
            decoder.decode("==");
            decoder.decode("-");
            decoder.decode("中文");
        } catch (Exception e) {
            fail("decode fail!");
        }
    }
}
