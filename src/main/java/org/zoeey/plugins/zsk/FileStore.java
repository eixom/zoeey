/*
 * MoXie (SysTem128@GMail.Com) 2009-1-18 15:27:38
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.plugins.zsk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zoeey.util.EncryptHelper;
import org.zoeey.util.EnvInfo;
import org.zoeey.util.FileHelper;
import org.zoeey.util.StringHelper;

/**
 * 基于文件的会话存储
 * @param <T>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class FileStore<T extends Serializable> implements StoreAble<T> {

    /**
     * 缓存目录
     */
    private String cacheDir = null;

    /**
     * 设定缓存目录
     * @param dir
     */
    public void setCacheDir(String dir) {
        this.cacheDir = dir;
    }

    /**
     * 获取缓存目录，默认为Java临时目录
     * @return
     */
    private String getCacheDir() {
        return cacheDir == null ? EnvInfo.getJavaIoTmpdir() : cacheDir;
    }

    /**
     * 根据会话名建立制取文件
     * @param key
     * @return
     */
    private File genFile(String key) {
        String fileName =  EncryptHelper.md5(key);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(getCacheDir());
        strBuilder.append(StringHelper.subString(fileName, 0, 9));
        strBuilder.append(EnvInfo.getFileSeparator());
        strBuilder.append(StringHelper.subString(fileName, 9, -1));
        strBuilder.append(".tmp");
        return new File(strBuilder.toString());
    }

    /**
     * 存储
     * @param key  存储标识
     * @param value 可存
     * @throws IOException
     */
    public void put(String key, T value) throws IOException {
        File file = genFile(key);
        if (!file.exists()) {
            FileHelper.tryCreate(file);
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(value);
        oos.flush();
        oos.close();
        oos = null;
    }

    /**
     *  获取
     * @param key 存储标识
     * @return
     */
    public T get(String key) {
        try {
            File file = genFile(key);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Object obj = ois.readObject();
            ois.close();
            ois = null;
            return (T) obj;
        } catch (IOException ex) {
            Logger.getLogger(FileStore.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileStore.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
