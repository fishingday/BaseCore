package kr.co.basedevice.corebase;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import kr.co.basedevice.corebase.util.DateTimeUtils;

public class DateTester {

	public static void main(String[] args) {
		LocalDate today = LocalDate.now().plusDays(1);
		
		System.err.println("today :" + today);
		System.err.println("getDayOfMonth : " + today.getDayOfMonth());
		System.err.println("lengthOfMonth : " + today.lengthOfMonth());
		System.err.println("getMonthValue : " + today.getMonthValue());
		System.err.println("withDayOfMonth : " + today.withMonth(today.getMonthValue()));
				
		System.err.println("getDayOfWeek :" + today.getDayOfWeek());
		System.err.println("getDayOfWeek().getValue() :" + today.getDayOfWeek().getValue());
		
		LocalDate firstDate = today.withDayOfMonth(1);
		System.err.println("firstDate :" + firstDate);
		
		LocalDate lastDate = today.withDayOfMonth(today.lengthOfMonth());  
		System.err.println("lastDate :" + lastDate);

//		today :2023-11-01
//		getDayOfMonth : 1
//		lengthOfMonth : 30
//		getMonthValue : 11
//		withDayOfMonth : 2023-11-01
//		getDayOfWeek :WEDNESDAY
//		getDayOfWeek().getValue() :3
//		firstDate :2023-11-01
//		lastDate :2023-11-30

		
		// 수요일은 몃개? 
		System.err.println("cntWeekdayOfMonth :" + DateTimeUtils.cntWeekdayOfMonth(today, lastDate, DayOfWeek.FRIDAY));
		
		
		System.err.println("truncatedTo(ChronoUnit.HOURS) : " + LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
		

	}

}
