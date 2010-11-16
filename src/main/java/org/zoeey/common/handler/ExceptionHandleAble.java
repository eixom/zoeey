/*
 * MoXie (SysTem128@GMail.Com) 2009-10-29 10:19:52
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.handler;

import java.util.logging.Level;

/**
 * 异常处理器接口
 * @author MoXie
 */
public interface ExceptionHandleAble {

    /**
     * 新建处理器
     * @return
     */
    public ExceptionHandleAble newHandler();

    /**
     * 异常对象
     * @param cause 异常
     * @return
     */
    public ExceptionHandleAble setCause(Throwable cause);

    /**
     * 仅异常信息
     * @param message 信息
     * @return
     */
    public ExceptionHandleAble setMessage(String message);

    /**
     * 异常等级
     * @param level
     * @return
     */
    public ExceptionHandleAble setLevel(Level level);

    /**
     * 异常等级
     * @param level
     * @return
     */
    public ExceptionHandleAble setLevel(String level);

    /**
     * 异常记录名称
     * @param logName
     * @return
     */
    public ExceptionHandleAble setLogName(String logName);

    /**
     * 异常细节
     * @param parts
     * @return
     */
    public ExceptionHandleAble setParts(Object[] parts);

    /**
     * 异常对象
     * @return
     */
    public Throwable getCause();

    /**
     * 异常等级
     * @return
     */
    public Level getLevel();

    /**
     * 异常记录名称
     * @return
     */
    public String getLogName();

    /**
     * 异常信息
     * @return
     */
    public String getMessage();

    /**
     * 异常细节
     * @return
     */
    public Object[] getParts();

    /**
     * 处理
     */
    public void handle();
}
