package kr.co.basedevice.corebase.search.todo;

import java.time.LocalDate;

import kr.co.basedevice.corebase.domain.code.TodoTypCd;
import lombok.Data;

@Data
public class SearchTodo {
	
	private Long workerSeq;
	
	private LocalDate toDay;
	
	private String todoTitle;
	
	private String todoCont;
	
	private String todoDesc;
	
	private String workTitl;
	
	private String workCont;
	
	private TodoTypCd todoTypCd;
	
	private Long checkerSeq;
}
