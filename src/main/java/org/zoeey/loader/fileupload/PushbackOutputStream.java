/*
 * MoXie (SysTem128@GMail.Com) 2009-5-14 14:23:11
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 可退步和缓冲的输出流
 * 特别注意：必须执行 {@link #flush() } 否则极有可能造成数据写入不全。
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
class PushbackOutputStream
        extends FilterOutputStream {

    /**
     * 缓冲区域
     */
    private byte buffer[];
    /**
     * 已缓冲量
     */
    private int count;
    /**
     * 等待队列
     */
    private List<Byte> queue = null;
    /**
     * 退步区域
     */
    private int backSize = 0;
    /**
     * 默认缓冲区大小
     */
    private static final int defaultSize = 4096;

    /**
     * 可退步的输出流，默认可退步数为 5 
     *
     * @param out   输出流
     */
    public PushbackOutputStream(OutputStream out) {
        this(out, defaultSize, 5);
    }

    /**
     * 可退步的输出流，默认可退步数为 5
     *
     * @param out   输出流
     * @param backSize 可退步数/退步区域大小
     */
    public PushbackOutputStream(OutputStream out, int backSize) {
        this(out, defaultSize, backSize);
    }

    /**
     * 可退步的输出流
     * @param out   输出流
     * @param size  缓冲大小
     * @param backSize 可退步数/退步区域大小
     */
    public PushbackOutputStream(OutputStream out, int size, int backSize) {
        super(out);
        if (size <= 0) {
            size = defaultSize;
        }
        queue = new ArrayList<Byte>();
        this.backSize = backSize;
        buffer = new byte[size];
        count = 0;
    }

    /**
     * 输出缓冲区
     * @throws java.io.IOException
     */
    private void flushBuffer()
            throws IOException {
        if (count > 0) {
            out.write(buffer, 0, count);
            count = 0;
        }
    }

    /**
     * 写入
     * @param b
     * @throws java.io.IOException
     */
    @Override
    public void write(int b)
            throws IOException {
        BufferWriterLocker.writeLock(queue);
        queue.add((byte) b);
        if (count >= buffer.length) {
            flushBuffer();
        }
        if (queue.size() > backSize) {
            buffer[count++] = queue.remove(0);
        }
        BufferWriterLocker.clear(queue);
    }

    /**
     * <pre>
     * 写入
     * </pre>
     * @param bs
     * @throws java.io.IOException
     */
    @Override
    public void write(byte[] bs) throws IOException {
        write(bs, 0, bs.length);
    }

    /**
     * <pre>
     * 退步
     * 注意：退步不可超过可退步数或写入量，否则产生 {@link java.util.NoSuchElementException}。
     * </pre>
     * @param count 步数
     */
    public void unwrite(int count) {
        BufferWriterLocker.writeLock(queue);
        for (int i = 0; i < count; i++) {
            queue.remove(queue.size() - 1);
        }
        BufferWriterLocker.clear(queue);
    }

    /**
     * <pre>
     * 退步
     * 注意：退步不可超过可退步数或写入量，否则产生 {@link java.util.NoSuchElementException}。
     * changelog:
     *      09-07-17 增加队列大小判断，之后移除；
     * </pre>
     */
    public void unwrite() {
        BufferWriterLocker.writeLock(queue);
        if (!queue.isEmpty()) {
            queue.remove(queue.size() - 1);
        }
        BufferWriterLocker.clear(queue);
    }

    /**
     * 写入
     * @param bs
     * @param off
     * @param len
     * @throws java.io.IOException
     */
    @Override
    public void write(byte[] bs, int off, int len)
            throws IOException {
        int i = -1;
        int end = off + len;
        while (true) {
            i++;
            if (i >= end) {
                break;
            }
            if (i < off) {
                continue;
            }
            write(bs[i]);
        }
    }

    /**
     * <pre>
     * 键保留在缓冲区域的内容全部输出
     * 注意：输出流{@link OutputStream#flush() }会被调用。
     * </pre>
     * @throws java.io.IOException
     */
    @Override
    public void flush()
            throws IOException {
        BufferWriterLocker.writeLock(queue);
        flushBuffer();
        byte[] _buf = new byte[queue.size()];
        int i = 0;
        for (Byte b : queue) {
            _buf[i] = b;
            i++;
        }
        out.write(_buf);
        out.flush();
        BufferWriterLocker.clear(queue);
    }

    /**
     * <pre>
     * 清理缓存队列关闭输出流
     * 注意：关闭后如再次写入则产生 队列为null 或 输出流对象为null 的异常。
     * 注意：输出流{@link OutputStream#close() }会被调用。
     * </pre>
     * @throws java.io.IOException
     */
    @Override
    public void close()
            throws IOException {
        BufferWriterLocker.writeLock(queue);
        queue = null;
        out.close();
        out = null;
        BufferWriterLocker.clear(queue);
    }
}
