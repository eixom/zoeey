/*
 * MoXie (SysTem128@GMail.Com) 2009-3-20 6:16:08
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner.js;

import java.io.File;
import org.zoeey.constant.EnvConstants;
import org.zoeey.resource.ResourceExceptionMsg;
import org.zoeey.util.DirInfo;
import org.zoeey.util.FileHelper;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsFile implements Cloneable{

    /**
     * 文件昵称
     */
    private String name = null;
    /**
     * 文件根路径
     * 当文件名以 @ 开头时被忽略
     */
    private String fileRoot = null;
    /**
     * 是否为静态文件 默认为 true
     */
    private boolean isStatic = true;
    /**
     * 是否仅在调试模式时加载
     */
    private boolean isDebug = false;
    /**
     * 文件路径 {@code <file>path</file>}
     */
    private String path = null;
    /**
     * 文件绝对路径
     */
    private String filePath = null;
    /**
     *  字符集
     */
    private String charset = EnvConstants.CHARSET;

    /**
     * 文件路径
     * @param path
     */
    public JsFile(String path) {
        this.path = path;
    }

    /**
     * <pre>
     * 最终路径 由 fileRoot和 path 组合或path本身
     * 注意以 @ 开头的数据完全保留 @ 后的数据原型
     * </pre>
     * @return
     * @throws JsFileException
     */
    public String getFilePath() throws JsFileException {

        boolean isAbs = false;
        if (filePath == null) {
            do {
                if (path == null || path.length() < 1) {
                    throw new JsFileException(ResourceExceptionMsg.JSCONTAINER_INCORRECTFILENAME_EXCEPTION);
                }
                isAbs = path.startsWith("@");
                if (isAbs) {
                    if (path.length() > 1) {
                        path = path.substring(1);
                    } else {
                        throw new JsFileException(ResourceExceptionMsg.JSCONTAINER_INCORRECTFILENAME_EXCEPTION);
                    }
                }
                if (fileRoot != null && !isAbs) {
                    filePath = fileRoot + path;
                } else {
                    filePath = path;
                }
            } while (false);
        }
        if (true) {
            filePath = FileHelper.backToslash(filePath);
            filePath = filePath.replaceAll("\\{classesDir\\}", DirInfo.getClassesDir())//
                    .replaceAll("\\{webDir\\}", DirInfo.getWebDir()) //
                    .replaceAll("\\{webInfoDir\\}", DirInfo.getWebInfoDir());
        }
        return filePath;
    }

    /**
     *
     * @param path
     * @return
     */
    public JsFile setPath(String path) {
        this.path = path;
        return this;
    }

    /**
     *
     * @return
     */
    public String getFileRoot() {
        return fileRoot;
    }

    /**
     *
     * @param fileRoot
     * @return
     */
    public JsFile setFileRoot(String fileRoot) {
        this.fileRoot = fileRoot;
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isIsDebug() {
        return isDebug;
    }

    /**
     *
     * @param isDebug
     * @return
     */
    public JsFile setIsDebug(boolean isDebug) {
        return this;
    }

    /**
     *
     * @return
     */
    public boolean isIsStatic() {
        return isStatic;
    }

    /**
     *
     * @param isStatic
     * @return
     */
    public JsFile setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
        return this;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * @return
     */
    public JsFile setName(String name) {
        this.name = name;
        return this;
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     *
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     *
     * @return
     * @throws JsFileException
     */
    public File toFile() throws JsFileException {
        return new File(getFilePath());
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append('[');
        // name
        strBuilder.append("{name:\"").append(name).append("\"},");
        // path
        strBuilder.append("{path:\"").append(path).append("\"},");
        // fileRoot
        strBuilder.append("{fileRoot:\"").append(fileRoot).append("\"},");
        // isStatic
        strBuilder.append("{isStatic:").append(isStatic).append("},");
        // isDebug
        strBuilder.append("{isDebug:").append(isDebug).append("},");
        // charset
        strBuilder.append("{charset:").append(charset).append('}');
        strBuilder.append(']');
        return strBuilder.toString();
    }

    @Override
    public Object clone() {
        JsFile jsFile = new JsFile(path);
        jsFile.setCharset(charset);
        jsFile.setFileRoot(fileRoot);
        jsFile.setIsDebug(isDebug);
        jsFile.setIsStatic(isStatic);
        jsFile.setName(name);
        return jsFile;
    }
}
