/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class DescriptionParserTest extends TestCase {

    /**
     *
     */
    public DescriptionParserTest() {
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
     * Test of getParam method, of class StringParser.
     */
    @Test
    public void testGetParam() {
        System.out.println("getParam");
        String source = null;
        Map paramMap = null;
        /**
         * 
         */
        source = "Content-Disposition: form-data; name=\"cFile\"; filename=\"\"\n"
                + "Content-Type: application/octet-stream";
        paramMap = DescriptionParser.getParam(source, true);

        assertEquals(new HashMap<String, String>() {

            {
                put("content-disposition", "form-data");
                put("name", "cFile");
                put("filename", "");
                put("content-type", "application/octet-stream");
            }
        }, paramMap);

        /**
         *
         */
        source = "Content-Disposition: form-data; name=\"cFile\"; filename=\"bug.png\"\n"
                + "Content-Type: application/octet-stream";
        paramMap = DescriptionParser.getParam(source, true);
        assertEquals(new HashMap<String, String>() {

            {
                put("content-disposition", "form-data");
                put("name", "cFile");
                put("filename", "bug.png");
                put("content-type", "application/octet-stream");
            }
        }, paramMap);

        /**
         *
         */
        source = "this is a string im=\"M\\oX\\\"ie\" im2=\"MoX\\\"ie\" "
                + "file = \"bug.png\" "
                + "name='haha' sex:male file2 = \"bug2.png\" "
                + "name2='haha2' ";
        paramMap = DescriptionParser.getParam(source, true);
        assertEquals(new HashMap<String, String>() {

            {
                put("im", "M\\oX\"ie");
                put("im2", "MoX\"ie");
                put("file", "bug.png");
                put("name", "haha");
                put("sex", "male");
                put("file2", "bug2.png");
                put("name2", "haha2");
            }
        }, paramMap);

        /**
         *
         */
        source = "Content-Disposition: form-data; name=\"bugFile\"; filename=\"test\\\\resource\\\\bug.png\"Content-Type: image/png ";
        paramMap = DescriptionParser.getParam(source, true);

        assertEquals(new HashMap<String, String>() {

            {
                put("name", "bugFile");
                put("content-disposition", "form-data");
                put("filename", "test\\resource\\bug.png");
                put("content-type", "image/png");
            }
        }, paramMap);
        /**
         * 
         */
        source = "Content-Disposition: form-data; name=\"bugFile\"; filename=\"test\\\\resource\\\\bug.png\"Content-Type: image/png abc";
        paramMap = DescriptionParser.getParam(source, true);

        assertEquals(new HashMap<String, String>() {

            {
                put("name", "bugFile");
                put("content-disposition", "form-data");
                put("filename", "test\\resource\\bug.png");
                put("content-type", "image/png");
            }
        }, paramMap);

        /**
         *
         */
        source = "Content-Disposition: form-data; name=\"bugFile\"; filename=\"test\\\\resource\\\\bug.png\"Content-Type: image/png abc";
        paramMap = DescriptionParser.getParam(source, false);

        assertEquals(new HashMap<String, String>() {

            {
                put("name", "bugFile");
                put("Content-Disposition", "form-data");
                put("filename", "test\\resource\\bug.png");
                put("Content-Type", "image/png");
            }
        }, paramMap);
    }
}
