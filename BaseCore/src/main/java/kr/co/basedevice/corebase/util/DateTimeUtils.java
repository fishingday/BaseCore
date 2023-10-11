package kr.co.basedevice.corebase.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DateTimeUtils {
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String toString(Date date) {
        return toString(DEFAULT_DATETIME_FORMAT, date);
    }

    public static String toString(String pattern, Date date) {
        String dateStr = null;
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            dateStr = sdf.format(date);
        }
        return dateStr;
    }
}