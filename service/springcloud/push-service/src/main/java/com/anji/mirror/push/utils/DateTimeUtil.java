package com.anji.mirror.push.utils;

import com.alibaba.fastjson.JSON;
import com.anji.mirror.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anji gaea teams
 * @Date: 2020/11/20
 * @Description:
 */
@Slf4j
public class DateTimeUtil {
    /**
     * 获取三个月前的时间戳和format
     */
    /**
     * 获取三个月前的时间戳和format
     * @param amount 负值
     * @return
     * @throws ParseException
     */
    public static Map<String, String> getMonthsAgoTimestamp(int amount) throws ParseException {
        Map<String, String> map = new HashMap<>();
        Date dNow = new Date();   //当前时间
        Date dBefore;
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, amount);  //设置为前3月
        dBefore = calendar.getTime();   //得到前3月的时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
        String defaultEndDate = sdf.format(dNow); //格式化当前时间
        Date parse = sdf.parse(defaultStartDate);
        log.info( Math.abs(amount)+"个月之前时间======={}", defaultStartDate);
        log.info(Math.abs(amount)+"个月之前时间戳======={}", parse.getTime());
        log.info("当前时间==========={}", defaultEndDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        calendar.add(Calendar.MONTH, amount);  //设置为前3月的前一个月
        String format = simpleDateFormat.format(calendar.getTime());
        log.info("前"+Math.abs(amount)+"月的前一个月==========={}", format);
        map.put("timestamp", DateUtil.format(parse, "yyyy-MM-dd HH:mm:ss") + "");
        map.put("format", format);
        return map;
    }

    public static void main(String[] args) throws ParseException {
        Map<String, String> threeMonthsAgoTimestamp = DateTimeUtil.getMonthsAgoTimestamp(-3);
        System.out.println(JSON.toJSONString(threeMonthsAgoTimestamp));
    }

}
