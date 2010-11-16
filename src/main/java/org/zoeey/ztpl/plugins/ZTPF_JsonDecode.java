/*
 * MoXie (SysTem128@GMail.Com) 2009-8-21 10:15:04
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.plugins;

import org.zoeey.util.JsonHelper;
import org.zoeey.ztpl.FunctionAble;
import org.zoeey.ztpl.ParamsMap;
import org.zoeey.ztpl.Ztpl;

/**
 *
 * @author MoXie
 */
public class ZTPF_JsonDecode implements FunctionAble {

    public String call(ParamsMap params, Ztpl tpl) {
        tpl.assign(params.getZObject("var").toString(), JsonHelper.decode(params.getZObject("str").toString()));
        return "";
    }
}
