/*
 * MoXie (SysTem128@GMail.Com) 2009-3-20 21:03:34
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner.js;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface JsListNodeAble {

    /**
     *
     * @return
     */
    public String getFileRoot();

    /**
     *
     * @param fileRoot
     */
    public void setFileRoot(String fileRoot);

    /**
     *
     * @return
     */
    public boolean isIsDebug();

    /**
     *
     * @param isDebug
     */
    public void setIsDebug(boolean isDebug);

    /**
     *
     * @return
     */
    public String getCharset();

    /**
     *
     * @param charset
     */
    public void setCharset(String charset);
}
