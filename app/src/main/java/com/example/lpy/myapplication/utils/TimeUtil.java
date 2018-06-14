package com.example.lpy.myapplication.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat yMd = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat dfMs = new SimpleDateFormat("mm:ss");

    public static String getYMd(String time) {
        try {
            Date date = yMd.parse(time);
            time = yMd.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static long getTimeDiff(String startTime, String endTime) {

        long diff = 0;
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
//            long days = diff / (1000 * 60 * 60 * 24);
//            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
//            long  minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
//            System.out.println("" + days + "天" + hours + "小时" + minutes + "分");
        } catch (Exception e) {
        }
        return diff / 1000;
    }

    public static String timeToString(long time) {
        StringBuffer buffer = new StringBuffer();
        long temp = time / (60 * 60); //小时
        buffer.append((temp < 10) ? "0" + temp + ":" : temp + ":");
        temp = time % (60 * 60) / 60;
        buffer.append((temp < 10) ? "0" + temp + ":" : temp + ":");
        temp = time % (60 * 60) % 60;
        buffer.append((temp < 10) ? "0" + temp : "" + temp);
        return buffer.toString();
    }
}