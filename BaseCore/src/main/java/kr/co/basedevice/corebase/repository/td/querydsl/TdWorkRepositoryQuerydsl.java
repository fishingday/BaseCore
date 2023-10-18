package kr.co.basedevice.corebase.repository.td.querydsl;

import java.util.List;

import kr.co.basedevice.corebase.dto.todo.TodoSummaryDto;
import kr.co.basedevice.corebase.search.todo.SearchTodo;

public interface TdWorkRepositoryQuerydsl {
	/**
	 *  사용자별 포인트 요약
	 * 
	 * @param searchTodo
	 * @return
	 */
	List<TodoSummaryDto> findByUserSummary(SearchTodo searchTodo);
}
