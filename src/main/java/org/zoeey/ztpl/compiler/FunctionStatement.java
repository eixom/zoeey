/*
 * MoXie (SysTem128@GMail.Com) 2009-8-27 9:44:18
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import org.zoeey.ztpl.FunctionAble;
import org.zoeey.ztpl.ZtplConfig;

/**
 * 函数编译器<br />
 * 函数名命名规则兼容Java方法名且可包括“.”号，但在标签内以$开头的“函数”，将会被识别为变量
 * @author MoXie
 */
public class FunctionStatement {

    /**
     * 编译跟踪器
     */
    private final ByteCodeHelper byteCodeHelper;
    /**
     * 语句
     */
    private String statement = null;
    /**
     * 解析位置
     */
    private int index = 0;
    /**
     * 语句长度
     */
    private int length = 0;
    /**
     * 函数名
     */
    private String name = null;
    /**
     * 模板配置
     */
    private ZtplConfig config = null;

    /**
     * 函数编译器
     * @param byteCodeHelper
     * @param config
     * @param statement
     */
    public FunctionStatement(ByteCodeHelper byteCodeHelper, ZtplConfig config, String statement) {
        this.byteCodeHelper = byteCodeHelper;
        this.config = config;
        this.statement = statement;
        this.length = statement.length();
        compile();
    }

    /**
     * 执行各项编译任务
     */
    private void compile() {
        /**
         * 析出方法名
         */
        seekName();
        /**
         * 析出参数
         */
        int paramsMapPos = seekParams();
        /**
         * 执行
         */
        execute(paramsMapPos);
    }

    /**
     * 剔除空白符
     */
    private void skipBlank() {
        char ch = 0;
        for (; index < length; index++) {
            ch = statement.charAt(index);
            if (Character.isWhitespace(ch)) {
                continue;
            } else {
                break;
            }
        }
    }

    /**
     * 析出方法名
     */
    private void seekName() {
        int ich = 0;
        /**
         * 函数名获取
         */
        StringBuilder strBuilder = new StringBuilder();
        do {
            if (index >= length) {
                break;
            }
            ich = statement.charAt(index);
            if ((index == 0 && Character.isJavaIdentifierStart(ich))//
                    || (index != 0 && ich == '.') //
                    || (index != 0 && Character.isJavaIdentifierPart(ich))//
                    ) { //
                strBuilder.append((char) ich);
            } else {
                break;
            }
            index++;
        } while (true);
        name = strBuilder.toString();
    }

    /**
     * 检测是否为合法的属性名起始（下划线"_"或字母）
     * @param ich 需要验证的字符
     * @return  <b>true</b> 符合属性名起始 <b>false</b> 不符合属性名起始
     */
    private boolean isStartOfAttrName(int ich) {
        return (ich == '_' || Character.isJavaIdentifierStart(ich));
    }

    /**
     * 检测是否为合法的属性名组成部分
     * @param ich 需要验证的字符
     * @return <b>true</b> 符合属性名组成部分 <b>false</b> 不符合属性名组成部分
     */
    private boolean isPartOfAttrName(int ich) {
        return (ich == '_' || Character.isLetter(ich) || Character.isDigit(ich));
    }

    /**
     * 析出参数，参数名只可由字母、数字、下划线组成
     */
    private int seekParams() {
        /**
         * 建立一个空ParamsMap
         * 取得此表的索引位置
         */
        int paramsMapPos = byteCodeHelper.newMap();
        /**
         * key1 = $value1 + 8
         */
        int ich = 0;
        String attrName = null;// 属性名称
        StringBuilder strBuilder = null;
        int attrLen = 0;
        do {
            if (index >= length) {
                break;
            }
            skipBlank();
            ich = 0;
            attrName = null;// 属性名称
            /**
             *
             * 参数名获取
             * 参数名可规则符合Java变量命名规则，且允许在非起始位置使用'.'。
             * [key1] = $value1 
             */
            strBuilder = new StringBuilder();
            attrLen = 0;
            do {
                if (index >= length) {
                    break;
                }
                attrLen++;
                ich = statement.charAt(index);
                if ((index == 0 && isStartOfAttrName(ich))//
                        || (index != 0 && ich == '.') //
                        || (index != 0 && isPartOfAttrName(ich))//
                        ) { //
                    strBuilder.append((char) ich);
                } else {
                    break;
                }
                index++;
            } while (true);
            if (index == 0) {
                return paramsMapPos;
            }
            attrName = strBuilder.toString();
            if (attrLen > 0) {
                /**
                 * key1[ ]= $value1
                 */
                skipBlank();
                /**
                 * key1 [=] $value1
                 */
                ich = 0;
                if (index < length) {
                    ich = statement.charAt(index);
                }
                if (ich == '=') {
                    // |= --> =|
                    index++;
                    /**
                     * key1 =[ ]$value1
                     */
                    skipBlank();
                    /**
                     * 使用表达式赋值
                     */
                    seekExpression();
                } else if (ich == 0 || isStartOfAttrName(ich)) {
                    /**
                     * 仅有属性名 则填充值为 true
                     */
                    byteCodeHelper.putToMap(paramsMapPos, attrName, byteCodeHelper.newVar(true));
                } else {
                    // 无属性
                }
            } else {
                break;
            }
        } while (true);
        return paramsMapPos;
    }

    /**
     * 字符串
     * @param strBuilder
     */
    private void seekString(StringBuilder strBuilder) {
        boolean isEscape = false;
        char ch = statement.charAt(index);
        char deli = ch;
        while (true) {
            index++;
            if (index >= length) {
                break;
            }
            ch = statement.charAt(index);
            if (isEscape) {
                isEscape = false;
                strBuilder.append(ch);
            } else {
                if (ch == '\\') {
                    isEscape = true;
                    continue;
                }
                if (ch == deli) {
                    break;
                } else {
                    strBuilder.append(ch);
                }
            }
        }
    }

    /**
     * 析出表达式（仅字符串内可出现空格）
     */
    private void seekExpression() {
        int ich = 0;
        StringBuilder strBuilder = new StringBuilder();
        doloop:
        do {
            if (index >= length) {
                break;
            }
            ich = statement.charAt(index);
            switch (ich) {
                case '\'':
                case '"':
                    strBuilder.append((char) ich);
                    seekString(strBuilder);
                    strBuilder.append((char) ich);
                    break;
                default:
                    if (Character.isWhitespace(ich)) {
                        break doloop;
                    }
                    strBuilder.append((char) ich);
            }
            index++;
        } while (true);
        System.out.print("expression:");
        System.out.println(strBuilder.toString());
    }

    /**
     * 执行
     */
    private void execute(int paramsMapPos) {
        @SuppressWarnings("unchecked")
        Class<FunctionAble> funcClass = (Class<FunctionAble>) config.getPluginMap().get(name);
        if (funcClass != null) {
            byteCodeHelper.callFunction(funcClass.getName(), paramsMapPos);
        }
    }

    /**
     * 获取函数名称
     * @return  函数名
     */
    public String getName() {
        return name;
    }
}
