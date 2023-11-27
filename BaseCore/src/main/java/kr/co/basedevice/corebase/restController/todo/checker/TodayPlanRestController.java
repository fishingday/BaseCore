package kr.co.basedevice.corebase.restController.todo.checker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.dto.todo.PlanWorkInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchPlanWork;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.TodoService;
import kr.co.basedevice.corebase.service.todo.WorkService;
import lombok.RequiredArgsConstructor;

/**
 * 확인자 화면 작업 계획
 * 
 * @author fishingday
 *
 */
@RestController
@RequestMapping("/checker/today_plan")
@RequiredArgsConstructor
public class TodayPlanRestController {
	
	final private TodoService todoService;
	final private WorkService workService;
		
	
	/**
	 * 작업자별 할일 목록
	 * - 대시보드에서 사용.
	 * 
	 * @param searchSettle
	 * @param page
	 * @return
	 */
	@GetMapping("/list_today_workinfo.json")
	public ResponseEntity<List<PlanWorkInfoDto>> listWorkerSettleInfo(SearchPlanWork searchPlanWork){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		searchPlanWork.setCheckerSeq(cmUser.getUserSeq());		
		if(ObjectUtils.isEmpty(searchPlanWork.getWorkDate())) {
			searchPlanWork.setWorkDate(LocalDate.now());
		}
		searchPlanWork.setWorkBeginDt(LocalDateTime.of(searchPlanWork.getWorkDate(), LocalTime.of(0, 0, 0, 0)).minusSeconds(1));
		searchPlanWork.setWorkEndDt(LocalDateTime.of(searchPlanWork.getWorkDate().plusDays(1), LocalTime.of(0, 0, 0, 0)));	
		
		List<PlanWorkInfoDto> listPlanWorkInfoDto = todoService.listPlanWorkInfo(searchPlanWork);
		
		return ResponseEntity.ok(listPlanWorkInfoDto);
	}

	/**
	 * 할일 저장
	 * 
	 * @param saveWork
	 * @return
	 */
	@PutMapping("/save_todo_work.json")
	public ResponseEntity<Boolean> saveTodoWork(TdWork confirmWork){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		confirmWork.setCheckerSeq(cmUser.getUserSeq());
		
		boolean isSave = workService.confirmWork(confirmWork);
				
		return ResponseEntity.ok(isSave);
	}	
}
