/*
 * MoXie (SysTem128@GMail.Com) 2009-8-27 17:17:33
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.util.List;
import java.util.Stack;

/**
 * 表达式解析，负责从表达式中析出参数与操作
 *  {@link http://en.wikipedia.org/wiki/Shunting-yard_algorithm }
 * @author MoXie
 */
public class Parser {

    public Stack<Token> getStack() {
        Overlooker looker = new Overlooker("haha");
        Lexer lexer = new Lexer("$str=str_replace($f-$b*$c+$d,!$e,$g)", looker);
        lexer.parse();
        TokenType type;
        Token current;
        Stack<Token> stack = new Stack<Token>();
        Stack<Token> outStack = new Stack<Token>();
        boolean pe;
        for (Token token : lexer.getTokenStack()) {
            type = token.getType();
            switch (type) {
                case VAR:
                case NUMBER:
                    outStack.add(token);
                    break;
                case FUNCTION:
                    stack.add(token);
                    break;
                case COMMA:
                    pe = false;
                    while (!stack.empty()) {
                        current = stack.lastElement();
                        if (current.getType() == TokenType.LPAREN) {
                            pe = true;
                            break;
                        } else {
                            outStack.add(current);
                            stack.pop();
                        }
                    }
                    if (!pe) {
                        looker.disPass(55);
                        return null;
                    }
                    break;
                case PLUS:
                case MINUS:
                case MULTIPLY:
                case DIV:
                case MOD:
                case ASSIGN:
                case NOT:
                    while (!stack.empty()) {
                        current = stack.lastElement();
                        type = current.getType();
                        if (!isOperator(type)) {
                            break;
                        }
                        if ((current.getAssoc() == AssocType.LEFT//
                                && (token.getPrecedence() <= current.getPrecedence()))//
                                ||//
                                (current.getAssoc() == AssocType.RIGHT//
                                && (token.getPrecedence() < current.getPrecedence()))) {
                            outStack.add(current);
                            stack.pop();
                        } else {
                            break;
                        }
                    }
                    stack.add(token);
                    break;
                case LPAREN:
                    stack.add(token);
                    break;
                case RPAREN:
                    pe = false;
                    while (!stack.empty()) {
                        current = stack.lastElement();
                        if (current.getType() == TokenType.LPAREN) {
                            pe = true;
                            break;
                        } else {
                            outStack.add(current);
                            stack.pop();
                        }
                    }
                    if (!pe) {
                        looker.disPass(90);
                        return null;
                    }
                    stack.pop();
                    if (!stack.empty()) {
                        current = stack.lastElement();
                        if (current.getType() == TokenType.FUNCTION) {
                            outStack.add(current);
                            stack.pop();
                        }
                    }
                    break;
                default:
                    looker.disPass(112);
                    return null;
            }
        }
        while (!stack.empty()) {
            current = stack.pop();
            type = current.getType();
            if (type == TokenType.LPAREN || type == TokenType.RPAREN) {
                looker.disPass(108);
                return null;
            }
            outStack.add(current);
        }
        printStack(outStack);
        return outStack;
    }

    private void printStack(List<Token> tokenStack) {
        for (Token token : tokenStack) {
            System.out.print(token.getWords());
            System.out.print(" ");
        }
    }

    public static boolean isOperator(TokenType type) {
        switch (type) {
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case DIV:
            case MOD:
            case ASSIGN:
            case NOT:
                return true;
            default:
                return false;
        }
    }
}
