package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkerSettleInfoDto {
	private Long workerSeq;
	
	private String workerNm;
	
	private Long settleAmount;
	
	private Long unSettleAmount;
	
	private LocalDateTime currentDt;
}
