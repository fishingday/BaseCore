package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.basedevice.corebase.domain.code.ConfmMethCd;
import kr.co.basedevice.corebase.domain.code.TodoTypCd;
import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class PlanWorkInfoDto {
	private String loginId;
	private String workerNm;
	private Long workerSeq;
	
	private Long todoTitl;
	private TodoTypCd todoTypCd;
	private ConfmMethCd confmMethCd;
		
	private Long workSeq;
	private Long todoSeq;
		
	private String workTitl;
	private String workCont;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime workDt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime confmDt;

	private WorkStatCd workStatCd;
	
	private Integer gainPoint;
	
	private Yn setleYn;

}
