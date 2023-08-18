package kr.co.basedevice.corebase.repository.cm.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmMenuDtl;
import kr.co.basedevice.corebase.domain.cm.QCmMenu;
import kr.co.basedevice.corebase.domain.cm.QCmMenuDtl;
import kr.co.basedevice.corebase.domain.cm.QCmMenuDtlRoleMap;
import kr.co.basedevice.corebase.domain.cm.QCmRole;
import kr.co.basedevice.corebase.domain.cm.QCmRoleMenuMap;
import kr.co.basedevice.corebase.domain.cm.QCmUser;
import kr.co.basedevice.corebase.domain.cm.QCmUserRoleMap;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.dto.system.MenuInfoDto;
import kr.co.basedevice.corebase.dto.system.ParentMenuDto;
import kr.co.basedevice.corebase.search.system.SearchMenu;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CmMenuRepositoryImpl implements CmMenuRepositoryQuerydsl{

	private final JPAQueryFactory jpaQueryFactory;
	
	@Override
	public List<CmMenu> findAllMenu() {
		QCmMenu cmMenu = QCmMenu.cmMenu;
		QCmRoleMenuMap cmRoleMenuMap = QCmRoleMenuMap.cmRoleMenuMap;
		QCmRole cmRole = QCmRole.cmRole;
		
		return jpaQueryFactory.selectFrom(cmMenu)
				.innerJoin(cmMenu.cmRoleMenuMapList, cmRoleMenuMap).fetchJoin()
				.innerJoin(cmRoleMenuMap.cmRole(), cmRole).fetchJoin()
		.where(
			cmMenu.delYn.eq(Yn.N),
			cmMenu.cmScrenYn.eq(Yn.N),
			cmMenu.menuPath.isNotEmpty(),
			cmRoleMenuMap.delYn.eq(Yn.N), 
			cmRole.delYn.eq(Yn.N))
		.fetch();
	}

	@Override
	public List<CmMenuDtl> findAllMenuDtl() {
		QCmMenu cmMenu = QCmMenu.cmMenu;
		QCmMenuDtl cmMenuDtl = QCmMenuDtl.cmMenuDtl;
		QCmMenuDtlRoleMap cmMenuDtlRoleMap = QCmMenuDtlRoleMap.cmMenuDtlRoleMap; 
		QCmRole cmRole = QCmRole.cmRole;
		
		return jpaQueryFactory.selectFrom(cmMenuDtl)
				.innerJoin(cmMenuDtl.cmMenu(), cmMenu).fetchJoin()
				.innerJoin(cmMenuDtl.cmMenuDtlRoleMapList, cmMenuDtlRoleMap).fetchJoin()
				.innerJoin(cmMenuDtlRoleMap.cmRole(), cmRole).fetchJoin()
		.where(
				cmMenu.delYn.eq(Yn.N),
				cmMenu.cmScrenYn.eq(Yn.N),
				cmMenu.menuPath.isNotEmpty(),
				cmMenuDtl.delYn.eq(Yn.N),
				cmMenuDtl.menuDtlPath.isNotEmpty(),
				cmMenuDtlRoleMap.delYn.eq(Yn.N), 
				cmRole.delYn.eq(Yn.N))
		.fetch();
	}

	@Override
	public List<CmMenu> findUserRolesMenu(Long userSeq, List<Long> roleSeqList) {

		QCmMenu cmMenu = QCmMenu.cmMenu;
		QCmRole cmRole = QCmRole.cmRole;
		QCmUser cmUser = QCmUser.cmUser;
		QCmUserRoleMap cmUserRoleMap = QCmUserRoleMap.cmUserRoleMap;
		QCmRoleMenuMap cmRoleMenuMap = QCmRoleMenuMap.cmRoleMenuMap;
		
		return jpaQueryFactory.selectFrom(cmMenu)
				.innerJoin(cmMenu.cmRoleMenuMapList, cmRoleMenuMap)
				.innerJoin(cmRoleMenuMap.cmRole(), cmRole)
				.innerJoin(cmRole.cmUserRoleMapList, cmUserRoleMap)
				.innerJoin(cmUserRoleMap.cmUser(), cmUser)
		.where(
			cmMenu.delYn.eq(Yn.N),
			cmMenu.cmScrenYn.eq(Yn.N),
			cmMenu.menuPath.isNotEmpty(),
			cmRoleMenuMap.delYn.eq(Yn.N), 
			cmRole.delYn.eq(Yn.N),
		    cmRole.roleSeq.in(roleSeqList),
		    cmUserRoleMap.delYn.eq(Yn.N),
		    cmUser.userSeq.eq(userSeq),
		    cmUser.delYn.eq(Yn.N)
		)
		.distinct()
		.orderBy(cmMenu.prntOrd.asc())
		.fetch();
	}

	@Override
	public List<CmMenu> findUserRoleMenu(Long userSeq, Long roleSeq) {
		QCmMenu cmMenu = QCmMenu.cmMenu;
		QCmRole cmRole = QCmRole.cmRole;
		QCmUser cmUser = QCmUser.cmUser;
		QCmUserRoleMap cmUserRoleMap = QCmUserRoleMap.cmUserRoleMap;
		QCmRoleMenuMap cmRoleMenuMap = QCmRoleMenuMap.cmRoleMenuMap;
		
		return jpaQueryFactory.selectFrom(cmMenu)
				.innerJoin(cmMenu.cmRoleMenuMapList, cmRoleMenuMap)
				.innerJoin(cmRoleMenuMap.cmRole(), cmRole)
				.innerJoin(cmRole.cmUserRoleMapList, cmUserRoleMap)
				.innerJoin(cmUserRoleMap.cmUser(), cmUser)
		.where(
			cmMenu.delYn.eq(Yn.N),
			cmMenu.cmScrenYn.eq(Yn.N),
			cmMenu.menuPath.isNotEmpty(),
			cmRoleMenuMap.delYn.eq(Yn.N), 
			cmRole.delYn.eq(Yn.N),
		    cmRole.roleSeq.eq(roleSeq),
		    cmUserRoleMap.delYn.eq(Yn.N),
		    cmUser.userSeq.eq(userSeq),
		    cmUser.delYn.eq(Yn.N)
		)
		.distinct()
		.orderBy(cmMenu.prntOrd.asc())
		.fetch();
	}

	@Override
	public List<MenuInfoDto> findBySearch(SearchMenu searchMenu) {
		QCmMenu cmMenu = QCmMenu.cmMenu;

		JPQLQuery<MenuInfoDto> query = jpaQueryFactory.select(
				Projections.bean(MenuInfoDto.class,
					 cmMenu.menuSeq
					,cmMenu.upMenuSeq
					,cmMenu.menuPath
					,cmMenu.menuNm
					,cmMenu.menuDesc
					,cmMenu.iConInfo
					,cmMenu.prntYn
					,cmMenu.cmScrenYn
					,cmMenu.prntOrd
				)
			)
			.from(cmMenu);		
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(cmMenu.delYn.eq(Yn.N));
						
		return query.where(builder).orderBy(cmMenu.prntOrd.asc()).fetch();
	}

	@Override
	public List<ParentMenuDto> findByParentMenuList() {
		QCmMenu cmMenu = QCmMenu.cmMenu;
		QCmMenu subMenu = QCmMenu.cmMenu;

		JPQLQuery<ParentMenuDto> query = jpaQueryFactory.select(
				Projections.bean(ParentMenuDto.class,
					 cmMenu.menuSeq
					,cmMenu.upMenuSeq
					,cmMenu.menuPath
					,cmMenu.menuNm
					,cmMenu.prntOrd
				)
			)
			.from(cmMenu);
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(cmMenu.delYn.eq(Yn.N));
		// upMenuSeq에 사용된 메뉴만 조회
		builder.and(cmMenu.menuSeq.in(JPAExpressions.select(Projections.bean(Long.class, subMenu.upMenuSeq))
	    	      .from(subMenu)
	    	      .where(subMenu.delYn.eq(Yn.N), subMenu.upMenuSeq.isNotNull())));
						
		return query.where(builder).orderBy(cmMenu.prntOrd.asc()).fetch();
	}
}
