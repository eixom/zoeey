/*
 * MoXie (SysTem128@GMail.Com) 2010-8-30 14:01:06
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: Apache License  Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import org.zoeey.util.BinaryFileHelper;
import org.zoeey.ztpl.ParamsMap;
import org.zoeey.ztpl.TemplateAble;
import org.zoeey.ztpl.Ztpl;

/**
 *
 * @author MoXie
 */
public class ByteCodeHelperTest extends TestCase {

    public ByteCodeHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of newClass method, of class ByteCodeHelper.
     */
    @Test
    public void testWriteStr() throws Exception {
        /**
         * writeStr
         */
        System.out.println("writeStr");
        String className = "Test_writeStr";
        ByteCodeHelper helper = new ByteCodeHelper();
        helper.newClass(className);
        helper.writeStr("test_writeStr");
        helper.endClass();
        byte[] codes = helper.getCode();
        BinaryFileHelper.write(new File(TestUtil.getResource("ztpl")//
                + "/byteCodeHelper/WriteStrTest.class"), codes);
        ZtplClassLoader loader = new ZtplClassLoader();
        TemplateAble template = loader.defineClass(className, codes).newInstance();
        StringWriter writer = new StringWriter();
        Map<String, Object> context = new ParamsMap();
        template.publish(writer, context, new Ztpl());
        assertEquals(writer.toString(), "test_writeStr");
        writer.close();
        /**
         * writeVar
         */
        System.out.println("writeVar");
        className = "Test_writeVar";
        helper = new ByteCodeHelper();
        helper.newClass(className);
        helper.writeVar("key1");
        helper.endClass();
        codes = helper.getCode();
        BinaryFileHelper.write(new File(TestUtil.getResource("ztpl")//
                + "/byteCodeHelper/WriteVarTest.class"), codes);

        loader = new ZtplClassLoader();
        template = loader.defineClass(className, codes).newInstance();
        writer = new StringWriter();
        context = new ParamsMap();
        context.put("key1", "test_writeVar");
        template.publish(writer, context, new Ztpl());
        assertEquals(writer.toString(), "test_writeVar");
        writer.close();
        /**
         * writeVarJugeNull
         */
        System.out.println("writeVarJugeNull");
        className = "Test_writeVarJugeNull";
        helper = new ByteCodeHelper();
        helper.newClass(className);
        helper.writeVarJugeNull("key2");
        helper.endClass();
        codes = helper.getCode();
        BinaryFileHelper.write(new File(TestUtil.getResource("ztpl")//
                + "/byteCodeHelper/WriteVarJugeNullTest.class"), codes);

        loader = new ZtplClassLoader();
        template = loader.defineClass(className, codes).newInstance();
        // key2 isnot null
        writer = new StringWriter();
        context = new ParamsMap();
        context.put("key2", "test_writeVarJugeNull");
        template.publish(writer, context, new Ztpl());
        assertEquals(writer.toString(), "test_writeVarJugeNull");
        // key2 is null
        writer = new StringWriter();
        context = new ParamsMap();
        context.put("key2", null);
        template.publish(writer, context, new Ztpl());
        assertEquals(writer.toString(), "");
        writer.close();
        /**
         * newMap_putToMap_getFromMap
         */
        System.out.println("newMap_putToMap_getFromMap");
        className = "Test_newMap_putToMap_getFromMap";
        helper = new ByteCodeHelper();
        helper.newClass(className);
        int paramsMapPos = helper.newMap();
        helper.putToMap(paramsMapPos, "key3", helper.newVar("test_newMap_putToMap_getFromMap"));
        int valPos = helper.getFromMap(paramsMapPos, "key3");
        helper.writeVar(valPos);
        helper.endClass();
        codes = helper.getCode();
        BinaryFileHelper.write(new File(TestUtil.getResource("ztpl")//
                + "/byteCodeHelper/NewMap_putToMap_getFromMap.class"), codes);

        loader = new ZtplClassLoader();
        template = loader.defineClass(className, codes).newInstance();
        writer = new StringWriter();
        context = new ParamsMap();
        template.publish(writer, context, new Ztpl());
        assertEquals(writer.toString(), "test_newMap_putToMap_getFromMap");
        writer.close();
        /**
         * callClass
         */
        System.out.println("callClass");
        String Test_writeSting_forCallClass = "Test_writeSting_forCallClass";
        {
            // 生成一个publish内调用的TemplateAble类
            helper = new ByteCodeHelper();
            helper.newClass(Test_writeSting_forCallClass);
            helper.writeStr(",test_callClass_b");
            helper.endClass();
            codes = helper.getCode();
            BinaryFileHelper.write(new File(TestUtil.getResource("ztpl")//
                    + "/byteCodeHelper/CallClass_Test_writeSting_forCallClass.class"), codes);
            loader = new ZtplClassLoader();
            template = loader.defineClass(Test_writeSting_forCallClass, codes).newInstance();
        }

        className = "Test_callClass";
        helper.newClass(className);
        helper.writeStr("test_callClass_a");
        helper.callClass(Test_writeSting_forCallClass);
        helper.writeStr(",test_callClass_c");
        helper.endClass();
        codes = helper.getCode();
        BinaryFileHelper.write(new File(TestUtil.getResource("ztpl")//
                + "/byteCodeHelper/CallClass.class"), codes);
        template = loader.defineClass(className, codes).newInstance();
        writer = new StringWriter();
        context = new ParamsMap();
        template.publish(writer, context, new Ztpl());
        assertEquals(writer.toString(), "test_callClass_a,test_callClass_b,test_callClass_c");
        writer.close();
    }
}
