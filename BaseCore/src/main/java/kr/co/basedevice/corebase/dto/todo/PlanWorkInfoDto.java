package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;

import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class PlanWorkInfoDto {
	private String loginId;
	private String workerNm;
	private Long workerSeq;
	
	private Long workSeq;
	private Long todoSeq;
	private String workTitl;
	private WorkStatCd workStatCd;
	
	private LocalDateTime confmDt;
	private LocalDateTime workDt;	
	
	private Integer gainPoint;
	private Yn setleYn;

}
