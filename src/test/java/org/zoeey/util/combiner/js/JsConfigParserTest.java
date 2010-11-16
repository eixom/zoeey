/*
 * MoXie (SysTem128@GMail.Com) 2009-3-20 8:06:27
 */
package org.zoeey.util.combiner.js;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.zoeey.common.TestUtil;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsConfigParserTest {

    /**
     *
     */
    public JsConfigParserTest() {
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
     * Test of newParser method, of class JsConfigParser.
     * @throws IOException 
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws JsFileException
     */
    @Test
    public void testNewParser() throws IOException, SAXException,
            ParserConfigurationException, JsFileException {
        System.out.println("newParser");
        JsConfigParser parser = new JsConfigParser();
        String filePath = TestUtil.getResource("jsLoader/JsLoaderTest.xml");
        parser.parse(new File(filePath));
        JsContext context = parser.getContext();
        /**
         * 根路径
         */
        assertEquals(context.getFileRoot(), "{classesDir}/jsLoader/script");
        StringBuffer strBuffer = new StringBuffer();
        for (JsFile js : context.getSafeRootList()) {
            strBuffer.append(js.getFilePath());
            strBuffer.append(',');
        }

        assertEquals(strBuffer.toString(), "/webRoot/another_safe_jses"
                + ",/webRoot/another_safe_jses2,");
        /**
         * 强制加载的文件
         */
        strBuffer = new StringBuffer();
        for (JsFile js : context.getForceFileList()) {
            strBuffer.append(js.getPath());
            strBuffer.append(',');
        }

        assertEquals(strBuffer.toString(), "/force.js,/force_2.js,/force_3.js,");
        /**
         * 跳转的路径
         */
        if (false) {
            strBuffer = new StringBuffer();
            for (JsFile js : context.getRedirectList().values()) {
                strBuffer.append(js.getName());
                strBuffer.append(js.getFilePath());
                strBuffer.append(',');
            }
            assertEquals(strBuffer.toString(), "cross_yh_weather"
                    + "http://someapi.com/weather.jsp,my_weatherd:/webRoot/scripts/weather.jsp,");
        }
        /**
         * 单独的文件
         */
        strBuffer = new StringBuffer();
        for (JsFile js : context.getSingleFileMap().values()) {
            strBuffer.append(js.getName());
            strBuffer.append(js.getPath());
            strBuffer.append(',');
        }

        assertEquals(strBuffer.toString(), "article.edit/edit.js"
                + ",single_2/article/single_2.js"
                + ",single_1/single_1.js,article.list/list.js,");
        /**
         * 组文件
         */
        strBuffer = new StringBuffer();
        Iterator<Entry<String, List<JsFile>>> iterator = context.getGroupFileMap().entrySet().iterator();
        Entry<String, List<JsFile>> entry;
        List<JsFile> jsList;
        strBuffer.append('[');
        while (iterator.hasNext()) {
            entry = iterator.next();
            strBuffer.append('{');
            strBuffer.append(entry.getKey());
            strBuffer.append(':');
            jsList = entry.getValue();
            for (JsFile js : jsList) {
                strBuffer.append('{');
                strBuffer.append(js.getName());
                strBuffer.append(':');
                strBuffer.append(js.getPath());
                strBuffer.append("},");
            }
            strBuffer.append("},");
        }
        strBuffer.append(']');
      
        assertEquals(strBuffer.toString(), "[{article.ajax:"
                + "{group_file_a:/json.list.js},{null:/json.update.js},},]");
        /**
         * 所有命名文件
         */
        strBuffer = new StringBuffer();
        Iterator<Entry<String, JsFile>> iteratorNamed = context.getNamedFileMap().entrySet().iterator();
        Entry<String, JsFile> entryNamed;
        while (iteratorNamed.hasNext()) {
            entryNamed = iteratorNamed.next();
            strBuffer.append(entryNamed.getKey());
            strBuffer.append(':');
            strBuffer.append(entryNamed.getValue().getPath());
            strBuffer.append(',');
        }        
        assertEquals(strBuffer.toString(), "article.edit:/edit.js"
                + ",single_2:/article/single_2.js"
                + ",force_2:/force_2.js"
                + ",article.list:/list.js"
                + ",single_1:/single_1.js"
                + ",group_file_a:/json.list.js,");
    }
}
