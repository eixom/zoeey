/*
 * MoXie (SysTem128@GMail.Com) 2009-3-26 20:21:08
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import org.zoeey.constant.EnvConstants;

/**
 * 文本文件操作辅助类
 * @author MoXie(SysTem128@GMail.Com)
 */
public class TextFileHelper {

    /**
     * 锁定创建
     */
    private TextFileHelper() {
    }

    /**
     * <pre>
     * 写文本文件 使用 UTF-8 字符集
     * Php : file_put_contents(String content);
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file
     * @param content
     * @throws java.io.IOException
     */
    public static void write(File file, String content)
            throws IOException {
        write(file, content, EnvConstants.CHARSET);
    }

    /**
     * <pre>
     * 写文本文件
     * Php : file_put_contents(String content);
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file
     * @param content
     * @param charset
     * @throws java.io.IOException
     */
    public static void write(File file, String content, String charset)
            throws IOException {
        FileHelper.tryCreate(file);
        FileLocker flock = new FileLocker(file);
        OutputStream os = null;
        try {
            flock.lockWrite();
            os = new FileOutputStream(file);
            os.write(content.getBytes(charset));
            os.flush();
        } finally {
            if (os != null) {
                os.close();
                os = null;
            }
            flock.releaseWrite();
        }

    }

    /**
     * <pre>
     * 写入文件,读取和写入均使用 {@link RuntimeConstant#CHARSET} 字符集
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * 特别注意：输入流假如使用了 file 文件，且使用了{@link FileLocker}，则会产生上述永久锁。
     * </pre>
     * @param file
     * @param is
     * @throws java.io.IOException
     */
    public static void write(File file, InputStream is)
            throws IOException {
        write(file, newReader(is, EnvConstants.CHARSET), EnvConstants.CHARSET);
    }

    /**
     * <pre>
     * 写入文件，使用同一字符集
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * 特别注意：输入流假如使用了 file 文件，且使用了{@link FileLocker}，则会产生上述永久锁。
     * </pre>
     * @param file
     * @param is
     * @param charset 读取与写出字符集
     * @throws java.io.IOException
     */
    public static void write(File file, InputStream is, String charset)
            throws IOException {
        write(file, newReader(is, charset), charset);
    }

    /**
     * <pre>
     * 写入文件
     * 特点：可使用 {@link #newReader(java.io.File, java.lang.String) } 或 {@link #newReader(java.io.InputStream, java.lang.String) }
     * 设定输入编码，以便转换不同编码文件。
     *
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * 特别注意：Reader如使用了 file 文件，且使用了{@link FileLocker}，则会产生上述永久锁。
     * </pre>
     * @param file
     * @param reader
     * @param charset 写出字符集
     * @throws java.io.IOException
     */
    public static void write(File file, Reader reader, String charset)
            throws IOException {
        FileLocker flock = new FileLocker(file);
        Writer writer = null;
        try {
            flock.lockWrite();
            writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)), charset);
            char[] buffer = new char[EnvConstants.BUFFER_CHAR_SIZE];
            int read = 0;
            while ((read = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, read);
            }
            writer.flush();
        } finally {

            if (writer != null) {
                writer.close();
                writer = null;
            }
            if (reader != null) {
                reader.close();
                reader = null;
            }
            flock.releaseWrite();
        }

    }

    /**
     * <pre>
     * 读取文本文件 使用 utf-8 字符集
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file  文件
     * @return
     * @throws java.io.IOException
     */
    public static String read(File file)
            throws IOException {
        return read(file, EnvConstants.CHARSET);
    }

    /**
     * <pre>
     * 读取文本文件
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file  文件
     * @param charset   字符集
     * @return
     * @throws java.io.IOException
     */
    public static String read(File file, String charset)
            throws IOException {
        FileLocker flock = new FileLocker(file);
        InputStream is = new FileInputStream(file);
        try {
            flock.lockRead();
            return read(is, charset);
        } finally {
            flock.releaseRead();
        }
    }

    /**
     * 读取文本文件 使用 utf-8 字符集
     * @param is
     * @return
     * @throws java.io.IOException
     */
    public static String read(InputStream is)
            throws IOException {
        return read(is, EnvConstants.CHARSET);
    }

    /**
     * 读取文本文件
     * @param is 输入流
     * @param charset 字符集
     * @return
     * @throws java.io.IOException
     */
    public static String read(InputStream is, String charset)
            throws IOException {
        if (charset == null) {
            charset = EnvConstants.CHARSET;
        }
        return read(new InputStreamReader(is, charset));
    }

    /**
     * 读取文本
     * @param reader
     * @return
     * @throws IOException
     */
    public static String read(Reader reader)
            throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        char[] buffer = new char[EnvConstants.BUFFER_CHAR_SIZE];
        int read = 0;
        try {
            while ((read = reader.read(buffer)) > 0) {
                strBuilder.append(buffer, 0, read);
            }
        } finally {
            if (reader != null) {
                reader.close();
                reader = null;
            }
        }
        buffer = null;
        return strBuilder.toString();
    }

    /**
     * 读取文本
     * @param reader
     * @param writer
     * @throws IOException
     */
    public static void read(Reader reader, Writer writer)
            throws IOException {
        char[] buffer = new char[EnvConstants.BUFFER_CHAR_SIZE];
        int read = 0;
        try {


            while ((read = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, read);
            }
        } finally {
            if (writer != null) {
                writer.close();
                writer = null;
            }
            if (reader != null) {
                reader.close();
                reader = null;
            }
        }
        buffer = null;

    }

    /**
     * <pre>
     * 追加数据
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file
     * @param content
     * @throws java.io.IOException
     */
    public static void append(File file, String content)
            throws IOException {
        append(file, content, EnvConstants.CHARSET);
    }

    /**
     * <pre>
     * 追加数据
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file
     * @param content
     * @param charset
     * @throws java.io.IOException
     */
    public static void append(File file, String content, String charset)
            throws IOException {
        FileHelper.tryCreate(file);
        FileLocker flock = new FileLocker(file);
        OutputStream os = null;
        try {
            flock.lockWrite();
            os = new FileOutputStream(file, true);
            os.write(content.getBytes(charset));
            os.flush();
        } finally {
            if (os != null) {
                os.close();
                os = null;
            }
            flock.releaseWrite();
        }
    }

    /**
     * 由文件建立输入流并生产一个Reader，使用参数charset所设定的编码解读。
     * @param file
     * @param charset
     * @return
     * @throws java.io.IOException
     */
    public static Reader newReader(File file, String charset) throws IOException {
        return new InputStreamReader(new FileInputStream(file), charset);
    }

    /**
     * 从输入流中生产一个Reader，使用参数charset所设定的编码解读。
     * @param is
     * @param charset
     * @return
     * @throws java.io.IOException
     */
    public static Reader newReader(InputStream is, String charset) throws IOException {
        return new InputStreamReader(is, charset);
    }

    /**
     * 建立文件Writer
     * @param file
     * @param charset
     * @return
     * @throws IOException
     */
    public static Writer newWriter(File file, String charset) throws IOException {
        return new OutputStreamWriter(new FileOutputStream(file), charset);
    }

    /**
     * 建立输出流Writer
     * @param os
     * @param charset
     * @return
     * @throws IOException
     */
    public static Writer newWriter(OutputStream os, String charset) throws IOException {
        return new OutputStreamWriter(os, charset);
    }
}
