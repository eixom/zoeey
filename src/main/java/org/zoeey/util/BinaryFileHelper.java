/*
 * MoXie (SysTem128@GMail.Com) 2009-8-7 12:44:38
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

/**
 * 二进制文件操作辅助工具
 * @author MoXie
 */
public class BinaryFileHelper {

    /**
     * 锁定创建
     */
    private BinaryFileHelper() {
    }

    /**
     * <pre>
     * 写文件
     * Php : file_put_contents(String content);
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file 文件不存在则自动创建
     * @param bytes 文件内容
     * @throws java.io.IOException
     */
    public static void write(File file, byte[] bytes)
            throws IOException {
        FileHelper.tryCreate(file);
        FileLocker flock = new FileLocker(file);
        OutputStream os = null;
        try {
            flock.lockWrite();
            os = new FileOutputStream(file);
            os.write(bytes);
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
     * 写入文件 
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * 特别注意：输入流假如使用了 file 文件，且使用了{@link FileLocker}，则会产生上述永久锁。
     * </pre>
     * @param file
     * @param is
     * @throws java.io.IOException
     */
    public static void write(File file, InputStream is)
            throws IOException {
        FileLocker flock = new FileLocker(file);
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        OutputStream os = null;
        try {
            flock.lockWrite();
            os = new FileOutputStream(file);
            os.write(bytes);
            os.flush();
        } finally {
            if (os != null) {
                os.close();
                os = null;
            }
            if (is != null) {
                is.close();
                is = null;
            }
            flock.releaseWrite();
        }
    }

    /**
     * <pre>
     * 读取文本文件
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file  文件
     * @return
     * @throws java.io.IOException
     */
    public static byte[] read(File file)
            throws IOException {
        FileLocker flock = new FileLocker(file);
        InputStream is = new FileInputStream(file);
        try {
            flock.lockRead();
            return read(is);
        } finally {
            flock.releaseRead();
        }
    }

    /**
     * 读取文本文件
     * @param is 输入流
     * @return
     * @throws java.io.IOException
     */
    public static byte[] read(InputStream is)
            throws IOException {
        byte[] bytes = null;
        try {
            bytes = new byte[is.available()];
            is.read(bytes);
        } finally {
            if (is != null) {
                is.close();
                is = null;
            }
        }
        return bytes;
    }

    /**
     * <pre>
     * 追加数据
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param file
     * @param bytes
     * @throws java.io.IOException
     */
    public static void append(File file, byte[] bytes)
            throws IOException {
        FileHelper.tryCreate(file);
        FileLocker flock = new FileLocker(file);
        OutputStream os = null;
        try {
            flock.lockWrite();
            os = new FileOutputStream(file, true);
            os.write(bytes);
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
     * 由文件建立输入流并生产一个Reader
     * @param file
     * @return
     * @throws java.io.IOException
     */
    public static Reader newReader(File file) throws IOException {
        return new InputStreamReader(new FileInputStream(file));
    }

    /**
     * 从输入流中生产一个Reader
     * @param is
     * @return
     * @throws java.io.IOException
     */
    public static Reader newReader(InputStream is) throws IOException {
        return new InputStreamReader(is);
    }
}
