/*
 * MoXie (SysTem128@GMail.Com) 2009-8-21 16:40:47
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Stack;
import org.zoeey.util.StringHelper;
import org.zoeey.ztpl.ZtplCompileException;
import org.zoeey.ztpl.ZtplConfig;
import org.zoeey.ztpl.ZtplConfig.Delimiter;

/**
 * 编译器，负责将模板语言转译为字节码。
 * @author MoXie
 */
class Compiler {

    /**
     * 行号
     */
    private int line = 0;
    /**
     * 字符位置
     */
    private int charPos = 0;
    /**
     * 指令集
     */
    private Stack<String> statementStack;
    /**
     * 编译跟踪器
     */
    private ByteCodeHelper byteCodeHelper = null;
    /**
     * 模板配置
     */
    private ZtplConfig config = null;
    /**
     * 初始定界符
     */
    private Delimiter defaultDelimiter;
    /**
     * 当前定界符
     */
    private Delimiter currentDelimiter;

    /**
     * 编译器
     * @param config
     */
    public Compiler(ZtplConfig config) {
        this.config = config;
        Delimiter delimiter = config.getDelimiter();
        defaultDelimiter = delimiter;
        currentDelimiter = delimiter;
        statementStack = new Stack<String>();
        byteCodeHelper = new ByteCodeHelper();
    }
    /**
     * 模板输入流
     */
    private PushbackInputStream tplStream = null;

    /**
     * 编译
     * @param tplFile 模板文件
     * @throws Exception
     */
    public byte[] compile(File tplFile) throws Exception {
        tplStream = new PushbackInputStream(new FileInputStream(tplFile), 25);
        int ich = 0;
        int pos = 0;
        int startChar = 0;
        boolean isBoun = false;
        StringBuilder strBuilder = new StringBuilder();
        String expression = null;
        do {
            isBoun = isBoundary(currentDelimiter.getLeft());
            {
                if (isBoun) {
                    /**
                     * 内容
                     */
                    {
                        byteCodeHelper.writeStr(strBuilder.toString());
                    }
                    if ((ich = tplStream.read()) == -1) {
                        break;
                    }
                    switch (ich) {
                        /**
                         * 注释
                         */
                        case '*':
                            skipCommon();
                            break;
                        /**
                         * 变量
                         */
                        case '$':
                            expression = seekVar();
                            compileVar(expression);
                            break;
                        /**
                         * 块函数终止
                         */
                        case '/':
                            seekBlockEnd();
                            break;
                        /**
                         * 函数
                         */
                        default:
                            if (Character.isJavaIdentifierStart(ich)) {
                                expression = seekFunction((char) ich);
                                compileFunction(expression);
                            } else {
                                String func = seekExceptionStatement((char) ich, strBuilder,//
                                        currentDelimiter);
                                {
                                    throw new ZtplCompileException("not defined statement", func, //
                                            line, charPos + currentDelimiter.getRight().length());
                                }
                            }
                    }
                    strBuilder = new StringBuilder();
                } else {
                    if ((ich = tplStream.read()) == -1) {
                        break;
                    }
                    pos++;
                    charPos++;
                    strBuilder.append((char) ich);
                }

            }
            if (isLineSep(pos, ich)) {
                line++;
                charPos = 0;
            }
        } while (true);
        byteCodeHelper.writeStr(strBuilder.toString());
        return byteCodeHelper.getCode();
    }

    /**
     * 重置定界符，将定界符重设为起始定界符
     */
    private void resetDelimiter() {
        currentDelimiter = defaultDelimiter;
    }
    /**
     * 最终出现换行符或回车符
     */
    private int lastLineSep = 0;
    /**
     * 最终出现换行符的位置
     */
    private int lastLineSepPos = -1;

    /**
     * 是否是分行
     * @param pos   当前位置
     * @param ich   字符
     * @return  <b>true</b> 换行 <b>false</b> 不换行
     */
    private boolean isLineSep(int pos, int ich) {

        switch (ich) {
            case '\r':
            case '\n':
                /**
                 * 起始换行
                 */
                if (lastLineSepPos != pos - 1) {
                    lastLineSepPos = pos;
                    lastLineSep = ich;
                    return true;
                }
                // unix: \n
                if ((lastLineSep == '\n' && ich == '\n')) {
                    lastLineSepPos = pos;
                    lastLineSep = ich;
                    return true;
                }
                // mac: \r
                if ((lastLineSep == '\r' && ich == '\r')) {
                    lastLineSepPos = pos;
                    lastLineSep = ich;
                    return true;
                }
                return false;
            default:
                // error:\n\r
                return false;
        }
    }

    /**
     * 效验分隔符
     * 注意：定界符确认将会被滤过
     * 例如：{@code <!--{,}-->}
     *      
     * @param boundary  定界符
     * @return  <b>true</b> 效验成功，参数给出的字符串为定界符
     *           <b>false</b> 校验失败，参数给出的字符串不是定界符
     * @throws IOException
     */
    private boolean isBoundary(String boundary)
            throws IOException {
        int boundaryLen = boundary.length();
        int ich = 0;
        char ch = 0;
        int idx = 0;
        boolean isBoun = false;
        do {
            if ((ich = tplStream.read()) == -1) {
                break;
            }
            if (idx >= boundaryLen) {
                isBoun = true;
                break;
            }
            ch = (char) ich;

            if (ch != boundary.charAt(idx)) {
                break;
            }
            idx++;
        } while (true);
        // 非定界符，回滚已读取字符
        if (!isBoun) {
            if (ich != -1) {
                tplStream.unread(ich);
            }
            tplStream.unread(boundary.substring(0, idx).getBytes());
        } else {
            // 是定界符，如最终字符不是终止符则回滚一位
            if (ich != -1) {
                tplStream.unread(ich);
            }
        }
        return isBoun;
    }

    /**
     * 滤过注释
     * @throws IOException
     */
    private void skipCommon()//
            throws IOException {
        int ich = 0;
        do {
            if ((ich = tplStream.read()) == -1) {
                break;
            }
            if (ich == '*') {
                if (isBoundary(currentDelimiter.getRight())) {
                    break;
                }
            }
        } while (true);
    }

    /**
     * 变量
     * @return 变量表达式，包括修饰器等（$name|format value="%s"）
     * @throws IOException
     */
    private String seekVar()//
            throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        int ich = 0;
        do {
            if ((ich = tplStream.read()) == -1) {
                break;
            }
            strBuilder.append((char) ich);
            if (isBoundary(currentDelimiter.getRight())) {
                break;
            }

        } while (true);
        return strBuilder.toString();
    }

    /**
     * 函数，块函数
     */
    private String seekFunction(char start)//
            throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(start);
        int ich = 0;
        do {
            if ((ich = tplStream.read()) == -1) {
                break;
            }
            strBuilder.append((char) ich);
            if (isBoundary(currentDelimiter.getRight())) {
                break;
            }

        } while (true);
        return strBuilder.toString();
    }

    /**
     * 块函数结束语句
     */
    private String seekBlockEnd()//
            throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        int ich = 0;
        do {
            if ((ich = tplStream.read()) == -1) {
                break;
            }
            strBuilder.append((char) ich);
            if (isBoundary(currentDelimiter.getRight())) {
                break;
            }

        } while (true);
        String statement = strBuilder.toString();
        if (!statement.equals(statementStack.pop())) {
            // todo throw something,if /elseif /else /if
        }
        return strBuilder.toString();
    }

    /**
     * 异常提示语制取
     * @param start 起始字符
     * @param strBuilder  异常信息
     * @param delimiter 定界符
     * @return  异常的标签
     * @throws IOException
     */
    private String seekExceptionStatement(char start, StringBuilder strBuilder, //
            Delimiter delimiter)//
            throws IOException {
        strBuilder = new StringBuilder(StringHelper.subString(strBuilder.toString(),//
                strBuilder.length() - 20, -1));
        strBuilder.append(delimiter.getLeft());
        strBuilder.append(start);
        int ich = 0;
        int length = 20;
        int pos = 0;
        do {
            if ((ich = tplStream.read()) == -1) {
                break;
            }
            pos++;
            strBuilder.append((char) ich);
            if (pos > length) {
                break;
            }

        } while (true);
        return strBuilder.toString();
    }

    /**
     * 处理变量
     * @param varExpression 变量表达式
     */
    private void compileVar(String varExpression) {
        int length = varExpression.length();//
        int idx = 0;
        int ich = 0;
        String varName = null;
        /**
         * 变量名获取
         */
        StringBuilder strBuilder = new StringBuilder();
        do {
            if (idx == length) {
                break;
            }
            ich = varExpression.charAt(idx);
            if ((idx == 0 && (Character.isJavaIdentifierStart(ich) || ich == '!'))//
                    || (idx != 0 && ich == '.') //
                    || (idx != 0 && Character.isJavaIdentifierPart(ich))//
                    ) { //
                strBuilder.append((char) ich);
            } else {
                break;
            }
            idx++;
        } while (true);
        varName = strBuilder.toString();
        if (varName.startsWith("!")) {
            byteCodeHelper.writeVarJugeNull(varName.substring(1));
        } else {
            byteCodeHelper.writeVar(varName);
        }
    }

    /**
     * 处理函数
     * @param varName
     */
    private void compileFunction(String functionExpression) {
        FunctionStatement func = new FunctionStatement(byteCodeHelper, config, functionExpression);
        statementStack.push(func.getName());
        //
    }
}
