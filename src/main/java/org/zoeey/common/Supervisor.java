/*
 * MoXie (SysTem128@GMail.Com) 2009-7-23 14:32:20
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局监督者<br />
 * 用于监督追踪处理流程
 * @author MoXie
 */
public class Supervisor {

    /**
     * 结论
     */
    private int conclusion = 0;
    /**
     * 消息队列
     */
    private List<Status> statusList;
    /**
     * 消息前缀
     */
    private String prefix = null;

    /**
     * 全局监督者
     */
    public Supervisor() {
        statusList = new ArrayList<Status>();
    }

    /**
     * 全局监督者
     * @param prefix 状况名称前缀
     */
    public Supervisor(String prefix) {
        statusList = new ArrayList<Status>();
        this.prefix = prefix;
    }

    /**
     * 新消息
     * @param name  状况名称
     */
    public void addStatus(String name) {
        addStatus(name, null, 0);
    }

    /**
     * 新消息
     * @param name  状况名称
     * @param biref 状况描述
     */
    public void addStatus(String name, String biref) {
        addStatus(name, biref, 0);
    }

    /**
     * 新消息
     * @param name  状况名称
     * @param biref 状况描述
     * @param sign  状况标识
     */
    public void addStatus(String name, String biref, int sign) {
        if (name != null) {
            statusList.add(new Status((prefix == null ? "" : prefix) + name, biref, sign));
        }
    }

    /**
     * 设定结论
     * @param conclusion  结论
     * @see Conclusions  
     */
    public void setConclusion(int conclusion) {
        this.conclusion = conclusion;
    }

    /**
     * 获取当前结论
     * @return  结论
     */
    public int getConclusion() {
        return this.conclusion;
    }

    /**
     * 判断当前结论
     * @param conclusion  结论
     * @see Conclusions  
     * @return 结论
     */
    public boolean isConclusion(int conclusion) {
        return this.conclusion == conclusion;
    }

    /**
     * 获取消息前缀
     * @return  消息前缀
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 设定消息前缀
     * @param prefix    消息前缀
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 获取状态列表
     * @return  所有状态列表
     */
    public List<Status> getStatusList() {
        return getStatusList(new StatusFileter() {

            public boolean accept(Status status) {
                return true;
            }
        });
    }

    /**
     * 获取消息队列
     * @param fileter
     * @return
     */
    public List<Status> getStatusList(StatusFileter fileter) {
        List<Status> _statusList = new ArrayList<Status>();
        if (fileter == null) {
            return getStatusList();
        }
        for (Status _status : statusList) {
            if (fileter.accept(_status)) {
                _statusList.add(_status);
            }
        }
        return _statusList;
    }

    /**
     * 设置消息队列
     * @param statusList
     */
    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    /**
     * 获取第一条消息，没有消息时返回 null
     * @return
     */
    public Status getFirstStatus() {
        if (!statusList.isEmpty()) {
            return statusList.get(0);
        }
        return null;
    }

    /**
     * 获取最后一条消息，没有消息时返回 null
     * @return
     */
    public Status getLastStatus() {
        if (!statusList.isEmpty()) {
            return statusList.get(statusList.size() - 1);
        }
        return null;
    }

    /**
     * 获取状况描述
     * @param name 状态名
     * @return
     */
    public String getBrief(String name) {
        for (Status _status : statusList) {
            if (_status.getName().equals(name)) {
                return _status.getBrief();
            }
        }
        return null;
    }

    /**
     * 获取状况级别
     * @param name 状态名
     * @return
     */
    public long getSign(String name) {
        for (Status _status : statusList) {
            if (_status.getName().equals(name)) {
                return _status.getSign();
            }
        }
        return -1;
    }

    /**
     * 获取状况列表
     * @return
     */
    public List<String> getBriefList() {
        return getBriefList(new StatusFileter() {

            public boolean accept(Status status) {
                return true;
            }
        });
    }

    /**
     * 获取状况列表
     * @param fileter 过滤
     * @return
     */
    public List<String> getBriefList(StatusFileter fileter) {
        if (!(fileter != null)) {
            return getBriefList();
        }
        List<String> briefList = new ArrayList<String>();
        for (Status _status : statusList) {
            if (fileter.accept(_status)) {
                briefList.add(_status.getBrief());
            }
        }
        return briefList;
    }

    /**
     * 获取状况名称列表
     * @return
     */
    public List<String> getNameList() {
        return getNameList(new StatusFileter() {

            public boolean accept(Status status) {
                return true;
            }
        });
    }

    /**
     * 获取状况名号曾列表
     * @param fileter 过滤
     * @return
     */
    public List<String> getNameList(StatusFileter fileter) {
        if (!(fileter != null)) {
            return getNameList();
        }
        List<String> nameList = new ArrayList<String>();
        for (Status _status : statusList) {
            if (fileter.accept(_status)) {
                nameList.add(_status.getName());
            }
        }
        return nameList;
    }

    /**
     * 排查状况
     * @return
     */
    public boolean hasStatus() {
        return hasStatus(new StatusFileter() {

            public boolean accept(Status status) {
                return true;
            }
        });
    }

    /**
     * 排查状况
     * @param fileter 状况过滤
     * @return  状况列表中是否包含过滤器所通过的状况
     */
    public boolean hasStatus(StatusFileter fileter) {
        if (!(fileter != null)) {
            return hasStatus();
        }
        for (Status _status : statusList) {
            if (fileter.accept(_status)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断状态是否存在
     * @param name
     * @return
     */
    public boolean hasStatus(String name) {
        for (Status _status : statusList) {
            if (_status.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清理状况记录
     */
    public void close() {
        statusList = null;
    }

    /**
     * 状况过滤器，常用于过滤 sign，取出需要进行提示的状况
     */
    public static interface StatusFileter {

        /**
         * 判断是否适合
         * @param status
         * @return
         */
        boolean accept(Status status);
    }
}
