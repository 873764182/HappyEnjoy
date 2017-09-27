package com.pixel.he.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pixel on 2017/9/26.
 */

public class DateUtil {

    // type：year month date
    public static String getDateString(String type) {
        return null;
    }

    public static String getStringByDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return simpleDateFormat.format(date);
    }
}
