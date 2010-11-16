/*
 * MoXie (SysTem128@GMail.Com) 2009-12-31 16:36:30
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.handler;

import org.zoeey.common.handler.ExceptionHandler;
import org.zoeey.common.handler.SimpleExceptionHandler;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.DirInfo;
import org.zoeey.util.TextFileHelper;
import org.zoeey.util.TimeHelper;

/**
 *
 * @author MoXie
 */
public class SimpleExceptionHandlerTest extends TestCase {

    /**
     *
     */
    public SimpleExceptionHandlerTest() {
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
        System.out.println("以下异常栈为调试输出，并非实际异常===================");
    }

    /**
     *
     */
    @After
    public void tearDown() {
        System.out.println("以上异常栈为调试输出，并非实际异常--------------------");
    }

    /**
     * Test of ExceptionHandleAble method, of class DefaultExceptionHandler.
     */
    @Test
    public void testExceptionHandleAble_Throwable() {
        System.out.println("ExceptionHandleAble");
        File file = new File(DirInfo.getClassesDir().concat("/noexistsfile.log"));
        try {
            TextFileHelper.read(file);
            fail("noexistsfile.log文件存在，无法测试异常处理器");
        } catch (IOException ex) {
            ExceptionHandler.setHandler(new SimpleExceptionHandler());
            ExceptionHandler.newHandler()//
                    .setLevel(Level.SEVERE)//
                    .setLogName("test")//
                    .setMessage("找不到文件(%s)。")//
                    .setParts(new Object[]{file.getName()})//
                    .setCause(ex).handle();
        }
    }
}
