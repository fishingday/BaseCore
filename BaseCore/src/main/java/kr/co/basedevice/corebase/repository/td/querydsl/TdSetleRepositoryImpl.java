package kr.co.basedevice.corebase.repository.td.querydsl;

import java.time.LocalDateTime;

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
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchSettle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TdSetleRepositoryImpl implements TdSetleRepositoryQueryDsl{
	
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<SettleInfoDto> pageSettleInfo(SearchSettle searchSettle, Pageable page) {
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
		
		if(!ObjectUtils.isEmpty(searchSettle.getSetleSeq())) {
			builder.and(todoSetle.setleSeq.eq(searchSettle.getSetleSeq()));
		}
		
		if(!ObjectUtils.isEmpty(searchSettle.getWorkerSeq())) {
			builder.and(todoSetle.workerSeq.eq(searchSettle.getWorkerSeq()));
		}
		
		if(!ObjectUtils.isEmpty(searchSettle.getAcountSeq())) {
			builder.and(todoSetle.acountSeq.eq(searchSettle.getAcountSeq()));
		}
		
		if(!ObjectUtils.isEmpty(searchSettle.getBeginSetleDt())) {
			if(ObjectUtils.isEmpty(searchSettle.getEndSetleDt())) {
				searchSettle.setEndSetleDt(LocalDateTime.now());
			}			
			builder.and(todoSetle.setleDt.between(searchSettle.getBeginSetleDt(), searchSettle.getEndSetleDt()));
		}
		
		if(!ObjectUtils.isEmpty(searchSettle.getWorkerNm())) {
			builder.and(worker.userNm.contains(searchSettle.getWorkerNm()));
		}
		
		query.where(builder);
		
		if(!ObjectUtils.isEmpty(searchSettle.getOrder()) && !ObjectUtils.isEmpty(searchSettle.getSort())) {
	    	Order direction = Order.valueOf(searchSettle.getOrder().toUpperCase());
	    	
	        if(searchSettle.getSort().equals("workerNm")) {
		        query.orderBy(new OrderSpecifier<>(direction, worker.userNm));
	        }else{
	        	query.orderBy(todoSetle.setleSeq.desc());
	        }
		}else {
        	query.orderBy(todoSetle.setleSeq.desc());
		}
		
		QueryResults<SettleInfoDto> queryResults = query.limit(page.getPageSize()).offset(page.getOffset()).fetchResults();

		return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
	}

	@Override
	public SettleInfoDto getSettleInfo(Long setleSeq) {
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
		builder.and(todoSetle.setleSeq.eq(setleSeq));
		
		query.where(builder);
		
		return query.fetchOne();
	}
}
