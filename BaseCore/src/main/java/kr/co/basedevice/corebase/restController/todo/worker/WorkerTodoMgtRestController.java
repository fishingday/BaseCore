package kr.co.basedevice.corebase.restController.todo.worker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

/**
 * 할일 할당 및 할일 목록 조회
 * 
 * @author fishingday
 *
 */
@RestController
@RequestMapping("/todo/worker/todo_mgt")
@RequiredArgsConstructor
public class WorkerTodoMgtRestController {
	
	final private TodoService todoService;

	// 할당 할일 목록 조회 with 일/주/월별 예상 포인트
	
	
	// 미할당/할당 작업 목록 조회 with 일/주/월별 예상 포인트
	
	
	// 할당/미할당 할일 저정
}
