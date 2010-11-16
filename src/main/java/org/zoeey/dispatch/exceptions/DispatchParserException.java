/*
 * MoXie (SysTem128@GMail.Com) 2009-8-6 15:04:05
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.dispatch.exceptions;

/**
 *
 * @author MoXie
 */
public class DispatchParserException extends Exception {


    /**
     * Creates a new instance of <code>NoRouterEntyClassException</code> without detail message.
     */
    public DispatchParserException() {
    }

    /**
     * Constructs an instance of <code>NoRouterEntyClassException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DispatchParserException(String msg) {
        super("${" + msg + "}");
    }

    /**
     *
     * @param msg
     * @param className
     */
    public DispatchParserException(String msg, String className) {
        super("${" + msg + "}" + className);
    }
}
