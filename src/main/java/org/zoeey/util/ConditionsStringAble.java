/*
 * MoXie (SysTem128@GMail.Com) 2009-4-17 16:00:53
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface ConditionsStringAble {

    /**
     * 激活条件
     * @param condition
     */
    void active(String condition);

    /**
     * 取消激活条件
     * @param condition
     */
    public void cancel(String condition);

    /**
     * 更新激活条件
     * 
     * @param oldCondition
     * @param newCondition
     */
    public void update(String oldCondition, String newCondition);

    /**
     * 移除所有激活条件
     */
    public void clear();

    /**
     * 增加条件与结果项
     * @param condition
     * @param str
     */
    void put(String condition, String str);

    /**
     * 从文件分析条件和结果项
     * @see #fromString(java.lang.String)
     * @param file
     * @throws java.io.IOException
     */
    void fromFile(File file) throws IOException;

    /**
     * <pre>
     * 从字符串中分析条件和对应字符串。
     * 条件名使用'&#64;'开头、'='号结尾，可包含数字、字母、减号和下划线。
     * ex.:
     * &#64;name="MoXie" is
     * my name. &#64;email=SysTem128&#64;gmail.com
     * </pre>
     * @param conditionText
     */
    void fromString(String conditionText);

    /**
     * 将结果转换为字符串
     * @return
     */
    @Override
    String toString();
}
