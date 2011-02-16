/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import org.zoeey.loader.ExampleServlet;
import org.zoeey.util.FileHelper;
import org.zoeey.util.TextFileHelper;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class StreamParserTest extends TestCase {

    /**
     *
     */
    public StreamParserTest() {
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
     * Test of parse method, of class StreamParser.
     * @throws Exception
     */
    @Test
    public void testParse() throws Exception {
        System.out.println("parse");

        HttpUnitOptions.setDefaultCharacterSet("utf-8");
        ServletRunner sr = new ServletRunner();
        sr.registerServlet("ExampleServlet", ExampleServlet.class.getName());
        ServletUnitClient sc = sr.newClient();
        PostMethodWebRequest postRequest = new PostMethodWebRequest("http://zoeey.org/ExampleServlet", true);
        postRequest.setParameter("name1", new String[]{"name1_value"});
        postRequest.setParameter("name2", new String[]{"name2_value"});
        postRequest.setParameter("name3", new String[]{"name3_value"});
        postRequest.setParameter("name5", new String[]{"中文字符串"});
        postRequest.setParameter("name6", new String[]{"路人甲"});
        postRequest.setParameter("MAX_FILE_SIZE", new String[]{"30000"});
        postRequest.selectFile("bugFile", new File(TestUtil.getBugPng()));
        postRequest.selectFile("bugFile2", new File(TestUtil.getBugPng()));
        //
        InvocationContext ic = sc.newInvocation(postRequest);
        HttpServletRequest request = ic.getRequest();
        String contentType = request.getContentType();
        String boundary = contentType.substring(contentType.indexOf('=') + 1);
        InputStream inStream = request.getInputStream();
        //
        UploadConfig config = new UploadConfig();
        File tempDir = new File(TestUtil.getResourceDir().concat("/temp"));
        FileHelper.tryMakeDirs(tempDir);
        config.setTempFileDir(tempDir);
        StreamParser sp = new StreamParser(boundary, config);
        sp.parse(inStream);
        assertEquals("{name5=[中文字符串], name6=[路人甲], name3=[name3_value], name1=[name1_value], name2=[name2_value], MAX_FILE_SIZE=[30000]}",//
                sp.getParamMap().toString());
        for (List<FileItem> fileItemList : sp.getFileMap().values()) {
            for (FileItem fileItem : fileItemList) {
                assertEquals("bug.png", fileItem.getOriginalName());
                assertEquals(fileItem.getError(), FileError.OK);
            }
        }
        FileHelper.tryDelete(tempDir, true);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testParse2() throws Exception {
        System.out.println("encodeTest");
        /**
         * 中文字符测试
         */
        String str = "中文";
        File file = File.createTempFile("temp_", ".tmp");
        TextFileHelper.write(file, str);
        assertEquals("中文", TextFileHelper.read(file));
        FileHelper.tryDelete(file);
        str = "中文字符串";
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            strBuilder.append(str.charAt(i));
        }
        assertEquals(str, strBuilder.toString());
        List<Character> list = new ArrayList<Character>();
        for (int i = 0; i < str.length(); i++) {
            list.add(str.charAt(i));
        }
        int size = list.size();
        char[] chars = new char[size];
        for (int i = 0; i < size; i++) {
            chars[i] = list.get(i);
        }
        assertEquals(str, new String(chars));
        file = File.createTempFile("temp_", ".tmp");
        TextFileHelper.write(file, str);
        InputStream is = new FileInputStream(file);
        int ich = 0;
        int[] ints = new int[str.length() * 3]; // utf-8 三个字节
        int i = 0;
        while ((ich = is.read()) != -1) {
            ints[i++] = ich;
        }
        FileHelper.tryDelete(file);
        byte[] bytes = new byte[ints.length];
        for (i = 0; i < ints.length; i++) {
            bytes[i] = (byte) ints[i];
        }
        assertEquals(str, new String(bytes, "utf-8"));
    }
}
