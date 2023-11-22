package kr.co.basedevice.corebase.restController.todo.checker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.td.TdQuiz;
import kr.co.basedevice.corebase.dto.todo.QuizInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchQuiz;
import kr.co.basedevice.corebase.service.todo.QuizService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checker/quiz_mgt")
@RequiredArgsConstructor
public class TodoQuizMgtRestController {
	
	final private QuizService quizService;
	
	/** 
	 * 퀴즈 목록 조회 : 사용된 퀴즈, 사용자별 퀴즈, 미사용 퀴즈 ...
	 * 
	 * @param SearchGrpCd 
	 * @return
	 */
	@GetMapping("/page_quiz_list.json")
	public ResponseEntity<Page<QuizInfoDto>> pageQuizList(SearchQuiz searchQuiz, Pageable page){
		if(page == null) {
			page = PageRequest.of(0, 10);
		}
		
		Page<QuizInfoDto> pageQuizInfo = quizService.findByQuizInfo(searchQuiz, page);

		
		return ResponseEntity.ok(pageQuizInfo);
	}
	
	/**
	 * 퀴즈 저장
	 * 
	 * @param tdQuiz
	 * @return
	 */
	@PutMapping("save_quiz.json")
	public ResponseEntity<Boolean> saveQuiz(TdQuiz tdQuiz){
		boolean isSave = quizService.saveQuiz(tdQuiz);
		
		return ResponseEntity.ok(isSave);
	}
}
