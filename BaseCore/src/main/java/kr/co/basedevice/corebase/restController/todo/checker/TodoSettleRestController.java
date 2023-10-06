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

}
