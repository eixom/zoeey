/*
 * MoXie (SysTem128@GMail.Com) 2009-7-27 10:03:07
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.exceptions;

/**
 *
 * @author MoXie
 */
public class LoaderException extends Exception {

    /**
     * Creates a new instance of <code>InvalidLoadArgumentException</code> without detail message.
     */
    public LoaderException() {
    }

    /**
     * Constructs an instance of <code>LoadMethodsException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public LoaderException(String msg) {
        super("${" + msg + "}");
    }

    /**
     *
     * @param msg
     * @param className
     */
    public LoaderException(String msg, String className) {
        super("${" + msg + "}" + className);
    }
}
