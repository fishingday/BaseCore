package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TodoSummaryDto {
	
	private LocalDate toDay;
	
	private Long userSeq;
	
	private String userNm;
	
	private Integer todayPoint; // 오늘 적립 가능 포인트
	
	private Integer availPoint; // 가용 포인트
	
	private Integer usePoint; // 사용 포인트
	
	private Integer accumulate; // 누적 포인트
	
}
