/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.net.URL;
import javax.servlet.ServletContext;

/**
 * 目录相关信息
 * @author MoXie(SysTem128@GMail.Com)
 */
public class DirInfo {

    /**
     * 锁定创建
     */
    private DirInfo() {
    }
    /**
     * classes 目录
     */
    private static String classesDir = null;
    /**
     * 设定部署目录
     */
    private static String deployDir = null;

    static {
        classesDir = initClassesDir();
    }

    /**
     * 设定部署目录。当Web容器为glassfish时，可能获取不到类路径。需要设定部署目录。
     * @param context
     */
    public static void setDeployDir(ServletContext context) {
        synchronized (DirInfo.class) {
            classesDir = null;
            deployDir = context.getRealPath("/");
            classesDir = initClassesDir();
        }
    }

    /**
     * 初始化Classes目录
     * @return  路径 使用 / 分隔，但不以 / 结尾。
     */
    private static String initClassesDir() {
        String classRoot = null;
        do {
            /**
             * unix:resin
             */
            URL webInfoURL = DirInfo.class.getResource(".");
            if (webInfoURL != null) {
                classRoot = webInfoURL.toString();
                classRoot = classRoot.replaceFirst("org.zoeey.util", "");
                break;
            }
            /**
             * tomcat
             */
            URL url = Thread.currentThread().getContextClassLoader().getResource("/");
            if (url != null) {
                classRoot = url.getPath();
            }
            /**
             * glassfish
             */
            if (classRoot == null || classRoot.indexOf("classes") == -1) {
                url = DirInfo.class.getClassLoader().getResource("/");
                if (url != null) {
                    classRoot = url.getPath();
                }
            }
            if ((classRoot == null || classRoot.indexOf("classes") == -1) && deployDir != null) {
                classRoot = deployDir.concat("/WEB-INF/classes");
            }
        } while (false);
        if (classRoot != null) {
            classRoot = FileHelper.backToslash(classRoot.replaceFirst("^file:", ""));
            /**
             * fixedbug:
             * linux 需要 / 前缀
             * windows 需要去掉 / 前缀（避免替换绝对路径时出错）
             */
            if ("linux".equalsIgnoreCase(EnvInfo.getOsName())) {
                classRoot = StringHelper.rtrim(classRoot, new char[]{'/'});
            } else {
                classRoot = StringHelper.trim(classRoot, new char[]{'/'});
            }
        }
        classRoot = UrlHelper.decode(classRoot);
        return classRoot;
    }

    /**
     * 获取classes的目录
     * @return  路径 使用 / 分隔，但不以 / 结尾。
     */
    public static String getClassesDir() {
        return classesDir;
    }

    /**
     * 获取WEB-INF或build目录
     * 仅当classes文件在WEB-INF目录下时。
     * @return  路径 使用 / 分隔，但不以 / 结尾。
     */
    public static String getWebInfoDir() {
        String classRoot = getClassesDir();
        if (classRoot != null) {
            classRoot = classRoot.replaceFirst("/classes$", "");
        }
        return classRoot;
    }

    /**
     * 获取WEB-INF或build目录下的文件
     * 仅当classes文件在WEB-INF目录下时。
     * @param fileName
     * @return  路径 使用 / 分隔，但不以 / 结尾。
     */
    public static String getWebInfoFile(String fileName) {
        return getWebInfoDir().concat(fileName);
    }

    /**
     * 获取web发布目录
     * 仅当目录层次为 web/WEB-INF/classes 时有效
     * @return  路径 使用 / 分隔，但不以 / 结尾。
     */
    public static String getWebDir() {
        String classRoot = getClassesDir();
        if (classRoot != null) {
            classRoot = classRoot.replaceFirst("/WEB-INF/classes$", "");
        }
        return classRoot;
    }

    /**
     * 获取Class 所在目录 
     * <p>
     *  <b>注意：</b>对于Jar包内的类，此方法并不有效。
     * </p>
     * @param clazz  需要获取路径的class。
     * @return  路径 使用 / 分隔，但不以 / 结尾。
     */
    public static String getClassDir(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return getClassesDir().concat("/").concat(clazz.getPackage().getName().replace('.', '/'));
    }
}
