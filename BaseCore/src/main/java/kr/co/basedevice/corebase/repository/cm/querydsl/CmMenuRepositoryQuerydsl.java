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
	List<CmMenu> findAllMainMenu();
	
	/**
	 * 역할이 할당된 모든 상세 메뉴 조회
	 * 
	 * @return
	 */
	List<CmMenuDtl> findAllDtlMenu();
}
