/*
 * MoXie (SysTem128@GMail.Com) 2009-6-25 1:18:27
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
public class FireJavaSimpleTest extends TestCase {

    /**
     *
     */
    public FireJavaSimpleTest() {
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
     * Test of log method, of class FireJavaSimple.
     * @throws IOException
     */
    @Test
    public void testLog() throws IOException {
        System.out.println("log");
        ServletRunner sr = new ServletRunner();
        sr.registerServlet("FireJavaSimple", "FireJavaSimple");
        ServletUnitClient sc = sr.newClient();
        PostMethodWebRequest postRequest = new PostMethodWebRequest("http://zoeey.org/", true);
        postRequest.setParameter("name", "MoXie");
        postRequest.setParameter("email", "SysTem128@GMail.Com");
        InvocationContext ic = sc.newInvocation(postRequest);
        HttpServletResponse response = ic.getResponse();
        FireJavaSimple.log(response, "LOG MESSAGE");
        FireJavaSimple.info(response, "INFO MESSAGE");
        FireJavaSimple.warn(response, "WARN MESSAGE");
        FireJavaSimple.error(response, "ERROR MESSAGE");
        WebResponse wresponse = ic.getServletResponse();
        assertEquals(wresponse.getHeaderField("X-WF-1-INDEX"), "4");
        assertEquals(wresponse.getHeaderField("X-WF-1-1-1-1"), "30|[{\"Type\":\"LOG\"},\"LOG MESSAGE\"]|");
        assertEquals(wresponse.getHeaderField("X-WF-1-1-1-2"), "32|[{\"Type\":\"INFO\"},\"INFO MESSAGE\"]|");
        assertEquals(wresponse.getHeaderField("X-WF-1-1-1-3"), "32|[{\"Type\":\"WARN\"},\"WARN MESSAGE\"]|");
        assertEquals(wresponse.getHeaderField("X-WF-1-1-1-4"), "34|[{\"Type\":\"ERROR\"},\"ERROR MESSAGE\"]|");
    }
}
