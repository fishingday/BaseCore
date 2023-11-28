package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class WorkerWorkDto {
	
	private String workerNm; 
	private String targeterCalnm;
	
	private Long workerSeq;
	
	private Long workSeq;
	
	private Long todoSeq;
	
	private String workTitl;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") //날짜 포멧 바꾸기
	private LocalDateTime workDt;
	
	private WorkStatCd workStatCd;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul") //날짜 포멧 바꾸기
	private LocalDateTime confmDt;
	
	private Integer gainPoint;
	
	private Long checkerSeq;
	
	private Yn setleYn;
}
