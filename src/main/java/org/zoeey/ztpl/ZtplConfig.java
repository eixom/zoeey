/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 20:52:03
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.zoeey.constant.EnvConstants;
import org.zoeey.util.FileHelper;

/**
 * 模板配置
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ZtplConfig {

    /**
     * 插件列表
     */
    private Map<String, Class<?>> pluginMap;
    /**
     * 是否检查模版更新
     */
    private boolean isCheckModify = true;
    /**
     * 缓存目录
     */
    private File cacheDir;
    /**
     * 编译文件存储目录
     */
    private File compileDir;
    /**
     * 模板文件与输出字符集
     */
    private String charset = EnvConstants.CHARSET;
    /**
     * 定界符
     */
    private Delimiter delimiter;

    /**
     * 模板配置
     */
    public ZtplConfig(String left, String right) {
        pluginMap = new HashMap<String, Class<?>>();
        cacheDir = new File(EnvConstants.DEFAULT_TEMP_DIR);
        compileDir = new File(EnvConstants.DEFAULT_TEMP_DIR);
        delimiter = new Delimiter(left, right);
        /**
         * 初始化默认函数
         */
        init();
    }

    public ZtplConfig() {
        this("<!--{", "}-->");
    }

    /**
     * 初始化内置函数
     */
    private void init() {
        pluginMap.put("json_encode", org.zoeey.ztpl.plugins.ZTPF_JsonEncode.class);
    }

    /**
     * 获取扩展列表
     * @return
     */
    public Map<String, Class<?>> getPluginMap() {
        return pluginMap;
    }

    /**
     * 添加扩展
     * @param name  挂载名，在模板内使用此名称调用这个函数
     * @param pluginClass   扩展类
     */
    public void addPlugin(String name, Class<?> pluginClass) {
        this.pluginMap.put(name, pluginClass);
    }

    /**
     * 是否检查模版更新
     * @return
     */
    public boolean isIsCheckModify() {
        return isCheckModify;
    }

    /**
     * 是否检查模版更新
     * @param isCheckModify
     */
    public void setIsCheckModify(boolean isCheckModify) {
        this.isCheckModify = isCheckModify;
    }

    /**
     * 缓存目录
     * @param cacheFileDir
     */
    public void setCacheDir(String cacheFileDir) {
        File _cacheDir = new File(cacheFileDir);
        if (_cacheDir != null) {
            if (!_cacheDir.isDirectory() && !_cacheDir.exists()) {
                FileHelper.tryMakeDirs(_cacheDir);
            }
            this.cacheDir = _cacheDir;
        }
    }

    /**
     * 缓存目录
     * @return
     */
    public File getCacheDir() {
        return cacheDir;
    }

    /**
     * 编译目录，供存储.class文件和模板文件信息
     * @param compileFileDir   生成的.class文件将会被放在此目录下
     */
    public void setCompileDir(String compileFileDir) {
        File _compileDir = new File(compileFileDir);
        if (_compileDir != null) {
            if (!_compileDir.isDirectory() && !_compileDir.exists()) {
                FileHelper.tryMakeDirs(_compileDir);
            }
            this.compileDir = _compileDir;
        }
    }

    /**
     * 编译目录
     * @return  放置生成的.class文件夹
     */
    public File getCompileDir() {
        return compileDir;
    }

    /**
     * 模板文件与输出字符集
     * @return  当前使用的字符集
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 模板文件与输出字符集
     * @param charset   模板读取输出所使用的字符集
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * 设置定界符，默认为{@code <!--{,}-->}
     * @param left  左定界符
     * @param right 右定界符
     */
    public void setDelimiter(String left, String right) {
        this.delimiter = new Delimiter(left, right);
    }

    /**
     * 获取定界符,默认为{@code <!--{,}-->}
     * @return  当前设定的定界符
     */
    public Delimiter getDelimiter() {
        return delimiter;
    }

    /**
     * 定界符
     */
    public static class Delimiter {

        /**
         * 左定界符
         */
        private String left;
        /**
         * 右定界符
         */
        private String right;

        /**
         * 定界符
         * @param left  左定界符
         * @param right 右定界符
         */
        public Delimiter(String left, String right) {
            check(left);
            check(right);
            this.left = left;
            this.right = right;
        }

        /**
         * 左定界符
         * @return
         */
        public String getLeft() {
            return left;
        }

        /**
         * 右定界符
         * @return
         */
        public String getRight() {
            return right;
        }

        /**
         * 检查定界符格式
         * @param delimiter 定界符
         */
        private void check(String delimiter) {
            // 不允许为空
            if (delimiter == null || delimiter.length() < 1) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            // 不可包含空白符
            for (int i = 0; i < delimiter.length(); i++) {
                if (Character.isWhitespace(delimiter.charAt(i))) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            }
        }
    }
}
