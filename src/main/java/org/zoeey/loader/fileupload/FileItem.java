/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.zoeey.util.FileHelper;

/**
 * 上传文件项
 * @author MoXie(SysTem128@GMail.Com)
 */
public class FileItem {

    /**
     * 参数标识符
     */
    /**
     * content-disposition
     */
    public static final String CONTENT_DISPOSITION_FLAG = "content-disposition";
    /**
     * 字段名
     */
    public static final String NAME_FLAG = "name";
    /**
     * 文件名
     */
    public static final String FILENAME_FLAG = "filename";
    /**
     * 文件头
     */
    public static final String CONTENT_TYPE_FLAG = "content-type";
    /**
     * 字符集
     */
    public static final String CHARSET_FLAG = "charset";
    /**
     * 字段名
     */
    private String fieldName = null;
    /**
     * 客户端机器文件的原名称。 
     */
    private String originalName = null;
    /**
     * <pre>
     * 文件的 MIME 类型，如果浏览器提供此信息的话。
     * 一个例子是“image/gif”。
     * 不过此 MIME 类型在 服务器 端并不检查，因此不要想当然认为有这个值。
     * </pre>
     */
    private String type = null;
    /**
     * 已上传文件的大小，单位为字节。
     */
    private long size = 0;
    /**
     * 文件被上传后在服务端储存的临时文件。
     */
    private File tempFile = null;
    /**
     * 和该文件上传相关的错误代码。
     */
    private FileError error = FileError.INIT;
    /**
     * 描述参数
     */
    private Map<String, String> header = null;

    /**
     * 上传文件项
     */
    public FileItem() {
    }

    /**
     * 和该文件上传相关的错误代码。
     * @return
     * @see FileError
     */
    public FileError getError() {
        return error;
    }

    /**
     * 和该文件上传相关的错误代码。
     * @see FileError
     */
    void setError(FileError error) {
        if (this.error == FileError.INIT) {
            this.error = error;
        }
    }

    /**
     * 获取表单字段名
     * @return
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 设置表单字段名
     * @param fieldName
     */
    void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * <pre>
     * 客户端机器文件的原名称。
     * 注意：该数据由浏览器提供，可能出现文件全路径的情况。
     * </pre>
     * @return
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * 客户端机器文件的原名称。
     */
    void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    /**
     * 已上传文件的大小，单位为字节。
     * @return
     */
    public long getSize() {
        return size;
    }

    /**
     * 已上传文件的大小，单位为字节。
     */
    void setSize(long size) {
        this.size = size;
    }

    /**
     * 文件被上传后在服务端储存的临时文件名。
     * @return
     */
    public File getTempFile() {
        return tempFile;
    }

    /**
     * 文件被上传后在服务端储存的临时文件名。
     */
    void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }

    /**
     * <pre>
     * 文件的 MIME 类型，如果浏览器提供此信息的话。
     * 一个例子是“image/gif”。
     * 不过此 MIME 类型在 服务器 端并不检查，因此不要想当然认为有这个值。
     * </pre>
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * <pre>
     * 文件的 MIME 类型，如果浏览器提供此信息的话。
     * 一个例子是“image/gif”。
     * 不过此 MIME 类型在 服务器 端并不检查，因此不要想当然认为有这个值。
     * </pre>
     */
    void setType(String type) {
        this.type = type;
    }

    /**
     * 文件描述参数
     *
     * @see MULTIPART_FORM_DATA_FLAG
     * @see CONTENT_DISPOSITION_FLAG
     * @see NAME_FLAG
     * @see FILENAME_FLAG
     * @see CONTENT_TYPE_FLAG
     * @see CONTENT_TRANSFER_ENCODING_FLAG
     * @return
     */
    public Map<String, String> getHeader() {
        return header;
    }

    /**
     * 文件描述参数
     *
     * @see MULTIPART_FORM_DATA_FLAG
     * @see CONTENT_DISPOSITION_FLAG
     * @see NAME_FLAG
     * @see FILENAME_FLAG
     * @see CONTENT_TYPE_FLAG
     * @see CONTENT_TRANSFER_ENCODING_FLAG
     * @param header
     */
    void setHeader(Map<String, String> header) {
        this.header = header;
    }

    /**<pre>
     * 移动文件
     * 注意：当 FileError 为 OK 时才有效
     * </pre>
     * @param file
     * @throws IOException 
     */
    public void moveTo(File file) throws IOException {
        if (error == FileError.OK) {
            FileHelper.move(this.getTempFile(), file);
        }
    }

    /**
     * <pre>
     * 移动文件
     * 注意：当 FileError 为 OK 时才有效
     * </pre>
     * @param filePath 
     * @throws IOException
     */
    public void moveTo(String filePath) throws IOException {
        if (error == FileError.OK) {
            File file = new File(filePath);
            FileHelper.move(this.getTempFile(), file);
        }
    }
}
