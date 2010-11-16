/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.validator;

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
public class WebValidatorTest {

    /**
     *
     */
    public WebValidatorTest() {
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
     * Test of isTitle method, of class WebValidator.
     */
    @Test
    public void testIsTitle_String() {
        System.out.println("isTitle");
        String title = "";
        boolean expResult = false;
        boolean result = WebValidator.isTitle(title);
        assertEquals(expResult, result);


    }

    /**
     * Test of isTitle_NO method, of class WebValidator.
     */
    @Test
    public void testIsTitle_NO() {
        System.out.println("isTitle_NO");
        String title = "";
        boolean expResult = true;
        boolean result = WebValidator.isTitle(title, true);
        assertEquals(expResult, result);


    }

    /**
     * Test of isTitle method, of class WebValidator.
     */
    @Test
    public void testIsTitle_StringArr() {
        System.out.println("isTitle");
        String[] titleList = null;
        boolean expResult = false;
        boolean result = WebValidator.isTitle(titleList, false);
        assertEquals(expResult, result);


    }

    /**
     * Test of isShortCode method, of class WebValidator.
     */
    @Test
    public void testIsShortCode() {
        System.out.println("isShortCode");
        String code = "";
        boolean expResult = false;
        boolean result = WebValidator.isShortCode(code, false);
        assertEquals(expResult, result);


    }

    /**
     * Test of isShortCode_NO method, of class WebValidator.
     */
    @Test
    public void testIsShortCode_NO() {
        System.out.println("isShortCode_NO");
        String code = "";
        boolean expResult = true;
        boolean result = WebValidator.isShortCode(code, true);
        assertEquals(expResult, result);


    }

    /**
     * Test of isContent method, of class WebValidator.
     */
    @Test
    public void testIsContent_String() {
        System.out.println("isContent");
        String content = "";
        boolean expResult = false;
        boolean result = WebValidator.isContent(content);
        assertEquals(expResult, result);


    }

    /**
     * Test of isContent_NO method, of class WebValidator.
     */
    @Test
    public void testIsContent_NO() {
        System.out.println("isContent_NO");
        String content = "";
        boolean expResult = true;
        boolean result = WebValidator.isContent(content, true);
        assertEquals(expResult, result);


    }

    /**
     * Test of isContent method, of class WebValidator.
     */
    @Test
    public void testIsContent_StringArr() {
        System.out.println("isContent");
        String[] contentList = null;
        boolean expResult = false;
        boolean result = WebValidator.isContent(contentList, false);
        assertEquals(expResult, result);


    }

    /**
     * Test of isMessage method, of class WebValidator.
     */
    @Test
    public void testIsMessage() {
        System.out.println("isMessage");
        String message = "";
        boolean expResult = false;
        boolean result = WebValidator.isMessage(message);
        assertEquals(expResult, result);


    }

    /**
     * Test of isSn method, of class WebValidator.
     */
    @Test
    public void testIsSn() {
        System.out.println("isSn");
        String sn = "";
        boolean expResult = false;
        boolean result = WebValidator.isSn(sn);
        assertEquals(expResult, result);


    }

    /**
     * Test of isFormatTime method, of class WebValidator.
     */
    @Test
    public void testIsFormatTime() {
        System.out.println("isFormatTime");
        String time = "";
        boolean expResult = false;
        boolean result = WebValidator.isFormatTime(time);
        assertEquals(expResult, result);


    }

    /**
     * Test of isFormatTime_NO method, of class WebValidator.
     */
    @Test
    public void testIsFormatTime_NO() {
        System.out.println("isFormatTime_NO");
        String time = "";
        boolean expResult = true;
        boolean result = WebValidator.isFormatTime(time, true);
        assertEquals(expResult, result);


    }

    /**
     * Test of isFormatDate method, of class WebValidator.
     */
    @Test
    public void testIsFormatDate() {
        System.out.println("isFormatDate");
        String time = "";
        boolean expResult = false;
        boolean result = WebValidator.isFormatDate(time);
        assertEquals(expResult, result);


    }

    /**
     * Test of isFormatDate_NO method, of class WebValidator.
     */
    @Test
    public void testIsFormatDate_NO() {
        System.out.println("isFormatDate_NO");
        String time = "";
        boolean expResult = true;
        boolean result = WebValidator.isFormatDate(time, true);
        assertEquals(expResult, result);


    }

    /**
     * Test of isModuleSn method, of class WebValidator.
     */
    @Test
    public void testIsModuleSn() {
        System.out.println("isModuleSn");
        String modSn = "";
        boolean expResult = false;
        boolean result = WebValidator.isModuleSn(modSn);
        assertEquals(expResult, result);


    }

    /**
     * Test of isAccount method, of class WebValidator.
     */
    @Test
    public void testIsAccount() {
        System.out.println("isAccount");
        String account = "";
        boolean expResult = false;
        boolean result = WebValidator.isAccount(account);
        assertEquals(expResult, result);


    }

    /**
     * Test of isAction method, of class WebValidator.
     */
    @Test
    public void testIsAction_String() {
        System.out.println("isAction");
        String action = "";
        boolean expResult = false;
        boolean result = WebValidator.isAction(action);
        assertEquals(expResult, result);


    }

    /**
     * Test of isAction method, of class WebValidator.
     */
    @Test
    public void testIsAction_String_StringArr() {
        System.out.println("isAction");
        String action = "";
        String[] actionList = null;
        boolean expResult = false;
        boolean result = WebValidator.isAction(action, actionList);
        assertEquals(expResult, result);


    }

    /**
     * Test of isColor method, of class WebValidator.
     */
    @Test
    public void testIsColor() {
        System.out.println("isColor");
        String color = "";
        boolean expResult = false;
        boolean result = WebValidator.isColor(color);
        assertEquals(expResult, result);


    }

    /**
     * Test of isRgbColor method, of class WebValidator.
     */
    @Test
    public void testIsRgbColor() {
        System.out.println("isRgbColor");
        String color = "";
        boolean expResult = false;
        boolean result = WebValidator.isRgbColor(color);
        assertEquals(expResult, result);


    }

    /**
     * Test of isRefer method, of class WebValidator.
     */
    @Test
    public void testIsRefer() {
        System.out.println("isRefer");
        Object obj = null;
        Object[] objList = null;
        boolean expResult = false;
        boolean result = WebValidator.isRefer(obj, objList);
        assertEquals(expResult, result);


    }

    /**
     * Test of isEmail method, of class WebValidator.
     */
    @Test
    public void testIsEmail() {
        System.out.println("isEmail");
        String email = "";
        boolean expResult = false;
        boolean result = WebValidator.isEmail(email);
        assertEquals(expResult, result);


    }

    /**
     * Test of isIntegerString method, of class WebValidator.
     */
    @Test
    public void testIsIntegerString_3args() {
        System.out.println("isIntegerString");
        String numStr = "";
        boolean allowZero = false;
        boolean allowNegative = false;
        boolean expResult = false;
        boolean result = WebValidator.isIntegerString(numStr, allowZero, allowNegative);
        assertEquals(expResult, result);


    }

    /**
     * Test of isIntegerString method, of class WebValidator.
     */
    @Test
    public void testIsIntegerString_String() {
        System.out.println("isIntegerString");
        String numStr = "";
        boolean expResult = false;
        boolean result = WebValidator.isIntegerString(numStr);
        assertEquals(expResult, result);


    }

    /**
     * Test of isIntegerString_Pos method, of class WebValidator.
     */
    @Test
    public void testIsIntegerString_Pos() {
        System.out.println("isIntegerString_Pos");
        String numStr = "";
        boolean expResult = false;
        boolean result = WebValidator.isIntegerString_Pos(numStr);
        assertEquals(expResult, result);


    }

    /**
     * Test of isIntegerString_Neg method, of class WebValidator.
     */
    @Test
    public void testIsIntegerString_Neg() {
        System.out.println("isIntegerString_Neg");
        String numStr = "0";
        boolean expResult = true;
        boolean result = WebValidator.isIntegerString_Neg(numStr);
        assertEquals(expResult, result);
        numStr = "1";
        expResult = true;
        result = WebValidator.isIntegerString_Neg(numStr);
        assertEquals(expResult, result);
        numStr = "-1";
        expResult = true;
        result = WebValidator.isIntegerString_Neg(numStr);
        assertEquals(expResult, result);


    }

    /**
     * Test of isPassword method, of class WebValidator.
     */
    @Test
    public void testIsPassword() {
        System.out.println("isPassword");
        String psw = "";
        boolean expResult = false;
        boolean result = WebValidator.isPassword(psw);
        assertEquals(expResult, result);


    }

    /**
     * Test of isPassword_NO method, of class WebValidator.
     */
    @Test
    public void testIsPassword_NO() {
        System.out.println("isPassword_NO");
        String psw = "";
        boolean expResult = true;
        boolean result = WebValidator.isPassword(psw, true);
        assertEquals(expResult, result);


    }

    /**
     * Test of isShortBrief method, of class WebValidator.
     */
    @Test
    public void testIsShortBrief() {
        System.out.println("isShortBrief");
        String shortBrief = "";
        boolean expResult = true;
        boolean result = WebValidator.isShortBrief(shortBrief);
        assertEquals(expResult, result);


    }

    /**
     * Test of isState method, of class WebValidator.
     */
    @Test
    public void testIsState() {
        System.out.println("isState");
        String state = "";
        int[] states = new int[]{1, 2, 3};
        boolean expResult = false;
        boolean result = WebValidator.isState(state, states);
        assertEquals(expResult, result);


    }

    /**
     * Test of isTagSn method, of class WebValidator.
     */
    @Test
    public void testIsTagSn() {
        System.out.println("isTagSn");
        String tagSn = "";
        boolean expResult = false;
        boolean result = WebValidator.isTagSn(tagSn);
        assertEquals(expResult, result);


    }

    /**
     * Test of isInArray method, of class WebValidator.
     */
    @Test
    public void testIsInArray() {
        System.out.println("isInArray");
        Object obj = null;
        Object[] objList = null;
        boolean expResult = false;
        boolean result = WebValidator.isInArray(obj, objList);
        assertEquals(expResult, result);


    }

    /**
     * Test of isBoolean method, of class WebValidator.
     */
    @Test
    public void testIsBoolean() {
        System.out.println("isBoolean");
        String boolStr = "";
        boolean expResult = false;
        boolean result = WebValidator.isBoolean(boolStr);
        assertEquals(expResult, result);


    }
}
