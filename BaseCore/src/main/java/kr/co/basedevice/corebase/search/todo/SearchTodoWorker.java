package kr.co.basedevice.corebase.search.todo;

import java.time.LocalDate;

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class SearchTodoWorker {
	private Long workerSeq;
	private Yn workerAgerYn;
	private LocalDate targetDay;
	
	private String order;
	private String sort;
}
