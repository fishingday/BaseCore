package kr.co.basedevice.corebase.dto.todo;

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class WorkerAgreeTodoDto {
	private Long todoSeq;
	
	private Yn agreeYn;
	
	private Long workerSeq;
}
