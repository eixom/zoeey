/*
 * MoXie (SysTem128@GMail.Com) 2010-1-21 11:11:04
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

/**
 *
 * @author MoXie
 */
public enum TokenType {

    /**
     * 数字
     */
    /**
     * 整型
     * decimal     : [1-9][0-9]*
     *             | 0
     *
     * hexadecimal : 0[xX][0-9a-fA-F]+
     *
     * octal       : 0[0-7]+
     *
     * integer     : [+-]?decimal
     *             | [+-]?hexadecimal
     *             | [+-]?octal
     * 
     * 浮点型
     * LNUM          [0-9]+
     * DNUM          ([0-9]*[\.]{LNUM}) | ({LNUM}[\.][0-9]*)
     * EXPONENT_DNUM ( ({LNUM} | {DNUM}) [eE][+-]? {LNUM})
     */
    NUMBER,
    /**
     * 字符串 "{@code ^(".+?")|('.+?')$}"
     */
    STRING,
    /**
     * 点号 "{@code .}"
     */
    DOT,
    /**
     * 变量
     * NVAR     $[a-zA-Z_][_\da-zA-Z]*
     */
    VAR,
    /**
     * 函数 ex. "{@code sin(12)}"
     * FUNCTION     [a-zA-Z][\da-zA-Z]*
     */
    FUNCTION,
    /**
     * 方法    ex."{@code $person.getName().length()}"
     * METHOD     {NVAR}(\.{FUNCTION})+
     */
    METHOD,
    /**
     * 单引号 single quotes "{@code '}"
     */
    SQUOTE,
    /**
     * 双引号 double quotation marks "{@code "}"
     */
    DQUOTE,
    /**
     * 反斜杠 backslash  "{@code \}"
     */
    BACKSLASH,
    /**
     * 左括号 left parenthesis "{@code (}"
     */
    LPAREN,
    /**
     * 右括号 right parenthesis "{@code )}"
     */
    RPAREN,
    /**
     * 左方括号 left square bracket "{@code [}"
     */
    LBRACKET,
    /**
     * 右方括号 right square bracket  "{@code ]}"
     */
    RBRACKET,
    /**
     * 左大括号 Open brace "{@code {}"
     */
    OBRACE,
    /**
     * 右大括号 Close brace "{@code }}"
     */
    CBRACE,
    /**
     * 逗号 "{@code ,}"
     */
    COMMA,
    /**
     * 分号 semicolon "{@code ;}"
     */
    SEMICOLON,
    /**
     * 冒号 colon "{@code :}"
     */
    COLON,
    /**
     * 赋值等号 equal mark "{@code =}"
     */
    ASSIGN,
    /**
     * 感叹号（否定） exclamation  "{@code !}"
     */
    NOT,
    /**
     * 问号 question mark  "{@code ?}"
     */
    QMARK,
    /**
     * 大于号 great than "{@code &gt;}"
     */
    GT,
    /**
     * 大于等于号 great than or equal "{@code &gt;=}"
     */
    GET,
    /**
     * 小于号 less than "{@code &lt;}"
     */
    LT,
    /**
     * 小于等于号 less than or equal "{@code &lt;=}"
     */
    LET,
    /**
     * 等于号 equal "{@code ==}"
     */
    EQ,
    /**
     * 不等号 not equal "{@code !=}"
     */
    NEQ,
    /**
     * 全等 congruent "{@code ===}"
     */
    CEQ,
    /**
     * 非全等 not congruent "{@code !==}"
     */
    NCEQ,
    /**
     * 加号 "{@code +}"
     */
    PLUS,
    /**
     * 自增 Increment Operator "{@code ++}"
     */
    INCSELF,
    /**
     * 减号 "{@code -}"
     */
    MINUS,
    /**
     * 自减 Decrement Operato "{@code --}"
     */
    DECSELF,
    /**
     * 乘号 Multiplication "{@code *}"
     */
    MULTIPLY,
    /**
     * 除号 "{@code /}"
     */
    DIV,
    /**
     * 取模 "{@code %}"
     */
    MOD,
    /**
     * 未知类型
     */
    UNDEFINED,
    /**
     *  常量
     */
    /**
     * 真 true "{@code true}"
     */
    TRUE,
    /**
     *  假 false "{@code false}"
     */
    FALSE,
    /**
     *  空 null "{@code null}"
     */
    NULL,
    /**
     *  Map Key
     */
    MAP_KEY,
    /**
     *  Map VALUE
     */
    MAP_VALUE,
    /**
     * 特殊符号
     */
    /**
     * 错误静默 SILENCE "{@code @}"
     */
    SILENCE,
    /**
     * 逻辑或（竖线） vertical bar "{@code ||}"
     */
    OR,
    /**
     * 修饰，按位或 vertical bar "{@code |}"
     */
    VBAR,
    /**
     * 且 and "{@code &&}"
     */
    AND,
    /**
     * 按位与 "{@code &}"
     */
    BAND,
    /**
     * 逻辑异或  xor "{@code XOR}"
     */
    XOR,
    /**
     * 按位异或 "{@code ^}"
     */
    BXOR,
    /**
     * 按位非  NOT "{@code ~}"
     */
    BNOT,
    /**
     * 按位左移   Shift left  " {@code <<}"
     */
    BSLEFT,
    /**
     * 按位右移   Shift left  "{@code  >>}"
     */
    BSRIGHT,
    /**
     * 按位无符右移   Shift left  "{@code  >>>}"
     */
    BSURIGHT,
}
