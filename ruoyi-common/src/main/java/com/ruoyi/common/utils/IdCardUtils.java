package com.ruoyi.common.utils;

import java.util.Date;

/**
 * 身份证信息算法类
 *
 * @author javaweb
 */
public class IdCardUtils {

    /**
     * 根据身份编号获取年龄
     *
     * @param Idcard 身份编号
     * @return 年龄
     */
    public static int getAgeByIdcard(String Idcard) {
        return AgeUtils.getAge(getBirthdayByIdcard(Idcard));
    }

    /**
     * 根据身份编号获取生日
     *
     * @param Idcard 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static Date getBirthdayByIdcard(String Idcard) {
        return DateUtils.strToDate(Idcard.substring(6, 14), "yyyyMMdd");
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param Idcard 身份编号
     * @return 生日(yyyy)
     */
    public static Short getYearByIdcard(String Idcard) {
        return Short.valueOf(Idcard.substring(6, 10));
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param Idcard 身份编号
     * @return 生日(MM)
     */
    public static Short getMonthByIdcard(String Idcard) {
        return Short.valueOf(Idcard.substring(10, 12));
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param Idcard 身份编号
     * @return 生日(dd)
     */
    public static Short getDateByIdcard(String Idcard) {
        return Short.valueOf(Idcard.substring(12, 14));
    }

    /**
     * 根据身份编号获取性别
     *
     * @param Idcard 身份编号
     */
    public static String getSexByIdcard(String Idcard) {
        String sCardNum = Idcard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            return "0";
        } else {
            return "1";
        }
    }


}