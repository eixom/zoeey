/*
 * MoXie (SysTem128@GMail.Com) 2009-8-6 14:08:06
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.dispatch;

import java.util.ArrayList;
import java.util.List;
import org.zoeey.util.StringHelper;

/**
 * 路由配置
 * @author MoXie
 */
class DispatchConfig {

    /**
     * 匹配模式是否区分大小写
     */
    private boolean ignoreCase = false;
    /**
     * 注解声明匹配模式
     */
    private List<String> annotList;
    /**
     * 仅需要初始化的发布类名称列表
     */
    private List<String> initList;
    /**
     * 需要初始化和发布的类名称列表
     */
    private List<Entry> publishList;

    /**
     *  路由配置
     */
    public DispatchConfig() {
        annotList = new ArrayList<String>();
        initList = new ArrayList<String>();
        publishList = new ArrayList<Entry>();
    }

    /**
     * 匹配模式是否区分大小写
     * @return
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    /**
     * 设置匹配模式是否区分大小写
     * @param ignoreCase
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * 添加注解声明的发布类
     * @param annot
     */
    public void addAnnot(String annot) {
        annot = StringHelper.trim(annot);
        if (!annotList.contains(annot)) {
            annotList.add(StringHelper.trim(annot));
        }
    }

    /**
     * 添加仅需要初始化（init）的类名
     * @param init
     */
    public void addInit(String init) {
        init = StringHelper.trim(init);
        if (!initList.contains(init)) {
            initList.add(init);
        }
    }

    /**
     * 新增需要发布的条目
     * @param pattern   匹配模式
     * @param publish   发布类名称
     */
    public void addPublish(String pattern, String publish) {
        publishList.add(new Entry(pattern, publish));
    }

    /**
     * 获取匹配模式在注解内的条目
     * @return
     */
    public List<String> getAnnotList() {
        return annotList;
    }

    /**
     * 获取仅需初始化的条目
     * @return
     */
    public List<String> getInitList() {
        return initList;
    }

    /**
     * 获取需发布的条目
     * @return
     */
    public List<Entry> getPublishList() {
        return publishList;
    }

    /**
     * 路由条目配置
     */
    static class Entry {

        /**
         * 匹配模式
         */
        private String pattern;
        /**
         * 发布类名称
         */
        private String publish;

        /**
         * 路由条目配置
         * @param pattern   匹配模式
         * @param publish   发布类名称
         */
        public Entry(String pattern, String publish) {
            this.pattern = StringHelper.trim(pattern);
            this.publish = StringHelper.trim(publish);
        }

        /**
         * 匹配模式
         * @return  匹配模式
         */
        public String getPattern() {
            return pattern;
        }

        /**
         * 发布类名称
         * @return  发布类名称
         */
        public String getPublish() {
            return publish;
        }
    }
}
