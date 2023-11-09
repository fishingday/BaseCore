package kr.co.basedevice.corebase.search.todo;

import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.TodoTypCd;
import lombok.Data;

@Data
public class SearchTodoMgt {
	
	private Long checkerSeq;
	
	private String todoTitl;
	private String workerNm;
	
	private TodoTypCd todoTypCd;
	private TodoCreCd todoCreCd;
	
	private String order;
	private String sort;
}
