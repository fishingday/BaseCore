package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TodoWorkDataDto {
	private Long workSeq;
	private Long todoSeq;
	private Long quizSeq;
	
	private Long workerSeq;
	
	private String workTitl;
	private String workCont;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") //날짜 포멧 바꾸기
	private LocalDateTime workBeginDt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") //날짜 포멧 바꾸기
	private LocalDateTime workEndDt;	
	
	private String workRsltVal;
	
	private Integer gainPoint;
}
