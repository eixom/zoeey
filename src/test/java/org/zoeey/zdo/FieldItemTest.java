/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

import org.zoeey.zdo.FieldItem;
import java.sql.Types;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class FieldItemTest {

    /**
     *
     */
    public FieldItemTest() {
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
     * Test of getName method, of class FieldItem.
     */
    private FieldItem fieldItem = new FieldItem("title", Types.VARCHAR, "This is title.");

    /**
     *
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "title";
        String result = fieldItem.getName();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class FieldItem.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "title2";
        fieldItem.setName(name);
        assertEquals("title2", fieldItem.getName());
    }

    /**
     * Test of getArgName method, of class FieldItem.
     */
    @Test
    public void testGetArgName() {
        System.out.println("getArgName");
        String expResult = "title";
        String result = fieldItem.getArgName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setArgName method, of class FieldItem.
     */
    @Test
    public void testSetArgName() {
        System.out.println("setArgName");
        String argName = "title3";
        fieldItem.setArgName(argName);
        String expResult = "title3";
        String result = fieldItem.getArgName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class FieldItem.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        int expResult = Types.VARCHAR;
        int result = fieldItem.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of setType method, of class FieldItem.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        int type = Types.VARCHAR;
        fieldItem.setType(type);
        int expResult = Types.VARCHAR;
        int result = fieldItem.getType();
        assertEquals(expResult, result);
    }
}
