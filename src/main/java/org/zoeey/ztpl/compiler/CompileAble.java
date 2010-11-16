/*
 * MoXie (SysTem128@GMail.Com) 2009-5-3 0:14:12
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import org.zoeey.ztpl.Ztpl;

/**
 * 编译插件接口
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface CompileAble {

    /**
     * 注册编译函数
     * @param ct
     * @param tpl
     */
    void call(ByteCodeHelper helper, Ztpl tpl);
}
