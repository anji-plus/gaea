package com.github.anji.plus.gaea.utils;

import com.github.anji.plus.gaea.constant.GaeaConstant;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 转换日期
 * @author lr
 * @since 2021-01-12
 */
public abstract class GaeaDateUtils {

    /**
     * 日期转换为指定格式的字符串
     * @param date
     * @param pattern
     * @return
     */
    public static String toString(Date date, String pattern) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 把指定格式的字符串转换为LocalDate
     * @param dateString
     * @param pattern
     * @return
     */
    public static LocalDate fromString(String dateString, String pattern) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * LocalDate 转换为Date
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * localDateTime 转换为Date
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * date 转换为LocalDate
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * date 转换为LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
