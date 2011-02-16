/*
 * MoXie (SysTem128@GMail.Com) 2010-1-22 15:35:11
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.zoeey.resource.ResourceExceptionMsg;
import org.zoeey.util.StringHelper;

/**
 * 分析器，负责分析表达式组成
 * 
 * @author MoXie
 */
public class Lexer {

    /**
     * 表达式
     */
    private String expression = null;
    /**
     * 解析位置
     */
    private int index = 0;
    /**
     * 表达式长度
     */
    private int length = 0;
    /**
     * 验证器
     */
    private Overlooker overlooker;
    private Stack<Token> tokenStack = null;

    /**
     * 
     * @param expression 
     * @param overlooker   检查器
     */
    public Lexer(String expression, Overlooker overlooker) {
        this.expression = expression;
        length = expression.length();
        index = 0;
        this.overlooker = overlooker;
        tokenStack = new Stack<Token>();
    }

    /**
     * <pre>
     * 【键】值表达式。不为空，不包含空格的字符串皆可作为键名
     * </pre>
     * @param strBuilder
     */
    private void seekKey(StringBuilder strBuilder) {
        char ch = 0;
        ch = expression.charAt(index);
        int i = 0;
        for (; index < length; index++, i++) {
            ch = expression.charAt(index);
            if (ch == ':') {
                index--;
                break;
            }
            if (i == 0) {
                if (Character.isJavaIdentifierStart(ch)) {
                    strBuilder.append(ch);
                    continue;
                }
            } else {
                if (Character.isJavaIdentifierPart(ch)) {
                    strBuilder.append(ch);
                    continue;
                }
            }
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_MAP_KEY_SYMBOLERROR);
            index--;
            break;

        }
        if (strBuilder.length() == 0) {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_MAP_KEY_NOTEXISTS);
        }
    }

    /**
     * 加号与自增
     */
    private void seekPlus() {
        /**
         * http://flash-gordon.me.uk/ansi.c.txt
         * x+++++y -> x++ + ++y
         * x++---y -> x++ - --y
         * 变量自增返回常量，常量不可自增。
         */
        char ch = 0;
        Token tmpToken;
        ch = expression.charAt(index);
        boolean force = false; // 强制为加号

        // 判断栈顶是否为自增或自减
        tmpToken = tokenStack.size() > 0 ? tokenStack.lastElement() : null;

        if (tmpToken != null //
                && (tmpToken.getType() == TokenType.INCSELF //
                || tmpToken.getType() == TokenType.DECSELF)) {
            force = true;
        }

        // 判断操作数前是否为自增或自减
        if (!force
                && (tmpToken != null //
                && tmpToken.getType() != TokenType.PLUS //
                && tmpToken.getType() != TokenType.MINUS)//
                ) {
            tmpToken = tokenStack.size() > 1 ? tokenStack.get(tokenStack.size() - 2) : null;
            if (tmpToken != null //
                    && (tmpToken.getType() == TokenType.INCSELF //
                    || tmpToken.getType() == TokenType.DECSELF)) {
                force = true;
            }
        }

        if (force) {
            tokenStack.add(new Token(TokenType.PLUS, "+", 4));
        } else {
            if (index < length - 1) {
                ch = expression.charAt(index + 1);
                if (ch == '+') {
                    index++;
                    tokenStack.add(new Token(TokenType.INCSELF, "++", 2, AssocType.BOTH, 1));
                    return;
                }
            }
            tokenStack.add(new Token(TokenType.PLUS, "+", 4));
        }
    }

    /**
     * 减号与自减
     */
    private void seekMinus() {
        char ch = 0;
        Token tmpToken;
        ch = expression.charAt(index);
        boolean force = false; // 强制为减号

        // 判断栈顶是否为自增或自减
        tmpToken = tokenStack.size() > 0 ? tokenStack.lastElement() : null;
        if (tmpToken != null //
                && (tmpToken.getType() == TokenType.INCSELF //
                || tmpToken.getType() == TokenType.DECSELF)) {
            force = true;
        }

        // 判断操作数前是否为自增或自减
        if (!force
                && (tmpToken != null //
                && tmpToken.getType() != TokenType.PLUS //
                && tmpToken.getType() != TokenType.MINUS)//
                ) {
            tmpToken = tokenStack.size() > 1 ? tokenStack.get(tokenStack.size() - 2) : null;
            if (tmpToken != null //
                    && (tmpToken.getType() == TokenType.INCSELF //
                    || tmpToken.getType() == TokenType.DECSELF)) {
                force = true;
            }
        }

        if (force) {
            tokenStack.add(new Token(TokenType.MINUS, "-", 4));
        } else {
            if (index < length - 1) {
                ch = expression.charAt(index + 1);
                if (ch == '-') {
                    index++;
                    tokenStack.add(new Token(TokenType.DECSELF, "--", 2, AssocType.BOTH, 1));
                    return;
                }
            }
            tokenStack.add(new Token(TokenType.MINUS, "-", 4));
        }
    }

    /**
     * 验证字符串，起始为双引号。
     * @param strBuilder
     * @return
     */
    private void seekStringDQuote(StringBuilder strBuilder) {
        char ch = 0;
        boolean isEscape = false;
        ch = expression.charAt(index);
        {
            while (true) {
                index++;
                if (index >= length) {
                    break;
                }
                ch = expression.charAt(index);
                if (isEscape) {
                    isEscape = false;
                    seekEscape(strBuilder);
                } else {
                    if (ch == '\\') {
                        isEscape = true;
                        continue;
                    }
                    if (ch == '"') {
                        break;
                    } else {
                        strBuilder.append(ch);
                    }
                }
                if (!overlooker.isPass()) {
                    break;
                }
            }
        }
        if (ch != '"') {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_STRING_DQUOTE_UNCLOSED);
            strBuilder = null;
        }
    }

    /**
     * 验证字符串，起始为单引号。
     * @param strBuilder
     * @return
     */
    private void seekStringSQuote(StringBuilder strBuilder) {
        char ch = 0;
        ch = expression.charAt(index);
        {
            while (true) {
                index++;
                if (index >= length) {
                    break;
                }
                ch = expression.charAt(index);
                if (ch == '\'') {
                    break;
                } else {
                    strBuilder.append(ch);
                }
                if (!overlooker.isPass()) {
                    break;
                }
            }
        }
        if (ch != '\'') {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_STRING_SQUOTE_UNCLOSED);
            strBuilder = null;
        }
    }

    /**
     * 验证十六进制字母
     * @param ch
     * @return
     */
    private boolean isHex(char ch) {
        if ((ch >= 'a' && ch <= 'f') || ch >= 'A' && ch <= 'F') {
            return true;
        }
        return false;
    }

    /**
     * 验证数字<br />
     * +123 -123 +1.23 -1.23
     * @param strBuilder
     * @return
     */
    private void seekNumber(StringBuilder strBuilder) {
        char ch = 0;
        int i = 0;
        int dotCount = 0;
        int xCount = 0;
        boolean isDigit = false;
        do {
            if (index >= length) {
                break;
            }
            ch = expression.charAt(index);
            if (i >= 1) {
                isDigit = Character.isDigit(ch);
                if (isDigit || ch == '.') {
                    if (ch == '.') {
                        dotCount++;
                        if (dotCount > 1) {
                            overlooker.disPass(index);
                            overlooker.setMsg(ResourceExceptionMsg.ZTPL_NUMBER_MULTI_DOT);
                            break;
                        }
                    }
                    strBuilder.append(ch);
                } else if (ch == 'x' || isHex(ch)) {
                    if (ch == 'x') {
                        xCount++;
                        if (xCount > 1 || i != 1 || expression.charAt(index - 1) != '0') {
                            overlooker.disPass(index);
                            overlooker.setMsg(ResourceExceptionMsg.ZTPL_NUMBER_X_POSITION);
                            break;
                        }
                    } else {
                        if (i < 2) {
                            overlooker.disPass(index);
                            overlooker.setMsg(ResourceExceptionMsg.ZTPL_NUMBER_X_POSITION);
                            break;
                        }
                    }
                    strBuilder.append(ch);
                } else {
                    ch = expression.charAt(--index);
                    break;
                }
            } else {
                strBuilder.append(ch);
            }
            i++;
            index++;
        } while (true);
        if (index < length) {
            if (!Character.isDigit(ch) && !(xCount == 1 && isHex(ch))) {
                overlooker.disPass(index);
                overlooker.setMsg(ResourceExceptionMsg.ZTPL_NUMBER_X_POSITION);
                strBuilder = null;
            }
        }
    }

    /**
     * 析出表
     * @return
     */
    private Map<Object, Object> seekMap() {
        String key = null;
        char ch = 0;
        StringBuilder strBuilder = new StringBuilder();
        Map<Object, Object> map = new LinkedHashMap<Object, Object>();
        boolean nextIsKey = true;
        for (index++; index < length; index++) {
            if (!overlooker.isPass()) {
                break;
            }
            ch = expression.charAt(index);
            if (ch != '}') {
                if (nextIsKey) {

                    strBuilder = new StringBuilder();
                    seekKey(strBuilder); // {"_key":"value"}
                    index++;
                    ch = expression.charAt(index);
                    if (!overlooker.isPass()) {
                        break;
                    }
                    if (ch != ':') {
                        overlooker.disPass(index);
                        overlooker.setMsg(ResourceExceptionMsg.ZTPL_MAP_KEY_END);
                        break;
                    }
                    key = strBuilder.toString();
                    strBuilder = null;
                    index++;
                    tokenStack.add(new Token(TokenType.MAP_KEY, key, 0));
                    analysis();
                    if (!overlooker.isPass()) {
                        break;
                    }
                    index++;
                }
                ch = expression.charAt(index);
                if (ch == ',') {
                    nextIsKey = true;
                    continue;
                } else {
                    nextIsKey = false;
                    analysis();
//                    overlooker.disPass(index);
                }
            } else {
                index--;
                break;
            }
        }
        if (ch != '}') {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_MAP_END);
        }
        return map;
    }

    /**
     * 寻求List
     * @return
     */
    private List<Object> seekList() {
        char ch = 0;
        List<Object> list = new ArrayList<Object>();
        Object value;
        for (index++; index < length; index++) {
            ch = expression.charAt(index);
            if (ch != ']') {
                analysis();
                if (overlooker.isPass()) {
//                    list.add(value); // ["_value"]
                } else {
                    break;
                }
                ch = expression.charAt(++index);

                ch = expression.charAt(index);
                if (ch == ',') {
                    tokenStack.add(new Token(TokenType.COMMA, ",", 0));
                    continue;
                } else if (ch == ']') {
                    break;
                } else {
                    analysis();
                }
            }
        }
        if (ch != ']') {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_LIST_END);
        }
        return list;
    }
    /**
     * 可转义字符
     */
    private static final char[] escs = {'"', '/', '\\', 'b', 'f', 'n', 'r', 't', 'u'}; // sorted

    /**
     * 寻求可转义字符
     * @param strBuilder
     */
    private void seekEscape(StringBuilder strBuilder) {
        char ch = expression.charAt(index);
        /**
         * 非必须转义字符被识别为本身
         * 本方法保留较为严格的验证
         */
        if (Arrays.binarySearch(escs, ch) < 0) {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_STRING_ESCAPE);
        }
        // unicode
        if (overlooker.isPass()) {
            if (ch == 'u') {
                seekUnicode(strBuilder);
            } else {
                strBuilder.append(StringHelper.toEscapedVal(ch));
            }
        }
        // not valied return "\\uXXXX"
        if (false) {
            if (!overlooker.isPass()) {
                strBuilder = null;
            }
        }
    }

    /**
     * 寻求unicode表达式
     * @param strBuilder
     */
    private void seekUnicode(StringBuilder strBuilder) {
        char ch = 0;
        boolean isU = true;
        int i = 0;
        char[] chars = new char[4];
        int start = index;
        while (i < 4) {
            ch = expression.charAt(++index);
            if (CharacterIterator.DONE == ch) {
                isU &= false;
                break;
            }
            if ((ch > 47 && ch < 58) // 0-9
                    || (ch > 64 && ch < 71) // A-F
                    || (ch > 96 && ch < 103) // a-f
                    ) {
                isU &= true;
            } else {
                isU &= false;
            }
            chars[i] = ch;
            i++;
        }
        // 不是unicode 直接输出 \\uXXXX
        if (!isU) {
            index = start;
            ch = 0;
            strBuilder.append("\\u");
            while (i > 0) {
                i--;
                strBuilder.append(expression.charAt(++index));
            }
        } else {
            strBuilder.append((char) Integer.parseInt(new String(chars), 16));
        }
        chars = null;
    }

    /**
     * 寻找函数或方法名 @todo last woking point~
     * @see TokenType#FUNCTION
     * @see TokenType#METHOD
     * @param strBuilder
     */
    private void seekMethod() {
        StringBuilder strBuilder = new StringBuilder();
        char ch = 0;
        ch = expression.charAt(index);
        int i = 0;
        String name = null;
        /**
         * 1 碰到括号 。 判断是否为关键词，是的话则异常
         * 2 非可定义为函数的字符 。不为关键词则异常。
         */
        int breakType = 0;
        for (; index < length; index++, i++) {
            ch = expression.charAt(index);
            if (ch == '(') {
                index--;
                breakType = 1;
                break;
            }
            if (i == 0) {
                if (Character.isLetter(ch)) {
                    strBuilder.append(ch);
                    continue;
                }
            } else {
                if (Character.isJavaIdentifierPart(ch)) {
                    strBuilder.append(ch);
                    continue;
                }
            }
            breakType = 2;
            index--;
            break;
        }
        name = strBuilder.toString();
        if ("true".equalsIgnoreCase(name)) {
            if (breakType == 1) {
                overlooker.disPass(index);
                overlooker.setMsg(ResourceExceptionMsg.ZTPL_FUNCTION_KEYWORD);
            }
            tokenStack.add(new Token(TokenType.TRUE, "true", 0));
            return;
        }
        if ("xor".equalsIgnoreCase(name)) {
            if (breakType == 1) {
                overlooker.disPass(index);
                overlooker.setMsg(ResourceExceptionMsg.ZTPL_FUNCTION_KEYWORD);
            }
            tokenStack.add(new Token(TokenType.XOR, "xor", 0));
            return;
        }
        if ("false".equalsIgnoreCase(name)) {
            if (breakType == 1) {
                overlooker.disPass(index);
                overlooker.setMsg(ResourceExceptionMsg.ZTPL_FUNCTION_KEYWORD);
            }
            tokenStack.add(new Token(TokenType.FALSE, "false", 0));
            return;
        }
        if ("null".equalsIgnoreCase(name)) {
            if (breakType == 1) {
                overlooker.disPass(index);
                overlooker.setMsg(ResourceExceptionMsg.ZTPL_FUNCTION_KEYWORD);
            }
            tokenStack.add(new Token(TokenType.NULL, "null", 0));
            return;
        }
        if (breakType == 2) {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_FUNCTION_SYMBOLERROR);
            return;
        }
        if (strBuilder.length() == 0) { // never happened
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_FUNCTION_LENGTH);
            return;
        }
        tokenStack.add(new Token(TokenType.FUNCTION, name, 2, AssocType.RIGHT, -1));
    }

    /**
     * 变量名
     * @param strBuilder
     */
    private void seekVar(StringBuilder strBuilder) {
        char ch = 0;
        index++;    //skip char "$"
        int i = 0;
        for (; index < length; index++, i++) {
            ch = expression.charAt(index);
            if (i == 0) {
                if (Character.isJavaIdentifierStart(ch)) {
                    strBuilder.append(ch);
                    continue;
                }
            } else {
                if (Character.isJavaIdentifierPart(ch)) {
                    strBuilder.append(ch);
                    continue;
                }
            }
            index--;
            break;

        }
        if (strBuilder.length() == 0) {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_VAR_LENGTH);
        }
    }

    /**
     * 赋值等号，等于，全等
     * @param strBuilder
     */
    private void seekEqual() {
        char ch = 0;
        index++;    //skip char "="
        int i = 1;
        for (; index < length; index++) {
            ch = expression.charAt(index);
            if (ch == '=') {
                i++;
                continue;
            }
            index--;
            break;

        }
        if (i == 1) {
            tokenStack.add(new Token(TokenType.ASSIGN, "=", 14, AssocType.RIGHT));
        } else if (i == 2) {
            tokenStack.add(new Token(TokenType.EQ, "==", 7));
        } else if (i == 3) {
            tokenStack.add(new Token(TokenType.CEQ, "===", 7));
        } else {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_EQUAL_NUM);
        }
    }

    /**
     * 不等于，不全等
     * @param strBuilder
     */
    private void seekNotEqual() {
        char ch = 0;
        index++;    //skip char "="
        int i = 1;
        for (; index < length; index++) {
            ch = expression.charAt(index);
            if (ch == '=') {
                i++;
                continue;
            }
            index--;
            break;
        }
        if (i == 1) {
            tokenStack.add(new Token(TokenType.NOT, "!", 2, AssocType.RIGHT, 1));
        } else if (i == 2) {
            tokenStack.add(new Token(TokenType.NEQ, "!=", 7));
        } else if (i == 3) {
            tokenStack.add(new Token(TokenType.NCEQ, "!==", 7));
        } else {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_EQUAL_NUM);
        }
    }

    /**
     * 大于，大于等于，右移，无符右移
     * @param strBuilder
     */
    private void seekGT() {
        char ch = 0;
        index++;    //skip char ">"
        int i = 1;
        int j = 1;
        for (; index < length; index++) {
            ch = expression.charAt(index);
            if (ch == '=') {
                i++;
            } else if (i == 1 && ch == '>') {
                j++;
                continue;
            } else {
                index--;
            }
            break;
        }
        if (j == 3) {
            tokenStack.add(new Token(TokenType.BSURIGHT, ">>>", 5));
        } else if (j == 2) {
            tokenStack.add(new Token(TokenType.BSRIGHT, ">>", 5));
        } else if (i == 2) {
            tokenStack.add(new Token(TokenType.GET, ">=", 6));
        } else if (i == 1) {
            tokenStack.add(new Token(TokenType.GT, ">", 6));
        }
    }

    /**
     * 小于，小于等于，左移
     * @param strBuilder
     */
    private void seekLT() {
        char ch = 0;
        index++;    //skip char "="
        int i = 1;
        for (; index < length; index++) {
            ch = expression.charAt(index);
            if (ch == '=') {
                i++;
                tokenStack.add(new Token(TokenType.LET, "<=", 6));
            } else if (ch == '<') {
                i++;
                tokenStack.add(new Token(TokenType.BSLEFT, "<<", 5));
            } else {
                index--;
            }
            break;
        }
        if (i == 1) {
            tokenStack.add(new Token(TokenType.LT, "<", 6));
        }
    }

    /**
     * 按位与，逻辑与
     * @param strBuilder
     */
    private void seekAnd() {
        char ch = 0;
        index++;    //skip char "&"
        int i = 1;
        for (; index < length; index++) {
            ch = expression.charAt(index);
            if (ch == '&') {
                i++;
            } else {
                index--;
            }
            break;
        }
        if (i == 1) {
            tokenStack.add(new Token(TokenType.BAND, "&", 8));
        } else if (i == 2) {
            tokenStack.add(new Token(TokenType.AND, "&&", 11));
        } else {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_AND_NUM);
        }
    }

    /**
     * 按位或，逻辑或
     * @param strBuilder
     */
    private void seekOr() {
        char ch = 0;
        index++;    //skip char "&"
        int i = 1;
        for (; index < length; index++) {
            ch = expression.charAt(index);
            if (ch == '|') {
                i++;
            } else {
                index--;
            }
            break;

        }
        if (i == 1) {
            tokenStack.add(new Token(TokenType.VBAR, "|", 10));
        } else if (i == 2) {
            tokenStack.add(new Token(TokenType.OR, "||", 12));
        } else {
            overlooker.disPass(index);
            overlooker.setMsg(ResourceExceptionMsg.ZTPL_AND_NUM);
        }
    }

    /**
     * 主转换方法
     */
    public void parse() {
        while (index < length) {
            analysis();
            index++;
        }
    }

    private void analysis() {
        if (!overlooker.isPass()) {
            return;
        }
        char ch;
        StringBuilder strBuilder;
        ch = expression.charAt(index);
        boolean isPass = false;
        int tmpIndex = -1; // 临时索引
        Token tmpToken = null; // 临时标识
        try {
            switch (ch) {
                case ' ':
                case '\t':
                case '\r':
                case '\n':
                    isPass = true;
                    break;
                case '"':
                    tokenStack.add(new Token(TokenType.DQUOTE, "\"", 0));
                    strBuilder = new StringBuilder();
                    seekStringDQuote(strBuilder);  // "...."
                    tokenStack.add(new Token(TokenType.STRING, strBuilder.toString(), 0));
                    if (overlooker.isPass()) {
                        tokenStack.add(new Token(TokenType.DQUOTE, "\"", 0));
                    }
                    strBuilder = null;
                    isPass = true;
                    break;
                case '\'':
                    tokenStack.add(new Token(TokenType.SQUOTE, "'", 0));
                    strBuilder = new StringBuilder();
                    seekStringSQuote(strBuilder);  // "...."
                    tokenStack.add(new Token(TokenType.STRING, strBuilder.toString(), 0));
                    if (overlooker.isPass()) {
                        tokenStack.add(new Token(TokenType.SQUOTE, "'", 0));
                    }
                    strBuilder = null;
                    isPass = true;
                    break;
                case '[':
                    tokenStack.add(new Token(TokenType.LBRACKET, "[", 1, AssocType.LEFT, -1));
                    seekList();
                    if (overlooker.isPass()) {
                        tokenStack.add(new Token(TokenType.RBRACKET, "]", 1, AssocType.LEFT, -1));
                        isPass = true;
                    }
                    break;
                case ']':
                    tokenStack.add(new Token(TokenType.RBRACKET, "]", 1, AssocType.LEFT, -1));
                    isPass = true;
                    break;
                case '{':
                    tokenStack.add(new Token(TokenType.OBRACE, "{", 1, AssocType.LEFT, -1));
                    seekMap();
                    if (overlooker.isPass()) {
                        isPass = true;
                    }
                    break;
                case '}':
                    tokenStack.add(new Token(TokenType.CBRACE, "}", 1, AssocType.LEFT, -1));
                    isPass = true;
                    break;
                case '0':
                // <editor-fold defaultstate="collapsed" desc="case [0-9] 数字">
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    // </editor-fold>
                    strBuilder = new StringBuilder();
                    seekNumber(strBuilder);
                    if (overlooker.isPass()) {
                        tokenStack.add(new Token(TokenType.NUMBER, strBuilder.toString(), 0));
                        isPass = true;
                    }
                    strBuilder = null;
                    break;
                case '.':
                    // $var.methodA()
                    // $var.methodA().methodB()
                    // 123.456
                    // "stringA".'stringB'
                    // [1,2,...,10]
                    // [1,3,...,20] 前两元计算step，第三元表示自动递增，最后一元表示终止界限
                    tokenStack.add(new Token(TokenType.DOT, ".", 1));
                    isPass = true;
                    break;
                case 'a':
                // <editor-fold defaultstate="collapsed" desc="case [a-zA-Z] 大小写字母">
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                    // </editor-fold>
                    // 函数(func())，常量(true,false,null)，无法识别时则作为字符串
                    seekMethod();
                    if (overlooker.isPass()) {
                        isPass = true;
                    }
                    break;
                case '@':
                    // 静默，捕捉到异常后不进行处理
                    tokenStack.add(new Token(TokenType.SILENCE, "@", 0));
                    isPass = true;
                    break;
                case '$':
                    // 变量
                    strBuilder = new StringBuilder();
                    seekVar(strBuilder);
                    if (overlooker.isPass()) {
                        tokenStack.add(new Token(TokenType.VAR, strBuilder.toString(), 0));
                        isPass = true;
                    }
                    strBuilder = null;
                    break;
                case '+':
                    // +
                    // ++
                    seekPlus();
                    isPass = true;
                    break;
                case '-':
                    // -
                    // --
                    seekMinus();
                    isPass = true;
                    break;
                case '*':
                    tokenStack.add(new Token(TokenType.MULTIPLY, "*", 3));
                    isPass = true;
                    break;
                case '/':
                    tokenStack.add(new Token(TokenType.DIV, "/", 3));
                    isPass = true;
                    break;
                case '%':
                    tokenStack.add(new Token(TokenType.MOD, "%", 3));
                    isPass = true;
                    break;
                case '(':
                    tokenStack.add(new Token(TokenType.LPAREN, "(", 1));
                    isPass = true;
                    break;
                case ')':
                    tokenStack.add(new Token(TokenType.RPAREN, ")", 1));
                    isPass = true;
                    break;
                case ',':
                    tokenStack.add(new Token(TokenType.COMMA, ",", 15));
                    isPass = true;
                    break;
                case '|':
                    seekOr();
                    if (overlooker.isPass()) {
                        isPass = true;
                    }
                    break;
                case ';':
                    tokenStack.add(new Token(TokenType.SEMICOLON, ";", 0));
                    isPass = true;
                    break;
                case ':':
                    // ?:
                    tokenStack.add(new Token(TokenType.COLON, ":", 13, AssocType.RIGHT, 3));
                    isPass = true;
                    break;
                case '=':
                    seekEqual();
                    if (overlooker.isPass()) {
                        isPass = true;
                    }
                    break;
                case '?':
                    // ?:
                    tokenStack.add(new Token(TokenType.QMARK, "?", 13, AssocType.RIGHT, 3));
                    isPass = true;
                    break;
                case '!':
                    seekNotEqual();
                    if (overlooker.isPass()) {
                        isPass = true;
                    }
                    break;
                case '>':
                    seekGT();
                    isPass = true;
                    break;
                case '<':
                    seekLT();
                    isPass = true;
                    break;
                case '&':
                    seekAnd();
                    if (overlooker.isPass()) {
                        isPass = true;
                    }
                    break;
                case '^':
                    tokenStack.add(new Token(TokenType.BXOR, "^", 9));
                    isPass = true;
                    break;
                case '~':
                    tokenStack.add(new Token(TokenType.BNOT, "~", 2, AssocType.RIGHT, 1));
                    isPass = true;
                    break;
            }
            if (!isPass) {
                overlooker.disPass(index);
                overlooker.setMsg(ResourceExceptionMsg.ZTPL_UNKNOW_SYMBOL);
            }
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            overlooker.disPass(index);
        }
    }

    public Stack<Token> getTokenStack() {
        return tokenStack;
    }
}
