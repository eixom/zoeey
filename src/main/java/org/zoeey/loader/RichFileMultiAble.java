/*
 * MoXie (SysTem128@GMail.Com) 2009-9-2 9:40:35
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

import org.zoeey.loader.fileupload.FileItem;

/**
 * 富化多文件字段
 * @author MoXie
 */
public interface RichFileMultiAble extends RichAble {

    /**
     * 富化多文件字段
     * @param fileItems
     * @return
     */
    public FileItem[] rich(String field,FileItem[] fileItems);
}
