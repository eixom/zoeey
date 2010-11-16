/*
 * MoXie (SysTem128@GMail.Com) 2009-8-6 15:07:52
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.dispatch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import org.zoeey.dispatch.DispatchConfig.Entry;
import org.zoeey.util.JsonHelper;

/**
 *
 * @author MoXie
 */
public class DispatchConfigParserTest extends TestCase {

    /**
     *
     */
    public DispatchConfigParserTest() {
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
     * Test of parse method, of class RouterConfigParser.
     * @throws Exception
     */
    @Test
    public void testParse()
            throws Exception {

        System.out.println("parse");
        File configFile = new File(TestUtil.getResource("DispatchConfig.xml"));
        DispatchConfigParser parser = new DispatchConfigParser();
        parser.parse(configFile);
        DispatchConfig context = parser.getContext();
        /**
         * isIgnoreCase
         */
        assertTrue(context.isIgnoreCase());
        /**
         * init list
         */
        assertEquals("[\"org.zoeey.dispatch.TimePublish\"]",
                JsonHelper.encode(context.getInitList()));
        /**
         * publish list
         */
        List<String> list = new ArrayList<String>();
        for (Entry item : context.getPublishList()) {
            list.add("Pattern:" + item.getPattern() + ",Publish:" + item.getPublish());
        }
        assertTrue(JsonHelper.encode(list).indexOf("/loaderExample") > -1);
        assertTrue(JsonHelper.encode(list).indexOf("/loader") > -1);
        assertTrue(JsonHelper.encode(list).indexOf("/validator") > -1);
        assertTrue(JsonHelper.encode(list).indexOf("/fireJava") > -1);
        assertTrue(JsonHelper.encode(list).indexOf("org.zoeey.dispatch.ArticlePublish") > -1);
        /**
         * annot list
         */
        assertEquals("[\"org.zoeey.dispatch.ArticlePublish\"]",
                JsonHelper.encode(context.getAnnotList()));

    }
}
