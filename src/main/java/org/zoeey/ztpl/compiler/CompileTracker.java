/*
 * MoXie (SysTem128@GMail.Com) 2009-4-30 22:09:44
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import java.util.HashMap;
import java.util.Map;
import org.zoeey.ztpl.TemplateAble;

/**
 * 编译跟踪器
 * 用于管理publish方法内的局部变量
 * @author MoXie(SysTem128@GMail.Com)
 */
public class CompileTracker {

    /**
     * 局部变量索引位置
     * 1、2和3号位置分别被
     * {@link TemplateAble#publish(java.io.Writer, java.util.Map, org.zoeey.ztpl.Ztpl)  publish()}
     * 的三个参数
     * {@link ByteCodeHelper#VAR_INDEX_WRITER writer}、
     * {@link ByteCodeHelper#VAR_INDEX_PARAMSMAP paramsMap}和
     * {@link ByteCodeHelper#VAR_INDEX_ZTPL ztpl} 使用
     */
    private int varPos;
    /**
     * 局部变量索引表
     */
    private Map<String, Integer> varMap;

    /**
     *
     * @param mv
     */
    public CompileTracker() {
        varPos = ByteCodeHelper.VAR_INDEX_ZTPL;
        varMap = new HashMap<String, Integer>();
    }

    /**
     * 上一变量索引位置
     * @return
     */
    public int prev() {
        return varPos - 1;
    }

    /**
     * 下一变量索引位置
     * @return
     */
    public int next() {
        varPos++;
        return varPos;
    }

    /**
     * 当前变量索引位置
     * @return
     */
    public int current() {
        return varPos;
    }

    /**
     * 检查局部变量是否存在
     * @param key 变量名
     * @return
     */
    public boolean exists(String key) {
        return varMap.containsKey(key);
    }

    /**
     * 获取局部变量索引
     * @param key 变量名
     * @return
     */
    public Integer indexOf(String key) {
        return varMap.get(key);
    }
}
