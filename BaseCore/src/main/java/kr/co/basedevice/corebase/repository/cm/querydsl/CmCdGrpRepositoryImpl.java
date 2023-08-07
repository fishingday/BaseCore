package kr.co.basedevice.corebase.repository.cm.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.basedevice.corebase.domain.cm.CmCdGrp;
import kr.co.basedevice.corebase.domain.cm.QCmCdDtl;
import kr.co.basedevice.corebase.domain.cm.QCmCdGrp;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.search.common.SearchCodeGrp;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CmCdGrpRepositoryImpl implements CmCdGrpRepositoryQuerydsl{

	private final JPAQueryFactory jpaQueryFactory;
	
	@Override
	public List<CmCdGrp> findBySearch(SearchCodeGrp searchCodeGrp) {
		QCmCdGrp cmCdGrp = QCmCdGrp.cmCdGrp;
		QCmCdDtl cmCdDtl = QCmCdDtl.cmCdDtl;
		
		JPQLQuery<CmCdGrp> query = jpaQueryFactory.selectFrom(cmCdGrp);
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(cmCdGrp.delYn.eq(Yn.N));
		
		if(!ObjectUtils.isEmpty(searchCodeGrp.getCdGrpNm())) {
			builder.and(cmCdGrp.grpCdNm.contains(searchCodeGrp.getCdGrpNm()));
		}
		if(!ObjectUtils.isEmpty(searchCodeGrp.getCdNm())) {
		    JPQLQuery <String> subQuery = 
		    		  JPAExpressions.select(Projections.bean(String.class, cmCdDtl.grpCd))
		    	      .from(cmCdDtl)
		    	      .where(cmCdDtl.delYn.eq(Yn.N), cmCdDtl.CdNm.contains(searchCodeGrp.getCdNm()));
		    	      
		  builder.and(cmCdGrp.grpCd.in(subQuery));
		}
		
		query.where(builder);
		
		// 오더링 대상이 하나 뿐이다.
		Order direction = null;
		if(!ObjectUtils.isEmpty(searchCodeGrp.getOrder())) {
	    	direction = Order.valueOf(searchCodeGrp.getOrder().toUpperCase());
		}else {
			direction = Order.ASC;
		}
		
		query.orderBy(new OrderSpecifier<>(direction, cmCdGrp.grpCd));
		
		return query.fetch();
	}

}
