package kr.co.basedevice.corebase.repository.td.querydsl;

import java.time.LocalDate;
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
import kr.co.basedevice.corebase.domain.td.QTdSetle;
import kr.co.basedevice.corebase.dto.todo.GetSettelDto;
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchSettle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TdSetleRepositoryImpl implements TdSetleRepositoryQueryDsl{
	
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<SettleInfoDto> pageSettleInfo(SearchSettle searchSettle, Pageable page) {
		QTdSetle tdSetle = QTdSetle.tdSetle;
		QCmUser worker = QCmUser.cmUser;
		QCmUser acounter = QCmUser.cmUser;
		
		JPQLQuery<SettleInfoDto> query = jpaQueryFactory.select(
				Projections.bean(SettleInfoDto.class,
					 tdSetle.setleSeq
					,tdSetle.workerSeq
					,tdSetle.acountSeq
					,tdSetle.totalSetlePoint
					,tdSetle.setleDesc
					,tdSetle.setleDt
				    ,worker.userNm.as("workerNm")
				    ,acounter.userNm.as("acountNm")
				)
			)
			.from(tdSetle)
			.innerJoin(worker).on(tdSetle.workerSeq.eq(worker.userSeq))
			.innerJoin(acounter).on(tdSetle.acountSeq.eq(acounter.userSeq));

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(tdSetle.delYn.eq(Yn.N));
		
		if(!ObjectUtils.isEmpty(searchSettle.getAcountSeq())) { // 확인자라면...
			builder.and(tdSetle.acountSeq.eq(searchSettle.getAcountSeq()));
		}
		
		if(!ObjectUtils.isEmpty(searchSettle.getWorkerSeq())) { // 작업자라면...
			builder.and(tdSetle.workerSeq.eq(searchSettle.getWorkerSeq()));
		}
		
		if(!ObjectUtils.isEmpty(searchSettle.getSettleBeginDate())) {
			if(ObjectUtils.isEmpty(searchSettle.getSettleEndDate())) {
				searchSettle.setSettleEndDate(LocalDate.now());
			}			
			builder.and(tdSetle.setleDt.between(
					LocalDateTime.of(searchSettle.getSettleBeginDate(), LocalTime.of(0, 0, 0)), 
					LocalDateTime.of(searchSettle.getSettleEndDate().plusDays(1), LocalTime.of(0, 0, 0).minusSeconds(1))));
		}
		
		if(!ObjectUtils.isEmpty(searchSettle.getWorkerNm())) {
			builder.and(worker.userNm.contains(searchSettle.getWorkerNm()));
		}
		
		if(!ObjectUtils.isEmpty(searchSettle.getSetleDesc())) {
			builder.and(tdSetle.setleDesc.contains(searchSettle.getSetleDesc()));
		}
		
		query.where(builder);
		
		if(!ObjectUtils.isEmpty(searchSettle.getOrder()) && !ObjectUtils.isEmpty(searchSettle.getSort())) {
	    	Order direction = Order.valueOf(searchSettle.getOrder().toUpperCase());
	    	
	        if(searchSettle.getSort().equals("workerNm")) {
		        query.orderBy(new OrderSpecifier<>(direction, worker.userNm));
	        }else if(searchSettle.getSort().equals("setleDt")) {
		        query.orderBy(new OrderSpecifier<>(direction, tdSetle.setleDt));
	        }else if(searchSettle.getSort().equals("totalSetlePoint")) {
		        query.orderBy(new OrderSpecifier<>(direction, tdSetle.totalSetlePoint));
	        }else if(searchSettle.getSort().equals("setleSeq")) {
		        query.orderBy(new OrderSpecifier<>(direction, tdSetle.setleSeq));
	        }else{
	        	query.orderBy(tdSetle.setleSeq.desc());
	        }
		}else {
        	query.orderBy(tdSetle.setleSeq.desc());
		}
		
		QueryResults<SettleInfoDto> queryResults = query.limit(page.getPageSize()).offset(page.getOffset()).fetchResults();

		return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
	}

	@Override
	public SettleInfoDto getSettleInfo(GetSettelDto getSettelDto) {
		QTdSetle todoSetle = QTdSetle.tdSetle;
		QCmUser worker = QCmUser.cmUser;
		QCmUser acounter = QCmUser.cmUser;
		
		JPQLQuery<SettleInfoDto> query = jpaQueryFactory.select(
				Projections.bean(SettleInfoDto.class,
					 todoSetle.setleSeq				
					,todoSetle.workerSeq
					,todoSetle.acountSeq
					,todoSetle.totalSetlePoint
					,todoSetle.setleDesc
					,todoSetle.setleDt
				    ,worker.userNm.as("workerNm")
				    ,worker.loginId
				    ,acounter.userNm.as("acountNm")
				)
			)
			.from(todoSetle)
			.innerJoin(worker).on(todoSetle.workerSeq.eq(worker.userSeq))
			.innerJoin(acounter).on(todoSetle.acountSeq.eq(acounter.userSeq));

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(todoSetle.delYn.eq(Yn.N));
		builder.and(todoSetle.setleSeq.eq(getSettelDto.getSetleSeq()));
		
		if(!ObjectUtils.isEmpty(getSettelDto.getWorkerSeq())) {
			builder.and(todoSetle.workerSeq.eq(getSettelDto.getWorkerSeq()));
		}
		if(!ObjectUtils.isEmpty(getSettelDto.getAcountSeq())) {
			builder.and(todoSetle.acountSeq.eq(getSettelDto.getAcountSeq()));
		}
		
		query.where(builder);
		
		return query.fetchOne();
	}
}
