package kr.co.basedevice.corebase.restController.todo.worker;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.todo.TdTodo;
import kr.co.basedevice.corebase.domain.todo.TdWork;
import kr.co.basedevice.corebase.dto.todo.TodayPlanDto;
import kr.co.basedevice.corebase.dto.todo.TodayTodoDto;
import kr.co.basedevice.corebase.dto.todo.TodoDetailDto;
import kr.co.basedevice.corebase.dto.todo.TodoSummaryDto;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo/worker/today_todo")
@RequiredArgsConstructor
public class TodayTodoRestController {
	
	final private TodoService todoService;
	
	/** 
	 * 할일의 오늘 수행 목록
	 * 
	 * @param SearchGrpCd 
	 * @return
	 */
	@GetMapping("/get_today_todo_list.json")
	public ResponseEntity<Map<String,Object>> findByTodoPlanList(SearchTodo searchTodo){
		CmUser worker = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		searchTodo.setWorkerSeq(worker.getUserSeq());
		
		if(ObjectUtils.isEmpty(searchTodo.getToDay())) {
			searchTodo.setToDay(LocalDate.now());
		}
		
		Map<String,Object> retMap = new HashMap<>();
		// 해당일에 할일 목록
		List <TodayTodoDto> todoPlanList = todoService.findByTodayPlanList4Worker(searchTodo);
		retMap.put("todoPlanList", todoPlanList);
		
		// Actor별 요약 : 지정일의 포인트, 미지급 포인트
		List <TodoSummaryDto> todoSummaryList = todoService.findByPointSummary4Worker(searchTodo);
		retMap.put("todoSummaryList", todoSummaryList);
		
		return ResponseEntity.ok(retMap);
	}
	
	
	/**
	 * 할일/작업 상세 조회
	 * 
	 * @param workSeq
	 * @return
	 */
	@GetMapping("/get_todo_detail.json")
	public ResponseEntity<Map<String,Object>> getTodoDetail(Long workSeq){
		
		Map<String,Object> retMap = new HashMap<>();

		// 작업 조회
		TdWork tdWork = todoService.getTdWork(workSeq);
		retMap.put("tdWork", tdWork);
		
		if(!ObjectUtils.isEmpty(tdWork)) { // 작업이 있는 곳에 계획이 있음
			// 할일 조회
			TdTodo tdTodo = todoService.getTdTodo(tdWork.getTodoSeq());
			retMap.put("tdTodo", tdTodo);
		}
		
		return ResponseEntity.ok(retMap);
	}
	
	/**
	 * 작업 입력/수정
	 * 
	 * @param tdWork
	 * @return
	 */
	@PutMapping("/save_work.json")
	public ResponseEntity<Boolean> saveWork(TdWork tdWork){
		CmUser worker = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		tdWork.setWorkerSeq(worker.getUserSeq());
		
		todoService.saveTdWork(tdWork);
		
		return ResponseEntity.ok(true);
	}
}
