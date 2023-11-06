package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkerSettleInfoDto {
	private Long workerSeq;
	
	private String workerNm;
	
	/**
	 * 누적 포인트
	 */
	private Long settlePoints;
	
	/**
	 * 사용 포인트
	 */
	private Long usePoints;
	
	/**
	 * 미 적립 포인트
	 */
	private Long unSettlePoints; 
	
	private LocalDateTime currentDt;
}
