package kr.co.basedevice.corebase.repository.cm.querydsl;

import java.util.List;

import kr.co.basedevice.corebase.domain.cm.CmMenu;

public interface CmMenuRepositoryQuerydsl {
	
	List<CmMenu> findAllMainMenu();
}
