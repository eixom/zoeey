/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zoeey.common;

import org.zoeey.common.Version;
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
public class VersionTest extends TestCase {

    /**
     *
     */
    public VersionTest() {
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
     * Test of compare method, of class Version.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        String ver = "Zoeey 0.1";
        /**
         * 当前版本较新
         */
        int expResult = -1;
        int result = Version.compare(ver);
        assertEquals(expResult, result);
        /**
         * 与当前版本相同
         */
        ver = String.valueOf(Version.VERSION);
        expResult = 0;
        result = Version.compare(ver);
        assertEquals(expResult, result);
        /**
         * 当前版本较旧
         */
        ver = ver + ".1";
        expResult = 1;
        result = Version.compare(ver);
        assertEquals(expResult, result);
        /**
         * 当前版本较旧
         */
        ver = ver + "Zoeey 999.0 beta";
        expResult = 1;
        result = Version.compare(ver);
        assertEquals(expResult, result);
    }

    /**
     * Test of equalOrlater method, of class Version.
     */
    @Test
    public void testEqualOrlater() {
        System.out.println("equalOrlater");
        String ver = String.valueOf(Version.VERSION);
        boolean expResult = true;
        boolean result = Version.equalOrlater(ver);
        assertEquals(expResult, result);
        ver = ver + "0.1";
        expResult = true;
        result = Version.equalOrlater(ver);
        assertEquals(expResult, result);
    }
}
