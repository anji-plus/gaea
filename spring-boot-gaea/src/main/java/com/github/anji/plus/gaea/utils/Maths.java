package com.github.anji.plus.gaea.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 加减乘除
 * @author lr
 * @since 2021-01-12
 */
public class Maths {

    /**
     * 比较大小，前一个大于后一个
     * @param value
     * @param value2
     * @return
     */
    public static boolean gt(String value, String value2) {
        BigDecimal bigDecimal = new BigDecimal(value);

        BigDecimal bigDecimal1 = new BigDecimal(value2);

        return bigDecimal.compareTo(bigDecimal1) > 0;
    }

    /**
     * 比较大小，前一个大于等于后一个
     * @param value
     * @param value2
     * @return
     */
    public static boolean ge(String value, String value2) {
        return gt(value, value2) || eq(value, value2);
    }

    /**
     * 相等
     * @param value
     * @param value2
     * @return
     */
    public static boolean eq(String value, String value2) {
        BigDecimal bigDecimal = new BigDecimal(value);

        BigDecimal bigDecimal1 = new BigDecimal(value2);

        return bigDecimal.compareTo(bigDecimal1) == 0;
    }

    /**
     * 加法
     * @param values
     * @return
     */
    public static<T> String plus(List<T> values, Function<T, String> function) {
        Optional<BigDecimal> reduce = values.stream()
                .map(function)
                .filter(StringUtils::isNotBlank)
                .map(BigDecimal::new)
                .reduce(BigDecimal::add);

        if (reduce.isPresent()) {
            return Double.toString(reduce.get().doubleValue());
        }
        return Double.toString(BigDecimal.ZERO.doubleValue());
    }

    /**
     * 加法
     * @param values
     * @return
     */
    public static String plus(List<String> values) {

        return plus(values, value -> value);
    }

    /**
     * 加法
     * @param values 多个值
     * @return
     */
    public static String plus(String... values) {
        List<String> listValues = Arrays.asList(values);
        return plus(listValues);
    }


    /**
     * 减法
     * @param values
     * @return
     */
    public static String minus(List<String> values) {
        Optional<BigDecimal> reduce = values.stream()
                .map(BigDecimal::new)
                .reduce(BigDecimal::subtract);

        if (reduce.isPresent()) {
            return Double.toString(reduce.get().doubleValue());
        }
        return Double.toString(BigDecimal.ZERO.doubleValue());
    }

    /**
     * 减法
     * @param values
     * @return
     */
    public static String minus(String... values) {
        return minus(Arrays.asList(values));
    }




    /**
     * 乘法
     * @param values
     * @return
     */
    public static<T> String multiply(List<T> values, Function<T, String> function) {
        Optional<BigDecimal> reduce = values.stream()
                .map(function)
                .map(BigDecimal::new)
                .reduce(BigDecimal::multiply);

        if (reduce.isPresent()) {
            return Double.toString(reduce.get().doubleValue());
        }
        return Double.toString(BigDecimal.ZERO.doubleValue());
    }

    /**
     * 乘法
     * @param values
     * @return
     */
    public static String multiply(String... values) {
        return multiply(6, values);
    }

    /**
     * 乘法
     * @param values
     * @return
     */
    public static String multiply(int scale, String... values) {
        Optional<BigDecimal> reduce = Arrays.asList(values).stream()
                .map(BigDecimal::new)
                .reduce(BigDecimal::multiply);

        if (reduce.isPresent()) {
            BigDecimal bigDecimal = reduce.get();
            bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
            return bigDecimal.toPlainString();
        }
        return Double.toString(BigDecimal.ZERO.doubleValue());
    }


    /**
     * 除法
     * @param value
     * @param value2
     * @return
     */
    public static String divide(String value, String value2) {

        BigDecimal bigDecimal = new BigDecimal(value);
        BigDecimal bigDecimal2 = new BigDecimal(value2);

        BigDecimal divide = bigDecimal.divide(bigDecimal2, 6, BigDecimal.ROUND_HALF_UP);
        return Double.toString(divide.doubleValue());
    }

    /**
     * 保留两位小数
     * @param value
     * @return
     */
    public static String strFormat(String value) {
    	DecimalFormat format = new DecimalFormat("0.00");
        return format.format(new BigDecimal(value));
    }

    public static void main(String[] args) {
        String plus = multiply("12", "10");
        System.out.println(plus);
    }
}
