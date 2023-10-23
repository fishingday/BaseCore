package kr.co.basedevice.corebase.dto.todo;

import kr.co.basedevice.corebase.domain.code.QuizTypCd;
import lombok.Data;

@Data
public class QuizInfoDto {
	
	private Long quizSeq;

	private QuizTypCd quizTypCd;
	
	private String quizTitl;
	
	private String quizCont;
	
	private String quizAnswer;
}
