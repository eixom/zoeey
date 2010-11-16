/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 21:58:11
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.plugins;

import org.zoeey.common.ZObject;
import org.zoeey.util.TimeFormatHelper;
import org.zoeey.ztpl.ModifierAble;
import org.zoeey.ztpl.ParamsMap;
import org.zoeey.ztpl.Ztpl;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZTPM_Dateformat implements ModifierAble {

     
    public Object registerModifier(Object val, ParamsMap params, Ztpl tpl) {
        String pattern = ZObject.conv(params.get("pattern")).toString();
        if (pattern.length() < 1) {
            pattern = TimeFormatHelper.DATE_MYSQL;
        }
        return TimeFormatHelper.format(ZObject.conv(val).toLong(), pattern);
    }
}
