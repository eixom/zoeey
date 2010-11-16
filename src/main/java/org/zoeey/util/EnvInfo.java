/*
 * MoXie (SysTem128@GMail.Com) 2009-1-18 23:35:09
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import org.zoeey.constant.EnvConstants;

/**
 * System Properties
 * 注意：属性不存在时返回 null
 * @author MoXie(SysTem128@GMail.Com)
 */
public class EnvInfo {

    private EnvInfo() {
        /**
         * 锁定创建
         */
    }

    /**
     * Java 运行时环境版本
     * @return
     */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Java 运行时环境供应商
     * @return
     */
    public static String getJavaVendor() {
        return System.getProperty("java.vendor");
    }

    /**
     * Java 供应商的 URL
     * @return
     */
    public static String getJavaVendorUrl() {
        return System.getProperty("java.vendor.url");
    }

    /**
     * Java 安装目录
     * @return
     */
    public static String getJavaHome() {
        return System.getProperty("java.home");
    }

    /**
     * Java 虚拟机规范版本
     * @return
     */
    public static String getJavaVMSpecificationVersion() {
        return System.getProperty("java.vm.specification.version");
    }

    /**
     * Java 虚拟机规范供应商
     * @return
     */
    public static String getJavaVMSpecificationVendor() {
        return System.getProperty("java.vm.specification.vendor");
    }

    /**
     * Java 虚拟机规范名称
     * @return
     */
    public static String getJavaVMSpecificationName() {
        return System.getProperty("java.vm.specification.name");
    }

    /**
     * Java 虚拟机实现版本
     * @return
     */
    public static String getJavaVMVersion() {
        return System.getProperty("java.vm.version");
    }

    /**
     * Java 虚拟机实现供应商
     * @return
     */
    public static String getJavaVMVendor() {
        return System.getProperty("java.vm.vendor");
    }

    /**
     * Java 虚拟机实现名称
     * @return
     */
    public static String getJavaVMName() {
        return System.getProperty("java.vm.name");
    }

    /**
     * Java 运行时环境规范版本
     * @return
     */
    public static String getJavaSpecificationVersion() {
        return System.getProperty("java.specification.version");
    }

    /**
     * Java 运行时环境规范供应商
     * @return
     */
    public static String getJavaSpecificationVendor() {
        return System.getProperty("java.specification.vendor");
    }

    /**
     * Java 运行时环境规范名称
     * @return
     */
    public static String getJavaSpecificationName() {
        return System.getProperty("java.specification.name");
    }

    /**
     * Java 类格式版本号
     * @return
     */
    public static String getJavaClassVersion() {
        return System.getProperty("java.class.version");
    }

    /**
     * Java 类路径
     * @return
     */
    public static String getJavaClassPath() {
        return System.getProperty("java.class.path");
    }

    /**
     * 加载库时搜索的路径列表
     * @return
     */
    public static String getJavaLibraryPath() {
        return System.getProperty("java.library.path");
    }

    /**
     * 默认的临时文件路径
     * @return
     */
    public static String getJavaIoTmpdir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 默认的临时文件路径
     * @return
     * @see #getJavaIoTmpdir()
     */
    public static String getTempDir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 要使用的 JIT 编译器的名称
     * @return
     */
    public static String getJavaCompiler() {
        return System.getProperty("java.compiler");
    }

    /**
     * 一个或多个扩展目录的路径
     * @return
     */
    public static String getJavaExtDirs() {
        return System.getProperty("java.ext.dirs");
    }

    /**
     * 操作系统的名称
     * @return
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * 操作系统的架构
     * @return
     */
    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    /**
     * 操作系统的版本
     * @return
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * 文件分隔符（在 UNIX 系统中是“/”）
     * @return
     */
    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    /**
     * 路径分隔符（在 UNIX 系统中是“:”）
     * @return
     */
    public static String getPathSeparator() {
        return System.getProperty("path.separator");
    }

    /**
     * 行分隔符（在 UNIX 系统中是“\n”）
     * @return
     */
    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    /**
     * 用户的账户名称
     * @return
     */
    public static String getUserName() {
        return System.getProperty("user.name");
    }

    /**
     * 用户的主目录
     * @return
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * 用户的当前工作目录
     * @return
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     * 文件编码
     * @return
     */
    public static String getFileEncoding() {
        return System.getProperty("file.encoding", EnvConstants.CHARSET);
    }

    /**
     * 用户语言代码
     * @return
     */
    public static String getLanguage() {
        return System.getProperty("user.language");
    }
}
