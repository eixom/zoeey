/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common.container;

/**
 * Resource存储专用静态容器 
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ServletHolder {

    /**
     * 修改实现为更可靠的 ThreadLocal
     */
    private static ThreadLocal<ServletResource> localVar = new ThreadLocal<ServletResource>();

    /**
     * 锁定创建
     */
    private ServletHolder() {
    }

    /**
     * 设置Servlet资源
     * @param resource 填充{@link ServletResource ServletResource}，
     */
    public static void set(ServletResource resource) {
        if (localVar != null) {
            localVar.set(resource);
        }
    }

    /**
     * 拿出最后一次存入的Servlet资源
     * @return 值为空时返回null
     */
    public static ServletResource get() {

        return localVar == null ? null : localVar.get();
    }

    /**
     * 清理当前会话数据<br />
     * 特别注意：在每次会话完成时务必执行，以避免数据被共享
     */
    public static void clear() {
        if (localVar != null) {
            localVar.remove();
        }
    }
}
