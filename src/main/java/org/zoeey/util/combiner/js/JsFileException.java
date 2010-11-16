/*
 * MoXie (SysTem128@GMail.Com) 2009-3-20 7:21:38
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner.js;

/**
 *  文件名析出异常 文件名为空
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsFileException extends Exception {


    /**
     * Creates a new instance of <code>PublishClassNoFoundException</code> without detail message.
     */
    public JsFileException() {
    }

    /**
     * Constructs an instance of <code>PublishClassNoFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public JsFileException(String msg) {
        super("${" + msg + "}");
    }

    /**
     *
     * @param msg
     * @param className
     */
    public JsFileException(String msg, String className) {
        super("${" + msg + "}" + className);
    }
}
