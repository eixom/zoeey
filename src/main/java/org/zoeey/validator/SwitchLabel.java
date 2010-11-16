/*
 * MoXie (SysTem128@GMail.Com) 2009-7-27 10:56:51
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.validator;

/**
 * 验证器结合类型
 * @author MoXie
 */
public enum SwitchLabel {

    /**
     * 空字段断言
     */
    ALLOWNULL_ASSERT,
    /**
     * 断言
     * 验证失败后直接跳出
     */
    ASSERT,
    /**
     * 连接
     * 与其他证器合并使用
     */
    JOIN
}
