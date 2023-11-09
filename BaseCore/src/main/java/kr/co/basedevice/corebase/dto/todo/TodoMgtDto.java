package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import kr.co.basedevice.corebase.domain.code.QuizTypCd;
import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.TodoTypCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class TodoMgtDto {
	private Long todoSeq;
	
	private String todoTitl;
	
	private String todoCont;
	
	private String todoDesc;
	
	private String completCondiVal;
	
	private Integer todoPoint;
	
	private TodoTypCd todoTypCd;
	
	private String todoDtlVal;
	
	private Integer dateLimitCnt;
	
	private TodoCreCd todoCreCd;
	
	private String todoCreDtlVal;
	
	private LocalDate postBeginDate;
	
	private LocalDate postEndDate;
	
	private LocalTime limitBeginTm;
	
	private LocalTime limitEndTm;
	
	private Yn quizUseYn;
	
	private QuizTypCd quizTypCd;
	
	private List<TodoUserDto> checkerList;
	
	private List<TodoUserDto> workerList;
}
