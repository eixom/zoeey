/*
 * MoXie (SysTem128@GMail.Com) 2010-9-4 15:07:40
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: Apache License  Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.common.TestUtil;
import org.zoeey.util.BinaryFileHelper;
import org.zoeey.util.JsonHelper;
import org.zoeey.ztpl.ParamsMap;
import org.zoeey.ztpl.TemplateAble;
import org.zoeey.ztpl.Ztpl;
import org.zoeey.ztpl.compiler.Token;

/**
 *
 * @author MoXie
 */
public class ParserTest {

    public ParserTest() {
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
     * Test of main method, of class Parser.
     */
    @Test
    public void testMain() {
        /**
         * writeStr
         */
        System.out.println("writeStr");
        String className = "Test_writeStr";
//            ByteCodeHelper helper = new ByteCodeHelper();
//            helper.newClass(className);
        Parser parser = new Parser();
        Stack<Token> tokenStack = parser.getStack();
        List<Token> stack = new Stack<Token>();
        Token token;
        TokenType type;
        int opcount = 0;
        while (!tokenStack.isEmpty()) {
            token = tokenStack.pop();
            type = token.getType();
            opcount = token.getOpcont();
            switch (type) {
                case VAR:
                case NUMBER:
                    stack.add(token);
                    break;
                case PLUS:
                case MINUS:
                case MULTIPLY:
                case DIV:
                case MOD:
                case ASSIGN:
                case NOT:
                case FUNCTION:
                    stack.add(token);
                    if (opcount > 0) {
                        for (int i = 0; i < opcount && !tokenStack.isEmpty(); i++) {
                            stack.add(tokenStack.pop());
                        }
                    } else {
                        while (!tokenStack.isEmpty()) {
                            token = tokenStack.lastElement();
                            if (token.getType() != TokenType.RPAREN) {
                                stack.add(tokenStack.pop());
                            } else {
                                break;
                            }
                        }
                    }
            }
        }

        System.out.println(JsonHelper.encode(stack));

        if (true) {
            return;
        }
//            helper.endClass();
//            byte[] codes = helper.getCode();
//            BinaryFileHelper.write(new File(TestUtil.getResource("ztpl")//
//                    + "/byteCodeHelper/WriteStrTest.class"), codes);
//            ZtplClassLoader loader = new ZtplClassLoader();
//            TemplateAble template = loader.defineClass(className, codes).newInstance();
//            StringWriter writer = new StringWriter();
//            Map<String, Object> context = new ParamsMap();
//            template.publish(writer, context, new Ztpl());
//            System.out.println(writer.toString());
//            writer.close();


    }
}
