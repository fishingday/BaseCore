package kr.co.basedevice.corebase.restController.todo.checker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo/checker/quiz_mgt")
@RequiredArgsConstructor
public class TodoQuizMgtRestController {
	
	final private TodoService todoService;
	
	// 퀴즈 목록 조회 : 사용된 퀴즈, 사용자별 퀴즈, 미사용 퀴즈 ...
	
	
	// 퀴즈 상세 조회
	
	
	// 퀴즈 저장
	
	
	// 사용자별 퀴즈 목록 (팝업)

}
