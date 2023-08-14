package kr.co.basedevice.corebase.repository.cm.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.basedevice.corebase.domain.cm.CmCdDtl;
import kr.co.basedevice.corebase.domain.cm.QCmCdDtl;
import kr.co.basedevice.corebase.domain.cm.QCmCdGrp;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.search.system.SearchDtlCd;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Repository
public class CmCdDtlRepositoryImpl implements CmCdDtlRepositoryQuerydsl{

	private final JPAQueryFactory jpaQueryFactory;
	
	@Override
	public List<CmCdDtl> findBySearch(SearchDtlCd searchDtlCd) {
		QCmCdGrp cmCdGrp = QCmCdGrp.cmCdGrp;
		QCmCdDtl cmCdDtl = QCmCdDtl.cmCdDtl;
		
		JPQLQuery<CmCdDtl> query = jpaQueryFactory.selectFrom(cmCdDtl)
				.innerJoin(cmCdGrp).on(cmCdDtl.grpCd.eq(cmCdGrp.grpCd));
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(cmCdDtl.delYn.eq(Yn.N));
		

		if(!ObjectUtils.isEmpty(searchDtlCd.getGrpCdNm())) {
			builder.and(cmCdGrp.grpCdNm.contains(searchDtlCd.getGrpCdNm()));
		}
		
		if(!ObjectUtils.isEmpty(searchDtlCd.getGrpCd())) {
			builder.and(cmCdDtl.grpCd.eq(searchDtlCd.getGrpCd()));
		}		
		

		if(!ObjectUtils.isEmpty(searchDtlCd.getCd())) {
			builder.and(cmCdDtl.cd.eq(searchDtlCd.getCd()));
		}
		
		if(!ObjectUtils.isEmpty(searchDtlCd.getCdNm())) {
			builder.and(cmCdDtl.cdNm.contains(searchDtlCd.getCdNm()));
		}
		

		if(!ObjectUtils.isEmpty(searchDtlCd.getCdDesc())) {
			builder.and(cmCdDtl.cdDesc.contains(searchDtlCd.getCdDesc()));
		}
		
		if(!ObjectUtils.isEmpty(searchDtlCd.getOpt1())) {
			builder.and(cmCdDtl.opt1.contains(searchDtlCd.getOpt1()));
		}

		if(!ObjectUtils.isEmpty(searchDtlCd.getOpt2())) {
			builder.and(cmCdDtl.opt2.contains(searchDtlCd.getOpt2()));
		}
		
		if(!ObjectUtils.isEmpty(searchDtlCd.getOpt3())) {
			builder.and(cmCdDtl.opt3.contains(searchDtlCd.getOpt3()));
		}

		if(!ObjectUtils.isEmpty(searchDtlCd.getOpt4())) {
			builder.and(cmCdDtl.opt4.contains(searchDtlCd.getOpt4()));
		}
		
		if(!ObjectUtils.isEmpty(searchDtlCd.getOpt5())) {
			builder.and(cmCdDtl.opt5.contains(searchDtlCd.getOpt5()));
		}
		
		query.where(builder);
		query.orderBy(cmCdDtl.prntOrd.asc());
		return query.fetch();
	}

}
