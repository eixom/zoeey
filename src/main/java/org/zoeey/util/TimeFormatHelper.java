/*
 * MoXie (SysTem128@GMail.Com) 2009-2-1 15:59:59
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 时间格式化
 * 
 * @author MoXie(SysTem128@GMail.Com)
 * @since 0.2
 */
public class TimeFormatHelper {

    /**
     * 锁定创建
     */
    private TimeFormatHelper() {
    }

    /**
     * 格式化long类型时间（当前 Unix 时间戳和毫秒数）
     * 
     * @return 格式化的时间
     * @see #DATE_MYSQL
     * @since 0.2
     */
    public static String format() {
        Date date = new Date();
        return format(date, DATE_MYSQL);
    }

    /**
     * 格式化long类型时间（当前 Unix 时间戳和毫秒数）
     * @param pattern 格式描述。
     * @return 格式化的时间
     * @since 0.2
     */
    public static String format(String pattern) {
        Date date = new Date();
        return format(date, pattern);
    }

    /**
     * 格式化long类型时间（当前 Unix 时间戳和毫秒数）
     *
     * @param time Unix 时间戳和毫秒数（毫秒级Unix时间戳）。
     * @param pattern 格式描述。
     * @return 格式化的时间
     * @since 0.2
     */
    public static String format(long time, String pattern) {
        Date date = new Date();
        date.setTime(time);
        return format(date, pattern);
    }

    /**
     * 格式化long类型时间（当前 Unix 时间戳和毫秒数）
     * @param time time Unix 时间戳和毫秒数（毫秒级Unix时间戳）。
     * @return
     */
    public static String format(long time) {
        Date date = new Date();
        date.setTime(time);
        return format(date, DATE_MYSQL);
    }

    /**
     * 格式化Date 
     *
     * @param date
     * @param pattern 格式描述。
     * @return 格式化的时间
     *
     * @since 0.2
     */
    public static String format(Date date, String pattern) {
        DateFormat dateFormator = new SimpleDateFormat(pattern);
        return dateFormator.format(date);
    }
    /**
     * <pre>
     * DATE_ATOM（string）
     * 原子钟格式（如：2005-08-15T15:52:01+00:00）
     * DATE_COOKIE（string）
     * HTTP Cookies 格式（如：Mon, 15 Aug 2005 15:52:01 UTC）
     * DATE_ISO8601（string）
     * ISO-8601（如：2005-08-15T15:52:01+0000）
     * DATE_RFC822（string）
     * RFC 822（如：Mon, 15 Aug 2005 15:52:01 UTC）
     * DATE_RFC850（string）
     * RFC 850（如：Monday, 15-Aug-05 15:52:01 UTC）
     * DATE_RFC1036（string）
     * RFC 1036（如：Monday, 15-Aug-05 15:52:01 UTC）
     * DATE_RFC1123（string）
     * RFC 1123（如：Mon, 15 Aug 2005 15:52:01 UTC）
     * DATE_RFC2822（string）
     * RFC 2822（如：Mon, 15 Aug 2005 15:52:01 +0000）
     * DATE_RSS（string）
     * RSS（如：Mon, 15 Aug 2005 15:52:01 UTC）
     * DATE_W3C（string）
     * World Wide Web Consortium（如：2005-08-15T15:52:01+00:00）
     * 新增格式
     * DATE_MYSQL
     * MySQL （如：2008-08-15 15：22）
    </pre>
     */
    public static final String DATE_MYSQL = "yyyy-MM-dd HH:mm:ss";

    /**
     *
     * 默认只可以转换 {@see #DATE_MYSQL},使用当前默认时区
     * @param timeStr 需要转化的时间字符串
     * @return
     */
    public static long strToTime(String timeStr) {
        return strToTime(timeStr, DATE_MYSQL);
    }

    /**
     * 使用自定义格式转换时间到long，使用当前默认时区
     * @param timeStr 时间字符串
     * @param format 格式化信息
     * @return
     */
    public static long strToTime(String timeStr, String format) {
        return strToTime(timeStr, format, TimeZone.getDefault());
    }

    /**
     * 使用自定义格式转换时间到long，使用当前自定义时区
     * @param timeStr 时间字符串
     * @param format 格式化信息
     * @param timeZone 时区
     * @return
     */
    public static long strToTime(String timeStr, String format, TimeZone timeZone) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setTimeZone(timeZone);
            date = dateFormat.parse(timeStr);
            return date.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(TimeHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1L;
    }
}
