/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 21:19:23
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
 * <pre>
 * ZTP = Zoeey Template Plugin - function
 * 为了避免编辑工具提示感染
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZTPF_JsonEncode implements FunctionAble {

    public String call(ParamsMap params, Ztpl tpl) {
        String as = params.getZObject("as").toString();
        System.out.println("=====json encode=====");
        System.out.println(JsonHelper.encode(params));
        System.out.println("-----/json encode-----");
        if (as.length() == 0) {
            return JsonHelper.encode(params.get("var"));
        } else {
            tpl.assign(as, JsonHelper.encode(params.get("var")));
            return "";
        }
    }
}
