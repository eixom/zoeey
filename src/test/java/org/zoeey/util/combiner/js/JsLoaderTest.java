/*
 * MoXie (SysTem128@GMail.Com) 2009-3-22 0:04:18
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner.js;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.zoeey.common.TestUtil;
import org.zoeey.util.TextFileHelper;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsLoaderTest extends TestCase {

    /**
     *
     */
    public JsLoaderTest() {
    }
    /**
     * Test of load method, of class JsLoader.
     */
    private static String resourceRoot = TestUtil.getResource("jsLoader");

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
     *
     * @throws IOException
     * @throws JsFileException
     * @throws InterruptedException
     */
    @Test
    public void testLoad() throws IOException, JsFileException, InterruptedException {

        JsLoader jsLoader = new JsLoader();
        jsLoader.setIsDebug(true);
        jsLoader.setCacheDirRoot(resourceRoot.concat("/cache"));
        JsContext context = new JsContext();
        String _jsRoot = resourceRoot + "/script/generated";

        TextFileHelper.write(new File(_jsRoot.concat("/force.js")), "file_force.js");
        TextFileHelper.write(new File(_jsRoot.concat("/single_1.js")), "file_single_1.js");
        TextFileHelper.write(new File(_jsRoot.concat("/single_2.js")), "file_single_2.js");
        TextFileHelper.write(new File(_jsRoot.concat("/group_1.js")), "file_group_1.js");
        TextFileHelper.write(new File(_jsRoot.concat("/group_2.js")), "file_group_2.js");
        TextFileHelper.write(new File(_jsRoot.concat("/group_3.js")), "file_group_3.js");

        context.setFileRoot(_jsRoot);
        // force
        context.addForceFile(new JsFile("/force.js").setFileRoot(_jsRoot));
        context.addForceFile(new JsFile("/force.js").setFileRoot(_jsRoot));
        // namedfile
        jsLoader.merge("single_2"); // 在combinText前即可
        jsLoader.merge("single_1"); // 在combinText前即可
        context.addNamedFile(new JsFile("/single_1.js").setFileRoot(_jsRoot).setName("single_1"));
        context.addNamedFile(new JsFile("/single_2.js").setFileRoot(_jsRoot).setName("single_2"));

        // group_1 : ["group_1.js","group_2.js"]
        // group_2 : ["group_3.js","group_1.js"]
        context.addGroupFile("group_1", new JsFile("/group_1.js").setFileRoot(_jsRoot));
        context.addGroupFile("group_1", new JsFile("/group_2.js").setFileRoot(_jsRoot));
        context.addGroupFile("group_2", new JsFile("/group_3.js").setFileRoot(_jsRoot));
        context.addGroupFile("group_2", new JsFile("/group_2.js").setFileRoot(_jsRoot));

        jsLoader.merge(new String[]{"group_1", "group_2"});
        jsLoader.setContext(context);
        StringWriter strWriter = new StringWriter();
        jsLoader.display(strWriter, -1);
        jsLoader.cleanCache("/");
        String combinedJs = strWriter.toString();

        assertTrue(combinedJs.indexOf("group_2") > -1);
        assertTrue(combinedJs.indexOf("group_1") > -1);
        if (combinedJs.indexOf("file_single_2") > combinedJs.indexOf("file_single_1")
                || combinedJs.indexOf("file_group_１") > combinedJs.indexOf("file_group_2")) {
            fail("加载顺序不对！");
        }

    }

    /**
     *
     * @throws IOException
     * @throws JsFileException
     * @throws InterruptedException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    @Test
    public void testLoadFromFile() throws IOException, JsFileException, InterruptedException, SAXException, ParserConfigurationException {
        JsLoader jsLoader = new JsLoader();
        JsContext context = new JsContext();
        File configFile = new File(TestUtil.getResource("jsLoader/JsLoaderTest.xml"));
        context = JsLoader.loadConfig(configFile);
        context.setFileRoot(resourceRoot);
        //
        jsLoader.merge("article.list");
        jsLoader.merge("article.edit");
        jsLoader.setCacheDirRoot(resourceRoot + "/cache");
        jsLoader.setContext(context);
        /**
         * generate js files
         */
        String str = TextFileHelper.read(jsLoader.fetch(1));
        int listIdx = str.indexOf("article/list.js");
        int editIdx = str.indexOf("article/edit.js");
        assertTrue(listIdx > -1 //
                && editIdx > -1 //
                && editIdx > listIdx);
    }

    /**
     *
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws JsFileException
     * @throws InterruptedException
     */
    @Test
    public void testJsLoader() throws SAXException, ParserConfigurationException, IOException, JsFileException, InterruptedException {
        String cacheDir = resourceRoot;

        File configFile = new File(TestUtil.getResource("jsLoader/JsLoaderTest.xml"));
        //
        JsLoader jsLoader = new JsLoader();
        // 缓存在哪里？
        jsLoader.setCacheDirRoot(cacheDir);
        // 设置文件是什么？
        jsLoader.setConfig(configFile);
        // 是否显示调试信息
        jsLoader.setIsDebug(true);
        // 除过强制加载文件外还想加载什么
        jsLoader.merge("article.list");
        jsLoader.merge("article.edit");
        jsLoader.setCacheDirRoot(resourceRoot + "/cache");
        //
        StringWriter out = new StringWriter();
        // 显示出来，注意超时时间
        jsLoader.display(out, 3);
        out.flush();
        out.close();
        String str = out.toString();
        int listIdx = str.indexOf("article/list.js");
        int editIdx = str.indexOf("article/edit.js");
        assertTrue(listIdx > -1 //
                && editIdx > -1 //
                && editIdx > listIdx);

    }
}
