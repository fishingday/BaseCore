package kr.co.basedevice.corebase.repository.cm.querydsl;

import java.util.List;

import kr.co.basedevice.corebase.domain.cm.CmCdDtl;
import kr.co.basedevice.corebase.search.system.SearchDtlCd;

public interface CmCdDtlRepositoryQuerydsl {

	/**
	 * 코드 상세 조회
	 * 
	 * @param searchDtlCd
	 * @return
	 */
	List<CmCdDtl> findBySearch(SearchDtlCd searchDtlCd);
}
