package kr.co.basedevice.corebase.search.todo;

import java.util.List;

import lombok.Data;

@Data
public class SearchWorker {
	
	private Long checkerSeq;
	private List<Long> listWorkerSeq;
	
	private String order;
	private String sort;
}
