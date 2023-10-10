package kr.co.basedevice.corebase.restController.todo.worker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo/worker/todo_mgt")
@RequiredArgsConstructor
public class WorkerTodoMgtRestController {
	
	final private TodoService todoService;

}
