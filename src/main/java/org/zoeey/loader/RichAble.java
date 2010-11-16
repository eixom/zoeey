/*
 * MoXie (SysTem128@GMail.Com) 2009-9-2 9:23:11
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.loader;

import org.zoeey.common.Supervisor;

/**
 * 富化器接口（通常富化类继承{@link org.zoeey.loader.RichBase RicherBase}便可）
 * @author MoXie
 */
public interface RichAble {

    /**
     * 判断是否需要富化
     * @param fieldName 字段名
     * @return
     */
    public boolean accept(String fieldName);

    /**
     * 获取数据读取器
     * @return
     */
    public Loader getLoader();

    /**
     * 载入数据读取器
     * @param loader
     */
    public void setLoader(Loader loader);

    /**
     * 获取全局监控者
     * @return
     */
    public Supervisor getSvisor();

    /**
     * 设置全局监控者
     * @param svisor    全局监控者
     */
    public void setSvisor(Supervisor svisor);
}

