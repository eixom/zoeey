/*
 * MoXie (SysTem128@GMail.Com) 2010-1-21 10:23:00
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

/**
 * 标识
 * @author MoXie
 */
public class Token {

    /**
     * 类型
     */
    private TokenType type = null;
    /**
     * 数据
     */
    private String words = null;
    /**
     * 优先级
     */
    private int precedence = 0;
    /**
     * 结合规则
     */
    private AssocType assoc = null;
    /**
     * 参数个数/目数
     */
    private int opcont = 0;

    /**
     * 标识
     * @param type  类型
     * @param words  词组
     * @param operatorCount 目数（参与操作参数个数如：unaru,binaru,ternary operator...）
     * @param precedence 操作符优先级
     * @param assoc 结合规则
     * @param opcount 参数个数
     */
    Token(TokenType type, String words, int precedence, AssocType assoc, int opcont) {
        this.type = type;
        this.words = words;
        this.precedence = precedence;
        this.assoc = assoc;
        this.opcont = opcont;
    }

    /**
     * 标识
     * @param type  类型
     * @param words  词组
     * @param operatorCount 目数（参与操作参数个数如：unaru,binaru,ternary operator...）
     * @param precedence 操作符优先级
     * @param assoc 结合规则
     */
    Token(TokenType type, String words, int precedence, AssocType assoc) {
        this(type, words, precedence, assoc, 2);
    }

    Token(TokenType type, String words, int precedence) {
        this(type, words, precedence, AssocType.LEFT, 2);
    }

    public int getPrecedence() {
        return precedence;
    }

    public TokenType getType() {
        return type;
    }

    public String getWords() {
        return words;
    }

    public AssocType getAssoc() {
        return assoc;
    }

    public int getOpcont() {
        return opcont;
    }
}
