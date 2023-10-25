package kr.co.basedevice.corebase.repository.td.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.basedevice.corebase.dto.todo.TodayPlanDto;
import kr.co.basedevice.corebase.dto.todo.WorkDetailInfoDto;
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
}
