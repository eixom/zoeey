/*
 * MoXie (SysTem128@GMail.Com) 2009-12-31 16:35:51
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.handler;

import org.zoeey.util.EnvInfo;

/**
 * 简单的异常处理器
 * @author MoXie
 */
public class SimpleExceptionHandler
        extends ExceptionHandleBase {

    /**
     * 处理
     */
    public void handle() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("%s : %s ", logName, level));
        strBuilder.append(EnvInfo.getLineSeparator());
        if (message != null) {
            strBuilder.append(String.format(message, parts));
            strBuilder.append(EnvInfo.getLineSeparator());
        }
        System.out.println(strBuilder.toString());
        if (cause != null) {
            cause.printStackTrace();
        }
    }

    public ExceptionHandleAble newHandler() {
        return new SimpleExceptionHandler();
    }
}
