package kr.co.basedevice.corebase.search.todo;

import java.time.LocalDate;

import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.TodoTypCd;
import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import lombok.Data;

@Data
public class SearchWork {
	
	private Long workerSeq;
	
	private String todoTitl;
	private TodoTypCd todoTypCd;	
	private TodoCreCd todoCreCd;

	private String workTitl;
	private String workCont;
	private WorkStatCd workStatCd;

	private LocalDate workBeginDt;
	private LocalDate workEndDt;
	
	private String order;
	private String sort;
}
