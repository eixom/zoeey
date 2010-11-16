/*
 * MoXie (SysTem128@GMail.Com) 2009-5-22 18:50:31
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

import org.zoeey.loader.fileupload.Uploader;
import org.zoeey.loader.fileupload.UploadConfig;
import org.zoeey.loader.fileupload.FileItem;
import org.zoeey.loader.fileupload.FileError;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import org.zoeey.util.JsonHelper;
import org.zoeey.util.TextFileHelper;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class UploaderTest extends TestCase {

    /**
     *
     */
    public UploaderTest() {
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
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testInit() throws IOException, ServletException {

        ServletRunner sr = new ServletRunner();
        sr.registerServlet("Uploader", "Uploader");
        ServletUnitClient sc = sr.newClient();
        PostMethodWebRequest postRequest = new PostMethodWebRequest("http://zoeey.org/", true);
        postRequest.selectFile("bugFile", new File(TestUtil.getBugPng()));
        postRequest.setParameter("name", "MoXie");
        postRequest.setParameter("email", "SysTem128@GMail.Com");
        postRequest.setParameter("multiLine", "1\n2\n3\n");
        InvocationContext ic = sc.newInvocation(postRequest);
        UploadConfig config = new UploadConfig();
        config.setFilesizeMax(888);
        Uploader up = new Uploader(ic.getRequest(), config);
        assertEquals("MoXie", up.getParamenter("name"));
        assertEquals("SysTem128@GMail.Com", up.getParamenter("email"));
        /**
         * 多行httpunit bug！？
         */
        assertEquals(JsonHelper.encode("1\r\n2\r\n3\r\n"), JsonHelper.encode(up.getParamenter("multiLine")));
        FileItem fileItem = up.getFileItem("bugFile");
        assertEquals(FileError.OK, fileItem.getError());
        assertEquals("bugFile", fileItem.getFieldName());
        assertEquals("bug.png", fileItem.getOriginalName());
        assertEquals(774, fileItem.getSize());
        assertTrue(fileItem.getTempFile().isFile());
        assertEquals(774, fileItem.getTempFile().length());
        assertEquals("image/png", fileItem.getType());

    }
}
