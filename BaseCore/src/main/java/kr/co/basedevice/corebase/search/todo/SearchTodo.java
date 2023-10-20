package kr.co.basedevice.corebase.search.todo;

import java.time.LocalDate;

import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import lombok.Data;

@Data
public class SearchTodo {
	
	private LocalDate toDay;	
	private Long workerSeq;	
	private String todoTitl;	
	private String todoCont;	
	private String todoDesc;	
	private String workTitl;	
	private String workCont;	
	private WorkStatCd workStatCd;
	private Long checkerSeq;
		
	private String order;
	private String sort;
}
