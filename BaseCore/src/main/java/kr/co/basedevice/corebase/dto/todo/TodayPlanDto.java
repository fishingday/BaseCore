package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;

import kr.co.basedevice.corebase.domain.code.QuizTypCd;
import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.TodoTypCd;
import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class TodayPlanDto {
	
	private Long todoSeq;	
	private String todoTitl;	
	private String todoCont;	
	private String completCondiVal;
	private TodoTypCd todoTypCd;
	private TodoCreCd todoCreCd;
	private Yn quizUseYn;	
	private QuizTypCd quizTypCd;
	
	private Long workSeq;
	private String workTitl;
	private String workCont;
	private LocalDateTime workDt;
	private WorkStatCd workStatCd;
	private LocalDateTime workPossBeginDt;
	private LocalDateTime workPossEndDt;	
	private LocalDateTime confmDt;
	private Integer gainPoint;
	private Yn setleYn;
	
}
