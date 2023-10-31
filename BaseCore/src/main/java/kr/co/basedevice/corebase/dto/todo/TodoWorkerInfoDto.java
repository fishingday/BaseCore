package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDate;

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
	
	private LocalDate postBeginDate;
	
	private LocalDate postEndDate;
	
	private Yn workerAgreYn;
	
	public int computePoint(LocalDate targetDay) {
		
		
		
		return 0;
	}
}
