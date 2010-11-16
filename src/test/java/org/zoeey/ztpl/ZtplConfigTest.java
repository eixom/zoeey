/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 21:00:43
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl;

import java.util.List;
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
public class ZtplConfigTest {

    /**
     *
     */
    public ZtplConfigTest() {
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
     * Test of addPlugins method, of class ZtplConfig.
     */
    @Test
    public void testAddPlugins_List() {
        System.out.println("addPlugins");
        List<Package> pluginPacks = null;
        ZtplConfig ztplConfig = new ZtplConfig();
    }

    /**
     * Test of addPlugins method, of class ZtplConfig.
     */
    @Test
    public void testAddPlugins_Package() {
        System.out.println("addPlugins");
    }
}