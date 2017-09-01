package com.hins.myreader.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Hins on 2017/8/27.
 */

public class DateUtil {

    public static SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);

    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        return mFormat.format(calendar.getTime());
    }

    public static String getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        //把日期设置为当前时间，然后增加一天
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return mFormat.format(calendar.getTime());

    }
}
