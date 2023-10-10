package kr.co.basedevice.corebase.service.todo;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.todo.TdTodo;
import kr.co.basedevice.corebase.domain.todo.TdWork;
import kr.co.basedevice.corebase.dto.todo.TodoDetailDto;
import kr.co.basedevice.corebase.dto.todo.TodayPlanDto;
import kr.co.basedevice.corebase.dto.todo.TodayTodoDto;
import kr.co.basedevice.corebase.dto.todo.TodoSummaryDto;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import kr.co.basedevice.corebase.search.todo.SearchTodoMgt;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TodoService {
	
	/**
	 * 유효한 할일들로 부터 작업을 생성한다.
	 * 
	 * @param today
	 * @return
	 */
	public boolean createWorkItems(LocalDate today) {
		
		
		
		return true;
	}
	
	
	/**
	 * 확인자 용 할일 목록
	 * 
	 * @param searchTodo
	 * @return
	 */
	public List<TodayPlanDto> findByTodayPlanList4Checker(SearchTodo searchTodo) {
		// TODO 해당일에 생성된 확인자의 할일/작업 목록
		
		return null;
	}

	public TdWork getTdWork(Long workSeq) {
		// TODO 작업 조회
		return null;
	}

	public TdTodo getTdTodo(Long todoSeq) {
		// TODO 할일 조회 
		return null;
	}

	public List<TodoSummaryDto> findByPointSummary4Checker(SearchTodo searchTodo) {
		// TODO 확인자의 할일을 완룐한 수행자의 해당일 획득 포인트와 지급예정포인트 
		
		return null;
	}

	public List<TodoDetailDto> findByTodoList(SearchTodoMgt searchTodoMgt) {
		
		// TODO 확인자가 생성한 할일 목록 조회
		return null;
	}

	public TodoDetailDto getTdTodoDetail(Long todoSeq) {
		// TODO 할일, 작업자, 확인자
		return null;
	}

	public List<TodayTodoDto> findByTodayPlanList4Worker(SearchTodo searchTodo) {
		// 사용자용 오늘의 할일
		return null;
	}

	public List<TodoSummaryDto> findByPointSummary4Worker(SearchTodo searchTodo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveTdWork(TdWork tdWork) {
		// TODO Auto-generated method stub
		
	}

}
