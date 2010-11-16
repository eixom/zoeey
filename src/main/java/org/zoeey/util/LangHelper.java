/*
 * MoXie (SysTem128@GMail.Com) 2009-8-10 16:01:18
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import org.zoeey.common.ZObject;
import org.zoeey.constant.EnvConstants;

/**
 * <pre>
 * 语言帮助
 * 作为国际化底层资源调用者出现
 * </pre>
 * @author MoXie
 */
public class LangHelper {

    /**
     * 锁定创建
     */
    private LangHelper() {
    }
    /**
     * 语言包根目录
     */
    private static String rootDir = null;
    /**
     * 文件编码
     */
    private static String charset = EnvConstants.CHARSET;

    /**
     * 设置语言包目录
     * @param langRootDir
     */
    public static void setRootDir(String langRootDir) {
        synchronized (LangHelper.class) {
            LangHelper.rootDir = langRootDir;
        }
    }

    /**
     * 获取语言包目录
     * @return
     */
    public static String getRootDir() {
        return rootDir;
    }

    /**
     * 获取语言包读取编码
     * @return
     */
    public static String getCharset() {
        return charset;
    }

    /**
     * 设置语言包读取编码
     * @param charset
     */
    public static void setCharset(String charset) {
        LangHelper.charset = charset;
    }

    /**
     * 获取语句，使用基本键值
     * @param relativePath
     * @param key
     * @return
     */
    public static String say(String relativePath, String key) {
        try {
            File file = new File(rootDir + relativePath);
            String text = TextFileHelper.read(file, charset);
            return KeyValueFile.toMap(text).get(key);
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * 获取语句，使用format。
     * @param relativePath
     * @param key
     * @param objs
     * @return
     */
    public static String say(String relativePath, String key, Object... objs) {
        String lang = say(relativePath, key);
        if (lang == null) {
            return null;
        }
        return String.format(lang, objs);
    }

    /**
     * <pre>
     * 获取语句，使用Map替换${key}为内容
     * 注意：如字符串内包含 ${key} 型字段表达式，但Map中不存在此键则此表达式将予以保留
     * </pre>
     * @param relativePath
     * @param key
     * @param map
     * @return
     */
    public static String say(String relativePath, String key, Map<String, Object> map) {
        String lang = say(relativePath, key);
        if (lang == null) {
            return null;
        }
        for (Entry<String, Object> entry : map.entrySet()) {
            lang = lang.replaceAll(StringHelper.regexEscape("${" + entry.getKey() + "}"), //
                    ZObject.conv(entry.getValue()).toString());
        }
        return lang;
    }
}
