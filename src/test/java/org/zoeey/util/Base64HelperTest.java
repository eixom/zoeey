/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.Base64Helper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public class Base64HelperTest {

    /**
     *
     */
    public Base64HelperTest() {
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
     * Test of encode method, of class Base64.
     * @throws UnsupportedEncodingException 
     * @throws IOException 
     */
    @Test
    public void testToString() throws UnsupportedEncodingException, IOException, IOException {
        System.out.println("toString");
        String result = Base64Helper.encode("moxie");
        assertEquals(result, "bW94aWU=");
        result = Base64Helper.encode("moxie".getBytes());
        assertEquals(result, "bW94aWU=");
        assertEquals(Base64Helper.encode("moxiemoxiemoxiemoxiemoxiemoxiemoxie"
                + "moxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxie"
                + "moxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxie"
                + "moxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxie"
                + "moxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxiemoxie"
                + ""), "bW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1v"
                + "eGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1v"
                + "eGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1v"
                + "eGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1v"
                + "eGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1v"
                + "eGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1veGllbW94aWVtb3hpZW1v"
                + "eGllbW94aWU=");
    }

    /**
     * Test of decode method, of class Base64.
     * @throws IOException
     */
    @Test
    public void testFromString() throws IOException {
        System.out.println("fromString");
        String str = "bW94aWU=";
        String result = Base64Helper.decode(str);
        assertEquals(result, "moxie");
        assertEquals(Base64Helper.decode("TW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW"
                + "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW"
                + "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW"
                + "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW"
                + "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW"
                + "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZU1vWGllTW"
                + "9YaWVNb1hpZU1vWGllTW9YaWVNb1hpZQ=="),
                "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie"
                + "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie"
                + "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie"
                + "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie"
                + "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie"
                + "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie"
                + "MoXieMoXieMoXieMoXieMoXieMoXieMoXieMoXie");
    }
}
