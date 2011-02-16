/*
 * MoXie (SysTem128@GMail.Com) 2009-8-14 15:12:54
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import org.zoeey.util.JsonHelper;
import org.zoeey.util.TextFileHelper;
import org.zoeey.zdo.zdbcontrol.ZDBControlMixed;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class DAOHelperTest {

    /**
     *
     */
    public DAOHelperTest() {
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
    private Connection conn;

    /**
     *
     */
    @Before
    public void setUp() {
        try {
            org.h2.Driver.load();
            conn = DriverManager.getConnection("jdbc:h2:mem:bookStore", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(DAOHelperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    @After
    public void tearDown() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            Logger.getLogger(DAOHelperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of exec method, of class DAOHelper.
     * @throws Exception
     */
    @Test
    public void testExec() throws Exception {
        System.out.println("exec");
        DAOHelper daoHelper = new DAOHelper(conn);
        String expResult = "success";
        String result = daoHelper.query("select 'success' ").exec(String.class);
        assertEquals(expResult, result);
    }

    /**
     * Test of forInt method, of class DAOHelper.
     * @throws Exception
     */
    @Test
    public void testForInt() throws Exception {
        System.out.println("forInt");
        DAOHelper daoHelper = new DAOHelper(conn);
        int expResult = 2;
        int result = daoHelper.query("select  2,3,4,5 ").getInt();
        assertEquals(expResult, result);

    }

    /**
     * Test of forInt method, of class DAOHelper.
     * @throws Exception
     */
    @Test
    public void testForLong() throws Exception {
        System.out.println("forLong");
        DAOHelper daoHelper = new DAOHelper(conn);
        long expResult = 1233213213213L;
        long result = daoHelper.query("select  1233213213213,3,4,5 ").getLong();
        assertEquals(expResult, result);

    }

    /**
     * Test of queryFromText method, of class DAOHelper.
     * @throws Exception
     */
    @Test
    public void testFromText() throws Exception {
        System.out.println("fromText");
        DAOHelper daoHelper = new DAOHelper(conn);
        daoHelper.setAutoClose(false);
        String expResult = "MoXie";
        String daoTestKV = "/**" //
                + "* 这个文件是为了测试 DAOHelper.queryFromText 的。" //
                + "*/" //
                + "findName = \"select 'MoXie' \";" //
                + "findEMail = \"select 'SysTem128@GMail.Com'\" " // 
                + "findInfo = \" select 'MoXie' " //
                + "    ,'SysTem128@GMail.Com' " //
                + "    ,true " //
                + "\";";
        File file = new File(TestUtil.getResource("daoHelperTest.kv"));
        TextFileHelper.write(file, daoTestKV);
        /**
         * findName
         */
        String result = daoHelper.queryFromText(file, "findName").getString();
        assertEquals(expResult, result);
        /**
         * findEMail
         */
        result = daoHelper.queryFromText(file, "findEMail").getString();
        assertEquals("SysTem128@GMail.Com", result);
        /**
         * findInfo
         */
        daoHelper.queryFromText(file, "findInfo").exec(new DAOHelper.ObjectHandler<Object>() {

            public Object handle(ResultSet rs) throws SQLException {
                assertEquals("MoXie", rs.getString(1));
                assertEquals("SysTem128@GMail.Com", rs.getString(2));
                assertEquals(true, rs.getBoolean(3));
                return null;
            }
        });
        daoHelper.close();
    }

    /**
     * Test of forInt method, of class DAOHelper.
     * @throws Exception
     */
    @Test
    public void testForQueryHandler() throws Exception {
        System.out.println("queryHandler");
        DemoData demoData = new DemoData("DAOHelper");
        DAOHelper daoHelper = new DAOHelper(demoData.getConn());
        daoHelper.setAutoClose(false);
        PageSet pageSet = new PageSet(100, 20, 5);
        assertEquals(daoHelper.query("select count(id) from T_ARTICLE ").getInt(), 499);
//        TimeMeasurer tm = new TimeMeasurer();
//        MemoryMeasurer mm = new MemoryMeasurer();
//        tm.start();
//        mm.start();
        Map<String, Object> pageParam = new HashMap<String, Object>();
        pageParam.put("limit", pageSet.getPageSize());
        pageParam.put("offset", pageSet.getOffset());
//        for (int i = 0; i < 10000; i++) {
        List<ArticleBean> result = daoHelper.query("select * from "
                + "T_Article where title LIKE CONCAT(CONCAT('%',?),'%') limit :limit offset :offset") //
                .bind(new Object[]{"0"})//
                .bindMap(pageParam)//
                .exec(new DAOHelper.ListHandler<ArticleBean>() {

            public ArticleBean handle(ResultSet rs, int index) throws SQLException {
                ArticleBean article = new ArticleBean();
                article.setId(rs.getInt("id"));
                article.setTitle(rs.getString("title"));
                return article;
            }
        });
//        }
//        System.out.println(tm.spend());
//        System.out.println(mm.spend());
        assertEquals(5, result.size());
        assertEquals(450, result.get(0).getId());
        List<Long> idList = new ArrayList<Long>();
        for (ArticleBean article : result) {
            idList.add(article.getId());
        }
        assertEquals("[450,460,470,480,490]", JsonHelper.encode(idList));
        demoData.destory();
        daoHelper.close();
        /**
         * time useage
         * 10,000
         * 1402
         * 1492
         * 1322
         * 10,00
         * 1382
         * 1489
         * 1506
         * memory useage
         * 10,000
         * 257872
         * 200080
         * 257736
         * 10,000
         * 181480
         * 181480
         * 181880
         */
    }
    /**
     * Test of forInt method, of class DAOHelper.
     * @throws Exception
     */
    private static Map<String, List<ArticleBean>> cacheArticleListMap = new HashMap<String, List<ArticleBean>>();
    private static Map<String, ArticleBean> cacheArticleMap = new HashMap<String, ArticleBean>();
    private static Map<String, List<UserBean>> cacheUserListMap = new HashMap<String, List<UserBean>>();
    private static Map<String, UserBean> cacheUserMap = new HashMap<String, UserBean>();

    /**
     *
     * @throws Exception
     */
    @Test
    public void testForCache() throws Exception {
        System.out.println("cache");
        DemoData demoData = new DemoData("DAOHelper");
        DAOHelper daoHelper = new DAOHelper(new ZDBControlMixed(demoData.getConn(), "cacahe_test"));
        daoHelper.setAutoClose(false);
        PageSet pageSet = new PageSet(100, 20, 5);
        Map<String, Object> pageParam = new HashMap<String, Object>();
        pageParam.put("limit", pageSet.getPageSize());
        pageParam.put("offset", pageSet.getOffset());
        final List<String> cacheResult = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            List<ArticleBean> result = daoHelper.query("select * "
                    + "from T_Article "
                    + "WHERE 1=1 "
                    + "and title LIKE CONCAT(CONCAT('%',?),'%') limit :limit offset :offset") //
                    .bind(new Object[]{"0"})//
                    .bindMap(pageParam)//
                    .cache(new DAOHelper.CacheHandler<List<ArticleBean>>() {

                public boolean isCached(String uniqueKey) throws SQLException {
                    return cacheArticleListMap.containsKey(uniqueKey);
                }

                public List<ArticleBean> get(String uniqueKey) throws SQLException {
                    cacheResult.add("cached");
                    return cacheArticleListMap.get(uniqueKey);
                }

                public void set(String uniqueKey, List<ArticleBean> data) throws SQLException {
                    cacheResult.add("uncached");
                    cacheArticleListMap.put(uniqueKey, data);
                }
            }, new DAOHelper.ListHandler<ArticleBean>() {

                public ArticleBean handle(ResultSet rs, int index) throws SQLException {
                    ArticleBean article = new ArticleBean();
                    article.setId(rs.getInt("id"));
                    article.setTitle(rs.getString("title"));
                    return article;
                }
            });

            assertEquals(5, result.size());
            assertEquals(450, result.get(0).getId());
            List<Long> idList = new ArrayList<Long>();
            for (ArticleBean article : result) {
                idList.add(article.getId());
            }
            assertEquals("[450,460,470,480,490]", JsonHelper.encode(idList));
        }
        assertEquals("[\"uncached\",\"cached\",\"cached\"]", JsonHelper.encode(cacheResult));
        demoData.destory();
    }

    private static class UserBean {

        private String name = null;
        private boolean isActive = false;

        public UserBean() {
        }

        public boolean isIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testBindBean() throws Exception {
        System.out.println("bindBean");
        DAOHelper daoHelper = new DAOHelper(conn);
        daoHelper.setAutoClose(false);
        String expResult = "name: MoXie isActive: true";
        UserBean user = new UserBean();
        user.setIsActive(true);
        user.setName("MoXie");
        String result = daoHelper.query("select :name as name,:isActive").bindBean(user) //
                .exec(new DAOHelper.ObjectHandler<String>() {

            public String handle(ResultSet rs) throws SQLException {
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append("name: ");
                strBuilder.append(rs.getString("name"));
                strBuilder.append(" isActive: ");
                strBuilder.append(rs.getBoolean(2));
                return strBuilder.toString();
            }
        });
        assertEquals(expResult, result);
        result = daoHelper.query("select :user.name as name,:user.isActive").prefix("user.").bindBean(user) //
                .exec(new DAOHelper.ObjectHandler<String>() {

            public String handle(ResultSet rs) throws SQLException {
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append("name: ");
                strBuilder.append(rs.getString("name"));
                strBuilder.append(" isActive: ");
                strBuilder.append(rs.getBoolean(2));
                return strBuilder.toString();
            }
        });
        assertEquals(expResult, result);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testForBean() throws Exception {
        /**
         * forBean
         */
        System.out.println("forBean");
        DAOHelper daoHelper = new DAOHelper(conn);
        daoHelper.setAutoClose(false);
        UserBean userResult =
                daoHelper.query("select ? as name,? as isActive")//
                .bind("MoXie", true) //
                .getBean(UserBean.class);
        assertEquals("MoXie", userResult.getName());
        assertEquals(true, userResult.isIsActive());
        /**
         *
         */
        System.out.println("forBeanList");
        List<UserBean> userList =
                daoHelper.query("select ? as name,? as isActive")//
                .bind("SysTem128", true) //
                .getBeanList(UserBean.class);
        for (UserBean _user : userList) {
            assertEquals("SysTem128", _user.getName());
            assertEquals(true, _user.isIsActive());
        }
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testCacheBean() throws Exception {
        /**
         * cacheBean
         */
        System.out.println("cacheBean");
        DAOHelper daoHelper = new DAOHelper(conn);
        daoHelper.setAutoClose(false);
        final List<String> cacheResult = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            UserBean userCache =
                    daoHelper.query("select ? as name,? as isActive")//
                    .bind("SysTem128", true) //
                    .cacheBean(new DAOHelper.CacheHandler<UserBean>() {

                public boolean isCached(String uniqueKey) throws SQLException {
                    return cacheUserMap.containsKey(uniqueKey);
                }

                public UserBean get(String uniqueKey) throws SQLException {
                    cacheResult.add("cached");
                    return cacheUserMap.get(uniqueKey);
                }

                public void set(String uniqueKey, UserBean data) throws SQLException {
                    cacheResult.add("uncached");
                    cacheUserMap.put(uniqueKey, data);
                }
            }, UserBean.class);
            assertEquals("SysTem128", userCache.getName());
            assertEquals(true, userCache.isIsActive());
        }
        assertEquals("[\"uncached\",\"cached\",\"cached\"]", JsonHelper.encode(cacheResult));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testCacheBeanList() throws Exception {
        /**
         * cacheBeanList
         */
        System.out.println("cacheBeanList");
        DAOHelper daoHelper = new DAOHelper(conn);
        daoHelper.setAutoClose(false);
        final List<String> cacheResult_list = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            List<UserBean> userListCache =
                    daoHelper.query("select ? as name,? as isActive")//
                    .bind("SysTem128", true) //
                    .cacheBeanList(new DAOHelper.CacheHandler<List<UserBean>>() {

                public boolean isCached(String uniqueKey) throws SQLException {
                    return cacheUserListMap.containsKey(uniqueKey);
                }

                public List<UserBean> get(String uniqueKey) throws SQLException {
                    cacheResult_list.add("cached");
                    return cacheUserListMap.get(uniqueKey);
                }

                public void set(String uniqueKey, List<UserBean> data) throws SQLException {
                    cacheResult_list.add("uncached");
                    cacheUserListMap.put(uniqueKey, data);
                }
            }, UserBean.class);
            for (UserBean _user : userListCache) {
                assertEquals("SysTem128", _user.getName());
                assertEquals(true, _user.isIsActive());
            }
        }
        assertEquals("[\"uncached\",\"cached\",\"cached\"]", JsonHelper.encode(cacheResult_list));
        // 
        assertEquals("[{\"NAME\":\"MoXie\",\"ISACTIVE\":\"true\"}]", JsonHelper.encode(daoHelper//
                .query("select ? as isActive,? as name")//
                .bind("true", "MoXie").getMapList()));
        //
        assertEquals("[{\"NAME\":\"MoXie\",\"ISACTIVE\":true}]", JsonHelper.encode(daoHelper//
                .query("select ? as isActive,? as name")//
                .bind(true, "MoXie").getMapList()));

        daoHelper.close();
    }
}
