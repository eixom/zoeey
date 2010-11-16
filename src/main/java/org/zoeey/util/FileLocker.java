/*
 * MoXie (SysTem128@GMail.Com) 2009-3-9 16:39:11
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import org.zoeey.constant.EnvConstants;

/**
 * 用于控制文件读取写入并发的锁
 * 注意：本类并不提供系统级别的文件锁定，如需要请查阅RandomAccessFile相关资料
 * @author MoXie(SysTem128@GMail.Com)
 */
public final class FileLocker {

    /**
     *  容器需要线程同步
     */
    private File file;
    private static final ConcurrentMap<File, ReentrantReadWriteLock> lockItemMap = new ConcurrentHashMap<File, ReentrantReadWriteLock>();

    /**
     * 锁定创建
     */
    public FileLocker(File file) {
        this.file = file;
    }

    /**
     * 获取写入锁。将同时获取 写入和读取 锁。
     * @param file
     */
    public void lockWrite() {
        if (file == null) {
            return;
        }
        WriteLock wlock = null;
        try {

            /**
             * 等待释放
             */
            ReentrantReadWriteLock rwlock = lockItemMap.get(file);
            ReentrantReadWriteLock oldRwLock = null;
            if (rwlock == null) {
                rwlock = new ReentrantReadWriteLock();
                oldRwLock = lockItemMap.putIfAbsent(file, rwlock);
                if (oldRwLock != null) {
                    rwlock = oldRwLock;
                }
            }
            wlock = rwlock.writeLock();

            while (!wlock.tryLock() || !rwlock.isWriteLockedByCurrentThread()) {
                Thread.sleep(EnvConstants.LOCKER_SLEEP_UNIT_TIME);
            }
        } catch (InterruptedException ex) {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
        } finally {
        }
        return;
    }

    /**
     * <pre>
     * 等待写入锁释放
     * 此读取锁联动与写入锁，并不能在单纯调用其的情况下锁定文件读取。
     * </pre>
     * @see #lockWrite(java.io.File)
     * @param file
     */
    public void lockRead() {

        if (file == null) {
            return;
        }

        try {
            ReadLock rlock = null;
            /**
             * 等待释放
             */
            ReentrantReadWriteLock rwlock = lockItemMap.get(file);
            ReentrantReadWriteLock oldRwLock = null;
            if (rwlock == null) {
                rwlock = new ReentrantReadWriteLock();
                oldRwLock = lockItemMap.putIfAbsent(file, rwlock);
                if (oldRwLock != null) {
                    rwlock = oldRwLock;
                }
            }
            rlock = rwlock.readLock();
            while (!rlock.tryLock()) {
                Thread.sleep(EnvConstants.LOCKER_SLEEP_UNIT_TIME);
            }
        } catch (InterruptedException ex) {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
        } finally {
        }
        return;
    }

    /**
     * 清理读锁
     * @param file
     */
    public void releaseRead() {
        ReadWriteLock rwlock = lockItemMap.get(file);
        if (rwlock != null) {
            rwlock.readLock().unlock();
        }
    }

    /**
     * 清理写人锁
     * @param file
     */
    public void releaseWrite() {
        ReadWriteLock rwlock = lockItemMap.get(file);
        if (rwlock != null) {
            rwlock.writeLock().unlock();
        }
    }
}
