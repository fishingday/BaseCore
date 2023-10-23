package kr.co.basedevice.corebase.dto.todo;

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class QuizUserInfoDto {
	
	private Long quizUserSeq;
	
	private Long quizSeq;
	
	private Long userSeq;

	private String loginId;

	private String userNm;
	
	private String userAnswer;
	
	private Integer answerCnt;
	
	private Yn sucesYn;
		
}
