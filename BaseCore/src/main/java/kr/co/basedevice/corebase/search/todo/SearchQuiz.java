package kr.co.basedevice.corebase.search.todo;

import kr.co.basedevice.corebase.domain.code.QuizTypCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class SearchQuiz {

	private QuizTypCd quizTypCd;
	private String quizTitl;
	private String quizCont;
	private String quizAnswer;
	
	private Yn useQuizYn;	
	private Long quizUserSeq;

	private String order;
	private String sort;
}
