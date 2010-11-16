/*
 * MoXie (SysTem128@GMail.Com) Aug 31, 2009 11:07:23 AM
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.secure;

import org.zoeey.secure.PostTicket;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.JsonHelper;

/**
 *
 * @author MoXie
 */
public class PostTicketTest extends TestCase {

    /**
     *
     */
    public PostTicketTest() {
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
     * Test of create method, of class PostTicket.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        ServletRunner sr = new ServletRunner();
        sr.registerServlet("Uploader", "Uploader");
        ServletUnitClient sc = sr.newClient();
        /**
         * 创建ticket -> 放入表单中 -> 提交后检验
         */
        /**
         * 第一次提交验证成功
         */
        String ticket = PostTicket.create("tokenKey", sc.getSession(true));
        Boolean isVali = PostTicket.vali(ticket, "tokenKey", sc.getSession(true));
        assertTrue(isVali);
        /**
         * 未获取新的ticket，第二次提交验证失败
         */
        isVali = PostTicket.vali(ticket, "tokenKey", sc.getSession(true));
        assertFalse(isVali);
        /**
         * ticket是模拟出来的，验证失败
         */
        ticket = PostTicket.create("tokenKey", sc.getSession(true));
        ticket += "abc";
        isVali = PostTicket.vali(ticket, "tokenKey", sc.getSession(true));
        assertFalse(isVali);
    }
}
