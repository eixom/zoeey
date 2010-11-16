/*
 * MoXie (SysTem128@GMail.Com) 2009-3-21 15:02:04
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner;

/**
 * 文本压缩接口
 * @author MoXie(SysTem128@GMail.Com)
 */
public interface TextCompressAble {

    /**
     *
     * @param str
     * @return
     */
    public String compress(String str);

    /**
     *
     * @param str
     * @param level
     * @return
     */
    public String compress(String str, int level);
}
