package kr.co.basedevice.corebase.controller.todo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checker")
public class TodoCheckerController {

	/**
	 * 오늘의 계획
	 * 
	 * @return
	 */
	@GetMapping(value={"/today_plan", "/today_plan/init.html"})
	public String viewTodayPlan() {
		return "/todo/checker/today_plan.html";
	}
	
	/**
	 * 할일 정산
	 * 
	 * @return
	 */
	@GetMapping(value={"/settle", "/settle/init.html"})
	public String viewTodoSettle() {
		return "/todo/checker/todo_settle.html";
	}
		
	/** 
	 * 할일 관리
	 * 
	 * @return
	 */
	@GetMapping(value={"/todo_mgt", "/todo_mgt/init.html"})
	public String viewTodoMgt() {
		return "/todo/checker/todo_mgt.html";
	}
		
	/** 
	 * Quiz 관리
	 * 
	 * @return
	 */
	@GetMapping(value={"/quiz_mgt", "/quiz_mgt/init.html"})
	public String viewQuizMgt() {
		return "/todo/checker/quiz_mgt.html";
	}
}
