package kr.co.basedevice.corebase.repository.td.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.basedevice.corebase.dto.todo.TodoSummaryDto;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TdWorkRepositoryImpl implements TdWorkRepositoryQuerydsl{

	private final JPAQueryFactory jpaQueryFactory;
	
	@Override
	public List<TodoSummaryDto> findByUserSummary(SearchTodo searchTodo) {

		
		
		
		return null;
	}

}
