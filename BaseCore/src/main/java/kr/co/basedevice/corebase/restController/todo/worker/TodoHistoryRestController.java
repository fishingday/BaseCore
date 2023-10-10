package kr.co.basedevice.corebase.restController.todo.worker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

/**
 * 작업 이력 조회
 * 
 * @author fishingday
 *
 */
@RestController
@RequestMapping("/todo/worker/todo_history")
@RequiredArgsConstructor
public class TodoHistoryRestController {
	
	final private TodoService todoService;

	// 작업 이력 조회
	
	
	// 작업 상세 조회
	
}
