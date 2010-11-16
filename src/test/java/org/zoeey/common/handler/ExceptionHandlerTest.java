/*
 * MoXie (SysTem128@GMail.Com) 2010-1-3 5:13:08
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.handler;

import org.zoeey.common.handler.ExceptionHandler;
import org.zoeey.common.handler.ExceptionHandleBase;
import org.zoeey.common.handler.ExceptionHandleAble;
import org.zoeey.common.handler.SimpleExceptionHandler;
import org.zoeey.common.handler.DefaultExceptionHandler;
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

/**
 *
 * @author MoXie
 */
public class ExceptionHandlerTest extends TestCase {

    /**
     *
     */
    public ExceptionHandlerTest() {
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
     * Test of newHandler method, of class ExceptionHandler.
     */
    @Test
    public void testNewHandler() {
        System.out.println("newHandler");
        Thread threadA = new Thread() {

            @Override
            public void run() {
                while (true) {
                    ExceptionHandler.setHandler(new HandleA());
                    ExceptionHandler.newHandler().handle();
                }
            }
        };
        Thread threadB = new Thread() {

            @Override
            public void run() {
                while (true) {
                    ExceptionHandler.setHandler(new HandleB());
                    ExceptionHandler.newHandler().handle();
                }
            }
        };

        if (true) {
            return; // test off
        }
        threadA.start();
        threadB.start();
        while (threadA.isAlive() || threadB.isAlive()) {
        }
    }

    /**
     *
     */
    public static class HandleA extends ExceptionHandleBase {

        public ExceptionHandleAble newHandler() {

            return new HandleA();
        }

        public void handle() {
            System.out.println("Handler A");
        }
    }

    /**
     *
     */
    public static class HandleB extends ExceptionHandleBase {

        public ExceptionHandleAble newHandler() {
            return new HandleB();
        }

        public void handle() {
            System.out.println("Handler B");
        }
    }

    /**
     * Test of newHandler method, of class ExceptionHandler.
     */
    @Test
    public void testNewHandler2() {
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
            System.out.println("==========================================");
            ExceptionHandler.setHandler(new DefaultExceptionHandler());
            ExceptionHandler.newHandler()//
                    .setLevel(Level.SEVERE)//
                    .setLogName("test")//
                    .setMessage("找不到文件(%s)。")//
                    .setParts(new Object[]{file.getName()})//
                    .setCause(ex).handle();
        }
    }
}
