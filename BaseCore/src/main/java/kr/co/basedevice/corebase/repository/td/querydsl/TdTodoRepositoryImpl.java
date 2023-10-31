package kr.co.basedevice.corebase.repository.td.querydsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.basedevice.corebase.domain.cm.QCmUser;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.QTdCheckerMap;
import kr.co.basedevice.corebase.domain.td.QTdTodo;
import kr.co.basedevice.corebase.domain.td.QTdWorkerMap;
import kr.co.basedevice.corebase.dto.todo.TodoDetailDto;
import kr.co.basedevice.corebase.dto.todo.TodoUserDto;
import kr.co.basedevice.corebase.dto.todo.TodoWorkerInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchTodoMgt;
import kr.co.basedevice.corebase.search.todo.SearchTodoWorker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TdTodoRepositoryImpl implements TdTodoRepositoryQuerydsl{
	private final JPAQueryFactory jpaQueryFactory;

	/**
	 * 할일 목록 조회
	 * 
	 */
	@Override
	public Page<TodoDetailDto> pageTodoDetailInfo(SearchTodoMgt searchTodoMgt, Pageable page) {
		QTdCheckerMap tdCheckerMap = QTdCheckerMap.tdCheckerMap;
		QTdWorkerMap tdWorkerMap = QTdWorkerMap.tdWorkerMap;
		QTdTodo tdTodo = QTdTodo.tdTodo;
		
		JPQLQuery<TodoDetailDto> query = jpaQueryFactory.select(
				Projections.bean(TodoDetailDto.class,
					 tdTodo.todoSeq
					,tdTodo.todoTitl
					,tdTodo.todoCont
					,tdTodo.todoDesc
					,tdTodo.completCondiVal
					,tdTodo.todoPoint
					,tdTodo.todoTypCd
					,tdTodo.todoDtlVal
					,tdTodo.dateLimitCnt
					,tdTodo.todoCreCd
					,tdTodo.todoCreDtlVal
					,tdTodo.postBeginDate
					,tdTodo.postEndDate
					,tdTodo.execBeginTm
					,tdTodo.execEndTm
					,tdTodo.quizUseYn
					,tdTodo.quizTypCd
				)
			)
			.from(tdTodo);
			BooleanBuilder builder = new BooleanBuilder();
			builder.and(tdTodo.delYn.eq(Yn.N));
			
			if(!ObjectUtils.isEmpty(searchTodoMgt.getTodoTitl())) {
				builder.and(tdTodo.todoTitl.contains(searchTodoMgt.getTodoTitl()));
			}			
			if(!ObjectUtils.isEmpty(searchTodoMgt.getTodoTypCd())) {
				builder.and(tdTodo.todoTypCd.eq(searchTodoMgt.getTodoTypCd()));
			}			
			if(!ObjectUtils.isEmpty(searchTodoMgt.getTodoCreCd())) {
				builder.and(tdTodo.todoCreCd.eq(searchTodoMgt.getTodoCreCd()));
			}
			
			if(!ObjectUtils.isEmpty(searchTodoMgt.getCheckerSeqList()) && searchTodoMgt.getCheckerSeqList().size() > 0) {
				JPQLQuery <Long> subQuery = 
			    		  JPAExpressions.select(Projections.bean(Long.class, tdCheckerMap.todoSeq))
			    	      .from(tdCheckerMap)
			    	      .where(tdCheckerMap.delYn.eq(Yn.N), tdCheckerMap.checkerSeq.in(searchTodoMgt.getCheckerSeqList()));			    	      
				builder.and(tdTodo.todoSeq.in(subQuery));
			}
			if(!ObjectUtils.isEmpty(searchTodoMgt.getWorkerSeqList()) && searchTodoMgt.getWorkerSeqList().size() > 0) {
				JPQLQuery <Long> subQuery = 
			    		  JPAExpressions.select(Projections.bean(Long.class, tdWorkerMap.todoSeq))
			    	      .from(tdWorkerMap)
			    	      .where(tdWorkerMap.delYn.eq(Yn.N), tdWorkerMap.workerSeq.in(searchTodoMgt.getWorkerSeqList()));			    	      
				builder.and(tdTodo.todoSeq.in(subQuery));
			}
		
			query.where(builder);
			if(!ObjectUtils.isEmpty(searchTodoMgt.getOrder()) && !ObjectUtils.isEmpty(searchTodoMgt.getSort())) {
		    	Order direction = Order.valueOf(searchTodoMgt.getOrder().toUpperCase());
		    	
		        if(searchTodoMgt.getSort().equals("todoTitl")) {
			        query.orderBy(new OrderSpecifier<>(direction, tdTodo.todoSeq));
		        }else if( searchTodoMgt.getSort().equals("todoPoint")) {
			       	query.orderBy(new OrderSpecifier<>(direction, tdTodo.todoPoint));
		        }else{
		        	query.orderBy(tdTodo.todoSeq.asc());
		        }
			}else {
	        	query.orderBy(tdTodo.todoSeq.asc());
			}
			
			QueryResults<TodoDetailDto> queryResults = query.limit(page.getPageSize()).offset(page.getOffset()).fetchResults();

			return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
	}

	/**
	 * 할일별 확인자 목록
	 * 
	 */
	@Override
	public List<TodoUserDto> getCheckerList(Long todoSeq) {
		QCmUser cmUser = QCmUser.cmUser;
		QTdCheckerMap tdCheckerMap = QTdCheckerMap.tdCheckerMap;
		
		JPQLQuery<TodoUserDto> query = jpaQueryFactory.selectDistinct(
				Projections.bean(TodoUserDto.class,
					cmUser.userSeq
					,cmUser.loginId
					,cmUser.userNm
					,cmUser.userTelNo
				)
			)
			.from(cmUser).innerJoin(tdCheckerMap).on(cmUser.userSeq.eq(tdCheckerMap.checkerSeq))
			.where(cmUser.delYn.eq(Yn.N), tdCheckerMap.delYn.eq(Yn.N), tdCheckerMap.todoSeq.eq(todoSeq));
				
		return query.fetch();
	}

	/**
	 * 할일별 작업자 목록
	 * 
	 */
	@Override
	public List<TodoUserDto> getWorkerList(Long todoSeq) {
		QCmUser cmUser = QCmUser.cmUser;
		QTdWorkerMap tdWorkerMap = QTdWorkerMap.tdWorkerMap;
		
		JPQLQuery<TodoUserDto> query = jpaQueryFactory.selectDistinct(
				Projections.bean(TodoUserDto.class,
					cmUser.userSeq
					,cmUser.loginId
					,cmUser.userNm
					,cmUser.userTelNo
					,tdWorkerMap.workerAgreYn
				)
			)
			.from(cmUser).innerJoin(tdWorkerMap).on(cmUser.userSeq.eq(tdWorkerMap.workerSeq))
			.where(cmUser.delYn.eq(Yn.N), tdWorkerMap.delYn.eq(Yn.N), tdWorkerMap.todoSeq.eq(todoSeq));
				
		return query.fetch();
	}


	/**
	 * 할일 작업자
	 * 
	 * @param searchTodoWorker
	 * @param page
	 * @return
	 */
	@Override
	public Page<TodoWorkerInfoDto> pageTodoWorkerInfo(SearchTodoWorker searchTodoWorker, Pageable page) {
		
		QTdTodo tdTodo = QTdTodo.tdTodo;
		QTdWorkerMap tdWorkerMap = QTdWorkerMap.tdWorkerMap;
		
		JPQLQuery<TodoWorkerInfoDto> query = jpaQueryFactory.select(
				Projections.bean(TodoWorkerInfoDto.class,
					 tdTodo.todoSeq
					,tdTodo.todoTitl
					,tdTodo.todoCont
					,tdTodo.todoDesc
					,tdTodo.completCondiVal
					,tdTodo.todoPoint
					,tdTodo.todoTypCd
					,tdTodo.todoDtlVal
					,tdTodo.dateLimitCnt
					,tdTodo.todoCreCd
					,tdTodo.todoCreDtlVal
					,tdTodo.postBeginDate
					,tdTodo.postEndDate
				)
			)
			.from(tdTodo).innerJoin(tdWorkerMap).on(tdTodo.todoSeq.eq(tdWorkerMap.todoSeq));
			BooleanBuilder builder = new BooleanBuilder();
			builder.and(tdTodo.delYn.eq(Yn.N));
			builder.and(tdWorkerMap.delYn.eq(Yn.N));
			builder.and(tdWorkerMap.workerSeq.eq(searchTodoWorker.getWorkerSeq()));			
			builder.and(tdTodo.postBeginDate.goe(searchTodoWorker.getTargetDay())); // <=
			builder.and(tdTodo.postEndDate.loe(searchTodoWorker.getTargetDay()));   // >= 
			
			if(!ObjectUtils.isEmpty(searchTodoWorker.getWorkerAgerYn())) {
				builder.and(tdWorkerMap.workerAgreYn.eq(searchTodoWorker.getWorkerAgerYn()));
			}
			
			query.where(builder);
			if(!ObjectUtils.isEmpty(searchTodoWorker.getOrder()) && !ObjectUtils.isEmpty(searchTodoWorker.getSort())) {
		    	Order direction = Order.valueOf(searchTodoWorker.getOrder().toUpperCase());
		    	
		        if(searchTodoWorker.getSort().equals("todoTitl")) {
			        query.orderBy(new OrderSpecifier<>(direction, tdTodo.todoSeq));
		        }else if( searchTodoWorker.getSort().equals("todoPoint")) {
			       	query.orderBy(new OrderSpecifier<>(direction, tdTodo.todoPoint));
		        }else{
		        	query.orderBy(tdTodo.todoSeq.asc());
		        }
			}else {
	        	query.orderBy(tdTodo.todoSeq.asc());
			}
			
			QueryResults<TodoWorkerInfoDto> queryResults = query.limit(page.getPageSize()).offset(page.getOffset()).fetchResults();

			return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
	}
	
}
