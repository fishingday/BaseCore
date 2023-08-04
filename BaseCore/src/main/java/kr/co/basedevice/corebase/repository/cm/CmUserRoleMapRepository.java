package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmUserRoleMap;
import kr.co.basedevice.corebase.domain.cm.CmUserRoleMapId;
import kr.co.basedevice.corebase.domain.code.Yn;

public interface CmUserRoleMapRepository extends JpaRepository<CmUserRoleMap, CmUserRoleMapId>{

	List<CmUserRoleMap> findByRoleSeqAndDelYn(Long roleSeq, Yn n);

	List<CmUserRoleMap> findByUserSeqAndDelYn(Long userSeq, Yn n);

}
