/*
 * MoXie (SysTem128@GMail.Com) 2009-3-9 9:31:08
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner;

import java.io.File;
import java.io.IOException;
import org.zoeey.constant.EnvConstants;
import org.zoeey.util.FileLocker;
import org.zoeey.util.StringHelper;
import org.zoeey.util.TextFileHelper;
import org.zoeey.util.TimeFormatHelper;

/**
 * <pre>
 * 合并文字资源
 * 在大量文件合并操作时注意释放资源
 * </pre>
 * @see #close() 
 * @author MoXie(SysTem128@GMail.Com)
 */
public class TextCombiner {

    /**
     * 字符串容器
     */
    private StringBuilder strBuilder = null;
    /**
     * 默认容器大小
     */
    private final static int CAPACITY_DEF = 100;
    private String boundary = null;
    private String charset = EnvConstants.CHARSET;

    /**
     * 设置初始capacity大小的连接器
     * @param capacity  字符缓冲带大小
     */
    public TextCombiner(int capacity) {
        strBuilder = new StringBuilder(capacity);
    }

    /**
     * 由字符串新建一个链接器
     * @param str   起始文字
     */
    public TextCombiner(String str) {
        strBuilder = new StringBuilder(str);
    }

    /**
     * 新建一个链接器
     * @see CAPACITY_DEF  默认字符缓冲带大小
     */
    public TextCombiner() {
        strBuilder = new StringBuilder(CAPACITY_DEF);
    }

    /**
     * 获取分界线
     * @return
     */
    public String getBoundary() {
        return boundary;
    }

    /**
     * <pre>
     * 设置分界线每次拼接前写入
     * 可以使用变量:
     * {date} 2006-09-09 11:22:55
     * 
     * {fileName} 文件名称 
     * 使用方式 ： {file} 文件名称：{fileName} {/file}
     * {file} 内的字符串不会出现在字符串连接内
     * </pre>
     * @param boundary
     */
    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    /**
     * 获取 charset 名称
     * 默认:utf-8
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 设置 charset 名称
     * 默认:utf-8
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * 推入分隔符
     * 
     * 可以使用变量:
     * {date} 2006-09-09 11:22:55
     *
     * {fileName} 文件名称
     * 使用方式 ： {file} 文件名称：{fileName} {/file}
     * {file} 内的字符串不会出现在字符串连接内
     */
    private void concatBoundary(File file) {
        if (boundary != null) {
            String _boundary = StringHelper.replace(boundary, "{date}" //
                    , TimeFormatHelper.format(System.currentTimeMillis(), TimeFormatHelper.DATE_MYSQL)//
                    );
            String fileName = "";
            if (file != null) {
                fileName = file.getName();
                _boundary = StringHelper.replace(_boundary, "{file}", ""); //
                _boundary = StringHelper.replace(_boundary, "{/file}", ""); //
            } else {
                _boundary = _boundary.replaceAll("\\{file\\}.*\\{/file\\}", "");
            }
            _boundary = StringHelper.replace(_boundary, "{fileName}", fileName);
            strBuilder.append(_boundary);
        }
    }

    /**
     * 连接文本文件 
     * @param file
     * @throws java.io.IOException
     */
    public void concat(File file) throws IOException {
        concatBoundary(file);
        strBuilder.append(TextFileHelper.read(file, charset));
    }

    /**
     * 连接文本文件
     * @param file 
     * @param charset
     * @throws java.io.IOException
     */
    public void concat(File file, String charset) throws IOException {
        concatBoundary(file);
        strBuilder.append(TextFileHelper.read(file, charset));
    }

    /**
     * 链接字符串
     * @param str
     */
    public void concat(String str) {
        strBuilder.append(str);
    }

    /**
     * 将拼接好的字符串拿出
     * @return
     */
    @Override
    public String toString() {
        return strBuilder.toString();
    }

    /**
     * <pre>
     * 将拼接好的字符串写入文件，支持并发,自动创建
     * 注意：写入会替换原有文件。
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file 文件
     * @return 刚写入的文件对象
     * @throws java.io.IOException
     */
    public File write(File file) throws IOException {
        /**
         * bug fixed FileLocker 如果放在autoCreate前则会造成造成永久锁。
         */
        /**
         * 写入文件
         */
        {
            TextFileHelper.write(file, strBuilder.toString(), charset);
        }

        return file;
    }

    /**
     *   清理资源
     */
    public void clear() {
        strBuilder = null;
    }
}
