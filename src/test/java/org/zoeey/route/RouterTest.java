/*
 * MoXie (SysTem128@GMail.Com) 2010-9-26 17:46:17
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: Apache License  Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.zoeey.route;

import org.zoeey.util.TimeMeasurer;
import java.util.HashMap;
import org.zoeey.route.ParamEntry;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class RouterTest {

    public RouterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class Router.
     */
    @Test
    public void testAdd_RuleItem() {

        System.out.println("add");
        RouterRule ruleItem = new RouterRule("/:action/:id", '/');
        Router router = new Router();
        router.add(ruleItem);



        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> expResult = new HashMap<String, Object>();
        expResult.put("action", "edit");
        expResult.put("id", "123");
        for (ParamEntry entry : router.parse("/edit/123")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        result.clear();
        expResult.clear();
    }

    /**
     * Test of add method, of class Router.
     */
    @Test
    public void testAdd_String() {
        System.out.println("add");
        String pattern = "/:action/:page";
        Router router = new Router();
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> expResult = new HashMap<String, Object>();
        router.add(pattern);
        //
        expResult.put("action", "list");
        expResult.put("page", "321");
        for (ParamEntry entry : router.parse("/list/321")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();
        /**
         * 
         */
        router = new Router();
        router.add("/:action")//
                .addArray("action", new String[]{"view"}, "/:id/:title")//
                .addArray("action", new String[]{"edit"}, "/:id");//
        //
        expResult.put("action", "login");
        for (ParamEntry entry : router.parse("/login")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();
    }

    /**
     * Test of addRegexp method, of class Router.
     */
    @Test
    public void testAddRegexp() {
        System.out.println("addRegexp");
        String varName = "action";
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> expResult = new HashMap<String, Object>();

        Router router = new Router();
        router.add("/:action");
        router.addRegexp(varName, "^list[\\d]+$", "/:page/:search");
        router.addRegexp(varName, "^view[\\d]+$", "/:id/:highlight");
        //
        expResult.put("action", "list123");
        expResult.put("page", "213");
        expResult.put("search", "zoeey");

        for (ParamEntry entry : router.parse("/list123/213/zoeey")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();
        //
        expResult.put("action", "view321");
        expResult.put("id", "312");
        expResult.put("highlight", "moxie");

        for (ParamEntry entry : router.parse("/view321/312/moxie")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();
    }

    /**
     * Test of addArray method, of class Router.
     */
    @Test
    public void testAddArray() {
        System.out.println("addArray");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> expResult = new HashMap<String, Object>();


        Router router = new Router();

        router.add("/:action");
        router.addArray("action", new String[]{"view", "edit"}, "/:id");
        router.addArray("action", new String[]{"list"}, "/:page");
        //
        expResult.put("action", "view");
        expResult.put("id", "567");

        for (ParamEntry entry : router.parse("/view/567")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();
        //
        expResult.put("action", "list");
        expResult.put("page", "765");

        for (ParamEntry entry : router.parse("/list/765")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();
    }

    /**
     * Test of addParamCount method, of class Router.
     */
    @Test
    public void testAddParamCount() {
        System.out.println("addParamCount");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> expResult = new HashMap<String, Object>();

        Router router = new Router();
        router.addParamCount(3, "/:key1/:key2/:key3");
        //
        expResult.put("key1", "val1");
        expResult.put("key2", "val2");
        expResult.put("key3", "val3");

        for (ParamEntry entry : router.parse("/val1/val2/val3")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();
    }

    /**
     * Test of addAllRegexp method, of class Router.
     */
    @Test
    public void testAddAllRegexp() {
        System.out.println("addAllRegexp");
        List<ParamEntry> list;
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> expResult = new HashMap<String, Object>();
        Router router = new Router();
        router.addAllRegexp("track/([a-zA-Z]{1,32})", new String[]{"trackSn"});
        router.addAllRegexp("track/([0-9]{1,32})/([a-zA-Z0-9]{1,32})", new String[]{"id", "trackSn"});
        //
        expResult.put("trackSn", "abcdefg");

        for (ParamEntry entry : router.parse("/track/abcdefg")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();

        expResult.put("id", "321");
        expResult.put("trackSn", "a1b2c3");

        for (ParamEntry entry : router.parse("/track/321/a1b2c3")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();
    }

    /**
     * Test of append method, of class Router.
     */
    @Test
    public void testAppend() {
        System.out.println("append");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> expResult = new HashMap<String, Object>();
        Router router = new Router();
        router.add("/:action");
        router.addArray("action", new String[]{"list", "search"}, "/:page").append("{/}", '-');
        // 值充足
        expResult.put("action", "list");
        expResult.put("page", "1");
        expResult.put("label", "123");
        expResult.put("title", "mytitle");
        expResult.put("highligh", "searchme");

        for (ParamEntry entry : router.parse("/list/1/label/123/title/mytitle/highligh/searchme")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();
        // 值不足
        expResult.put("action", "list");
        expResult.put("page", "1");
        expResult.put("label", "123");
        expResult.put("title", "mytitle");
        expResult.put("highligh", null);

        for (ParamEntry entry : router.parse("/list/1/label/123/title/mytitle/highligh")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();
        // 值为空

        expResult.put("action", "list");
        expResult.put("page", "1");

        for (ParamEntry entry : router.parse("/list/1")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        expResult.clear();
        result.clear();

    }

    /**
     * Test of shiftSep method, of class Router.
     */
    @Test
    public void testShiftSep() {
        System.out.println("shiftSep");
        char sep = '-';
        Router router = new Router();
        router.add("/:action");
        router.addArray("action", new String[]{"list"}, "/:page");
        router.shiftSep(sep);
        router.addArray("action", new String[]{"view"}, ":id-:title-:time");
        router.shiftSep(Router.DEFAULT_SEP);
        router.addArray("action", new String[]{"view"}, "/:sign/:rurl");

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> expResult = new HashMap<String, Object>();
        expResult.put("action", "view");
        expResult.put("id", "123");
        expResult.put("title", "mytitle");
        expResult.put("time", "2010/9/27");
        expResult.put("sign", "sign_123");
        expResult.put("rurl", "zoeey.org");
        for (ParamEntry entry : router.parse("/view/123-mytitle-2010/9/27-sign_123/zoeey.org")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        result.clear();
        expResult.clear();

    }

    /**
     * Test of end method, of class Router.
     */
    @Test
    public void testEnd() {
        System.out.println("end");

        /**
         * 匹配后不中断
         */
        Router router = new Router();
        router.add("/:action");
        router.addArray("action", new String[]{"list"}, "/:page");
        router.addArray("action", new String[]{"list"}, "/:id");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> expResult = new HashMap<String, Object>();
        expResult.put("action", "list");
        expResult.put("page", "123");
        expResult.put("id", "321");
        for (ParamEntry entry : router.parse("/list/123/321")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        result.clear();
        expResult.clear();

        /**
         * 匹配后中断
         */
        router = new Router();
        router.add("/:action");
        router.addArray("action", new String[]{"list"}, "/:page").end();
        router.addArray("action", new String[]{"list"}, "/:id");
        expResult.put("action", "list");
        expResult.put("page", "123");
        for (ParamEntry entry : router.parse("/list/123/3211")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        result.clear();
        expResult.clear();


    }

    /** 
     */
    @Test
    public void testParse() {
        System.out.println("parse ");
        Router router = new Router();
        router.add("/:action") // 正则匹配已出现的变量
                .addRegexp("action", "(list)", "/:page/:label")//
                // 参数个数是某值
                .addParamCount(5, "/:id/:page/:label/:highlight").end()//
                // 已出现变量在某集合内
                .addArray("action", new String[]{"view", "display"}, "/:id/:title")//
                .addArray("action", new String[]{"edit"}, "/:id")//
                .addArray("action", new String[]{"del", "delete"}, "/:id[;]")//
                .addArray("action", new String[]{"testSep"}, "/:id[,]")//
                // 全QueryString正则匹配
                .addAllRegexp("track/(.+?)/?$", new String[]{"trackSn"});
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> expResult = new HashMap<String, Object>();
        /**
         * 正则匹配已出现的变量
         */
        expResult.put("action", "list");
        expResult.put("page", "1");
        for (ParamEntry entry : router.parse("/list/1")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        result.clear();
        expResult.clear();

        /**
         * 已出现变量在某集合内
         */
        expResult.put("action", "view");
        expResult.put("id", "1");
        expResult.put("title", "this_is_your_article_title");
        // --
        for (ParamEntry entry : router.parse("view/1/this_is_your_article_title")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        result.clear();
        expResult.clear();


        /**
         * 参数个数是某值
         */
        expResult.put("action", "view");
        expResult.put("id", "1");
        expResult.put("page", "10");
        expResult.put("label", "3");
        expResult.put("highlight", "highlightWords");
        // --
        for (ParamEntry entry : router.parse("view/1/10/3/highlightWords")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        result.clear();
        expResult.clear();

        /**
         * 全QueryString正则匹配
         */
        expResult.put("action", "track");
        expResult.put("trackSn", "test123");
        // --
        for (ParamEntry entry : router.parse("/track/test123")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        result.clear();
        expResult.clear();

        /**
         *
         */
        expResult.put("action", "display");
        expResult.put("id", "1");
        expResult.put("title", "this_is_your_article_title");
        // --
        for (ParamEntry entry : router.parse("/display/1/this_is_your_article_title")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        result.clear();
        expResult.clear();
        /**
         *
         */
        expResult.put("action", "edit");
        expResult.put("id", "10101011");
        // --
        for (ParamEntry entry : router.parse("/edit/10101011")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult, result);
        result.clear();
        expResult.clear();
        /**
         *
         */
        expResult.put("action", "del");
        expResult.put("id", new String[]{"10", "10", "10", "11"});
        // --
        for (ParamEntry entry : router.parse("/del/10;10;10;11")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult.get("action"), result.get("action"));
        assertArrayEquals((String[]) expResult.get("id"), (String[]) result.get("id"));
        result.clear();
        expResult.clear();
        /**
         *
         */
        expResult.put("action", "testSep");
        expResult.put("id", new String[]{"10", "10", "10", "11"});
        // --
        for (ParamEntry entry : router.parse("/testSep/10,10,10,11")) {
            result.put(entry.getKey(), entry.getValue());
        }
        assertEquals(expResult.get("action"), result.get("action"));
        assertArrayEquals((String[]) expResult.get("id"), (String[]) result.get("id"));
        result.clear();
        expResult.clear();

        if (true) {
            return;
        }

        /**
         *
         */
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        for (int i = 0; i < 10000; i++) {
            router = new Router();
            router.add("/:action") // 正则匹配已出现的变量
                    .addRegexp("action", "(list)", "/:page/:label")//
                    // 参数个数是某值
                    .addParamCount(5, "/:id/:page/:label/:highlight").end()//
                    // 已出现变量在某集合内
                    .addArray("action", new String[]{"view", "display"}, "/:id/:title")//
                    .addArray("action", new String[]{"edit"}, "/:id")//
                    .addArray("action", new String[]{"del", "delete"}, "/:id[;]")//
                    .addArray("action", new String[]{"testSep"}, "/:id[,]")//
                    // 全QueryString正则匹配
                    .addAllRegexp("track/(.+?)/?$", new String[]{"trackSn"});
            router.parse("/testSep/10,10,10,11");
        }
        tm.stop();
        System.out.println(tm.spend());
        /**
         *  before refactoring
         *  10,000
         *  1228
         *  1011
         *  969
         *  after
         *  639
         *  501
         *  553
         */
        expResult.clear();
        expResult = null;
    }
}
