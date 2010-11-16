/*
 * MoXie (SysTem128@GMail.Com) 2009-4-24 2:34:37
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.util.VarFinder;
import java.util.HashMap;
import java.util.Map;
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
public class VarFinderTest extends TestCase {

    /**
     *
     */
    public VarFinderTest() {
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
     * Test of find method, of class VarFinder.
     */
    @Test
    public void testFinder() {
        System.out.println("finder");
        Map<String, String> mapA, mapB, mapC;
        mapA = new HashMap<String, String>();
        mapB = new HashMap<String, String>();
        mapC = new HashMap<String, String>();
        mapA.put("key_1", "mapA_value_1");
        mapB.put("key_1", "mapB_value_1");
        mapB.put("key_2", "mapB_value_2");
        mapC.put("key_1", "mapC_value_1");
        mapC.put("key_2", "mapC_value_2");
        mapC.put("key_3", "mapC_value_3");
        VarFinder<String, String> varFinder = new VarFinder<String, String>();
//        TimeMeasurer tm = new TimeMeasurer();
//        MemoryMeasurer mm = new MemoryMeasurer();
//        tm.start();
//        mm.start();
//        for (int i = 0; i < 10000; i++) {
//            varFinder.find("key_3").from(mapA).or("key_2").or("key_3").done();
//            varFinder.find("key_3").from(mapB).or("key_2").or("key_3").done();
//            varFinder.find("key_3").from(mapC).or("key_2").or("key_3").done();
//            varFinder.find("key_1").from(mapA).or(mapB).or(mapC).done();
//            varFinder.find("key_2").from(mapA).or(mapB).or(mapC).done();
//            varFinder.find("key_3").from(mapA).or(mapB).or(mapC).done();
//            varFinder.from(mapA).or(mapC).or(mapB).find("key_1").done();
//            varFinder.from(mapA).or(mapC).or(mapB).find("key_2").done();
//            varFinder.from(mapA).or(mapC).or(mapB).find("key_3").done();
//            varFinder.from(mapA).or(mapC).or(mapB).find("key_4").done();
//            varFinder.from(mapA).or(mapC).or(mapB).find("key_4").or()//
//                    .find("key_2").from(mapA).or(mapB)//
//                    .done("default_value");
//        }
//        tm.stop();
//        mm.stop();
//        System.out.println(tm.spend());
//        System.out.println(mm.spend());
        /**
         * 10,000 658352b
         * 100,000 41664b
         * 10,000 138ms
         * 10,000 132ms
         * 10,000 137ms
         * 100,000 1078ms
         * 100,000 978ms
         * 100,000 1015ms
         * 100,000 993ms
         * 1,000,000 8988ms
         * 1,000,000 9189ms
         * 1,000,000 9753ms
         */
        assertEquals(varFinder.find("key_3").from(mapA).or("key_2").or("key_3").done(), null);
        assertEquals(varFinder.find("key_3").from(mapB).or("key_2").or("key_3").done(), "mapB_value_2");
        assertEquals(varFinder.find("key_3").from(mapC).or("key_2").or("key_3").done(), "mapC_value_3");
        assertEquals(varFinder.find("key_1").from(mapA).or(mapB).or(mapC).done(), "mapA_value_1");
        assertEquals(varFinder.find("key_2").from(mapA).or(mapB).or(mapC).done(), "mapB_value_2");
        assertEquals(varFinder.find("key_3").from(mapA).or(mapB).or(mapC).done(), "mapC_value_3");
        assertEquals(varFinder.from(mapA).or(mapC).or(mapB).find("key_1").done(), "mapA_value_1");
        assertEquals(varFinder.from(mapA).or(mapC).or(mapB).find("key_2").done(), "mapC_value_2");
        assertEquals(varFinder.from(mapA).or(mapC).or(mapB).find("key_3").done(), "mapC_value_3");
        assertEquals(varFinder.from(mapA).or(mapC).or(mapB).find("key_4").done("default_value"),//
                "default_value");
        assertEquals(varFinder.from(mapA).or(mapC).or(mapB).find("key_4").or()//
                .find("key_2").from(mapA).or(mapB)//
                .done("default_value"), "mapB_value_2");


    }
}