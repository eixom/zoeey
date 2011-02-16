    /*
 * MoXie (SysTem128@GMail.Com) 2009-11-30 11:09:10
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
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
public class BeanHelperTest {

    /**
     *
     */
    public BeanHelperTest() {
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
     *
     */
    public static class Person {

        private String name;
        private int age;
        private boolean isActive;

        /**
         *
         */
        public Person() {
        }

        /**
         *
         * @param name
         * @param age
         * @param isActive
         */
        public Person(String name, int age, boolean isActive) {
            this.name = name;
            this.age = age;
            this.isActive = isActive;
        }

        /**
         *
         * @param age
         */
        public void setAge(int age) {
            this.age = age;
        }

        /**
         *
         * @param isActive
         */
        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        /**
         *
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         *
         * @return
         */
        public int getAge() {
            return age;
        }

        /**
         *
         * @return
         */
        public boolean isIsActive() {
            return isActive;
        }

        /**
         *
         * @return
         */
        public String getName() {
            return name;
        }
    }

    /**
     *
     */
    public static class HanMeimei extends Person {

        private String dress;

        /**
         *
         */
        public HanMeimei() {
        }

        /**
         *
         * @param name
         * @param age
         * @param isActive
         * @param dress
         */
        public HanMeimei(String name, int age, boolean isActive, String dress) {
            super(name, age, isActive);
            this.dress = dress;
        }

        /**
         *
         * @return
         */
        public String getDress() {
            return dress;
        }

        /**
         *
         * @param dress
         */
        public void setDress(String dress) {
            this.dress = dress;
        }
    }

    /**
     * Test of toMap method, of class BeanHelper.
     * @throws Exception
     */
    @Test
    public void testToMap() throws Exception {
        System.out.println("toMap");
        HanMeimei obj = new HanMeimei("Han Meimei", 20, true, "dress");
        Map<String, Object> expResult = new HashMap<String, Object>();
        expResult.put("isActive", true);
        expResult.put("dress", "dress");
        expResult.put("name", "Han Meimei");
        expResult.put("age", 20);
        expResult.put("class", "org.zoeey.util.BeanHelperTest$HanMeimei");


        Map<String, Object> result = BeanHelper.toMap(obj);

        assertEquals(JsonHelper.encode(expResult), JsonHelper.encode(result));
        expResult = new HashMap<String, Object>();
        expResult.put("isActive", true);
        expResult.put("dress", "dress");
        expResult.put("name", "Han Meimei");
        expResult.put("age", 20);
        result = BeanHelper.toMapIgnoreClass(obj);
        Assert.assertEquals(JsonHelper.encode(expResult), JsonHelper.encode(result));
    }

    /**
     * Test of toMap method, of class BeanHelper.
     * @throws Exception
     */
    @Test
    public void testFromMap() throws Exception {
        System.out.println("fromMap");
        HanMeimei obj = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("age", 20);
        map.put("class", "org.zoeey.util.BeanHelperTest$HanMeimei");
        map.put("isActive", true);
        map.put("dress", "dress");
        map.put("name", "Han Meimei");

        obj = (HanMeimei) BeanHelper.fromMap(map);
        Assert.assertEquals(obj.getAge(), 20);
        Assert.assertEquals(obj.isIsActive(), true);
        Assert.assertEquals(obj.getDress(), "dress");
        Assert.assertEquals(obj.getName(), "Han Meimei");
    }

    /**
     * Test of toMap method, of class BeanHelper.
     * @throws Exception
     */
    @Test
    public void testFromMap2Arg() throws Exception {
        System.out.println("fromMap2Arg");
        HanMeimei obj = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("age", 20);
        map.put("class", "org.zoeey.util.BeanHelperTest$HanMeimei");
        map.put("isActive", true);
        map.put("dress", "dress");
        map.put("name", "Han Meimei");

        obj = (HanMeimei) BeanHelper.fromMap(map, HanMeimei.class);
        Assert.assertEquals(obj.getAge(), 20);
        Assert.assertEquals(obj.isIsActive(), true);
        Assert.assertEquals(obj.getDress(), "dress");
        Assert.assertEquals(obj.getName(), "Han Meimei");
    }
}
