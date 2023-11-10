package kr.co.basedevice.corebase.search.todo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SearchSettle {
	
	private Long workerSeq;	
	private Long acountSeq;	
	
	private String workerNm;
	private String setleDesc;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate settleBeginDate;	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate settleEndDate;
	
	private String order;
	private String sort;
}
