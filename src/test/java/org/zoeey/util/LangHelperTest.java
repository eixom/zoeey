/*
 * MoXie (SysTem128@GMail.Com) 2009-8-10 16:20:58
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class LangHelperTest {

    /**
     *
     */
    public LangHelperTest() {
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
     * Test of setLangRootDir method, of class LangHelper.
     */
    @Test
    public void testSetLangRootDir() {
        System.out.println("setLangRootDir");
        String langRootDir = TestUtil.getResource("");
        LangHelper.setRootDir(langRootDir);
        assertTrue(new File(LangHelper.getRootDir().concat("/langPack.kv")).exists());
    }

    /**
     * Test of say method, of class LangHelper.
     */
    @Test
    public void testSay_String_String() {
        System.out.println("say");
        String relativePath = "/langPack.kv";
        String key = "user.account";
        String expResult = "姓名";
        String result = LangHelper.say(relativePath, key);
        assertEquals(expResult, result);
        key = "user.password";
        expResult = "密码";
        result = LangHelper.say(relativePath, key);
        assertEquals(expResult, result);
    }

    /**
     * Test of say method, of class LangHelper.
     */
    @Test
    public void testSay_3args_1() {
        System.out.println("say");
        String relativePath = "/langPack.kv";
        String key = "user.counter";
        Object[] objs = new Object[]{10, 20, "MoXie"};
        String expResult = "共有 10 人注册，当前在线 20 人，最后注册会员 MoXie 。";
        String result = LangHelper.say(relativePath, key, objs);
        assertEquals(expResult, result);
    }

    /**
     * Test of say method, of class LangHelper.
     */
    @Test
    public void testSay_3args_2() {
        System.out.println("say");
        String relativePath = "/langPack.kv";
        String key = "user.counterKey";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userCount", 20);
        map.put("onlineCount", 30);
        map.put("lastRegister", "SysTem128");
        String expResult = "共有 20 人注册，当前在线 30 人，最后注册会员 SysTem128 。";
        String result = LangHelper.say(relativePath, key, map);
        assertEquals(expResult, result);
    }
}
