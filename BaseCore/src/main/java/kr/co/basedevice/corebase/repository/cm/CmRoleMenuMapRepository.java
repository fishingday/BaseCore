package kr.co.basedevice.corebase.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMap;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMapId;

public interface CmRoleMenuMapRepository extends JpaRepository<CmRoleMenuMap, CmRoleMenuMapId> {

}
