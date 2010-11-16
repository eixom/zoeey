/*
 * MoXie (SysTem128@GMail.Com) 2009-12-31 16:35:51
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.handler;

import java.util.logging.Logger;

/**
 * 默认的异常处理器
 * @author MoXie
 */
public class DefaultExceptionHandler
        extends ExceptionHandleBase {

    /**
     * 新建处理器
     * @return
     */
    public ExceptionHandleAble newHandler() {
        return new DefaultExceptionHandler();
    }

    /**
     * 处理
     */
    public void handle() {
        Logger logger = null;
        if (logName != null) {
            logger = Logger.getLogger(logName);
        } else {
            logger = Logger.getAnonymousLogger();
        }
        logger.log(level, String.format(message, parts), cause);
    }
}
