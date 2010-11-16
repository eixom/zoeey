/*
 * MoXie (SysTem128@GMail.Com) 2009-8-6 16:40:22
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
import org.zoeey.util.JsonHelper;

/**
 *
 * @author MoXie
 */
public class DispatchConfigLoaderTest extends TestCase {

    /**
     *
     */
    public DispatchConfigLoaderTest() {
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
     * Test of load method, of class RouterConfigLoader.
     * @throws Exception
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("load");
        File configFile = new File(TestUtil.getResource("DispatchConfig.xml"));
        DispatchConfigLoader.load(configFile);
        List<String> list = new ArrayList<String>();
        for (PublishEntry entry : Dispatcher.getPublisherList()) {
            list.add("Pattern:" + entry.getPattern() + ", Class:" + entry.getPublish().getClass().getName());
        }
        
        if (JsonHelper.encode(list).indexOf("ArticlePublish") == -1) {
            fail("Class no found");
        }

        list.clear();
        for (PublishEntry entry : Dispatcher.getInitorList()) {
            list.add("Pattern:" + entry.getPattern() + ", Class:" + entry.getPublish().getClass().getName());
        }

        if (JsonHelper.encode(list).indexOf("TimePublish") == -1) {
            fail("Class no found");
        }
    }
}
