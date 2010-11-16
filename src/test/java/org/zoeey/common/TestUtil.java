/*
 * MoXie (SysTem128@GMail.Com) 2009-9-5 19:19:18
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common;

import org.junit.Test;
import org.zoeey.util.UrlHelper;

/**
 *
 * @author MoXie
 */
public class TestUtil {

    /**
     * 锁定创建
     */
    public TestUtil() {
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testAno() {
        System.out.println("这个类仅作工具使用，不属于测试类。");
    }

    /**
     * 获取 bug.png 地址 <br />
     * bug.png
     * @return
     */
    public static String getBugPng() {
        return UrlHelper.decode(ClassLoader.getSystemResource("bug.png").getFile());
    }

    /**
     * 获取资源
     * @param name  资源名（例如：bug.png）
     * @return  资源未找到时返回 null
     */
    public static String getResource(String name) {
        return UrlHelper.decode(ClassLoader.getSystemResource(name).getFile());
    }

    /**
     *  获取资源路径
     * @return
     */
    public static String getResourceDir() {
        return UrlHelper.decode(ClassLoader.getSystemResource("").getFile());
    }

    /**
     * 获取需要压缩的文件列表
     * @return
     */
    public static String getZipFilesDir() {
        return UrlHelper.decode(ClassLoader.getSystemResource("zip/files").getFile());
    }

    /**
     * 获取压缩文件目录
     * @return
     */
    public static String getZipDir() {
        return UrlHelper.decode(ClassLoader.getSystemResource("zip/zip").getFile());
    }

    /**
     * 获取压缩文件
     * @param name  压缩文件名
     * @return
     */
    public static String getZipFile(String name) {
        return UrlHelper.decode(ClassLoader.getSystemResource("zip/zip").getFile().concat(name));
    }

    /**
     *  获取解压目录
     * @return
     */
    public static String getUnZipDir() {
        return UrlHelper.decode(ClassLoader.getSystemResource("zip/unzip").getFile());
    }
}
