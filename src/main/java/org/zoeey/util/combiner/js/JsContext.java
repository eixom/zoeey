/*
 * MoXie (SysTem128@GMail.Com) 2009-3-20 7:09:00
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util.combiner.js;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zoeey.constant.EnvConstants;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class JsContext {

    /**
     * 根路径
     */
    private String fileRoot = null;
    /**
     * 安全目录列表
     */
    private List<JsFile> safeRootList;
    /**
     * 强制文件列表
     */
    private List<JsFile> forceFileList;
    /**
     * 跳转列表
     */
    private Map<String, JsFile> redirectMap;
    /**
     * 分组命名的文件
     */
    private Map<String, List<JsFile>> groupFileMap;
    /**
     * 单独命名的文件
     */
    private Map<String, JsFile> singleFileMap;
    /**
     * 受到命名的文件列表
     */
    private Map<String, JsFile> namedFileMap;
    private String charset = EnvConstants.CHARSET;

    /**
     *
     */
    public JsContext() {
        groupFileMap = new HashMap<String, List<JsFile>>();
        singleFileMap = new HashMap<String, JsFile>();
        safeRootList = new ArrayList<JsFile>();
        forceFileList = new ArrayList<JsFile>();
        redirectMap = new HashMap<String, JsFile>();
        namedFileMap = new HashMap<String, JsFile>();
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
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     *
     * @param fileRoot
     */
    public void setFileRoot(String fileRoot) {
        this.fileRoot = fileRoot;
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
     */
    public List<JsFile> getForceFileList() {
        return forceFileList;
    }

    /**
     *
     * @param jsFile
     */
    public void addForceFile(JsFile jsFile) {
        this.forceFileList.add(jsFile);
    }

    /**
     * 组文件列表
     * @return
     */
    public Map<String, List<JsFile>> getGroupFileMap() {
        return groupFileMap;
    }

    /**
     * 设置文件组
     * @param name
     * @param jsFileList
     */
    public void addGroupFile(String name, List<JsFile> jsFileList) {
        this.groupFileMap.put(name, jsFileList);
    }

    /**
     * 设置组文件
     * @param name
     * @param jsFile
     */
    public void addGroupFile(String name, JsFile jsFile) {
        List<JsFile> jsFileList = this.groupFileMap.get(name);
        if (jsFileList == null) {
            jsFileList = new ArrayList<JsFile>();
            jsFileList.add(jsFile);
            this.groupFileMap.put(name, jsFileList);
        } else {
            jsFileList.add(jsFile);
        }

    }

    /**
     * 设置分组文件
     * @param groupFileMap
     */
    public void setGroupFileMap(Map<String, List<JsFile>> groupFileMap) {
        this.groupFileMap = groupFileMap;
    }

    /**
     *
     * @return
     */
    public Map<String, JsFile> getRedirectList() {
        return redirectMap;
    }

    /**
     *
     * @param jsFile
     */
    public void addRedirect(JsFile jsFile) {
        this.redirectMap.put(jsFile.getName(), jsFile);
    }

    /**
     *
     * @param redirectMap
     */
    public void setRedirectMap(Map<String, JsFile> redirectMap) {
        this.redirectMap = redirectMap;
    }

    /**
     *
     * @return
     */
    public List<JsFile> getSafeRootList() {
        return safeRootList;
    }

    /**
     *
     * @param jsFile
     */
    public void addSafeRootList(JsFile jsFile) {
        this.safeRootList.add(jsFile);
    }

    /**
     *
     * @param forceFileList
     */
    public void setForceFileList(List<JsFile> forceFileList) {
        this.forceFileList = forceFileList;
    }

    /**
     *
     * @param redirectList
     */
    public void setRedirectList(List<JsFile> redirectList) {
        Map<String, JsFile> redMap = new HashMap<String, JsFile>(redirectList.size());
        for (JsFile jsFile : redirectList) {
            redMap.put(jsFile.getName(), jsFile);
        }
        this.redirectMap = redMap;
    }

    /**
     *
     * @param safeRootList
     */
    public void setSafeRootList(List<JsFile> safeRootList) {
        this.safeRootList = safeRootList;
    }

    /**
     * 获取单独文件列表
     * @return
     */
    public Map<String, JsFile> getSingleFileMap() {
        return singleFileMap;
    }

    /**
     * 新增单独文件
     * @param name
     * @param jsFile
     */
    public void addSingleFileMap(String name, JsFile jsFile) {
        this.singleFileMap.put(name, jsFile);
    }

    /**
     * 设置单独文件列表
     * @param singleFileMap
     */
    public void setSingleFileMap(Map<String, JsFile> singleFileMap) {
        this.singleFileMap = singleFileMap;
    }

    /**
     *
     * @param singleFileList
     */
    public void setSingleFileMap(List<JsFile> singleFileList) {
        Map<String, JsFile> singleMap = new HashMap<String, JsFile>(singleFileList.size());
        for (JsFile jsFile : singleFileList) {
            singleMap.put(jsFile.getName(), jsFile);
        }
        this.singleFileMap = singleMap;
    }

    /**
     * 被命名的文件列表
     * @return
     */
    public Map<String, JsFile> getNamedFileMap() {
        return namedFileMap;
    }

    /**
     * 命名列表
     * @return
     */
    public List<String> getNameList() {
        List<String> nameList = new ArrayList();
        nameList.addAll(namedFileMap.keySet());
        nameList.addAll(groupFileMap.keySet());
        return nameList;
    }

    /**
     * 增加被命名的文件
     * @param jsFile
     */
    public void addNamedFile(JsFile jsFile) {
        namedFileMap.put(jsFile.getName(), jsFile);
    }

    /**
     * 设置被命名的文件Map
     * @param namedFileMap
     */
    public void setNamedFileMap(Map<String, JsFile> namedFileMap) {
        this.namedFileMap = namedFileMap;
    }
}
