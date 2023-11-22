package kr.co.basedevice.corebase.dto.todo;

import lombok.Data;

@Data
public class TodoWorkDataDto {
	private Long workSeq;
	private Long todoSeq;
	private Long quizSeq;
	
	private Long workerSeq;
	
	private String workTitl;
	private String workCont;
}
