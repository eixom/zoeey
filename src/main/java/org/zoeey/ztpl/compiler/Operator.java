/*
 * MoXie (SysTem128@GMail.Com) 2009-9-7 10:43:20
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/gpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

/**
 *
 * @author MoXie
 */
public enum Operator {

    /**
     * 取否
     */
    NOT("!", 9, 1),
    /**
     * 取负
     */
    NG("-", 9, 1),
    /**
     * 自增
     */
    INCSELF("++", 9, 1),
    /**
     * 自减
     */
    DECSELF("--", 9, 1),
    /**
     * 乘
     */
    MULTIPLY("*", 8, 2),
    /**
     * 除
     */
    DIV("/", 8, 2),
    /**
     * 取模
     */
    MOD("%", 7, 2),
    /**
     * 加
     */
    PLUS("+", 7, 2),
    /**
     * 算术减
     */
    MINUS("-", 7, 2),
    /**
     * 小于
     */
    LT("<", 6, 2),
    /**
     * 小等于
     */
    LE("<=", 6, 2),
    /**
     * 大于
     */
    GT(">", 6, 2),
    /**
     * 大等于
     */
    GE(">=", 6, 2),
    /**
     * 等
     */
    EQ("==", 5, 2),
    /**
     * 全等
     */
    CEQ("===", 5, 2),
    /**
     * 不等
     */
    NEQ("!=", 5, 2),
    /**
     * 非全等
     */
    NCEQ("!==", 5, 2),
    /**
     * 与
     */
    AND("&&", 4, 2),
    /**
     * 或
     */
    OR("||", 3, 2),
    /**
     * 映射表
     */
    MAP("{", 2, 2),
    /**
     * 列表
     */
    LIST("[", 2, 2),
    /**
     * 三目
     */
    CASE("?", 1, 0),
    SECTION(":", 1, 0);
    /**
     * 标志符
     */
    private String token;
    /**
     * 优先级
     */
    private int precedence;
    /**
     * 类型
     */
    private int type;

    /**
     * 操作符
     * @param token 标志符
     * @param precedence  优先级
     * @param type  类型
     */
    Operator(String token, int precedence, int type) {
        this.token = token;
        this.precedence = precedence;
        this.type = type;
    }

    /**
     * 优先级
     * @return
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * 标志符
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * 类型
     * @return 
     */
    public int getType() {
        return type;
    }
}
