package kr.co.basedevice.corebase.repository.cm.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
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
	public Page<UserInfoDto> pageUserInfo(SearchUserInfo searchUserInfo, Pageable pageable) {
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
			.innerJoin(cmUserRoleMap).on(cmUser.userSeq.eq(cmUserRoleMap.userSeq))
			.innerJoin(cmRole).on(cmUserRoleMap.roleSeq.eq(cmRole.roleSeq));
		
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(cmUser.delYn.eq(Yn.N));
		builder.and(cmUserRoleMap.delYn.eq(Yn.N));
		builder.and(cmRole.delYn.eq(Yn.N));
		
		if(!ObjectUtils.isEmpty(searchUserInfo.getLoginId())) {
			builder.and(cmUser.loginId.contains(searchUserInfo.getLoginId()));
		}
		
		if(!ObjectUtils.isEmpty(searchUserInfo.getUserNm())) {
			builder.and(cmUser.userNm.contains(searchUserInfo.getUserNm()));
		}

		if(!ObjectUtils.isEmpty(searchUserInfo.getUserTelNo())) {
			builder.and(cmUser.userTelNo.endsWith(searchUserInfo.getUserTelNo()));			
		}
		
		if(!ObjectUtils.isEmpty(searchUserInfo.getRoleSeq())) {
			builder.and(cmRole.roleSeq.eq(searchUserInfo.getRoleSeq()));
		}		
		
		query.where(builder).orderBy(cmUser.userSeq.desc());		
		QueryResults<UserInfoDto> queryResults = query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetchResults();

		return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
	}

}
