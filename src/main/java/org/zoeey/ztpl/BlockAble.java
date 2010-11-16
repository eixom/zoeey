/*
 * MoXie (SysTem128@GMail.Com) 2009-5-1 3:09:26
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl;

/**
 * 块函数接口
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface BlockAble {

    /**
     * 注册块函数开始
     * @param params  模板参数
     * @param tpl 模板引擎对象
     */
    public void start(ParamsMap params, Ztpl tpl);

    /**
     * 注册块函数结束
     * @param params  模板参数
     * @param tpl 模板引擎对象
     * @return 是否结束 （切勿返回 常量 true）
     */
    public boolean end(ParamsMap params, Ztpl tpl);
}
