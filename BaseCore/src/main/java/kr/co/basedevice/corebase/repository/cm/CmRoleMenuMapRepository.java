package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMap;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMapId;
import kr.co.basedevice.corebase.domain.code.Yn;

public interface CmRoleMenuMapRepository extends JpaRepository<CmRoleMenuMap, CmRoleMenuMapId> {

	/**
	 * 역할 별 메뉴 맵핑 목록
	 * 
	 * @param roleSeq
	 * @param n
	 * @return
	 */
	List<CmRoleMenuMap> findByRoleSeqAndDelYn(Long roleSeq, Yn yn);

}
