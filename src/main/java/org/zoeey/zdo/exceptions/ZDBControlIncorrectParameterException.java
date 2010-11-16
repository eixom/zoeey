/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
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
public class ZDBControlIncorrectParameterException extends SQLException {

    /**
     * Creates a new instance of <code>SQLHelperNoFieldException</code> without detail message.
     */
    public ZDBControlIncorrectParameterException() {
    }

    /**
     * Constructs an instance of <code>PublishClassNoFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ZDBControlIncorrectParameterException(String msg) {
        super("${" + msg + "}");
    }

    /**
     *
     * @param msg
     * @param className
     */
    public ZDBControlIncorrectParameterException(String msg, String className) {
        super("${" + msg + "}" + className);
    }
}
