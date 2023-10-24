package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SettleInfoDto {
	
	private Long setleSeq;
	
	private Long workerSeq;
	
	private Long acountSeq;
	
	private Integer totalSetlePoint;
	
	private String setleDesc;

	private LocalDateTime setleDt;
	
	private String workerNm;
	
	private String loginId;
	
	private String acountNm;
	
	private Integer workItemCnt;
	
	private List<Long> workSeqList;
	
}
