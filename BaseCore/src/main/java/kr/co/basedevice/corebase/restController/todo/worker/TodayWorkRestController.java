package kr.co.basedevice.corebase.restController.todo.worker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
import kr.co.basedevice.corebase.dto.todo.PlanWorkInfoDto;
import kr.co.basedevice.corebase.dto.todo.PointSummaryDto;
import kr.co.basedevice.corebase.dto.todo.WorkerSettleInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import kr.co.basedevice.corebase.search.todo.SearchWork;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.SettleService;
import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

/**
 * 오늘의 할일
 * 
 */
@RestController
@RequestMapping("/worker/today_work")
@RequiredArgsConstructor
public class TodayWorkRestController {
	
	private final TodoService todoService;
	private final SettleService settleService;

	/** 
	 * 할일의 오늘 수행 목록
	 * 
	 * @param SearchGrpCd 
	 * @return
	 */
	@GetMapping("/list_today_works.json")
	public ResponseEntity<List<PlanWorkInfoDto>> findByWorkPlanList(SearchWork searchWork){
		CmUser worker = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		searchWork.setWorkerSeq(worker.getUserSeq());
		searchWork.setWorkBeginDt(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusSeconds(1L));
		searchWork.setWorkEndDt(LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS));		
		searchWork.setSort("workDt");
		searchWork.setOrder("ASC");
		
		// 해당일에 할일 목록
		List <PlanWorkInfoDto> todoPlanList = todoService.findByTodayPlanList4Worker(searchWork);
				
		return ResponseEntity.ok(todoPlanList);
	}
	
	
	/** 
	 * 작업자별 포인트 목록
	 * 
	 * @param SearchGrpCd 
	 * @return
	 */
	@GetMapping("/point_summary.json")
	public ResponseEntity<List<WorkerSettleInfoDto>> findByTodoPlanList(SearchWork searchWork){
		CmUser worker = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();

		// 작업자별 요약 : 지정일의 포인트, 미지급 포인트
		List<WorkerSettleInfoDto> listWorkerSettleInfo = settleService.getWorkerSettleInfo(worker.getUserSeq());
		
		return ResponseEntity.ok(listWorkerSettleInfo);
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
