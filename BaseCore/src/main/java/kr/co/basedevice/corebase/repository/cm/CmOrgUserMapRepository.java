package kr.co.basedevice.corebase.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmOrgUserMap;
import kr.co.basedevice.corebase.domain.cm.CmOrgUserMapId;

public interface CmOrgUserMapRepository extends JpaRepository<CmOrgUserMap, CmOrgUserMapId>{

}
