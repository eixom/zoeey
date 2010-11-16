/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
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
 * 时间消耗计量器
 * @author MoXie(SysTem128@GMail.Com)
 */
public final class TimeMeasurer {

    /**
     * 起始时间集合
     */
    private Map<String, Long> startTimeMap;
    /**
     * 结束时间集合
     */
    private Map<String, Long> stopTimeMap;
    /**
     * 起始时间
     */
    private long startTime = 0L;
    /**
     * 结束时间
     */
    private long stopTime = 0L;

    /**
     *
     */
    public TimeMeasurer() {
        startTimeMap = new HashMap<String, Long>();
        stopTimeMap = new HashMap<String, Long>();
    }

    /**
     * 获取起始时间
     * @param name
     * @return
     */
    public long getStartTime(String name) {
        return startTimeMap.get(name);
    }

    /**
     * 获取起始时间
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * 开始计量
     * @param name
     */
    public void start(String name) {
        startTimeMap.put(name, System.currentTimeMillis());
    }

    /**
     * 开始计量
     */
    public void start() {
        startTime = System.currentTimeMillis();
    }

    /**
     * 获取结束时间
     * @param name
     * @return
     */
    public long getStopTime(String name) {
        return stopTimeMap.get(name);
    }

    /**
     * 结束计量
     * @param name
     */
    public void stop(String name) {
        stopTimeMap.put(name, System.currentTimeMillis());
    }

    /**
     * 结束计量
     */
    public void stop() {
        stopTime = System.currentTimeMillis();
    }

    /**
     * 获取消耗时间
     * @param name
     * @return
     */
    public long spend(String name) {
        return getStopTime(name) - getStartTime(name);
    }

    /**
     * <pre>
     * 获取消耗时间的列表
     * [名称, 起始时间, 结束时间, 耗时]
     * [
     *   [runTime_0, 1239544109572, 1239544109577, 5]
     * , [runTime_1, 1239544109577, 1239544109582, 5]
     * , [runTime_2, 1239544109582, 1239544109592, 10]
     * , [runTime_3, 1239544109592, 1239544109597, 5]
     * , [runTime_4, 1239544109597, 1239544109602, 5]
     * ]
     * </pre>
     * @return
     */
    public List<List<Object>> getSpendMapping() {
        Iterator<Entry<String, Long>> iterator = startTimeMap.entrySet().iterator();
        Entry<String, Long> entry;

        List<List<Object>> listList = new ArrayList<List<Object>>();
        List<Object> list;
        String key;
        Long val;
        Long _val;
        while (iterator.hasNext()) {
            list = new ArrayList<Object>();
            entry = iterator.next();
            key = entry.getKey();
            val = entry.getValue();
            _val = stopTimeMap.get(key);
            list.add(key);
            list.add(val);
            if (_val == null) {
                list.add(null);
                list.add(0L);
            } else {
                list.add(_val);
                list.add(_val - val);
            }
            listList.add(list);
        }
        return listList;
    }

    /**
     * 获取消耗时间
     * @return
     */
    public long spend() {
        if (stopTime == 0L) {
            stop();
        }
        return stopTime - startTime;
    }

    /**
     * 清理，清理之后必须重新获取实例才可使用命名计量
     */
    public void clear() {
        startTimeMap.clear();
        stopTimeMap.clear();
        startTimeMap = null;
        stopTimeMap = null;
    }
}
