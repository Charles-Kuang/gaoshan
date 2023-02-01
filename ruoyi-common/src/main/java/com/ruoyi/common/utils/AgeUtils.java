package com.ruoyi.common.utils;

import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;

/**
 * 年龄工具类
 */
public class AgeUtils {

    /**
     * 计算年龄，就近原则
     */
    public static Integer getAge(Date birthday) {
        if (birthday == null) return 0;
        Calendar c2 = Calendar.getInstance();
        if (c2.before(birthday)) return 0;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(birthday);

        int month = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int totalMonths = year * 12 + month;

        int age = totalMonths / 12;
        int leaveMonth = totalMonths % 12;

        int day = c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH);
        int totalDayOfMonth = c2.getActualMaximum(Calendar.DAY_OF_MONTH);

        int p = day * 100 / totalDayOfMonth;
        if (leaveMonth == 11) {
            if (p >= 50 || p < 0 && p >= -50) age += 1;
        }
        return age;
    }

    /**
     * 计算年龄，就近原则
     */
    public static Integer getAge(Date birthday, Date endDate) {
        if (birthday == null) return 0;
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        if (c.before(birthday)) return 0;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        int totalMonths = Months.monthsBetween(formatter.parseDateTime(DateUtils.dateToStr(birthday, "yyyy-MM-dd")), formatter.parseDateTime(DateUtils.dateToStr(endDate, "yyyy-MM-dd"))).getMonths();

        int age = totalMonths / 12;
        int leaveMonth = totalMonths % 12;

        int day = Integer.parseInt(DateUtils.dateToStr(endDate, "dd")) - Integer.parseInt(DateUtils.dateToStr(birthday, "dd"));
        int totalDayOfMonth = DateUtils.getDaysOfMonth(endDate);

        int p = day * 100 / totalDayOfMonth;
        if (leaveMonth == 11) {
            if (p >= 50 || p < 0 && p >= -50) age += 1;
        }
        return age;
    }

    /**
     * 计算余月，就近原则
     */
    public static Integer getLeaveMonth(Date birthday) {
        if (birthday == null) return 0;
        Calendar c = Calendar.getInstance();
        if (c.before(birthday)) return 0;

        Date now = new Date();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        int day = Integer.parseInt(DateUtils.dateToStr(now, "dd")) - Integer.parseInt(DateUtils.dateToStr(birthday, "dd"));
        int totalDayOfMonth = DateUtils.getDaysOfMonth(now);

        int i = Months.monthsBetween(formatter.parseDateTime(DateUtils.dateToStr(birthday, "yyyy-MM-dd")), formatter.parseDateTime(DateUtils.dateToStr(now, "yyyy-MM-dd"))).getMonths() % 12;
        int p = day * 100 / totalDayOfMonth;
        if (p >= 50 || p < 0 && p >= -50) {
            if (i == 11) i = 0;
            else i += 1;
        }
        return i;
    }

    /**
     * 计算余月，就近原则
     */
    public static Integer getLeaveMonth(Date birthday, Date endDate) {
        if (birthday == null) return 0;
        Calendar c = Calendar.getInstance();
        if (c.before(birthday)) return 0;

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        int day = Integer.parseInt(DateUtils.dateToStr(endDate, "dd")) - Integer.parseInt(DateUtils.dateToStr(birthday, "dd"));
        int totalDayOfMonth = DateUtils.getDaysOfMonth(endDate);

        int totalMonths = Months.monthsBetween(formatter.parseDateTime(DateUtils.dateToStr(birthday, "yyyy-MM-dd")), formatter.parseDateTime(DateUtils.dateToStr(endDate, "yyyy-MM-dd"))).getMonths() % 12;
        int p = day * 100 / totalDayOfMonth;
        if (p >= 50 || p < 0 && p >= -50) {
            if (totalMonths == 11) totalMonths = 0;
            else totalMonths += 1;
        }
        return totalMonths;
    }

    /**
     * 计算总月份，就近原则
     */
    public static Integer getMonth(Date birthday) {
        if (birthday == null) return 0;
        Calendar c = Calendar.getInstance();
        if (c.before(birthday)) return 0;

        Date now = new Date();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        int day = Integer.parseInt(DateUtils.dateToStr(now, "dd")) - Integer.parseInt(DateUtils.dateToStr(birthday, "dd"));
        int totalDayOfMonth = DateUtils.getDaysOfMonth(now);

        int totalMonths = Months.monthsBetween(formatter.parseDateTime(DateUtils.dateToStr(birthday, "yyyy-MM-dd")), formatter.parseDateTime(DateUtils.dateToStr(now, "yyyy-MM-dd"))).getMonths();
        int p = day * 100 / totalDayOfMonth;
        if (p >= 50 || p < 0 && p >= -50) {
            if (totalMonths == 11) totalMonths = 0;
            else totalMonths += 1;
        }
        return totalMonths;
    }

    /**
     * 计算总月份，就近原则
     */
    public static Integer getMonth(Date birthday, Date endDate) {
        if (birthday == null) return 0;
        Calendar c = Calendar.getInstance();
        if (c.before(birthday)) return 0;

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        int day = Integer.parseInt(DateUtils.dateToStr(endDate, "dd")) - Integer.parseInt(DateUtils.dateToStr(birthday, "dd"));
        int totalDayOfMonth = DateUtils.getDaysOfMonth(endDate);

        int totalMonths = Months.monthsBetween(formatter.parseDateTime(DateUtils.dateToStr(birthday, "yyyy-MM-dd")), formatter.parseDateTime(DateUtils.dateToStr(endDate, "yyyy-MM-dd"))).getMonths();
        int p = day * 100 / totalDayOfMonth;
        if (p >= 50 || p < 0 && p >= -50) {
            if (totalMonths == 11) totalMonths = 0;
            else totalMonths += 1;
        }
        return totalMonths;
    }

}
