/*
 * MoXie (SysTem128@GMail.Com) 2009-6-16 16:34:52
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo.exceptions;

import java.sql.SQLException;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZDOException extends SQLException {

    /**
     * Creates a new instance of <code>SQLHelperNoFieldException</code> without detail message.
     */
    public ZDOException() {
    }

    /**
     * Constructs an instance of <code>PublishClassNoFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ZDOException(String msg) {
        super("${" + msg + "}");
    }

    /**
     *
     * @param msg
     * @param className
     */
    public ZDOException(String msg, String className) {
        super("${" + msg + "}" + className);
    }
}
