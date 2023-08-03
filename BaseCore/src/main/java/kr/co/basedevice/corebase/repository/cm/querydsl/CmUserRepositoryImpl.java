package kr.co.basedevice.corebase.repository.cm.querydsl;

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

import kr.co.basedevice.corebase.domain.cm.QCmRole;
import kr.co.basedevice.corebase.domain.cm.QCmUser;
import kr.co.basedevice.corebase.domain.cm.QCmUserRoleMap;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.dto.common.UserInfoDto;
import kr.co.basedevice.corebase.search.common.SearchUserInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CmUserRepositoryImpl implements CmUserRepositoryQuerydsl{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<UserInfoDto> pageUserInfo(SearchUserInfo searchUserInfo, Pageable page) {
		QCmUser cmUser = QCmUser.cmUser;
		QCmUserRoleMap cmUserRoleMap = QCmUserRoleMap.cmUserRoleMap;
		QCmRole cmRole = QCmRole.cmRole;
				
		JPQLQuery<UserInfoDto> query = jpaQueryFactory.selectDistinct(
				Projections.bean(UserInfoDto.class,
					 cmUser.userSeq
					,cmUser.loginId
					,cmUser.userNm
					,cmUser.userTelNo
					,cmUser.loginFailCnt
					,cmUser.loginDt
					,cmUser.lastLoginIp
					,cmUser.userStatCd
					,cmUser.acuntExpDt
					,cmUser.delYn
				)
			)
			.from(cmUser)
			.leftJoin(cmUserRoleMap).on(cmUser.userSeq.eq(cmUserRoleMap.userSeq))
			.leftJoin(cmRole).on(cmUserRoleMap.roleSeq.eq(cmRole.roleSeq));
		
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(cmUser.delYn.eq(Yn.N));
		//builder.and(cmUserRoleMap.delYn.eq(Yn.N));
		//builder.and(cmRole.delYn.eq(Yn.N));
		
		if(!ObjectUtils.isEmpty(searchUserInfo.getLoginId())) {
			builder.and(cmUser.loginId.contains(searchUserInfo.getLoginId()));
		}
		
		if(!ObjectUtils.isEmpty(searchUserInfo.getUserNm())) {
			builder.and(cmUser.userNm.contains(searchUserInfo.getUserNm()));
		}

		if(!ObjectUtils.isEmpty(searchUserInfo.getUserTelNo())) {
			builder.and(cmUser.userTelNo.endsWith(searchUserInfo.getUserTelNo()));			
		}
		
		if(!ObjectUtils.isEmpty(searchUserInfo.getRoleCd())) {
			builder.and(cmRole.roleCd.eq(searchUserInfo.getRoleCd()));
		}
		
		query.where(builder);
		
		if(!ObjectUtils.isEmpty(searchUserInfo.getOrder()) && !ObjectUtils.isEmpty(searchUserInfo.getSort())) {
	    	Order direction = Order.valueOf(searchUserInfo.getOrder().toUpperCase());
	    	
	        if(searchUserInfo.getSort().equals("loginId")) {
		        query.orderBy(new OrderSpecifier<>(direction, cmUser.loginId));
	        }else if( searchUserInfo.getSort().equals("userNm")) {
		       	query.orderBy(new OrderSpecifier<>(direction, cmUser.userNm));
	        }else if( searchUserInfo.getSort().equals("userTelNo")) {
		       	query.orderBy(new OrderSpecifier<>(direction, cmUser.userTelNo));
        	}else if( searchUserInfo.getSort().equals("userStatCd")) {
		       	query.orderBy(new OrderSpecifier<>(direction, cmUser.userStatCd));
        	}else if( searchUserInfo.getSort().equals("acuntExpDt")) {
		       	query.orderBy(new OrderSpecifier<>(direction, cmUser.acuntExpDt));
        	}else if( searchUserInfo.getSort().equals("loginDt")) {
		        query.orderBy(new OrderSpecifier<>(direction, cmUser.loginDt));
        	}else if( searchUserInfo.getSort().equals("lastLoginIp")) {
		        query.orderBy(new OrderSpecifier<>(direction, cmUser.lastLoginIp));
        	}else if ( searchUserInfo.getSort().equals("loginFailCnt")) {
		        query.orderBy(new OrderSpecifier<>(direction, cmUser.loginFailCnt));
	        }else{
	        	query.orderBy(cmUser.userSeq.asc());
	        }
		}else {
        	query.orderBy(cmUser.userSeq.asc());
		}
		
		QueryResults<UserInfoDto> queryResults = query.limit(page.getPageSize()).offset(page.getOffset()).fetchResults();

		return new PageImpl<>(queryResults.getResults(), page, queryResults.getTotal());
	}

}
