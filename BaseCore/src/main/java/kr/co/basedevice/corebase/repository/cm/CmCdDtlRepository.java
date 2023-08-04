package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.cm.CmCdDtl;

public interface CmCdDtlRepository extends JpaRepository<CmCdDtl, Long>{

	/**
	 * 코드 그룹별 코드 상세 조회
	 * 
	 * @param grpCd
	 * @return
	 */
	@Query("select m from CmCdDtl m inner join CmCdGrp n on (m.grpCd = n.grpCd) where m.delYn = 'N' and n.delYn = 'N' and n.grpCd = ?1  ORDER BY m.prntOrd ASC")
	List<CmCdDtl> findByGrpCd(String grpCd);

}
