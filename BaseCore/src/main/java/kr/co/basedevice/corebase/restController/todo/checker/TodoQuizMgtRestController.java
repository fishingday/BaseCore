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

}
