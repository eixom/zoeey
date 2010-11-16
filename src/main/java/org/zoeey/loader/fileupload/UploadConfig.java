/*
 * MoXie (SysTem128@GMail.Com) 2009-5-23 1:13:31
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

import java.io.File;
import java.util.Arrays;
import org.zoeey.constant.EnvConstants;

/**
 * 文件上传配置
 * @author MoXie(SysTem128@GMail.Com)
 */
public class UploadConfig {

    /**
     * 解码字符集
     */
    private String charset = null;
    /**
     * 临时文件前缀
     */
    private String tempFilePrefix = "fileupload_";
    /**
     * 临时文件后缀
     */
    private String tempFileSuffix = ".tmp"; // .tmp
    /**
     * 文件大小限制
     * 默认文件大小限制为8兆
     */
    private int filesizeMax = EnvConstants.DEFAULT_UPLOAD_FILEMAXSIZE;
    /**
     * 文件总大小限制（包括文字字段）
     * 默认文件大小限制为128兆
     */
    private int contentLengthLimit = EnvConstants.DEFAULT_UPLOAD_FILEMAXSIZE;
    /**
     * 临时文件目录
     */
    private File tempFileDir = null;
    /**
     * 允许的文件类型
     */
    private String[] allowTypes = null;

    /**
     *
     */
    public UploadConfig() {
        charset = EnvConstants.CHARSET;
    }

    /**
     * <pre>
     * 临时文件前缀
     * 默认为： fileupload_
     * </pre>
     * @return
     */
    public String getTempFilePrefix() {
        return tempFilePrefix;
    }

    /**
     * <pre>
     * 临时文件前缀
     * 默认为： fileupload_
     * </pre>
     * @param tempFilePrefix
     */
    public void setTempFilePrefix(String tempFilePrefix) {
        this.tempFilePrefix = tempFilePrefix;
    }

    /**
     * <pre>
     * 临时文件后缀
     * 默认为： .tmp
     * </pre>
     * @return
     */
    public String getTempFileSuffix() {
        return tempFileSuffix;
    }

    /**
     * <pre>
     * 临时文件后缀
     * 默认为： .tmp
     * </pre>
     * @param tempFileSuffix
     */
    public void setTempFileSuffix(String tempFileSuffix) {
        this.tempFileSuffix = tempFileSuffix;
    }

    /**
     * <pre>
     * 临时文件存放目录
     * 默认为：Java临时文件目录
     * </pre>
     * @return
     */
    public File getTempFileDir() {
        if (tempFileDir == null) {
            return new File(EnvConstants.DEFAULT_TEMP_DIR);
        }
        return tempFileDir;
    }

    /**
     * <pre>
     * 临时文件存放目录
     * 默认为：Java临时文件目录
     * </pre>
     * @param tmpFileDir
     */
    public void setTempFileDir(File tmpFileDir) {
        this.tempFileDir = tmpFileDir;
    }

    /**
     * <pre>
     * 文件大小上限
     * 默认为：8m
     * </pre>
     * @return
     */
    public int getFilesizeMax() {
        return filesizeMax;
    }

    /**
     * <pre>
     * 文件大小上限
     * 默认为：8m = 8 * 1024 * 1024
     * </pre>
     * @param filesizeMax
     */
    public void setFilesizeMax(int filesizeMax) {
        this.filesizeMax = filesizeMax;
    }

    /**
     * <pre>
     * 允许文件类型，为null时允许全部
     * 默认为： null
     * </pre>
     * @return
     */
    public String[] getAllowTypes() {
        return allowTypes;
    }

    /**
     * <pre>
     * 允许文件类型，为null时允许全部
     * 默认为： null
     * </pre>
     * @param allowTypes
     */
    public void setAllowTypes(String[] allowTypes) {
        if (allowTypes != null) {
            Arrays.sort(allowTypes);
        }
        this.allowTypes = allowTypes.clone();
    }

    /**
     * 分析使用的字符集
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 设置分析使用的字符集
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }
}
