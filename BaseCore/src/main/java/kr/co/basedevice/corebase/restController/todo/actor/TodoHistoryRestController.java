package kr.co.basedevice.corebase.restController.todo.actor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo/actor/todo_history")
@RequiredArgsConstructor
public class TodoHistoryRestController {
	
	final private TodoService todoService;

}
