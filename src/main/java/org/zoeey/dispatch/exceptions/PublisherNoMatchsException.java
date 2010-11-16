/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.dispatch.exceptions;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class PublisherNoMatchsException extends Exception {


    /**
     * Creates a new instance of <code>PublishClassNoFoundException</code> without detail message.
     */
    public PublisherNoMatchsException() {
    }

    /**
     * Constructs an instance of <code>PublishClassNoFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public PublisherNoMatchsException(String msg) {
        super("${" + msg + "}");
    }

    /**
     *
     * @param msg
     * @param className
     */
    public PublisherNoMatchsException(String msg, String className) {
        super("${" + msg + "}" + className);
    }
}
