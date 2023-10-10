package kr.co.basedevice.corebase.service.todo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.todo.TdTodo;
import kr.co.basedevice.corebase.domain.todo.TdWork;
import kr.co.basedevice.corebase.dto.todo.TodoPlanDto;
import kr.co.basedevice.corebase.dto.todo.TodoSummaryDto;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TodoService {
	
	final private TodoService todoService;

	/**
	 * 확인자 용 할일 목록
	 * 
	 * @param searchTodo
	 * @return
	 */
	public List<TodoPlanDto> findByTodoPlanList4Checker(SearchTodo searchTodo) {
		// TODO Auto-generated method stub
		return null;
	}

	public TdWork getTdWork(Long workSeq) {
		// TODO Auto-generated method stub
		return null;
	}

	public TdTodo getTdTodo(Long todoSeq) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TodoSummaryDto> findByPointSummary4Checker(SearchTodo searchTodo) {
		// TODO Auto-generated method stub
		return null;
	}

}
