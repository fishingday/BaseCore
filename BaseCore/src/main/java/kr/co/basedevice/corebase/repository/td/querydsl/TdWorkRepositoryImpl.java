package kr.co.basedevice.corebase.repository.td.querydsl;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.basedevice.corebase.domain.cm.QCmUser;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.QTdTodo;
import kr.co.basedevice.corebase.domain.td.QTdWork;
import kr.co.basedevice.corebase.dto.todo.TodayPlanDto;
import kr.co.basedevice.corebase.dto.todo.WorkDetailInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import kr.co.basedevice.corebase.search.todo.SearchWork;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TdWorkRepositoryImpl implements TdWorkRepositoryQuerydsl{

	private final JPAQueryFactory jpaQueryFactory;
	
	
	@Override
	public Page<TodayPlanDto> pageTodayPlan(SearchTodo searchTodo, Pageable page) {
		QTdTodo tdTodo = QTdTodo.tdTodo;
		QTdWork tdWork = QTdWork.tdWork;
		QCmUser cmUser = QCmUser.cmUser; 
		
		JPQLQuery<TodayPlanDto> query = jpaQueryFactory.select(
				Projections.bean(TodayPlanDto.class,
					 tdTodo.todoSeq
					,tdTodo.todoTitl
					,tdTodo.todoCont
					,tdTodo.completCondiVal
					,tdTodo.todoTypCd
					,tdTodo.todoCreCd
					,tdTodo.quizUseYn
					,tdTodo.quizTypCd
					
					,tdWork.workSeq
					,tdWork.workTitl
					,tdWork.workCont
					,tdWork.workDt
					,tdWork.workStatCd
					,tdWork.workPossBeginDt
					,tdWork.workPossEndDt
					,tdWork.confmDt
					,tdWork.gainPoint
					,tdWork.setleYn
				)
			)
			.from(tdTodo)
			.innerJoin(tdWork).on(tdTodo.todoSeq.eq(tdWork.todoSeq))
			.leftJoin(cmUser).on(tdWork.workerSeq.eq(cmUser.userSeq));
			
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(tdTodo.delYn.eq(Yn.N));
		builder.and(tdWork.delYn.eq(Yn.N));
		
		if(!ObjectUtils.isEmpty(searchTodo.getToDay())) {
			builder.and(tdWork.workPossBeginDt.loe(LocalDateTime.of(searchTodo.getToDay(), LocalTime.of(0, 0, 0))));  // =<
			builder.and(tdWork.workPossEndDt.goe(LocalDateTime.of(searchTodo.getToDay(), LocalTime.of(23, 59, 59)))); // >=	
		}
		
		if(!ObjectUtils.isEmpty(searchTodo.getWorkerSeq())) {
			builder.and(tdWork.workerSeq.eq(searchTodo.getWorkerSeq()));
		}

		if(!ObjectUtils.isEmpty(searchTodo.getTodoTitl())) {
			builder.and(tdTodo.todoTitl.contains(searchTodo.getTodoTitl()));			
		}
		
		if(!ObjectUtils.isEmpty(searchTodo.getTodoCont())) {
			builder.and(tdTodo.todoCont.contains(searchTodo.getTodoCont()));
		}
		
		if(!ObjectUtils.isEmpty(searchTodo.getTodoDesc())) {
			builder.and(tdTodo.todoDesc.contains(searchTodo.getTodoDesc()));			
		}

		if(!ObjectUtils.isEmpty(searchTodo.getWorkTitl())) {
			builder.and(tdWork.workTitl.contains(searchTodo.getWorkTitl()));
		}
		
		if(!ObjectUtils.isEmpty(searchTodo.getWorkCont())) {
			builder.and(tdWork.workCont.contains(searchTodo.getWorkCont()));
		}
		
		if(!ObjectUtils.isEmpty(searchTodo.getWorkStatCd())) {
			builder.and(tdWork.workStatCd.eq(searchTodo.getWorkStatCd()));
		}
		if(!ObjectUtils.isEmpty(searchTodo.getCheckerSeq())) {
			builder.and(tdWork.checkerSeq.eq(searchTodo.getCheckerSeq()));
		}
				
		query.where(builder);
		
		if(!ObjectUtils.isEmpty(searchTodo.getOrder()) && !ObjectUtils.isEmpty(searchTodo.getSort())) {
	    	Order direction = Order.valueOf(searchTodo.getOrder().toUpperCase());
	    	
	        if(searchTodo.getSort().equals("loginId")) {
		        query.orderBy(new OrderSpecifier<>(direction, tdTodo.todoTitl));
	        }else{
	        	query.orderBy(tdWork.workSeq.desc());
	        }
		}else {
        	query.orderBy(tdWork.workSeq.desc());
		}
		
		QueryResults<TodayPlanDto> queryResults = query.limit(page.getPageSize()).offset(page.getOffset()).fetchResults();

		return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
	}


	@Override
	public Page<WorkDetailInfoDto> pageWorkHistory(SearchWork searchWork, Pageable page) {
		QTdTodo tdTodo = QTdTodo.tdTodo;
		QTdWork tdWork = QTdWork.tdWork;
		
		JPQLQuery<WorkDetailInfoDto> query = jpaQueryFactory.select(
				Projections.bean(WorkDetailInfoDto.class,
					 tdTodo.todoSeq
					,tdTodo.todoTitl
					,tdTodo.todoCont
					,tdTodo.todoDesc
					,tdTodo.completCondiVal
					,tdTodo.todoPoint
					,tdTodo.todoTypCd
					,tdTodo.dateLimitCnt
					,tdTodo.todoCreCd
					,tdTodo.quizUseYn
					,tdTodo.quizTypCd
					
					,tdWork.workSeq
					,tdWork.workerSeq
					,tdWork.workTitl
					,tdWork.workCont
					,tdWork.workDt
					,tdWork.workStatCd
					,tdWork.confmDt
					,tdWork.gainPoint
					,tdWork.checkerSeq
					,tdWork.setleYn
				)
			)
			.from(tdTodo)
			.innerJoin(tdWork).on(tdTodo.todoSeq.eq(tdWork.todoSeq));

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(tdTodo.delYn.eq(Yn.N));
		builder.and(tdWork.delYn.eq(Yn.N));
		builder.and(tdWork.workerSeq.eq(searchWork.getWorkerSeq()));
		
		if(!ObjectUtils.isEmpty(searchWork.getTodoTitl())) {
			builder.and(tdTodo.todoTitl.contains(searchWork.getTodoTitl()));
		}
		
		if(!ObjectUtils.isEmpty(searchWork.getTodoTypCd())) {
			builder.and(tdTodo.todoTypCd.eq(searchWork.getTodoTypCd()));
		}
		
		if(!ObjectUtils.isEmpty(searchWork.getTodoCreCd())) {
			builder.and(tdTodo.todoCreCd.eq(searchWork.getTodoCreCd()));
		}
		
		if(!ObjectUtils.isEmpty(searchWork.getWorkTitl())) {
			builder.and(tdWork.workTitl.contains(searchWork.getWorkTitl()));
		}
		
		if(!ObjectUtils.isEmpty(searchWork.getWorkCont())) {
			builder.and(tdWork.workCont.contains(searchWork.getWorkCont()));
		}

		if(!ObjectUtils.isEmpty(searchWork.getWorkStatCd())) {
			builder.and(tdWork.workStatCd.eq(searchWork.getWorkStatCd()));
		}
		
		if(!ObjectUtils.isEmpty(searchWork.getWorkBeginDt())) {			
			if(ObjectUtils.isEmpty(searchWork.getWorkEndDt())) {
				searchWork.setWorkEndDt(searchWork.getWorkBeginDt());			}
			builder.and(tdWork.workDt.between(LocalDateTime.of(searchWork.getWorkBeginDt(), LocalTime.of(0, 0, 0))
					, LocalDateTime.of(searchWork.getWorkBeginDt(), LocalTime.of(23, 59, 59))));
		}
		
		query.where(builder);
		
		if(!ObjectUtils.isEmpty(searchWork.getOrder()) && !ObjectUtils.isEmpty(searchWork.getSort())) {
	    	Order direction = Order.valueOf(searchWork.getOrder().toUpperCase());
	    	
	        if(searchWork.getSort().equals("workTitl")) {
		        query.orderBy(new OrderSpecifier<>(direction, tdWork.workTitl));
	        }else if(searchWork.getSort().equals("todoTitl")) {
		        query.orderBy(new OrderSpecifier<>(direction, tdTodo.todoTitl));
	        }else{
	        	query.orderBy(tdWork.workSeq.desc());
	        }
		}else {
        	query.orderBy(tdWork.workSeq.desc());
		}
		
		QueryResults<WorkDetailInfoDto> queryResults = query.limit(page.getPageSize()).offset(page.getOffset()).fetchResults();

		return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
	}

}
