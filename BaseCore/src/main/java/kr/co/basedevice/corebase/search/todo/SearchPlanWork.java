package kr.co.basedevice.corebase.search.todo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SearchPlanWork {
	
	private Long checkerSeq;
	
	private LocalDateTime beginDate;
	private LocalDateTime endDate;

	private String order;
	private String sort;
}
