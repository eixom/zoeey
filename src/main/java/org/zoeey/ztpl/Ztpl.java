/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 20:22:40
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl;

import org.zoeey.ztpl.compiler.CacheHandler;
import org.zoeey.ztpl.compiler.CompileHandler;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.zoeey.util.FileLocker;
import org.zoeey.util.TextFileHelper;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class Ztpl {

    /**
     * 资源容器
     */
    private Map<String, Object> context;
    /**
     * 配置文件
     */
    private ZtplConfig config;
    /**
     * 缓存处理
     */
    private CacheHandler cacheHandler = null;

    /**
     *
     */
    public Ztpl() {
        this(new ZtplConfig());
    }

    /**
     *
     * @param config
     */
    public Ztpl(ZtplConfig config) {
        this.context = new HashMap<String, Object>();
        this.config = config;
        cacheHandler = new CacheHandler(config);
    }

    /**
     * 
     * @param key
     * @param value
     * @return
     */
    public Ztpl assign(String key, Object value) {
        context.put(key, value);
        return this;
    }

    /**
     *
     * @param map
     * @return
     */
    public Ztpl assignAll(Map<String, Object> map) {
        context.putAll(map);
        return this;
    }

    /**
     * 设置缓存键
     * @param key
     * @return
     */
    public Ztpl setCacheKey(Object key) {
        cacheHandler.setCacheKey(key);
        return this;
    }

    /**
     * 设置缓存键
     * @param key
     * @return
     */
    public Ztpl appendCacheKey(Object key) {
        cacheHandler.appendCacheKey(key);
        return this;
    }

    /**
     * 模板文件
     * @param tplFile
     * @return
     */
    public Ztpl setTplFile(File tplFile) {
        cacheHandler.setTplFile(tplFile);
        return this;
    }

    /**
     * 设置缓存时常 默认为3600秒（一小时）
     * @param cacheTime
     * @return
     */
    public Ztpl setCacheTime(int cacheTime) {
        cacheHandler.setCacheTime(cacheTime);
        return this;
    }

    /**
     * 获取输出
     * @return 
     * @throws IOException
     */
    public boolean isCached() throws IOException {
        return cacheHandler.isCached();
    }

    /**
     * <pre>
     * 获取输出
     * 注意：此方法使用了 {@link FileLocker}，嵌套使用 {@link FileLocker} 时可能造成永久锁。
     * </pre>
     * @param writer
     * @throws IOException
     */
    public void fetch(Writer writer) throws IOException {
        File cacheFile = cacheHandler.getCache();
        if (!cacheHandler.isCached()) {
            /**
             * compile
             */
            TemplateAble tplPublish = null;
            CompileHandler compileHandler = new CompileHandler(this);
            tplPublish = compileHandler.getCompiledClass(cacheHandler.getTplFile(), config);
            if (tplPublish != null) {
                FileLocker flock = new FileLocker(cacheFile);
                try {
                    flock.lockWrite();
                    Writer cacheWriter = TextFileHelper.newWriter(cacheFile, config.getCharset());
                    tplPublish.publish(cacheWriter, context, this);
                    cacheWriter.flush();
                    cacheWriter.close();
                    cacheWriter = null;
                } finally {
                    flock.releaseWrite();
                }
            } else {
                System.out.println("error tpl publish nofound");
                //todo: throw something
            }
        }
        TextFileHelper.read(TextFileHelper.newReader(cacheFile, config.getCharset()), writer);
    }
}
