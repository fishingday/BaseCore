package kr.co.basedevice.corebase.search.todo;

import java.util.List;

import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.TodoTypCd;
import lombok.Data;

@Data
public class SearchTodoMgt {

	private List<Long> checkerSeqList;
	private List<Long> workerSeqList;
	private String todoTitl;
	private TodoTypCd todoTypCd;
	private TodoCreCd todoCreCd;
	
	private String order;
	private String sort;
}
