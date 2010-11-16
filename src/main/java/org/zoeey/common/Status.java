/*
 * MoXie (SysTem128@GMail.Com) 2009-7-24 10:01:24
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common;

/**
 * 状况
 * @author MoXie
 */
public class Status {

    /**
     * 状况名称
     */
    private String name = null;
    /**
     * 状况描述
     */
    private String brief = null;
    /**
     * 状况标志
     */
    private int sign = -1;

    /**
     * 状况
     */
    public Status() {
    }

    /**
     * 状况
     * @param name  状况名称
     * @param brief 状况描述
     * @param sign  状况标志
     */
    public Status(String name, String brief, int sign) {
        this.name = name;
        this.brief = brief;
        this.sign = sign;
    }

    /**
     * 状况描述
     * @return
     */
    public String getBrief() {
        return brief;
    }

    /**
     * 设置状况描述
     * @param brief 描述
     */
    public void setBrief(String brief) {
        this.brief = brief;
    }

    /**
     * 获取状况名称
     * @return  状况名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置状况名称
     * @param name  状况名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取状况标志
     * @return  状况标志
     */
    public int getSign() {
        return sign;
    }

    /**
     * 设置状况标志
     * @param sign  状况标志
     */
    public void setSign(int sign) {
        this.sign = sign;
    }
}
