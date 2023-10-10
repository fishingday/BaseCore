package kr.co.basedevice.corebase.restController.todo.worker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo/worker/today_todo")
@RequiredArgsConstructor
public class TodayTodoRestController {
	
	final private TodoService todoService;
	
}