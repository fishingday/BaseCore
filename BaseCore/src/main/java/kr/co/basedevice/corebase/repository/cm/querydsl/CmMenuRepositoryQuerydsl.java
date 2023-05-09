package kr.co.basedevice.corebase.repository.cm.querydsl;

import java.util.List;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmMenuDtl;

public interface CmMenuRepositoryQuerydsl {
	
	/**
	 * 역할이 할당된 모든 메뉴 조회
	 * 
	 * @return
	 */
	List<CmMenu> findAllMenu();
	
	/**
	 * 역할이 할당된 모든 상세 메뉴 조회
	 * 
	 * @return
	 */
	List<CmMenuDtl> findAllMenuDtl();
	
	/**
	 * 사용자 역할별 메뉴 목록
	 * 
	 * @param userSeq
	 * @param roleSeq
	 * @return
	 */
	List<CmMenu> findUserRoleMenu(Long userSeq, Long roleSeq);
}
