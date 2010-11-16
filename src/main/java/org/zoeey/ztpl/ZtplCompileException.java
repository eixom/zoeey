/*
 * MoXie (SysTem128@GMail.Com) 2009-8-22 19:35:47
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl;

/**
 *
 * @author MoXie
 */
public class ZtplCompileException extends Exception {

    /**
     * Creates a new instance of <code>ZTPLCompileException</code> without detail message.
     */
    public ZtplCompileException() {
    }

    /**
     * Constructs an instance of <code>ZTPLCompileException</code> with the specified detail message.
     * @param msg the detail message.
     * @param code
     * @param line
     * @param pos 
     */
    public ZtplCompileException(String msg, String code, int line, int pos) {
        super(msg + "line:" + line + " char:" + pos + " code:" + code);
    }
}
