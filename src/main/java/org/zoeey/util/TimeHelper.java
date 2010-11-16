/*
 * MoXie (SysTem128@GMail.Com) 2009-3-22 3:32:43
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.util.Calendar;

/**
 * <pre>
 * 常用时间点获取
 * 每日时间起始为当日 00:00 时。
 * 每日时间结束是为次日 00:00 时。
 * </pre>
 * @author MoXie(SysTem128@GMail.Com)
 */
public class TimeHelper {

    /**
     * 基准时间
     */
    private long millisTime = 0L;
    /**
     * 一天
     */
    private static final long oneDay = 24 * 3600 * 1000;

    /**
     * 时间辅助对象
     */
    public TimeHelper( ) {
        this.millisTime = System.currentTimeMillis();
    }
    /**
     * 时间辅助对象
     * @param millisTime 毫秒级相对时间
     */
    public TimeHelper(long millisTime) {
        this.millisTime = millisTime;
    }

    /**
     * 获取被清零到 天 的Calendar
     * @return
     */
    private Calendar getDayCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisTime);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar;
    }

    /**
     * 获取当月起始时间
     * @return
     */
    public long monthStart() {
        Calendar calendar = getDayCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当月结束时间（也是下月的开始时间）
     * @return
     */
    public long monthEnd() {
        Calendar calendar = getDayCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTimeInMillis() + oneDay;
    }

    /**
     * 获取当周起始时间
     * @return
     */
    public long weekStart() {
        Calendar calendar = getDayCalendar();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当周结束时间（也是下周的开始时间）
     * @return
     */
    public long weekEnd() {
        Calendar calendar = getDayCalendar();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        return calendar.getTimeInMillis() + oneDay;
    }

    /**
     * 获取当天的起始时间
     * @return
     */
    public long today() {
        return millisTime - millisTime % oneDay;
    }

    /**
     * 获取明天的起始时间
     * @return
     */
    public long tomorrow() {
        return today() + oneDay;
    }

    /**
     * 获取昨天的起始时间
     * @return
     */
    public long yesterday() {
        return today() - oneDay;
    }

    /**
     * 精确到秒的时间
     * @return
     */
    public static long time() {
        return (long) Math.floor(System.currentTimeMillis() / 1000);
    }
}
