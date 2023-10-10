package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TodoSummaryDto {
	
	private LocalDate toDay;
	
	private Long userSeq;
	
	private String userNm;
	
	private Integer todayPoint;
	
	private Integer availPoint;
	
}
