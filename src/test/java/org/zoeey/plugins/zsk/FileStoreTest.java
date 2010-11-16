/*
 * MoXie (SysTem128@GMail.Com) 2009-1-18 15:52:00
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins.zsk;

import java.io.IOException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.constant.EnvConstants;
import org.zoeey.util.EnvInfo;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class FileStoreTest extends TestCase {

    /**
     *
     */
    public FileStoreTest() {
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
     * Test of setCacheDir method, of class FileStore.
     * @throws IOException 
     */
    @Test
    public void testSetCacheDir() throws IOException {
        System.out.println("cacheDir");
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount("MoXie");
        userInfo.setAge(100);
        FileStore<UserInfo> fileStore = new FileStore();
        fileStore.setCacheDir(EnvInfo.getJavaIoTmpdir());
        fileStore.put("userInfo", userInfo);
        UserInfo userInfoFromFile = fileStore.get("userInfo");
        if (userInfoFromFile == null) {
            fail("对象读取失败");
        }
        assertEquals(userInfoFromFile.getAccount(), "MoXie");
    }
}
