/*
 * MoXie (SysTem128@GMail.Com) 2009-3-20 21:04:28
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner.js;

import org.zoeey.constant.EnvConstants;

/**
 * <file> 父级节点
 * @author MoXie(SysTem128@GMail.Com)
 */
public abstract class JsListNodeBase {

    /**
     * 是否调试
     */
    private boolean isDebug = false;
    /**
     * 根路径
     */
    private String fileRoot = null;
    /**
     * 字符集
     */
    private String charset = EnvConstants.CHARSET;

    /**
     *
     * @return
     */
    public String getFileRoot() {
        return fileRoot;
    }

    /**
     *
     * @param fileRoot
     */
    public void setFileRoot(String fileRoot) {
        this.fileRoot = fileRoot;
    }

    /**
     *
     * @return
     */
    public boolean isIsDebug() {
        return isDebug;
    }

    /**
     *
     * @param isDebug
     */
    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     *
     * @param isDebug
     */
    public void setIsStatic(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     *
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     *
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }
}
