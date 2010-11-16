/*
 * MoXie (SysTem128@GMail.Com) 2009-3-20 6:27:16
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner.js;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.zoeey.common.Version;
import org.zoeey.constant.EnvConstants;
import org.zoeey.util.EncryptHelper;
import org.zoeey.util.EnvInfo;
import org.zoeey.util.FileHelper;
import org.zoeey.util.TextFileHelper;
import org.zoeey.util.combiner.TextCombiner;

/**
 * Js合并加载器
 * 注意：文件路径不需以分隔符结尾。
 *      
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsLoader {

    /**
     * 缓存目录
     */
    private String cacheDirRoot = null;
    /**
     * 键列表
     */
    private List<String> nameList = null;
    /**
     * 已验证键列表
     */
    private List<String> valiedNameList = null;
    /**
     * 载入的键是否已验证
     */
    private boolean hasNameValied = false;
    /**
     * 压缩级别
     */
    private int compress = 0;
    /**
     * Js资源
     */
    private JsContext context = null;
    /**
     * 是否显示调试信息
     */
    private boolean isDebug = false;

    /**
     *
     */
    public JsLoader() {
        cacheDirRoot = EnvConstants.DEFAULT_TEMP_DIR;
        nameList = new ArrayList<String>();
        valiedNameList = new ArrayList<String>();
    }

    /**
     * 设置配置文件
     * @param configFile
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     */
    public void setConfig(File configFile) throws SAXException,
            ParserConfigurationException,
            IOException {
        context = loadConfig(configFile);
    }

    /**
     * 读取配置
     * 
     * @param configFile  配置文件
     * @return
     * @throws org.xml.sax.SAXException  xml 分析异常
     * @throws javax.xml.parsers.ParserConfigurationException   获取 DocumentBuilderFactory 异常
     * @throws java.io.IOException
     */
    public static JsContext loadConfig(File configFile)
            throws SAXException,
            ParserConfigurationException,
            IOException {
        JsConfigParser jscParser = new JsConfigParser();
        jscParser.parse(configFile);
        return jscParser.getContext();
    }

    /**
     * Js文件资源
     * @return
     */
    public JsContext getContext() {
        return context;
    }

    /**
     * 验证名称列表
     * @return
     */
    public boolean isNamesValied() {
        return getValiedNameList().size() == nameList.size();
    }

    /**
     * 获取已验证、排序的名称列表
     * @return
     */
    public List<String> getValiedNameList() {
        if (hasNameValied || context == null) {
            return valiedNameList;
        }
        List<String> _nameList = context.getNameList();
        if (_nameList.size() < nameList.size()) {
            for (String name : _nameList) {
                if (nameList.contains(name)) {
                    valiedNameList.add(name);
                }
            }
        } else {
            for (String name : nameList) {
                if (_nameList.contains(name)) {
                    valiedNameList.add(name);
                }
            }
        }
        /**
         * bugfixed:按照命名顺序进行Js加载
         */
//        Collections.sort(valiedNameList);
        hasNameValied = true;
        return valiedNameList;
    }

    /**
     * 设置Js文件资源
     * @param context
     * @see #setConfig(java.io.File)
     */
    public void setContext(JsContext context) {
        this.context = context;
    }

    /**
     * 是否显示文件信息
     * @return
     */
    public boolean isDebug() {
        return isDebug;
    }

    /**
     * 设置是否显示文件信息
     * @param isDebug
     */
    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * 获取 缓存目录
     * @return
     */
    public String getCacheDirRoot() {
        return cacheDirRoot;
    }

    /**
     * 设置 缓存目录
     * @param cacheDirRoot
     */
    public void setCacheDirRoot(String cacheDirRoot) {
        this.cacheDirRoot = cacheDirRoot;
    }

    /**
     * <pre>
     * 从字符串里把key分析出来
     * articleEdit,product_Edit -> ["articleEdit","product_Edit"]
     * </pre>
     * @param key
     * @return
     */
    private List<String> parseKeys(String key) {
        String[] keys = new String[0];
        if (key != null) {
            keys = key.split(",");
        }
        return Arrays.asList(keys);
    }

    /**
     * 添加资源项名称
     * @param name
     */
    public void merge(String name) {
        hasNameValied = false;
        nameList.add(name);
    }

    /**
     * 添加资源项名称
     * @param names
     */
    public void merge(String[] names) {
        hasNameValied = false;
        for (String name : names) {
            nameList.add(name);
        }
    }

    /**
     * 添加资源项名称
     * @param nameList
     */
    public void merge(List<String> nameList) {
        hasNameValied = false;
        this.nameList.addAll(nameList);
    }

    /**
     * 取消资源项名称
     * @param name
     */
    public void remove(String name) {
        hasNameValied = false;
        nameList.remove(name);
    }

    /**
     * 取消资源项名称
     * @param names
     */
    public void remove(String[] names) {
        hasNameValied = false;
        nameList.removeAll(Arrays.asList(names));
    }

    /**
     * 取消资源项名称
     * @param nameList
     */
    public void remove(List<String> nameList) {
        hasNameValied = false;
        nameList.removeAll(nameList);
    }

    /**
     * 检查是否超时 是否需要更新缓存
     * @param cacheTimeout 超时时间，单位秒。为零时永不过期。
     * @param file
     * @return
     * <pre>
     * 返回{@code true}的情况：
     *    1）文件不存在。
     *    2）最后修改时间到现在超过限时；
     *  返回{@code false} 的情况：
     *    1）超时时间为 0；
     *    2）最后修改时间到现在未超时；
     * </pre>
     */
    private static boolean isTimeout(int cacheTimeout, File file) {
        boolean isTimeout = true;
        do {
            /**
             * 文件不存在
             * 不允许缓存
             * 未超时
             * 超时
             */
            if (!file.exists()) {
                break;
            }
            if (cacheTimeout == 0) {
                isTimeout = false;
                break;
            }
            if (cacheTimeout < 0) {
                isTimeout = true;
                break;
            }
            if (file != null && file.canRead()) {
                // 最后修改时间到现在时差 与 超时时间比较
                isTimeout = (System.currentTimeMillis() - file.lastModified()) > (cacheTimeout * 1000);
                break;
            }
        } while (false);
        return isTimeout;
    }

    /**
     * 生成文件，文件名过长将会被转换为其md5
     * @return
     */
    private File genFile(boolean isVali) {
        StringBuilder strBuilder = new StringBuilder(100);
        List<String> _nameList = null;
        if (isVali) {
            getValiedNameList();
            _nameList = valiedNameList;
        } else {
            _nameList = nameList;
        }
        for (String str : _nameList) {
            strBuilder.append(str);
            strBuilder.append('/');
        }
        String fileName = strBuilder.toString();
        if (fileName.length() > 200) {
            fileName = EncryptHelper.md5(fileName);
        }
        strBuilder = new StringBuilder(255);
        strBuilder.append(getCacheDirRoot());
        strBuilder.append(EnvInfo.getFileSeparator());
        strBuilder.append(FileHelper.fileNameEscape(fileName, '_', true));
        strBuilder.append("_" + _nameList.size());
        strBuilder.append(".js");

        return new File(strBuilder.toString());
    }

    /**
     * 获取合并完毕的文件
     * @param cacheTimeout 缓存更新周期，单位秒。 为 0 时不缓存，可以用于调试。
     * @return
     * @throws IOException
     * @throws JsFileException
     */
    public File fetch(int cacheTimeout)
            throws IOException, JsFileException {
        File file = null;
        file = genFile(false);
        if (!isTimeout(cacheTimeout, file)) {
            return file;
        }
        file = genFile(true);
        if (isTimeout(cacheTimeout, file)) {
            combinText().write(file);
        }
        return file;
    }

    /**
     * <pre>
     * 显示读取的Js文件
     * 注意：
     * 合并文件名由文件昵称拼合而成，且当文件名长度超过200时会被忽略后面的字符。
     * </pre>
     * @param out
     * @param cacheTimeout 缓存更新周期，单位秒。为 0 时不缓存，可以用于调试。
     * @throws IOException
     * @throws JsFileException
     */
    public void display(Writer out, int cacheTimeout)
            throws IOException, JsFileException {
        File file = fetch(cacheTimeout);
        out.write(TextFileHelper.read(file, context.getCharset()));
    }

    /**
     * 链接多个JsFile
     * @param txtComb
     * @param jsFileList
     * @throws org.zoeey.util.combiner.js.JsFileException
     * @throws java.io.IOException
     */
    private void combJsFileList(TextCombiner txtComb, List<JsFile> jsFileList) throws JsFileException, IOException {
        for (JsFile jsFile : jsFileList) {
            txtComb.concat(jsFile.toFile(), jsFile.getCharset());
        }
    }
    private static final String lineSep = EnvInfo.getLineSeparator();

    /**
     *  合并文件
     * @param nameList
     * @return
     * @throws IOException
     * @throws JsFileException
     */
    private TextCombiner combinText()
            throws IOException, JsFileException {
        TextCombiner txtComb = new TextCombiner();
        txtComb.setCharset(context.getCharset());
        do {
            JsFile jsFile;
            if (isDebug) {
                txtComb.concat("/*  ");
                txtComb.concat(Version.VERSION);
                txtComb.concat(" , Js Loader */");
                txtComb.setBoundary(lineSep + "/* {file}file: {fileName},{/file}date: {date} */" + lineSep);
            } else {
                txtComb.setBoundary(lineSep);
            }
            /**
             * 加载强制目录
             */
            combJsFileList(txtComb, context.getForceFileList());
            Map<String, JsFile> namedFileMap = context.getNamedFileMap();
            Map<String, List<JsFile>> groupFileMap = context.getGroupFileMap();
            List<JsFile> safeFileList = context.getSafeRootList();
            Map<String, JsFile> safeFileMap = new HashMap<String, JsFile>();
            /**
             * 安全目录文件加载
             */
            for (JsFile _jsFile : safeFileList) {
                for (File file : FileHelper.listFilesRecusive(new File(_jsFile.getFilePath()))) {
                    jsFile = (JsFile) _jsFile.clone();
                    jsFile.setFileRoot(null).setPath(file.getAbsolutePath());
                    safeFileMap.put(file.getName(), jsFile);
                }
            }
            String fileName = null;
            JsFile _jsFile = null;
            for (String name : valiedNameList) {
                /**
                 * 加载命名的文件
                 */
                if (namedFileMap.containsKey(name)) {
                    if (isDebug) {
                        txtComb.concat("/* name: ");
                        txtComb.concat(name);
                        txtComb.concat(" */");
                    }
                    jsFile = namedFileMap.get(name);
                    txtComb.concat(new File(jsFile.getFilePath()), jsFile.getCharset());
                    continue;
                }
                /**
                 * 分组命名的文件
                 */
                if (groupFileMap.containsKey(name)) {
                    if (isDebug) {
                        txtComb.concat(lineSep + "/* groupName: " + name + " */");
                    }
                    combJsFileList(txtComb, groupFileMap.get(name));
                    continue;
                }
                /**
                 * 安全目录文件加载
                 */
                if (name.startsWith("@")) {
                    fileName = name.substring(1);
                    _jsFile = safeFileMap.get(fileName);
                    if (_jsFile != null) {
                        txtComb.concat(new File(_jsFile.getFilePath()), _jsFile.getCharset());
                    }
                }
            }
        } while (false);
        return txtComb;
    }

    /**
     *
     * @param cacheDir  缓存路径,相对于缓存目录
     */
    public void cleanCache(String cacheDir) {
        /**
         * 获取绝对路径
         * 递归式的删除所有缓存文件
         */
        FileHelper.tryDelete(new File(FileHelper.getAbsolutePath(cacheDir, cacheDirRoot, 0)), true);
    }

    /**
     * 关闭
     * 注意：在Serlvet中当JsLoader为成员变量时可能造成数据污染，需要在退出前关闭JsLoader。
     */
    public void close() {
        if (nameList != null) {
            nameList.clear();
        }
        if (valiedNameList != null) {
            valiedNameList.clear();
        }
        hasNameValied = false;
    }
}
