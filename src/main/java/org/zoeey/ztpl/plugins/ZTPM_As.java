/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 22:24:23
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
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZTPM_As implements ModifierAble {

    /**
     *
     * @param val
     * @param params
     * @param tpl
     * @return
     */
    public Object registerModifier(Object val, ParamsMap params, Ztpl tpl) {
        tpl.assign(params.getZObject("value").toString(), val);
        return val;
    }
}
