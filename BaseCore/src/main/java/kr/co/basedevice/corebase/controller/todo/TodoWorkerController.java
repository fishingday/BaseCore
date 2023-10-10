package kr.co.basedevice.corebase.controller.todo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todo/worker")
public class TodoWorkerController {
	
	/**
	 * 오늘의 할일
	 * 
	 * @return
	 */
	@GetMapping(value={"/today_todo", "/today_todo/init.html"})
	public String viewTodayPlan() {
		return "/todo/worker/today_todo.html";
	}
	
	/**
	 * 할일 관리(사용자)
	 * 
	 * @return
	 */
	@GetMapping(value={"/todo_mgt", "/todo_mgt/init.html"})
	public String viewTodoSettle() {
		return "/todo/worker/todo_mgt.html";
	}
		
	/** 
	 * 할일 이력
	 * 
	 * @return
	 */
	@GetMapping(value={"/todo_history", "/todo_history/init.html"})
	public String viewTodoMgt() {
		return "/todo/worker/todo_history.html";
	}
		
	/** 
	 * 포인트 관리
	 * 
	 * @return
	 */
	@GetMapping(value={"/point_settle", "/point_settle/init.html"})
	public String viewPointSettleMgt() {
		return "/todo/worker/point_settle.html";
	}
}
