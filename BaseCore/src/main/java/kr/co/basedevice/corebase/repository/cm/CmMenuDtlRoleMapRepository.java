package kr.co.basedevice.corebase.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmMenuDtlRoleMap;
import kr.co.basedevice.corebase.domain.cm.CmMenuDtlRoleMapId;

public interface CmMenuDtlRoleMapRepository  extends JpaRepository<CmMenuDtlRoleMap, CmMenuDtlRoleMapId>{

}
