/*
 * MoXie (SysTem128@GMail.Com) 2010-9-3 10:55:09
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: Apache License  Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.zoeey.ztpl.compiler;

/**
 * 结合规则类型
 * @author MoXie
 */
public enum AssocType {

    /**
     * left to right
     * + - * / % 
     */
    LEFT,
    /**
     * = ! 
     * right to left
     */
    RIGHT,
    /**
     * ++i --i
     * i++ i--
     * right to left
     */
    BOTH
}
