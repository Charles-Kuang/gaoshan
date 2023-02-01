package com.ruoyi.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.hutool.core.lang.Assert;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 * 
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 时长转换
     *
     * @param time 通话时长（秒）
     * @return 几小时几分钟几秒
     */
    public static String getStrTime(Integer time) {
        if (time == null || time == 0) return "";
        StringBuilder str = new StringBuilder();
        int hour = time / 3600;
        if (hour != 0) str.append(hour).append("小时");
        int minutes = (time - hour * 3600) / 60;
        if (minutes != 0) str.append(minutes).append("分钟");
        int seconds = time - hour * 3600 - minutes * 60;
        if (seconds != 0) str.append(seconds).append("秒");
        return str.toString();
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 日期转换为字符串 默认"yyyy-MM-dd HH:mm:ss"
     *
     * @param date 日期
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, null);
    }

    /**
     * 日期转换为字符串
     *
     * @param date   日期
     * @param format 日期格式
     */
    public static String dateToStr(Date date, String format) {
        if (date == null) return null;
        // 如果没有指定字符串转换的格式，则用默认格式进行转换
        if (null == format || "".equals(format) || "Datetime".equals(format)) {
            format = YYYY_MM_DD_HH_MM_SS;
        } else if ("Timestamp".equals(format)) {
            format = TIMESTAMP_FORMAT;
        } else if ("Date".equals(format)) {
            format = YYYY_MM_DD;
        } else if ("DateSimple".equals(format)) {
            format = "yyyyMMdd";
        } else if ("Simple".equals(format)) {
            format = "yyyyMMdd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取该月有多少天
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date strToDate(String time, String formatterString) {
        if (StringUtils.isBlank(time) || StringUtils.isBlank(formatterString)) return null;
        SimpleDateFormat formatter = new SimpleDateFormat(formatterString);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(time, pos);
    }

    public static Date strToDateThrow(String time) {
        if (StringUtils.isBlank(time)) return null;
        Date value = strToDate(time);
        Assert.notNull(value, "无法识别日期格式: " + value);
        return value;
    }

    /**
     * 任意类型日期字符串转时间
     */
    public static Date strToDate(String time) {
        if (StringUtils.isBlank(time)) return null;
        SimpleDateFormat formatter;
        int tempPos = time.indexOf("AD");
        time = time.trim();
        formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
        if (tempPos > -1) {
            time = time.substring(0, tempPos) +
                    "公元" + time.substring(tempPos + "AD".length());//china
            formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
        }
        if (time.contains(".")) time = time.replaceAll("\\.", "/");
        if (time.contains("-")) time = time.replaceAll("-", "/");
        if (!time.contains("/") && !time.contains(" ")) {
            formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        } else if (time.contains("/")) {
            if (time.contains("am") || time.contains("pm")) formatter = new SimpleDateFormat("yyyy/MM/dd KK:mm:ss a");
            else if (time.contains(" ")) formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            else {
                String[] dateArr = time.split("/");
                if (dateArr.length == 2 || (dateArr.length == 3 && StringUtils.isBlank(dateArr[2])))
                    formatter = new SimpleDateFormat("yyyy/MM");
                else formatter = new SimpleDateFormat("yyyy/MM/dd");
            }
        }
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(time, pos);
    }

}
