/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.constant;

import org.zoeey.util.EnvInfo;

/**
 * 系统常量
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface EnvConstants {

    /**
     *  默认全局字符集
     */
    public static final String CHARSET = "UTF-8";
    /**
     * 调试模式开关
     */
    public static final boolean IS_DEBUGING = true;
    /**
     * 防循环锁 跳出条件（循环次数，尽量只在调试模式中使用）
     */
    public static final int FORCE_JUMPER = 50000;
    /**
     * 文本读取缓冲大小 byte
     */
    public static final int BUFFER_BYTE_SIZE = 8192;
    /**
     * 文本读取缓冲大小 char
     */
    public static final int BUFFER_CHAR_SIZE = BUFFER_BYTE_SIZE;
    /**
     * 并发容器默认容量
     */
    public static final int CONCURRENT_CAPACITY_SIZE = 500;
    /**
     * locker SLEEP_UNIT_TIME / MICROSECONDS
     */
    public static final int LOCKER_SLEEP_UNIT_TIME = 20;
    /**
     * locker SLEEP_TIMEOUT / MILLISECONDS
     */
    public static final int LOCKER_SLEEP_TIMEOUT = 10000;
    /**
     * 默认临时文件目录
     */
    public static final String DEFAULT_TEMP_DIR = EnvInfo.getJavaIoTmpdir();
    /**
     * 默认文件上传最大尺寸(单位：字节 总量：8兆)
     */
    public static final int DEFAULT_UPLOAD_FILEMAXSIZE = 8 * 1024 * 1024;
    /**
     * 默认文件上传总大小限制（128兆）
     */
    public static final int DEFAULT_UPLOAD_CONTENTLENGTHLIMIT = 128 * 1024 * 1024;
}
