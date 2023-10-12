package kr.co.basedevice.corebase.restController.todo.checker;

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
import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.dto.todo.TodayPlanDto;
import kr.co.basedevice.corebase.dto.todo.TodoSummaryDto;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

/**
 * 확인자 화면 작업 계획
 * 
 * @author fishingday
 *
 */
@RestController
@RequestMapping("/todo/checker/today_plan")
@RequiredArgsConstructor
public class TodayPlanRestController {
	
	final private TodoService todoService;
		
	/** 
	 * 확인자가 생성한 할일의 오늘 수행 목록
	 * 
	 * @param SearchGrpCd 
	 * @return
	 */
	@GetMapping("/get_today_plan_list.json")
	public ResponseEntity<Map<String,Object>> findByTodayPlanList(SearchTodo searchTodo){
		CmUser checker = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		searchTodo.setCheckerSeq(checker.getUserSeq());
		
		if(ObjectUtils.isEmpty(searchTodo.getToDay())) {
			searchTodo.setToDay(LocalDate.now());
		}
		
		Map<String,Object> retMap = new HashMap<>();
		// 해당일에 할일 목록
		List <TodayPlanDto> todayPlanList = todoService.findByTodayPlanList4Checker(searchTodo);
		retMap.put("todayPlanList", todayPlanList);
		
		// Actor별 요약 : 지정일의 포인트, 미지급 포인트
		List <TodoSummaryDto> todoSummaryList = todoService.findByPointSummary4Checker(searchTodo);
		retMap.put("todoSummaryList", todoSummaryList);
		
		return ResponseEntity.ok(retMap);
	}
	
	/**
	 * 작업 상세 조회
	 * 
	 * @param workSeq
	 * @return
	 */
	@GetMapping("/get_todo_plan.json")
	public ResponseEntity<Map<String,Object>> getTodoPlanDetail(Long workSeq){
		
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
	 * 할일 데이터의 편집 또는 생성
	 * 
	 * @param tdWork
	 * @return
	 */
	@PutMapping("/save_todo_work.json")
	public ResponseEntity<Boolean> saveTodoWork(TdWork tdWork){
		CmUser checker = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		tdWork.setCheckerSeq(checker.getUserSeq());
		
		
		return ResponseEntity.ok(true);
	}
}
