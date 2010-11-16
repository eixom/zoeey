/*
 * MoXie (SysTem128@GMail.Com) 2009-4-11 13:55:50
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.IpHelper;
import java.util.Date;
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
public class IpHelperTest {

    /**
     *
     */
    public IpHelperTest() {
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
     * Test of toLong method, of class IpHelper.
     */
    @Test
    public void testToLong() {
        System.out.println("toLong");
        String ipStr = "208.77.188.166";
        long expResult = -800211802;
        long result = IpHelper.toLong(ipStr);
        assertEquals(expResult, result);
        //
        ipStr = "172.16.1.21";
        expResult = -1408237291;
        result = IpHelper.toLong(ipStr);
        assertEquals(expResult, result);
        //
        ipStr = "172.16.1.2132111";
        expResult = 0L;
        result = IpHelper.toLong(ipStr);
        assertEquals(expResult, result);
        //
        ipStr = "172.16.1.21.2.3";
        expResult = 0L;
        result = IpHelper.toLong(ipStr);
        assertEquals(expResult, result);
        //
        ipStr = "172.16.a.b";
        expResult = -1408237568;
        result = IpHelper.toLong(ipStr);
        assertEquals(expResult, result);
        //
        ipStr = "172.16.1.28";
        expResult = -1408237284L;
        result = IpHelper.toLong(ipStr);
        assertEquals(expResult, result);
        //
        ipStr = "0.0.0.0";
        expResult = 0L;
        result = IpHelper.toLong(ipStr);
        assertEquals(expResult, result);

    }

    /**
     * Test of toString method, of class IpHelper.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        long ipLong = 3494755494L;
        String expResult = "208.77.188.166";
        String result = IpHelper.toString(ipLong);
        assertEquals(expResult, result);

        ipLong = 2886730005L;
        expResult = "172.16.1.21";
        result = IpHelper.toString(ipLong);
        assertEquals(expResult, result);

        ipLong = -1408237291;
        expResult = "172.16.1.21";
        result = IpHelper.toString(ipLong);
        assertEquals(expResult, result);

        ipLong = 0x100000000L;
        expResult = "";
        result = IpHelper.toString(ipLong);
        assertEquals(expResult, result);

        ipLong = -1408237284L;
        expResult = "172.16.1.28";
        result = IpHelper.toString(ipLong);
        assertEquals(expResult, result);
    }
}
