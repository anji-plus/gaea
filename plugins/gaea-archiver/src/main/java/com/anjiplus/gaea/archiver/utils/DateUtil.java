package com.anjiplus.gaea.archiver.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 日期工具类
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
public class DateUtil {

    private static String defaultDatePattern = "yyyy-MM-dd";

    public static Date addMonth(String date, int increase){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date result = sdf.parse(date);
            return addMonth(result, increase);
        }catch (Exception e){
            return null;
        }
    }

    public static Date addMonth(Date date, int increase){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, increase);
        Date result = rightNow.getTime();
        return result;
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return date == null ? null : sdf.format(date);
    }

    public static String formatDate(Date date) {
        return format(date, defaultDatePattern);
    }

    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (dateStr != null && !"".equals(dateStr)) {
            try {
                Date d = sdf.parse(dateStr);
                return d;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }


}
