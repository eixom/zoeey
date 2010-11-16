/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.zdo;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface ArticleBeanAddAble {

    /**
     *
     * @return
     */
    String getContent();

    /**
     *
     * @return
     */
    String getTitle();

    /**
     *
     * @param content
     */
    void setContent(String content);

    /**
     *
     * @param title
     */
    void setTitle(String title);

    /**
     *
     * @return
     */
    public long getEditTime();

    /**
     *
     * @param editTime
     */
    public void setEditTime(long editTime);
}
