package kr.co.basedevice.corebase.repository.td.querydsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.basedevice.corebase.dto.todo.TodoDetailDto;
import kr.co.basedevice.corebase.dto.todo.TodoUserDto;
import kr.co.basedevice.corebase.dto.todo.TodoWorkerInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchTodoMgt;
import kr.co.basedevice.corebase.search.todo.SearchTodoWorker;

public interface TdTodoRepositoryQuerydsl {

	/**
	 * 할일 목록 조회
	 * 
	 * @param searchTodoMgt
	 * @param pageable
	 * @return
	 */
	Page<TodoDetailDto> pageTodoDetailInfo(SearchTodoMgt searchTodoMgt, Pageable pageable);
	
	/**
	 * 할일별 확인자 목록
	 * 
	 * @param todoSeq
	 * @return
	 */
	List<TodoUserDto> getCheckerList(Long todoSeq);

	/**
	 * 할일별 작업자 목록
	 * 
	 * @param todoSeq
	 * @return
	 */
	List<TodoUserDto> getWorkerList(Long todoSeq);
	

	/**
	 * 할일 작업자
	 * 
	 * @param searchTodoWorker
	 * @param page
	 * @return
	 */
	Page<TodoWorkerInfoDto> pageTodoWorkerInfo(SearchTodoWorker searchTodoWorker, Pageable page);
	
}
