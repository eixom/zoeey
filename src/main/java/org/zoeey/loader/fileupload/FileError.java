/*
 * MoXie (SysTem128@GMail.Com) 2009-5-18 22:33:46
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public enum FileError {

    /**
     * 初始状态
     */
    INIT,
    /**
     * 没有错误发生，文件上传成功。
     */
    OK,
    /**
     * 上传的文件超过了上限制
     */
    MAXSIZE,
    /**
     * 文件只有部分被上传。
     */
    PARTIAL,
    /**
     * 没有文件被上传。
     */
    NO_FILE,
    /**
     * 找不到临时文件夹。
     */
    //    NO_TEMP_DIR,
    /**
     * 文件写入失败。
     */
    //    CANNOT_WRITE,
    /**
     * IO异常
     */
    IOEX,
    /**
     * 不适合的类型
     */
    BANED_TYPE
}
