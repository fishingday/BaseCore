package kr.co.basedevice.corebase.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmUserRoleMap;
import kr.co.basedevice.corebase.domain.cm.CmUserRoleMapId;

public interface CmUserRoleMapRepository extends JpaRepository<CmUserRoleMap, CmUserRoleMapId>{

}
