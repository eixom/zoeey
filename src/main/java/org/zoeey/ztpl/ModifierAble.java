/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 21:41:18
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl;

/**
 * 修饰器接口
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface ModifierAble {

    /**
     * 注册修饰器
     * @param val   源变量
     * @param params    模板参数
     * @param tpl   模板引擎对象
     * @return  修饰后的变量
     */
    public Object registerModifier(Object val, ParamsMap params, Ztpl tpl);
}
