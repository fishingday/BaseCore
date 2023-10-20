package kr.co.basedevice.corebase.repository.td.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.basedevice.corebase.dto.todo.TodayPlanDto;
import kr.co.basedevice.corebase.search.todo.SearchTodo;

public interface TdWorkRepositoryQuerydsl {

	Page<TodayPlanDto> pageTodayPlan(SearchTodo searchTodo, Pageable pageable);
}
