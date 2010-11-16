/*
 * MoXie (SysTem128@GMail.Com) 2009-8-10 14:06:54
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.Base64Encoder;
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
public class Base64EncoderTest {

    /**
     *
     */
    public Base64EncoderTest() {
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
     * Test of encode method, of class Base64Encoder.
     */
    @Test
    public void testEncode_String() {
        System.out.println("encode");
        String str = "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie" +
                "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie" +
                "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie" +
                "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie" +
                "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie";
        Base64Encoder encoder = new Base64Encoder();
        String expResult = "TW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW" +
                "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZQ==";
        String result = encoder.encode(str);
        assertEquals(expResult, result);
    }
}
