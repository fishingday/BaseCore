package kr.co.basedevice.corebase.repository.cm.querydsl;

import java.util.List;

import kr.co.basedevice.corebase.domain.cm.CmCdGrp;
import kr.co.basedevice.corebase.search.common.SearchGrpCd;

public interface CmCdGrpRepositoryQuerydsl {
	
	/**
	 * 코드 그룹 조회
	 * 
	 * @param searchCodeGrp
	 * @return
	 */
	List<CmCdGrp> findBySearch(SearchGrpCd searchCodeGrp);
}
