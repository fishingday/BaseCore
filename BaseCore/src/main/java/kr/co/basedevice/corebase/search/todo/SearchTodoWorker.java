package kr.co.basedevice.corebase.search.todo;

import java.time.LocalDate;

import kr.co.basedevice.corebase.domain.code.TodoTypCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class SearchTodoWorker {
	private Long workerSeq;
	
	private String todoTitl;
	private String checkerNm;
	
	private TodoTypCd todoTypCd;
	private Yn workerAgerYn;
	
	private LocalDate TargetDay;
	
	private String order;
	private String sort;
}
