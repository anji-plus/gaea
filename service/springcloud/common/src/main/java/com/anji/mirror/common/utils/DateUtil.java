package com.anji.mirror.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DateUtil {

    private static String defaultDatePattern = "yyyy-MM-dd";

    private static String defaultDateTimePattern = "yyyy-MM-dd HH:mm:ss.SSS";

    private static String defaultyyyyMMddPattern = "yyyyMMdd";

    private static String defaultYmdHmsPattern = "yyyy-MM-dd HH:mm:ss";

    private static String defaultHmsPattern = "HH:mm:ss";

    public static Date now(){
        return new Date();
    }

    public static Date yestorday(){
        Date today=now();
        return add(today, Calendar.DAY_OF_MONTH, -1);
    }


    public static String todayString(){
        Date now = new Date();
        return format(now, defaultDatePattern);
    }

    public static String nowString(){
        Date now = new Date();
        return format(now, defaultDateTimePattern);
    }

    /**Date转字符串
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return date == null ? null : sdf.format(date);
    }

    /**Date转字符串 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return format(date, defaultDatePattern);
    }

    /**毫秒数转字符串 yyyy-MM-dd
     * @param timeInMillis 毫秒数
     * @return
     */
    public static String formatDate(Long timeInMillis){
        if(timeInMillis == null){
            return null;
        }
        Date date = new Date(timeInMillis);
        return formatDate(date);
    }

    /**Date转字符串 yyyy-MM-dd HH:mm:ss.SSS
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return format(date, defaultDateTimePattern);
    }

    /**毫秒数转字符串 yyyy-MM-dd HH:mm:ss.SSS
     * @param timeInMillis 毫秒数
     * @return
     */
    public static String formatDateTime(Long timeInMillis){
        if(timeInMillis == null){
            return null;
        }
        Date date = new Date(timeInMillis);
        return formatDateTime(date);
    }

    /**毫秒数转字符串 HH:mm:ss
     * @param timeInMillis 毫秒数
     * @return
     */
    public static String formatHmsDateTime(Long timeInMillis){
        if(timeInMillis == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(defaultHmsPattern);
        return sdf.format(timeInMillis);
    }

    /**字符串转日期
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (dateStr == null || "".equals(dateStr)) {
            return null;
        }
        try {
            Date d = sdf.parse(dateStr);
            return d;
        } catch (ParseException e) {
            System.out.println("日期转换错误: " + e.getMessage());
            return null;
        }
    }

    /**毫秒数转日期
     * @param timeInMillis 毫秒
     * @return
     */
    public static Date parse(Long timeInMillis){
        if(timeInMillis == null){
            return null;
        }
        return new Date(timeInMillis);
    }

    /**字符串yyyy-MM-dd转日期
     * @param dateStr yyyy-MM-dd
     * @return
     */
    public static Date parseDate(String dateStr) {
        return parse(dateStr, defaultDatePattern);
    }

    /**字符串yyyy-MM-dd HH:mm:ss.SSS转日期
     * @param dateStr yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static Date parseDateTime(String dateStr) {
        return parse(dateStr, defaultDateTimePattern);
    }


    /**字符串yyyy-MM-dd HH:mm:ss转日期
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date parseHmsTime(String dateStr) {
        return parse(dateStr, defaultYmdHmsPattern);
    }


    /**在date日期基础上，在指定年月日时分等位置上，+ amount
     * @param date
     * @param calendorField Calendar.DAY_OF_MONTH Calendar.MINUTE Calendar.SECOND
     * @param amount 正数是加，负数是减
     * @return
     */
    public static Date add(Date date, int calendorField, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendorField, amount);
        return cal.getTime();
    }

    /** 返回开始日期和结束日期之间所有的日期字符串
     * @param start 开始日期
     * @param end 结束日期
     * @param pattern 字符串格式
     * @param millisOffset 天86400000 小时3600000
     * @return
     */
    public static List<String> GetDatesStringBetween(Date start, Date end, String pattern, long millisOffset) {
        if(StringUtils.isBlank(pattern)){
            pattern=defaultDatePattern;
        }
        List<String> result = new ArrayList<String>();
        while (start.getTime() <= end.getTime()) {
            String item = format(start, pattern);
            result.add(item);
            start = new Date(start.getTime() + millisOffset);
        }
        return result;
    }

    /** 返回开始日期和结束日期之间所有的日期字符串
     * @param start 开始日期
     * @param end 结束日期
     * @param pattern 字符串格式
     * @param millisOffset 天86400000 小时3600000
     * @param prefix 日期前缀
     * @return
     */
    public static List<String> GetDatesStringBetween(Date start, Date end, String pattern, long millisOffset,String prefix) {
        if(StringUtils.isBlank(pattern)){
            pattern=defaultDatePattern;
        }
        List<String> result = new ArrayList<String>();
        while (start.getTime() <= end.getTime()) {
            String item = format(start, pattern);
            result.add(prefix + item);
            start = new Date(start.getTime() + millisOffset);
        }
        return result;
    }


    public static List<String> GetDaysStringBetween(Date start, Date end) {
        return GetDatesStringBetween(start, end, defaultDatePattern, 86400000);
    }

    /**返回开始时间和结束时间之间所有的日期字符串
     * @param startStr yyyy-MM-dd
     * @param endStr yyyy-MM-dd
     * @return
     */
    public static List<String> GetDaysStringBetween(String startStr, String endStr) {
        return GetDatesStringBetween(parseDate(startStr), parseDate(endStr), defaultDatePattern, 86400000);
    }

    /**返回开始时间和结束时间之间所有的日期字符串
     * @param startStr yyyy-MM-dd
     * @param endStr yyyy-MM-dd
     * @param prefix 日期字符串前缀
     * @return prefix-yyyyMMdd 例：syslog-20201006
     */
    public static List<String> GetDaysStringBetween(String startStr, String endStr,String prefix) {
        return GetDatesStringBetween(parseDate(startStr), parseDate(endStr), defaultyyyyMMddPattern, 86400000, prefix);
    }

    /**返回开始时间和结束时间之间所有的日期字符串
     * @param start
     * @param end
     * @param prefix 日期字符串前缀
     * @return prefix-yyyyMMdd 例：syslog-20201006
     */
    public static List<String> GetDaysStringBetween(Date start, Date end, String prefix) {
        return GetDatesStringBetween(start, end, defaultyyyyMMddPattern, 86400000, prefix);
    }

    public static List<String> GetHoursStringBetween(Date start, Date end) {
        return GetDatesStringBetween(start, end, "yyyy-MM-dd HH", 3600000);
    }

    /** 判断原日期是否在目标日期之后
     * @param src
     * @param dst
     * @return
     */
    public static boolean after(Date src, Date dst) {
        if(src == null || dst == null){
            return false;
        }
        return src.after(dst);
    }
    /** 判断原日期src是否在目标日期dstStr之后
     * @param src
     * @param dstStr yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static boolean after(Date src, String dstStr){
        Date dst = parseDateTime(dstStr);
        return after(src, dst);
    }

    /** 判断原日期src是否在目标日期dst之前
     * @param src
     * @param dst
     * @return
     */
    public static boolean before(Date src, Date dst) {
        if(src == null || dst == null){
            return false;
        }
        return src.before(dst);
    }
    /** 判断原日期src是否在目标日期dstStr之前
     * @param src
     * @param dstStr yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static boolean before(Date src, String dstStr){
        Date dst = parseDateTime(dstStr);
        return before(src, dst);
    }

    /** 根据生日计算年龄
     * @param birthDay
     * @return
     */
    public static int age(Date birthDay) {
        int result=0;
        try {
            Calendar cal = Calendar.getInstance();
            if (cal.before(birthDay)) {
                return 0;
            }
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(birthDay);

            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

            int age = yearNow - yearBirth;

            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;
                }else{
                    age--;
                }
            }
            result=age;
        } catch (Exception e) {
            //
            result=0;
        }
        return result;
    }
    /** 根据生日计算年龄
     * @param birthDay yyyy-MM-dd
     * @return
     */
    public static int age(String birthDay) {
        return age(parseDate(birthDay));
    }

    /**
     * logTime=2020-09-16 16:00:00 UTC
     * UTC字符串0时区格式的时间，转换成+8时区的时间
     * @param time
     * @return
     * @throws Exception
     */
    public static Date formatTimeEight(String time) {
        if (time.endsWith("UTC")) {
            time = time.replace("UTC", "").trim();
        }
        Date d = parse(time, defaultYmdHmsPattern);
        long rightTime = d.getTime() + 8 * 60 * 60 * 1000;
        //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
        return new Date(rightTime);
    }


    /**
     * 获取几天前或者几天后的时间
     *
     * @return
     */
    public static Date getCalendarNDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }




    /**
     * 查询某一天是一年中的第几天
     * @param dateTime date
     * @return
     * @throws ParseException
     */
    public static int calcHowManyDays(String dateTimeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = sdf.parse(dateTimeStr);
        return calcHowManyDays(dateTime);//指示当前年中的天数
    }

    /**
     *
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static int calcHowManyDays(Date dateTime) throws ParseException {
        String dateResult = formatDate(dateTime);
        String[] dateArray = dateResult.split("-");
        int year = Integer.valueOf(dateArray[0]);
        int month = Integer.valueOf(dateArray[1]);
        int day = Integer.valueOf(dateArray[2]);
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month-1);
        ca.set(Calendar.DAY_OF_MONTH, day);//指示当前月中的天数
        if (ca.get(Calendar.YEAR) != year|| ca.get(Calendar.MONTH) != month - 1 || ca.get(Calendar.DAY_OF_MONTH) != day)
        {
            return 1;
        }
        return ca.get(Calendar.DAY_OF_YEAR);//指示当前年中的天数
    }

    public static void main(String[] args) throws ParseException {
        String pastDateTmdHms = getPastDateTmdHms(1);
        System.out.println(pastDateTmdHms);
        pastDateTmdHms =getPastDateTmdHms(2);
        System.out.println(pastDateTmdHms);
        pastDateTmdHms =getPastDateTmdHms(5);
        System.out.println(pastDateTmdHms);
        pastDateTmdHms =getPastDateTmdHms(10);
        System.out.println(pastDateTmdHms);
        pastDateTmdHms =getPastDateTmdHms(40);
        System.out.println(pastDateTmdHms);
        pastDateTmdHms =getPastDateTmdHms(60);
        System.out.println(pastDateTmdHms);
        System.out.println("-----------");
        pastDateTmdHms = getPastDateTmdHms(1,"2021-01-04 12:00:00.000");
        System.out.println(pastDateTmdHms);
        pastDateTmdHms =getPastDateTmdHms(2,"2021-01-04 12:00:00.000");
        System.out.println(pastDateTmdHms);
        pastDateTmdHms =getPastDateTmdHms(5,"2021-01-04 12:00:00.000");
        System.out.println(pastDateTmdHms);
        pastDateTmdHms =getPastDateTmdHms(10,"2021-01-04 12:00:00.000");
        System.out.println(pastDateTmdHms);
        pastDateTmdHms =getPastDateTmdHms(40,"2021-01-04 12:00:00.000");
        System.out.println(pastDateTmdHms);
        pastDateTmdHms =getPastDateTmdHms(60,"2021-01-04 12:00:00.000");
        System.out.println(pastDateTmdHms);
    }

    public static String toString(Date date, String format) {
        String dateStr = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(date);
        } catch (Exception e) {
        }
        return dateStr;
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<String> getPastDateByDay(int intervals ) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = intervals-1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDateTmdHms(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @param time 距离当前传入的时候
     * @return
     */
    public static String getPastDateTmdHms(int past,String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDateTime(time));
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取指定货时间过去第几天的日期
     * @param past
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static String getPastDate(int past, String dateTime) throws ParseException {
        int day = calcHowManyDays(dateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, day - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取指定货时间过去第几天的日期
     * @param past
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static String getAfterDate(int past, String dateTime) throws ParseException {
        int day = calcHowManyDays(dateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, day + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 根据开始时间和结束时间获取日期数组
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static List<String> findDates(String startTime, String endTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = sdf.parse(startTime);
        Date end = sdf.parse(endTime);
        List<String> lDate = new ArrayList();
        lDate.add(sdf.format(sdf.parse(startTime)));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(sdf.parse(startTime));
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(sdf.parse(endTime));
        // 测试此日期是否在指定日期之后
        while (sdf.parse(endTime).after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sdf.format(calBegin.getTime()));
        }
        return lDate;
    }


    /**
     * 获得当天零时零分零秒
     * @return
     */
    public static Date initDateByDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 判断两时间差距hour
     * @param before
     * @param left
     * @param hour
     * @return 大于hour返回false，反正返回true
     */
    public static boolean betweenDateWithHour(Date before, Date left, long hour) {
        Instant beforeInstant = Instant.ofEpochMilli(before.getTime());
        Instant leftInstant = Instant.ofEpochMilli(left.getTime());
        long between = ChronoUnit.HOURS.between(beforeInstant, leftInstant);
        return hour >= between;
    }


    /**
     * 判断两时间差距分钟
     * @param before
     * @param left
     * @param minute
     * @return 大于minute返回false，反正返回true
     */
    public static boolean betweenDateWithMinute(Date before, Date left, long minute) {
        Instant beforeInstant = Instant.ofEpochMilli(before.getTime());
        Instant leftInstant = Instant.ofEpochMilli(left.getTime());
        long between = ChronoUnit.MINUTES.between(beforeInstant, leftInstant);
        return minute >= between;
    }

    /**
     * 判断两时间差距秒
     * @param beforeString
     * @param leftString
     * @return
     */
    public static long betweenDateWithSecond(String beforeString, String leftString) {
        Date before = parseDateTime(beforeString);
        Date left = parseDateTime(leftString);
        Instant beforeInstant = Instant.ofEpochMilli(before.getTime());
        Instant leftInstant = Instant.ofEpochMilli(left.getTime());
        return ChronoUnit.SECONDS.between(beforeInstant, leftInstant);
    }

    /**
     * 校验日期格式是否满足yyyy-MM-dd HH:mm:ss.SSS或者为空
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean DateFormartCheck(String startTime, String endTime) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            dateFormat.parse(startTime);
            dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
