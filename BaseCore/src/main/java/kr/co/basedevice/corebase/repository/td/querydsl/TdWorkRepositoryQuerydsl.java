package kr.co.basedevice.corebase.repository.td.querydsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.basedevice.corebase.dto.todo.PlanWorkInfoDto;
import kr.co.basedevice.corebase.dto.todo.TodayPlanDto;
import kr.co.basedevice.corebase.dto.todo.WorkDetailInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkerWorkDto;
import kr.co.basedevice.corebase.search.todo.SearchPlanWork;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import kr.co.basedevice.corebase.search.todo.SearchWork;

public interface TdWorkRepositoryQuerydsl {

	/**
	 * 작업 계획 목록
	 * 
	 * @param searchTodo
	 * @param pageable
	 * @return
	 */
	Page<TodayPlanDto> pageTodayPlan(SearchTodo searchTodo, Pageable pageable);
	
	/**
	 * 작업 이력 목록
	 * 
	 * @param searchWork
	 * @param page
	 * @return
	 */
	Page<WorkDetailInfoDto> pageWorkHistory(SearchWork searchWork, Pageable page);
	
	/**
	 * 작업 목록
	 * 
	 * @param searchPlanWork
	 * @return
	 */
	List<PlanWorkInfoDto> listPlanWorkInfo(SearchPlanWork searchPlanWork);
	

	/**
	 * 작업자별 할일/작업 목록
	 * 
	 * @param searchWork
	 * @return
	 */
	List<PlanWorkInfoDto> findByTodayPlanList4Worker(SearchWork searchWork);
	

	/**
	 * 미정산 작업 목록
	 * 
	 * @param listWorkerSeq
	 * @param acountSeq
	 * @return
	 */
	List<WorkerWorkDto> findByWork4UnSettle(List<Long> listWorkerSeq, Long acountSeq);
}
