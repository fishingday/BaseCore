package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;

import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import lombok.Data;

@Data
public class WorkQuizInfoDto {
	private Long quizSeq;
	private Long workSeq;
	private Long todoSeq;
	private String workTitl;
	private String workCont;
	private LocalDateTime workDt;
	private WorkStatCd workStatCd;
	private Integer gainPoint;
	
	private Long workerSeq;
	private String loginId;
	private String userNm;
}
