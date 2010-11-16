/*
 * MoXie (SysTem128@GMail.Com) 2009-5-17 1:49:52
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader.fileupload;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.zoeey.constant.EnvConstants;

/**
 * <pre>
 * 缓冲写入锁,防止缓冲域写入冲突
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
class BufferWriterLocker {

    /**
     *
     */
    private static final ReentrantLock lock = new ReentrantLock();
    /**
     *  
     */
    private static List<List<Byte>> writtingList = new ArrayList<List<Byte>>();

    /**
     * 锁定创建
     */
    private BufferWriterLocker() {
    }

    /**
     * 获取写入锁。将同时获取 写入和读取 锁。
     * @param file
     */
    public static void writeLock(List<Byte> deque) {
        try {
            int i = 0;
            try {
                while (!lock.tryLock()) {
                    i++;
                    Thread.sleep(EnvConstants.LOCKER_SLEEP_UNIT_TIME);
                }
            } catch (InterruptedException ex) {
                // Restore the interrupted status
                Thread.currentThread().interrupt();
            }
            /**
             * 等待释放
             */
            try {
                while (writtingList.contains(deque)) {
                    i++;
                    Thread.sleep(EnvConstants.LOCKER_SLEEP_UNIT_TIME);

                }
                writtingList.remove(deque);
            } catch (InterruptedException ex) {
                // Restore the interrupted status
                Thread.currentThread().interrupt();
            }
        } finally {
            //
            lock.unlock();
            writtingList.remove(deque);
        }
    }

    /**
     * 完成任务
     * @param filePath
     */
    public static final void clear(List<Byte> deque) {
        writtingList.remove(deque);
    }
}
