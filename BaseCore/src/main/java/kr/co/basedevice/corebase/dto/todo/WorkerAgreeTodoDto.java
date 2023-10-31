package kr.co.basedevice.corebase.dto.todo;

import java.util.List;

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class WorkerAgreeTodoDto {
	private List<Long> listTodoSeq;
	
	private Yn AgreeYn;
	
	private Long workerSeq;
}
