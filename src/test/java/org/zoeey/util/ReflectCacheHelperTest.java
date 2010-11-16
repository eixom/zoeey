/*
 * MoXie (SysTem128@GMail.Com) 2009-5-9 15:12:35
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.loader.User;
import org.zoeey.loader.annotations.Request;
import org.zoeey.util.BeanHelperTest.HanMeimei;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ReflectCacheHelperTest extends TestCase {

    /**
     *
     */
    public ReflectCacheHelperTest() {
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
     * Test of cache method, of class ReflectCacheHelper.
     */
    @Test
    public void testCache() {
        System.out.println("cache");
        Class<?> clazz = Article.class;
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        Map<Method, Request> apmm = null;
        for (int i = 0; i < 1; i++) {
            ReflectCacheHelper rc = ReflectCacheHelper.get(clazz);

            for (Method method : rc.getMethodMap().values()) {
                method.getName();
            }
//            list = rc.getAnnotationPresentedList(Request.class);
//            apmm = rc.getAnnotationPresentedMethodMap(Request.class);
        }

//        System.out.println(tm.spend());
        /**
         * cached
         * 1,000,000
         * getAnnotationPresentedList
         * 4345
         * 4168
         * 4298
         * getAnnotationedMethodList
         * 3976
         * 4158
         * 4080
         * 4309
         * 100,000
         * 605ms
         * 583ms
         * 519ms
         * 519ms
         * 533ms
         * cached method annotations
         * 481ms
         * 483ms
         * 506ms
         * 10,000
         * 149ms
         * 111ms
         * 109ms
         * 1,000
         * 95ms
         * 81ms
         * 73ms
         * uncached
         * 1,000
         * 1127ms
         * 1144ms
         * 1184ms
         * 10,000
         * 8913ms
         */
        clazz = User.class;
        ReflectCacheHelper reflect = ReflectCacheHelper.get(clazz);
        apmm = reflect.<Request>getAnnotationPresentedMethodMap(Request.class);
        StringBuilder strBuilder = new StringBuilder();
        String[] names = new String[apmm.values().size()];
        int i = 0;
        for (Request req : apmm.values()) {
            names[i++] = req.name();
        }
        String[] expResult = "photos,email,items,historys,account,avatar,id".split(",");
        assertTrue(expResult.length == names.length);
        assertTrue(ArrayHelper.inArray(expResult, names));
        strBuilder = new StringBuilder();
        Map<Method, Map<Class<? extends Annotation>, Annotation>> maMap = reflect.getMethodAnnotationMap();
        List<String> nameList = new ArrayList<String>();
        for (Map<Class<? extends Annotation>, Annotation> aMap : maMap.values()) {
            for (Annotation annot : aMap.values()) {
                nameList.add(annot.annotationType().getName());
            }
        }

        expResult = StringHelper.split("org.zoeey.loader.annotations.Request"
                + ",org.zoeey.validator.annotations.Accessory"
                + ",org.zoeey.loader.annotations.Request"
                + ",org.zoeey.validator.annotations.Email"
                + ",org.zoeey.validator.annotations.Conclusion"
                + ",org.zoeey.loader.annotations.Request"
                + ",org.zoeey.loader.annotations.Request"
                + ",org.zoeey.loader.annotations.Request"
                + ",org.zoeey.loader.annotations.Request"
                + ",org.zoeey.loader.annotations.Request", ',');

        assertTrue(expResult.length == nameList.size());
        assertTrue(ArrayHelper.inArray(nameList.toArray(new String[0]), expResult));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testGetGetMap() throws Exception {
        System.out.println("getGetMap");
        Object obj = new HanMeimei("Han Meimei", 20, true, "dress");
        ReflectCacheHelper refHelper = ReflectCacheHelper.get(obj.getClass());
        Map<String, Method> beanMap = refHelper.getGetMap();
        String[] keys = new String[beanMap.entrySet().size()];
        int i = 0;
        for (Entry<String, Method> entry : beanMap.entrySet()) {
            keys[i++] = entry.getKey();
        }

        String[] expResult = "age,class,isActive,dress,name".split(",");
        assertTrue(expResult.length == keys.length);
        assertTrue(ArrayHelper.inArray(expResult, keys));
    }

    /**
     * multi-thread test
     */
    @Test
    public void testPut() throws Exception {
        if (true) {
            return;
        }
        System.out.println("testPut");
        ObjectCacheHelper.removeAll();
        for (int i = 0; i < 10; i++) {
            Thread thread = new PutThread();
            thread.start();
        }
        while (Thread.activeCount() > 1) {
            Thread.sleep(100);
        }
    }

    private static class PutThread extends Thread {

        @Override
        public void run() {
            ReflectCacheHelper.get(HanMeimei.class);
        }
    }
}
