/*
 * MoXie (SysTem128@GMail.Com) 2010-1-22 17:01:07
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author MoXie
 */
public class LexerTest extends TestCase {

    public LexerTest() {
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
     * Test of analysis method, of class Analyzer.
     */
    @Test
    public void testAnalysis() {
        System.out.println("analysis");
        Overlooker overlooker = null;
        Lexer analyzer = null;
        List<Object> result = new ArrayList<Object>();
        if (true) {
            /**
             * String d quote
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("\"value1\"", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[DQUOTE, \", STRING, value1, DQUOTE, \"]", result.toString());
            result.clear();
            /**
             * String s quote
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("\'value1\'", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[SQUOTE, ', STRING, value1, SQUOTE, ']", result.toString());
            result.clear();
            /**
             * list
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("[\"value5\",\"value6\"]", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[LBRACKET, [, DQUOTE, \", STRING, value5, DQUOTE, \", COMMA"
                    + ", ,, DQUOTE, \", STRING, value6, DQUOTE, \", RBRACKET, ]]"//
                    , result.toString());
            result.clear();

            /**
             * Map
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("{key1:\"value1\",key2:\"value2\""
                    + ",map1:{key3:\"value3\",key5:\"value5\"}"
                    + ",list1:[\"value5\",\"value6\"]"
                    + "}", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[OBRACE, {, MAP_KEY, key1, DQUOTE, \", STRING, value1"
                    + ", DQUOTE, \", MAP_KEY, key2, DQUOTE, \", STRING, value2"
                    + ", DQUOTE, \", MAP_KEY, map1, OBRACE, {, MAP_KEY, key3"
                    + ", DQUOTE, \", STRING, value3, DQUOTE, \", MAP_KEY, key5"
                    + ", DQUOTE, \", STRING, value5, DQUOTE, \", CBRACE, }"
                    + ", MAP_KEY, list1, LBRACKET, [, DQUOTE, \", STRING, value5"
                    + ", DQUOTE, \", COMMA, ,, DQUOTE, \", STRING, value6"
                    + ", DQUOTE, \", RBRACKET, ], CBRACE, }]"//
                    , result.toString());
            result.clear();
            /**
             * var
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("$var", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }

            assertEquals("[VAR, var]"//
                    , result.toString());
            result.clear();
            /**
             * silence
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("@func", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }

            assertEquals("[SILENCE, @, FUNCTION, func]"//
                    , result.toString());
            result.clear();
            /**
             * constant
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("true", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }

            assertEquals("[TRUE, true]", result.toString());
            result.clear();
            /**
             * plus
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("1+1", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[NUMBER, 1, PLUS, +, NUMBER, 1]", result.toString());
            result.clear();
            /**
             * INCSELF
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("$i+++1", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[VAR, i, INCSELF, ++, PLUS, +, NUMBER, 1]" //
                    , result.toString());
            result.clear();
            /**
             * INCSELF
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("$i+++++$i", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[VAR, i, INCSELF, ++, PLUS, +, INCSELF, ++, VAR, i]" //
                    , result.toString());
            result.clear();
            /**
             * INCSELF
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("++$i+1", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[INCSELF, ++, VAR, i, PLUS, +, NUMBER, 1]" //
                    , result.toString());
            result.clear();
            /**
             * INCSELF
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("++$i+++$i", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[INCSELF, ++, VAR, i, PLUS, +, INCSELF, ++, VAR, i]" //
                    , result.toString());
            result.clear();
            //////////////////////////////////////////
            /**
             * DECSELF
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("$i-----$i", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[VAR, i, DECSELF, --, MINUS, -, DECSELF, --, VAR, i]" //
                    , result.toString());
            result.clear();
            /**
             * DECSELF
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("--$i-1", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[DECSELF, --, VAR, i, MINUS, -, NUMBER, 1]" //
                    , result.toString());
            result.clear();
            /**
             * DECSELF
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("--$i---$i", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[DECSELF, --, VAR, i, MINUS, -, DECSELF, --, VAR, i]" //
                    , result.toString());
            result.clear();
            /**
             * INCSELF，DECSELF
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("++$i---$i", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[INCSELF, ++, VAR, i, MINUS, -, DECSELF, --, VAR, i]" //
                    , result.toString());
            result.clear();
            /**
             * BACKSLASH,RPAREN
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("(++$i)+(++$i)", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[LPAREN, (, INCSELF, ++, VAR, i, RPAREN, ), PLUS, +, LPAREN, (, INCSELF, ++, VAR, i, RPAREN, )]" //
                    , result.toString());
            result.clear();
            /**
             * COMMA
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("func($x,$y)", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[FUNCTION, func, LPAREN, (, VAR, x, COMMA, ,, VAR, y, RPAREN, )]" //
                    , result.toString());
            result.clear();
            /**
             * SEMICOLON
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("$name|trim", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[VAR, name, VBAR, |, FUNCTION, trim]" //
                    , result.toString());
            result.clear();
            /**
             * QMARK,COLON
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("$isDebug?100:200", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[VAR, isDebug, QMARK, ?, NUMBER, 100, COLON, :, NUMBER, 200]" //
                    , result.toString());
            result.clear();
            /**
             * ASSGIN,EQ.CEQ
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("$b=true==$i===1", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[VAR, b, ASSIGN, =, TRUE, true, EQ, ==, VAR, i, CEQ, ===, NUMBER, 1]" //
                    , result.toString());
            result.clear();
            /**
             * ASSGIN,EQ,CEQ
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("$b=!$t!=$i!==1", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[VAR, b, ASSIGN, =, NOT, !, VAR, t, NEQ, !=, VAR, i, NCEQ, !==, NUMBER, 1]" //
                    , result.toString());
            result.clear();
            /**
             * GT,GET
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("128>8997", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }

            assertEquals("[NUMBER, 128, GT, >, NUMBER, 8997]" //
                    , result.toString());
            result.clear();
            /**
             * GT,GET
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("128>=8997", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[NUMBER, 128, GET, >=, NUMBER, 8997]" //
                    , result.toString());
            result.clear();
            /**
             * LT,LET
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("128<8997", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[NUMBER, 128, LT, <, NUMBER, 8997]" //
                    , result.toString());
            result.clear();
            /**
             * LT,LET
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("128<=8997", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[NUMBER, 128, LET, <=, NUMBER, 8997]" //
                    , result.toString());
            result.clear();
            /**
             * MULTIPLY,DIV,MOD
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("128*228/328%528", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[NUMBER, 128, MULTIPLY, *, NUMBER, 228, DIV, /, NUMBER, 328, MOD, %, NUMBER, 528]" //
                    , result.toString());
            result.clear();
            /**
             * AND
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("true && false", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[TRUE, true, AND, &&, FALSE, false]" //
                    , result.toString());
            result.clear();
            /**
             * BAND
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("2  & 1", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[NUMBER, 2, BAND, &, NUMBER, 1]" //
                    , result.toString());
            result.clear();
            /**
             * XOR
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("true  xor false", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[TRUE, true, XOR, xor, FALSE, false]" //
                    , result.toString());
            result.clear();
            /**
             * BXOR
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("~255", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[BNOT, ~, NUMBER, 255]" //
                    , result.toString());
            result.clear();
            /**
             * BSLEFT
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("5<<12", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[NUMBER, 5, BSLEFT, <<, NUMBER, 12]" //
                    , result.toString());
            result.clear();
            /**
             * BSRIGHT
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("12>>5", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[NUMBER, 12, BSRIGHT, >>, NUMBER, 5]" //
                    , result.toString());
            result.clear();
            /**
             * BSURIGHT
             */
            overlooker = new Overlooker("test.tpl");
            analyzer = new Lexer("12>>>5", overlooker);
            analyzer.parse();
            for (Token token : analyzer.getTokenStack()) {
                result.add(token.getType());
                result.add(token.getWords());
            }
            assertEquals("[NUMBER, 12, BSURIGHT, >>>, NUMBER, 5]" //
                    , result.toString());
            result.clear();
        }

//        TimeMeasurer tm = new TimeMeasurer();
//        MemoryMeasurer mm = new MemoryMeasurer();
//        mm.start();
//        tm.start();
//        for (int i = 0; i < 10000; i++) {

        /**
         * complex expression
         */
        overlooker = new Overlooker("test.tpl");
        analyzer = new Lexer("[128*228/328%528,true,128>39,$i++"
                + ",{keyA:'valueA',keyB:222,keyC:328-128*228%528}]", overlooker);
        analyzer.parse();
        for (Token token : analyzer.getTokenStack()) {
            result.add(token.getType());
            result.add(token.getWords());
        }
        assertEquals("[LBRACKET, ["
                + ", NUMBER, 128, MULTIPLY, *, NUMBER, 228, DIV, /"
                + ", NUMBER, 328, MOD, %, NUMBER, 528"
                + ", COMMA, ,"
                + ", TRUE, true"
                + ", COMMA, ,"
                + ", NUMBER, 128, GT, >, NUMBER, 39"
                + ", COMMA, ,"
                + ", VAR, i, INCSELF, ++"
                + ", COMMA, ,"
                + ", OBRACE, {"
                + ", MAP_KEY"
                + ", keyA, SQUOTE, ', STRING, valueA, SQUOTE, '"
                + ", MAP_KEY, keyB, NUMBER, 222"
                + ", MAP_KEY, keyC, NUMBER, 328, MINUS, -, NUMBER, 128"
                + ", MULTIPLY, *, NUMBER, 228, MOD, %, NUMBER, 528, CBRACE, }, RBRACKET, ]]" //
                , result.toString());
        result.clear();
//        }
//        System.out.println(tm.spend());
//        System.out.println(mm.spend());
        /**
         * 1,000
         * tm 155
         * mm 51520
         * tm 203
         * mm 51520
         * tm 230
         * mm 51520
         * 
         * 10,000
         * tm 787
         * mm -144656
         * tm 1048
         * mm -144656
         * tm 777
         * mm -144656
         * 排除测试代码后
         * tm 212
         * mm 56400
         * 
         * 100,000
         * tm 6012
         * mm 28368
         * tm 6021
         * mm 28368
         * tm 6032
         * mm 28368
         * 排除测试代码后
         * tm 1697
         * mm -74512
         * tm 1809
         * mm -74512
         * tm 1731
         * mm -74512
         *
         */
    }
}
