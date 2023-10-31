package kr.co.basedevice.corebase.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    
    /**
     * 지정일 후 월말까지의 남은 요일 수 반환
     * 
     * @param targetDay
     * @param dayOfWeek
     * @return
     */
    public static int cntWeekdayOfMonth(LocalDate beginDay, DayOfWeek targetDayOfWeek) {
    	return DateTimeUtils.cntWeekdayOfMonth(beginDay, null, targetDayOfWeek);
    }
    
    public static int cntWeekdayOfMonth(LocalDate beginDay, LocalDate endDay, DayOfWeek targetDayOfWeek) {
    	
    	if(endDay == null) {
    		endDay = beginDay.withDayOfMonth(beginDay.lengthOfMonth());
    	}
    	    	
    	// 남은 날수
    	int days = ((int) ChronoUnit.DAYS.between(beginDay, endDay)) + 1;    	
    	int remainWeek = days/7;
    	
    	int modWeek = days%7;
    	
    	if(modWeek > 0) {
    		int minusDays = beginDay.getDayOfWeek().getValue() - 1;
        	int targetDay = targetDayOfWeek.getValue() - minusDays;
        	
        	if(targetDay > 0 && modWeek >= targetDay && targetDay >= 1) {    		
        		remainWeek++;
        	}
    	}
    	return remainWeek;
    }
}