/*
 * MoXie (SysTem128@GMail.Com) 2010-1-3 4:36:10
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.handler;

import org.zoeey.common.handler.ExceptionHandler;
import org.zoeey.common.handler.DefaultExceptionHandler;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.DirInfo;
import org.zoeey.util.TextFileHelper;

/**
 *
 * @author MoXie
 */
public class DefaultExceptionHandlerTest {

    /**
     *
     */
    public DefaultExceptionHandlerTest() {
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
     * Test of newHandler method, of class DefaultExceptionHandler.
     */
    @Test
    public void testNewHandler() {
        System.out.println("newHandler");
        File file = new File(DirInfo.getClassesDir().concat("/noexistsfile.log"));
        try {
            TextFileHelper.read(file);
        } catch (IOException ex) {
            ExceptionHandler.setHandler(new DefaultExceptionHandler());
            ExceptionHandler.newHandler()//
                    .setLevel(Level.SEVERE)//
                    .setLogName("default test")//
                    .setMessage("找不到文件(%s)。")//
                    .setParts(new Object[]{file.getName()})//
                    .setCause(ex).handle();
        }

    }
}
