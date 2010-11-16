/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 21:31:45
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl;

/**
 *  扩展函数接口
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface FunctionAble {

    /**
     * 注册函数
     * @param params   模板参数
     * @param tpl  模板引擎对象
     * @return  返回的字符串将被直接输出
     */
    public String call(ParamsMap params, Ztpl tpl);
}
