package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;

import kr.co.basedevice.corebase.domain.code.QuizTypCd;
import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.TodoTypCd;
import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.domain.td.TdWork;
import lombok.Data;

@Data
public class WorkDetailInfoDto {
	private Integer num;
	
	private Long todoSeq;
	private String todoTitl;
	private String todoCont;
	private String todoDesc;
	private String completCondiVal;
	private Integer todoPoint;	
	private TodoTypCd todoTypCd;
	private Integer dateLimitCnt;	
	private TodoCreCd todoCreCd;	
	private Yn quizUseYn;
	private QuizTypCd quizTypCd;
	
	private Long workSeq;	
	private Long workerSeq;
	private String workTitl;
	private String workCont;
	private LocalDateTime workDt;
	private WorkStatCd workStatCd;
	private LocalDateTime confmDt;
	private Integer gainPoint;
	private Long checkerSeq;
	private Yn setleYn;
	
	public void setTdTodo(TdTodo tdTodo) {
		this.todoSeq 		= tdTodo.getTodoSeq();
		this.todoTitl 		= tdTodo.getTodoTitl();
		this.todoCont		= tdTodo.getTodoCont();
		this.todoDesc		= tdTodo.getTodoDesc();
		this.completCondiVal= tdTodo.getCompletCondiVal();
		this.todoPoint		= tdTodo.getTodoPoint();
		this.todoTypCd		= tdTodo.getTodoTypCd();
		this.dateLimitCnt	= tdTodo.getDateLimitCnt();
		this.todoCreCd 		= tdTodo.getTodoCreCd();
		this.quizUseYn		= tdTodo.getQuizUseYn();
		this.quizTypCd		= tdTodo.getQuizTypCd();
	}
	
	public void setTdWork(TdWork tdWork) {
		this.workSeq	= tdWork.getWorkSeq();
		this.workerSeq	= tdWork.getWorkerSeq();
		this.workTitl	= tdWork.getWorkTitl();
		this.workCont	= tdWork.getWorkCont();
		this.workDt		= tdWork.getWorkDt();
		this.workStatCd	= tdWork.getWorkStatCd();
		this.confmDt	= tdWork.getConfmDt();
		this.gainPoint	= tdWork.getGainPoint();
		this.checkerSeq	= tdWork.getCheckerSeq();
		this.setleYn	= tdWork.getSetleYn();
	}
}
