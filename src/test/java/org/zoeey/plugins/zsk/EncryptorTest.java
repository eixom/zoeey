/*
 * MoXie (SysTem128@GMail.Com) 2009-1-18 16:42:01
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins.zsk;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.Base64Helper;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class EncryptorTest {

    /**
     *
     */
    public EncryptorTest() {
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
     * Test of encrypt method, of class Encryptor.
     * @throws IOException
     */
    @Test
    public void testEncrypt()
            throws IOException {
        System.out.println("encrypt");
        String content = "需要加密的内容";
        //
        Encryptor encryptor = new Encryptor("psw !");
        String result = encryptor.encrypt(content);

        //
        encryptor = new Encryptor("psw !");
        assertEquals(encryptor.decrypt(result), "需要加密的内容");
        content = "需要加密的内容";
        //
        encryptor = new Encryptor("一个中文的超长密匙！");
        result = encryptor.encrypt(content);
        assertEquals(encryptor.decrypt(result), "需要加密的内容");
    }
}
