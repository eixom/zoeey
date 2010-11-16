/*
 * MoXie (SysTem128@GMail.Com) 2009-5-2 20:28:41
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.io.File;
import java.io.IOException;
import org.zoeey.util.FileHelper;
import org.zoeey.ztpl.ZtplConfig;

/**
 * 缓存操作类
 * @author MoXie(SysTem128@GMail.Com)
 */
public class CacheHandler {

    /**
     * 模板文件
     */
    private File tplFile = null;
    /**
     * 缓存键
     */
    private StringBuilder cacheKey = null;
    /**
     * 缓存时长
     */
    private long cacheTime = 3600L;
    /**
     * 模板配置信息
     */
    private ZtplConfig config = null;

    /**
     * 缓存操作类
     * @param config
     */
    public CacheHandler(ZtplConfig config) {
        this.config = config;
        this.cacheKey = new StringBuilder();
    }

    /**
     *缓存键
     * @param cacheKey
     */
    public void setCacheKey(Object cacheKey) {
        this.cacheKey = new StringBuilder(String.valueOf(cacheKey));
    }

    /**
     *缓存键
     * @param cacheKey
     */
    public void appendCacheKey(Object cacheKey) {
        this.cacheKey.append(cacheKey);
    }

    /**
     * 设置缓存时长
     * @param cacheTime
     */
    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    /**
     * 设置模板文件
     * @param tplFile
     */
    public void setTplFile(File tplFile) {
        this.tplFile = tplFile;
    }

    /**
     * 获取模板路径
     * @return
     */
    public File getTplFile() {
        return tplFile;
    }

    /**
     * 检查是否超时 是否需要更新缓存 ， - 1 为永久缓存
     * @param cacheTimeout 超时时间，单位秒
     * @param file
     * @return
     * <pre>
     * 返回{@code true}的情况：
     *    1）文件不存在，或文件对象为null。
     *    2）最后修改时间到现在超过限时；
     *    3）超时时间为 0；
     *  返回{@code false} 的情况：
     *    1）最后修改时间到现在未超时；
     * </pre>
     */
    private boolean isTimeout(File file) {
        boolean isTimeout = true;
        do {
            /**
             * 文件不存在
             * 不允许缓存
             * 未超时
             * 超时
             */
            if (file == null || !file.exists()) {
                break;
            }
            /**
             * 永久缓存
             */
            if (cacheTime == -1) {
                isTimeout = false;
                break;
            }
            /**
             * 永不缓存
             */
            if (cacheTime == 0) {
                isTimeout = true;
                break;
            }
            /**
             * 时差比较
             */
            if (file.canRead()) {
                // 最后修改时间到现在时差 与 超时时间比较
                isTimeout = (System.currentTimeMillis() - file.lastModified()) > (cacheTime * 1000);
                break;
            }
        } while (false);
        return isTimeout;
    }

    /**
     * 制取缓存文件
     * @param cacheDir
     * @param tplFileName
     * @param cacheKey
     * @return
     */
    private File getCacheFile() {
        StringBuilder strBuilder = new StringBuilder(50);
        strBuilder.append(config.getCacheDir().getAbsolutePath());
        strBuilder.append('/');
        strBuilder.append(CompileHelper.getPackName("cache_", tplFile));
        strBuilder.append('/');
        strBuilder.append(CompileHelper.getClassName("cache_", tplFile));
        return new File(FileHelper.backToslash(strBuilder.toString()));
    }

    /**
     * 获取缓存文件
     * @return
     * @throws java.io.IOException
     */
    public File getCache() //
            throws IOException {
        File file = getCacheFile();
        if (!file.exists()) {
            FileHelper.tryCreate(file);
        }
        return file;
    }

    /**
     * 是否缓存
     * @return
     * @throws java.io.IOException
     */
    public boolean isCached() //
            throws IOException {
        File file = getCacheFile();
        return !isTimeout(file);
    }
}
