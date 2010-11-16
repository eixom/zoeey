/*
 * MoXie (SysTem128@GMail.Com) 2009-4-23 14:29:43
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 内存消耗计量器
 * @author MoXie(SysTem128@GMail.Com)
 */
public class MemoryMeasurer {

    /**
     * 起始内存集合
     */
    private Map<String, Long> startMemoryMap;
    /**
     * 结束内存集合
     */
    private Map<String, Long> stopMemoryMap;
    /**
     *
     */
    private long startMemory = 0L;
    private long stopMemory = 0L;

    /**
     *
     */
    public MemoryMeasurer() {
        startMemoryMap = new HashMap<String, Long>();
        stopMemoryMap = new HashMap<String, Long>();
    }

    private static final long freeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * 获取起始内存
     * @param name
     * @return
     */
    public long getStartMemory(String name) {
        return startMemoryMap.get(name);
    }

    /**
     * 获取起始内存
     * @return
     */
    public long getStartMemory() {
        return startMemory;
    }

    /**
     * 开始计量
     * @param name
     */
    public void start(String name) {
        startMemoryMap.put(name, freeMemory());
    }

    /**
     * 开始计量
     */
    public void start() {
        startMemory = freeMemory();
    }

    /**
     * 获取结束内存
     * @param name
     * @return
     */
    public long getStopMemory(String name) {
        return stopMemoryMap.get(name);
    }

    /**
     * 结束计量
     * @param name
     */
    public void stop(String name) {
        stopMemoryMap.put(name, freeMemory());
    }

    /**
     * 结束计量
     */
    public void stop() {
        stopMemory = freeMemory();
    }

    /**
     * 获取消耗内存
     * @param name
     * @return
     */
    public long spend(String name) {
        return getStartMemory(name) - getStopMemory(name);
    }

    /**
     * <pre>
     * 获取消耗内存的列表
     * [名称, 起始闲余内存, 结束闲余内存, 消耗]
     * [
     *   [runTime_0, 3881344, 3861832, 19512]
     *  , [runTime_1, 3861832, 3861832, 0]
     *  , [runTime_2, 3861832, 3861832, 0]
     *  , [runTime_3, 3861832, 3852192, 9640]
     *  , [runTime_4, 3852192, 3836696, 15496]
     * ]

     * </pre>
     * @return
     */
    public List<List> getSpendMapping() {
        Iterator<Entry<String, Long>> iterator = startMemoryMap.entrySet().iterator();
        Entry<String, Long> entry;

        List<List> listList = new ArrayList<List>();
        List list;
        String key;
        Long val;
        Long _val;
        while (iterator.hasNext()) {
            list = new ArrayList();
            entry = iterator.next();
            key = entry.getKey();
            val = entry.getValue();
            _val = stopMemoryMap.get(key);
            list.add(key);
            list.add(val);
            list.add(_val);
            list.add(val - _val);
            listList.add(list);
        }
        return listList;
    }

    /**
     * 获取消耗内存
     * @return
     */
    public long spend() {
        if (stopMemory == 0L) {
            stop();
        }
        return startMemory - stopMemory;
    }

    /**
     * 清理，清理之后必须重新获取实例才可使用命名计量
     */
    public void clear() {
        startMemoryMap.clear();
        stopMemoryMap.clear();
        startMemoryMap = null;
        stopMemoryMap = null;
    }
}
