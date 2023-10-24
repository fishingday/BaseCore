package kr.co.basedevice.corebase.search.todo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SearchSettle {
	
	private Long setleSeq;
	
	private Long workerSeq;	
	private Long acountSeq;	
	private LocalDateTime beginSetleDt;	
	private LocalDateTime endSetleDt;	
	private String workerNm;
	
	private String order;
	private String sort;
}
