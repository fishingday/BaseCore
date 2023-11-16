package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.TodoTypCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class TodoWorkerInfoDto {

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
	
	private Integer aplytoOrd;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate postBeginDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate postEndDate;
	
	private Yn workerAgreYn;
		
	private List<TodoUserDto> checkerList;
}
