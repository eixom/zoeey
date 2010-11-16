/*
 * MoXie (SysTem128@GMail.Com) 2009-9-2 9:39:35
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

import java.util.ArrayList;
import java.util.List;
import org.zoeey.common.Supervisor;
import org.zoeey.loader.fileupload.FileItem;

/**
 * 富化器
 * @author MoXie
 */
public class Richer   {

    /**
     * 单文本字段富化器
     */
    private List<RichSingleAble> richSingleList = null;
    /**
     * 多文本字段富化器
     */
    private List<RichMultiAble> richMultiList = null;
    /**
     * 单文件富化器
     */
    private List<RichFileSingleAble> richFileSingleList = null;
    /**
     * 多文件富化器
     */
    private List<RichFileMultiAble> richFileMultiList = null;

    /**
     * 新增但文本字段富化器
     * @param richSingleAble
     */
    public void add(RichSingleAble richSingleAble) {
        if (richSingleList == null) {
            richSingleList = new ArrayList<RichSingleAble>();
        }
        richSingleList.add(richSingleAble);
    }

    /**
     * 新增多文本字段富化器
     * @param richMultiAble
     */
    public void add(RichMultiAble richMultiAble) {
        if (richMultiList == null) {
            richMultiList = new ArrayList<RichMultiAble>();
        }
        richMultiList.add(richMultiAble);
    }

    /**
     * 新增但文件富化器
     * @param richFileSingleAble
     */
    public void add(RichFileSingleAble richFileSingleAble) {
        if (richFileSingleList == null) {
            richFileSingleList = new ArrayList<RichFileSingleAble>();
        }
        richFileSingleList.add(richFileSingleAble);
    }

    /**
     * 新增多文件富化器
     * @param richFileMultiAble
     */
    public void add(RichFileMultiAble richFileMultiAble) {

        if (richFileMultiList == null) {
            richFileMultiList = new ArrayList<RichFileMultiAble>();
        }
        richFileMultiList.add(richFileMultiAble);
    }

    /**
     * 移除指定的富化器
     * @param richSingleAble
     */
    public void remove(RichSingleAble richSingleAble) {
        richSingleList.remove(richSingleAble);
    }

    /**
     * 移除指定的富化器
     * @param richMultiAble
     */
    public void remove(RichMultiAble richMultiAble) {
        richMultiList.remove(richMultiAble);
    }

    /**
     * 移除指定的富化器
     * @param richFileSingleAble
     */
    public void remove(RichFileSingleAble richFileSingleAble) {
        richFileSingleList.remove(richFileSingleAble);
    }

    /**
     * 移除指定的富化器
     * @param richFileMultiAble
     */
    public void remove(RichFileMultiAble richFileMultiAble) {
        richFileMultiList.remove(richFileMultiAble);
    }

    /**
     * 移除验证某字段的富化器
     * @param field
     */
    public void remove(String field) {
        int i = 0;
        for (i = richSingleList.size() - 1; i >= 0; i--) {
            if (richSingleList.get(i).accept(field)) {
                richSingleList.remove(i);
            }
        }
        for (i = richMultiList.size() - 1; i >= 0; i--) {
            if (richMultiList.get(i).accept(field)) {
                richMultiList.remove(i);
            }
        }
        for (i = richFileSingleList.size() - 1; i >= 0; i--) {
            if (richFileSingleList.get(i).accept(field)) {
                richFileSingleList.remove(i);
            }
        }
        for (i = richFileMultiList.size() - 1; i >= 0; i--) {
            if (richFileMultiList.get(i).accept(field)) {
                richFileMultiList.remove(i);
            }
        }
    }

    /**
     * 富化单文本字段
     * @param field
     * @param value
     * @return
     */
    public String rich(String field, String value) {
        if (richSingleList != null) {
            for (RichSingleAble richSingle : richSingleList) {
                if (richSingle.accept(field)) {
                    richSingle.setLoader(loader);
                    richSingle.setSvisor(svisor);
                    value = richSingle.rich(field,value);
                }
            }
        }
        return value;
    }

    /**
     * 富化多文本字段
     * @param field
     * @param values 
     * @return
     */
    public String[] rich(String field, String[] values) {
        if (richMultiList != null) {
            for (RichMultiAble richMulti : richMultiList) {
                if (richMulti.accept(field)) {
                    richMulti.setLoader(loader);
                    richMulti.setSvisor(svisor);
                    values = richMulti.rich(field,values);
                }
            }
        }
        return values;
    }

    /**
     * 富化单文件字段
     * @param field
     * @param value
     * @return
     */
    public FileItem rich(String field, FileItem value) {
        if (richFileSingleList != null) {
            for (RichFileSingleAble richFileSingle : richFileSingleList) {
                if (richFileSingle.accept(field)) {
                    richFileSingle.setLoader(loader);
                    richFileSingle.setSvisor(svisor);
                    value = richFileSingle.rich(field,value);
                }
            }
        }
        return value;
    }

    /**
     * 富化多文件字段
     * @param field
     * @param values
     * @return
     */
    public FileItem[] rich(String field, FileItem[] values) {
        if (richFileMultiList != null) {
            for (RichFileMultiAble richFileMulti : richFileMultiList) {
                if (richFileMulti.accept(field)) {
                    richFileMulti.setLoader(loader);
                    richFileMulti.setSvisor(svisor);
                    values = richFileMulti.rich(field,values);
                }
            }
        }
        return values;
    }

    /**
     * 读取器
     */
    private Loader loader = null;
    /**
     * 全局监督者
     */
    private Supervisor svisor = null;

    /**
     * 读取器
     * @return  读取器
     */
    public Loader getLoader() {
        return loader;
    }

    /**
     * 读取器
     * @param loader    读取器
     */
    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    /**
     * 全局监督者
     * @return  全局监督者
     */
    public Supervisor getSvisor() {
        return svisor;
    }

    /**
     * 全局监督者
     * @param svisor    全局监督者
     */
    public void setSvisor(Supervisor svisor) {
        this.svisor = svisor;
    }
}
