/*
 * MoXie (SysTem128@GMail.Com) 2009-4-23 14:33:58
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.StringHelper;
import org.zoeey.util.MemoryMeasurer;
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
public class MemoryMeasurerTest {

    /**
     *
     */
    public MemoryMeasurerTest() {
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
     * Test of getStartMemory method, of class MemoryMeasurer.
     * @throws InterruptedException
     */
    @Test
    public void testGetStartMemory_String() throws InterruptedException {
        String name = "runTime";
        MemoryMeasurer mm = new MemoryMeasurer();
        String[] memsTest = new String[5];
        for (int i = 0; i < 5; i++) {
            name = "runTime_" + i;
            mm.start(name);
            memsTest[i] = StringHelper.repeat(name, 50 * i);
            mm.stop(name);
        }
        assertNotSame(mm.getSpendMapping(), "[]");
    }
}