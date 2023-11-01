package kr.co.basedevice.corebase.restController.todo.worker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.dto.todo.TodoWorkerInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkerAgreeTodoDto;
import kr.co.basedevice.corebase.search.todo.SearchTodoWorker;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.TodoService;
import kr.co.basedevice.corebase.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;

/**
 * 할일 동의 및 할일 목록 조회
 * 
 * @author fishingday
 *
 */
@RestController
@RequestMapping("/worker/work_mgt")
@RequiredArgsConstructor
public class WorkerWorkMgtRestController {
	
	final private TodoService todoService;

	/**
	 * 할일 목록 조회 
	 * with 일/주/월별 예상 포인트
	 * 
	 * @param searchTodoWorker
	 * @param page
	 * @return
	 */
	@GetMapping("/page_work_info.json")
	public ResponseEntity<Map<String, Object>> pageWorkInfo(SearchTodoWorker searchTodoWorker, Pageable page){
		if(page == null) {
			page = PageRequest.of(0, 10);
		}
		
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		searchTodoWorker.setWorkerSeq(cmUser.getUserSeq());
		
		// 지정일이 없다면..
		if(ObjectUtils.isEmpty(searchTodoWorker.getTargetDay())) {
			searchTodoWorker.setTargetDay(LocalDate.now());
		}
		
		Map<String, Object> retMap = new HashMap<>();
		Page<TodoWorkerInfoDto> pageTodoWorkerInfo = todoService.pageTodoWorkerInfo(searchTodoWorker, page);
		retMap.put("pageTodoWorkerInfo", pageTodoWorkerInfo);
				
		if(pageTodoWorkerInfo != null && !pageTodoWorkerInfo.isEmpty()){			
			int maxDay = searchTodoWorker.getTargetDay().lengthOfMonth();
			LocalDate firstDate = searchTodoWorker.getTargetDay().withDayOfMonth(1);
			
			// 오늘의 주
			LocalDate monWeek = searchTodoWorker.getTargetDay().plusDays(searchTodoWorker.getTargetDay().getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
			LocalDate sunWeek = searchTodoWorker.getTargetDay().plusDays(DayOfWeek.SUNDAY.getValue() - searchTodoWorker.getTargetDay().getDayOfWeek().getValue());
			
			int maxDayPoint = 0;
			int maxWeekPoint = 0;
			int maxMonthPoint = 0;
			for(TodoWorkerInfoDto todoWorkerInfoDto : pageTodoWorkerInfo.getContent()) {
				if(todoWorkerInfoDto.getTodoCreCd() == TodoCreCd.DAILY) { // 매일은 허용 횟수가 있다.
					maxDayPoint += (todoWorkerInfoDto.getTodoPoint().intValue() * todoWorkerInfoDto.getDateLimitCnt().intValue());					
					maxWeekPoint += (todoWorkerInfoDto.getTodoPoint().intValue() * todoWorkerInfoDto.getDateLimitCnt().intValue()) * 7; // 일주일
					maxMonthPoint += (todoWorkerInfoDto.getTodoPoint().intValue() * todoWorkerInfoDto.getDateLimitCnt().intValue()) * maxDay;
				}else if(todoWorkerInfoDto.getTodoCreCd() == TodoCreCd.MONTH) { // 특정 일자를 , 로 구분
					String [] aplyDays = todoWorkerInfoDto.getTodoDtlVal().split(",");				
					for(String aplyDay : aplyDays) {
						if(aplyDay.equals(String.valueOf(searchTodoWorker.getTargetDay().getDayOfMonth()))) { // 오늘이 그날...
							maxDayPoint += todoWorkerInfoDto.getTodoPoint();
						}
						int dayOfYear = LocalDate.now().withDayOfMonth(Integer.valueOf(aplyDay)).getDayOfYear();
						if(monWeek.getDayOfYear() <= dayOfYear && sunWeek.getDayOfYear() >= dayOfYear) {
							maxWeekPoint += todoWorkerInfoDto.getTodoPoint();
						}
					}
					
					maxMonthPoint += todoWorkerInfoDto.getTodoPoint() * aplyDays.length;
				}else if(todoWorkerInfoDto.getTodoCreCd() == TodoCreCd.WEEK) { // 특정 요일이 몇번 들어가 있는지 확인
					String [] aplyWeeks = todoWorkerInfoDto.getTodoDtlVal().split(","); // DayOfWeek 값을 넣어야...
					for(String aplyWeek : aplyWeeks) {
						DayOfWeek aplyWeekCode = DayOfWeek.valueOf(aplyWeek);
						
						if(aplyWeekCode == searchTodoWorker.getTargetDay().getDayOfWeek()) {
							maxDayPoint += todoWorkerInfoDto.getTodoPoint();
						}						
						
						int cnt = DateTimeUtils.cntWeekdayOfMonth(firstDate, aplyWeekCode);
						maxMonthPoint += cnt * todoWorkerInfoDto.getTodoPoint();
					}
				}
			}
			retMap.put("maxDayPoint", maxDayPoint);
			retMap.put("maxWeekPoint", maxWeekPoint);
			retMap.put("maxMonthPoint", maxMonthPoint);
		}
		
		return ResponseEntity.ok(retMap);
	}
	
	/**
	 * 할당된 할일 목록 조회
	 * 
	 * @return
	 */
	@GetMapping("/list_assign_todo.json")
	public ResponseEntity<List<TdTodo>> listAssignTodo(){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		List<TdTodo> listTdTodo = todoService.findByAgreeTodo(cmUser.getUserSeq());
		
		return ResponseEntity.ok(listTdTodo);
	}
	
	/**
	 * 미할달된 할일 목록 조회
	 * 
	 * @return
	 */
	@GetMapping("/list_unassign_todo.json")
	public ResponseEntity<List<TdTodo>> listUnassignTodo(){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		List<TdTodo> listTdTodo = todoService.findByUnAgreeTodo(cmUser.getUserSeq());
		
		return ResponseEntity.ok(listTdTodo);
	}
	
	/**
	 * 할일에 대한 동의/미동의 저장
	 * 
	 * @param workerAgreeTodo
	 * @return
	 */
	@PutMapping("/save_worker_agree")
	public ResponseEntity<Boolean> saveWorkerAgreeTodo(WorkerAgreeTodoDto workerAgreeTodo){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		workerAgreeTodo.setWorkerSeq(cmUser.getUserSeq());
		
		boolean isSave = todoService.saveWorkerAgreeTodo(workerAgreeTodo);		
		
		return ResponseEntity.ok(isSave);
	}	
}
