/*
 * MoXie (SysTem128@GMail.Com) 2009-12-31 16:51:31
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.handler;

/**
 * 异常处理器
 * @author MoXie
 */
public class ExceptionHandler {

    private static ExceptionHandleAble handler = null;

    /**
     * 获取异常处理器
     * @return  异常处理器
     */
    public static ExceptionHandleAble newHandler() {

        synchronized (ExceptionHandler.class) {
            if (handler == null) {
                handler = new DefaultExceptionHandler();
            }
            return handler.newHandler();
        }
    }

    /**
     * 设置当前异常处理器
     * @param handler   异常处理器
     */
    public static void setHandler(ExceptionHandleAble handler) {
        synchronized (ExceptionHandler.class) {
            ExceptionHandler.handler = handler;
        }
    }
}
