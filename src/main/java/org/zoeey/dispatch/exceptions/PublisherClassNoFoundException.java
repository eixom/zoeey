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
public class PublisherClassNoFoundException extends Exception {

    private static final long serialVersionUID = 1616828885119618835L;

    /**
     * Creates a new instance of <code>NoRouterEntyClassException</code> without detail message.
     */
    public PublisherClassNoFoundException() {
    }

    /**
     * Constructs an instance of <code>NoRouterEntyClassException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public PublisherClassNoFoundException(String msg) {
        super("${" + msg + "}");
    }

    /**
     *
     * @param msg
     * @param className
     */
    public PublisherClassNoFoundException(String msg, String className) {
        super("${" + msg + "}" + className);
    }
}
