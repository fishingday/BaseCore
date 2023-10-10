package kr.co.basedevice.corebase.restController.todo.checker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo/checker/todo_settle")
@RequiredArgsConstructor
public class TodoSettleRestController {
	
	final private TodoService todoService;
	
	
	// 정산 목록 with 사용자별 미정산 현황 
	
	
	// 정산 상세 조회
	
	
	// 정산 하기

}
