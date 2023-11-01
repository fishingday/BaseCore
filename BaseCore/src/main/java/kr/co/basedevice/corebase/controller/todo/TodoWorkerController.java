package kr.co.basedevice.corebase.controller.todo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/worker")
public class TodoWorkerController {
	
	/**
	 * 오늘의 할일
	 * 
	 * @return
	 */
	@GetMapping(value={"/today_work", "/today_work/init.html"})
	public String viewTodayPlan() {
		return "/todo/worker/today_work.html";
	}
	
	/**
	 * 할일 동의 및 관리(사용자)
	 * 
	 * @return
	 */
	@GetMapping(value={"/work_mgt", "/work_mgt/init.html"})
	public String viewTodoSettle() {
		return "/todo/worker/work_mgt.html";
	}
		
	/** 
	 * 할일 이력
	 * 
	 * @return
	 */
	@GetMapping(value={"/history", "/history/init.html"})
	public String viewTodoMgt() {
		return "/todo/worker/work_history.html";
	}
		
	/** 
	 * 포인트 관리
	 * 
	 * @return
	 */
	@GetMapping(value={"/settle", "/settle/init.html"})
	public String viewPointSettleMgt() {
		return "/todo/worker/settle.html";
	}
}
