/*
 * MoXie (SysTem128@GMail.Com) 2010-1-3 4:37:40
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.handler;

import java.util.logging.Level;

/**
 * 异常处理器基类
 * @author MoXie
 */
public abstract class ExceptionHandleBase implements ExceptionHandleAble {

    /**
     * 异常描述
     */
    protected String message = null;
    /**
     * 异常对象
     */
    protected Throwable cause = null;
    /**
     * 异常等级
     */
    protected Level level = null;
    /**
     * 记录名称
     */
    protected String logName = null;
    /**
     * 异常细节
     */
    protected Object[] parts = null;

    /**
     * 异常对象
     * @param cause 异常
     * @return  
     */
    public ExceptionHandleAble setCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    /**
     * 异常信息
     * @param message 信息
     * @return
     */
    public ExceptionHandleAble setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * 异常等级
     * @param level
     * @return
     */
    public ExceptionHandleAble setLevel(Level level) {
        this.level = level;
        return this;
    }

    /**
     * 异常等级
     * @param level
     * @return
     */
    public ExceptionHandleAble setLevel(String level) {
        this.level = Level.parse(level);
        return this;
    }

    /**
     * 异常细节
     * @param parts
     * @return
     */
    public ExceptionHandleAble setParts(Object[] parts) {
        this.parts = parts;
        return this;
    }

    /**
     * 异常记录名称
     * @param logName
     * @return
     */
    public ExceptionHandleAble setLogName(String logName) {
        this.logName = logName;
        return this;
    }

    /**
     * 异常对象
     * @return
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * 异常等级
     * @return
     */
    public Level getLevel() {
        return level;
    }

    /**
     * 异常记录名称
     * @return
     */
    public String getLogName() {
        return logName;
    }

    /**
     * 异常信息
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * 异常细节
     * @return
     */
    public Object[] getParts() {
        return parts;
    }
}
