/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import java.io.File;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.Supervisor;
import org.zoeey.common.TestUtil;
import org.zoeey.loader.fileupload.FileError;
import org.zoeey.loader.fileupload.FileItem;
import org.zoeey.loader.fileupload.UploadConfig;
import org.zoeey.route.Query;
import org.zoeey.route.Router;
import org.zoeey.util.JsonHelper;
import org.zoeey.validator.standards.EmailVali;
import org.zoeey.validator.ValiAble;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class LoaderTest extends TestCase {

    /**
     *
     */
    public LoaderTest() {
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
     * Test of fetch method, of class Loader.
     * @throws Exception
     */
    @Test
    public void testFetch() throws Exception {
        System.out.println("fetch");

        ServletRunner sr = new ServletRunner();
        sr.registerServlet("ExampleServlet", ExampleServlet.class.getName());
        ServletUnitClient sc = sr.newClient();

        /**
         * Post,Cookies,Session method request
         */
        sc.putCookie("id", "128");
        PostMethodWebRequest postRequest = new PostMethodWebRequest("http://zoeey.org/ExampleServlet?email=example@zoeey.org", false);
        postRequest.setParameter("historys", new String[]{"a", "b", "c"});
        // 
        InvocationContext ic = sc.newInvocation(postRequest);
        ExampleServlet example = (ExampleServlet) ic.getServlet();
        example.setAccount(ic.getRequest(), "MoXie");
        Query query = new Query(null, null);
        query.setRequest(ic.getRequest());
        //
        Supervisor svisor = new Supervisor();
        Loader loader = new Loader(query);
        User user = new User();
        loader.<User>load(user);
        assertEquals(user.getAccount(), "MoXie"); // session 方式取值
        assertEquals(user.getId(), 128); // cookies 方式取值
        assertEquals(JsonHelper.encode(user.getHistorys()), "[\"a\",\"b\",\"c\"]"); // post String[] post方式取多值
        assertNull(user.getAvatar()); // FileItem 文件取值
        /**
         * get method request
         */
        GetMethodWebRequest getRequest = new GetMethodWebRequest("http://zoeey.org/ExampleServlet");
        getRequest.setParameter("email", "SysTem128@GMail.Com");
        getRequest.setParameter("items", new String[]{"1", "2", "3"});
        ic = sc.newInvocation(getRequest);
        query = new Query(null, null);
        query.setRequest(ic.getRequest());
        loader = new Loader(query);
        loader.setSvisor(svisor);
        loader.setValis(new ValiAble[]{new EmailVali()});
        loader.load(user);
        assertEquals(user.getId(), 128); // cookies
        assertEquals(user.getEmail(), "SysTem128@GMail.Com"); // get
        assertEquals(JsonHelper.encode(user.getItemIds()), "[1,2,3]"); // get int[]
        /**
         * file request
         */
        postRequest = new PostMethodWebRequest("http://zoeey.org/", true);
        File file = new File(TestUtil.getBugPng());
        postRequest.selectFile("avatar", file);
        postRequest.selectFile("photos", file);
        postRequest.setParameter("historys", new String[]{"a", "b", "c"});
        ic = sc.newInvocation(postRequest);
        UploadConfig config = new UploadConfig();
        config.setAllowTypes(new String[]{"image/png", "image/gif", "image/jpeg"});
        query = new Query(null, null);
        query.setRequest(ic.getRequest());
        loader = new Loader(query, config);
        loader.load(user);
        assertEquals(JsonHelper.encode(user.getHistorys()), "[\"a\",\"b\",\"c\"]"); // post String[]
        FileItem avatar = user.getAvatar(); // FileItem
        assertEquals(FileError.OK, avatar.getError());
        assertEquals("avatar", avatar.getFieldName());
        assertEquals("bug.png", avatar.getOriginalName());
        assertEquals(774, avatar.getSize());
        assertTrue(avatar.getTempFile().isFile());
        assertEquals(774, avatar.getTempFile().length());
        assertEquals("image/png", avatar.getType());
        FileItem[] photos = user.getPhotos(); // FileItem

        if (photos != null) { // 在修改User Photos的Accessory types时去掉了image/png
            for (FileItem photo : photos) {
                assertEquals(FileError.OK, photo.getError());
                assertEquals("photos", photo.getFieldName());
                assertEquals("bug.png", photo.getOriginalName());
                assertEquals(774, photo.getSize());
                assertTrue(photo.getTempFile().isFile());
                assertEquals(774, photo.getTempFile().length());
                assertEquals("image/png", photo.getType());
            }
        }
        /**
         * Loader Object test case
         */
        getRequest = new GetMethodWebRequest("http://zoeey.org/ExampleServlet");
        getRequest.setParameter("email", "SysTem128@GMail.Com");
        getRequest.setParameter("items", new String[]{"1", "2", "3"});
        ic = sc.newInvocation(getRequest);
        query = new Query(null, null);
        query.setRequest(ic.getRequest());
        loader = new Loader(query);
        loader.setSvisor(svisor);
        loader.setValis(new ValiAble[]{new EmailVali()});
        loader.load(user);
        assertEquals(user.getEmail(), "SysTem128@GMail.Com");
        assertEquals(svisor.getConclusion(), 0);
        for (String msg : svisor.getBriefList()) {
            fail("error: ".concat(msg));
        }
        /**
         * work with router
         */
        getRequest = new GetMethodWebRequest("http://zoeey.org/ExampleServlet");
        getRequest.setParameter("email", "SysTem128@GMail.Com");
        getRequest.setParameter("items", new String[]{"1", "2", "3"});
        ic = sc.newInvocation(getRequest);
        query = new Query(null, null);
        query.setRequest(ic.getRequest());
        loader = new Loader(query);
        Router router = new Router();
        router.add("/:action/:items[,]");
        loader.setParamHandler(router.getHandler("/login/5,6,7"));
        assertEquals(loader.getGet("action"), "login");
        assertEquals("3", loader.getGet("items")); // GET 权重大于 ROUTER
        assertArrayEquals(loader.getGets("items"), new String[]{"5", "6", "7", "1", "2", "3"});
    }
}
