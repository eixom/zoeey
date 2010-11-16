/*
 * MoXie (SysTem128@GMail.Com) 2010-1-22 15:39:14
 * 
 * Copyright &copy; 2008-2010 Zoeey.Org . All rights are reserved.
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

/**
 * 模板解析检查器，负责管理异常信息
 * @author MoXie
 */
public class Overlooker {

    private String tplName = null;
    private String msg = null;

    public Overlooker(String tplName) {
        this.tplName = tplName;
    }
    /**
     * 状态
     */
    private boolean pass = true;
    /**
     * 列
     */
    private int column = -1;

    /**
     * 是否通过验证
     * @return
     */
    public boolean isPass() {
        return pass;
    }

    /**
     * 列位置
     * @return
     */
    public int getColumn() {
        return column;
    }

    /**
     * 模板名
     * @return
     */
    public String getTplName() {
        return tplName;
    }

    /**
     * 标记错误
     * @param index
     */
    void disPass(int column) {
        System.out.println(column);
        if (this.pass == true) {
            this.pass = false;
            this.column = column;
        }
        pass = false;
    }

    /**
     * 错误提示
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置错误提示
     * @param msg
     */
    void setMsg(String msg) {
        this.msg = msg;
    }
}
