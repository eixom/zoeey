/*
 * MoXie (SysTem128@GMail.Com) 2009-9-2 9:32:08
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

/**
 * 富化单文本字段
 * @author MoXie
 */
public interface RichSingleAble extends RichAble {

    /**
     * 富化单文本字段
     * @param field
     * @param value
     * @return
     */
    public String rich(String field,String value);
}
