package kr.co.basedevice.corebase.repository.cm.querydsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.QCmMenu;
import kr.co.basedevice.corebase.domain.cm.QCmRole;
import kr.co.basedevice.corebase.domain.cm.QCmRoleMenuMap;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CmMenuRepositoryImpl implements CmMenuRepositoryQuerydsl{

	private final JPAQueryFactory jpaQueryFactory;
	
	@Override
	public List<CmMenu> findAllMainMenu() {
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
		.orderBy(cmMenu.prntOrd.asc())
		.fetch();
	}

}
