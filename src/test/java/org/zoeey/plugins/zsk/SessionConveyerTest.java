/*
 * MoXie (SysTem128@GMail.Com) 2009-6-19 10:56:30
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins.zsk;

import java.io.IOException;
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
public class SessionConveyerTest {

    /**
     *
     */
    public SessionConveyerTest() {
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
     * Test of setSessionId method, of class SessionConveyer.
     * @throws IOException 
     */
    @Test
    public void testSetSessionId() throws IOException {
        System.out.println("setSessionId");
        String clientSessionId = "conversation A";
        /**
         * A 发起会话
         */
        SessionConveyer sc = new SessionConveyer();
        sc.setData("is MoXie");
        sc.setSessionId(clientSessionId);
        String ticket = sc.getTicket("psw from a");
        /**
         * 将会话内容(ticket)递给 B
         */
        sc = new SessionConveyer();
        sc.parse(ticket, "psw from a");
        // 进行验证
        if (sc.vali()) {
            assertEquals(sc.getData(), "is MoXie");
        } else {
            fail("验证失败");
        }
        /**
         * 将数据反馈回 A
         */
        sc.setData("true");
        ticket = sc.getTicket("psw from b");
        /**
         * A 接收
         */
        sc.parse(ticket, "psw from b");
        if (sc.vali(clientSessionId)) {
            assertEquals(sc.getData(), "true");
        } else {
            fail("验证失败");
        }
    }
}
