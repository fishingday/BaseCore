package kr.co.basedevice.corebase.restController.todo.worker;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.QuizTypCd;
import kr.co.basedevice.corebase.domain.td.TdQuiz;
import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.dto.todo.PlanWorkInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkerSettleInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchWork;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.QuizService;
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
	private final QuizService quizService;
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
		
		if(ObjectUtils.isEmpty(searchWork.getWorkDate())) {
			searchWork.setWorkDate(LocalDate.now());
		}
		searchWork.setWorkBeginDate(searchWork.getWorkDate());
		searchWork.setWorkEndDate(searchWork.getWorkDate().plusDays(1));
		
		// 해당일에 할일 목록
		List <PlanWorkInfoDto> todoPlanList = todoService.findByTodayPlanList4Worker(searchWork);
				
		return ResponseEntity.ok(todoPlanList);
	}
	
	/**
	 * 작업자별 할일 목록
	 * 
	 * @return
	 */
	@GetMapping("/list_mytodo.json")
	public ResponseEntity<List<TdTodo>> findByMyShortTodoList(){
		CmUser worker = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		List<TdTodo> listTdTodo = 	todoService.findByUserSeq(worker.getUserSeq());
				
		return ResponseEntity.ok(listTdTodo);
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
	
	@GetMapping("/today_quiz.json")
	public ResponseEntity<TdQuiz> findByQuiz(QuizTypCd quizTypCd){
		CmUser worker = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		TdQuiz tdQuiz =quizService.getTodayQuiz(worker.getUserSeq(), quizTypCd);
		
		return ResponseEntity.ok(tdQuiz);
	}
}
