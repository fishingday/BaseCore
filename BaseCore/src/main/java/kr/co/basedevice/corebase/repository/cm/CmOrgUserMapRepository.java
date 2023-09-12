package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmOrgUserMap;
import kr.co.basedevice.corebase.domain.cm.CmOrgUserMapId;
import kr.co.basedevice.corebase.domain.code.Yn;

public interface CmOrgUserMapRepository extends JpaRepository<CmOrgUserMap, CmOrgUserMapId>{

	long countByOrgSeqAndDelYn(Long orgSeq, Yn n);

	List<CmOrgUserMap> findByUserSeqAndDelYn(Long userSeq, Yn n);

}
