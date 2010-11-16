/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 22:14:50
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.plugins;

import org.zoeey.ztpl.ModifierAble;
import org.zoeey.ztpl.ParamsMap;
import org.zoeey.ztpl.Ztpl;

/**
 * 设置默认值
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZTPM_Default implements ModifierAble {

    public Object registerModifier(Object val, ParamsMap params, Ztpl tpl) {
        if (val == null) {
            return params.get("value");
        }
        return val;
    }
}
