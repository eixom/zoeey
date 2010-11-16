/*
 * MoXie (SysTem128@GMail.Com) 2009-5-2 16:40:43
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.plugins;

import org.objectweb.asm.Opcodes;
import org.zoeey.ztpl.compiler.CompileAble;
import org.zoeey.ztpl.Ztpl;
import org.zoeey.ztpl.compiler.ByteCodeHelper;

/**
 * ZTP = Zoeey Template Plugin - compile
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZTPC_If implements CompileAble, Opcodes {

    public void call(ByteCodeHelper helper, Ztpl tpl) {
        helper.writeStr("/* if statement start */");
        helper.writeStr("/* /if statement end */");
    }
}
