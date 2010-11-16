/*
 * MoXie (SysTem128@GMail.Com) 2009-8-16 11:09:14
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.exceptions;

/**
 *
 * @author MoXie
 */
public class DAOBindBeanException extends Exception {

    /**
     * Creates a new instance of <code>DAOBindBeanException</code> without detail message.
     */
    public DAOBindBeanException() {
    }

    /**
     * Constructs an instance of <code>PublishClassNoFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DAOBindBeanException(String msg) {
        super("${" + msg + "}");
    }

    /**
     *
     * @param msg
     * @param className
     */
    public DAOBindBeanException(String msg, String className) {
        super("${" + msg + "}" + className);
    }

    /**
     *
     * @param msg
     * @param cause
     */
    public DAOBindBeanException(String msg, Throwable cause) {
        super("${" + msg + "}" , cause);
    }
}
