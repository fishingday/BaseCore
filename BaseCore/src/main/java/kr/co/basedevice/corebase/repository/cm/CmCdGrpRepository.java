package kr.co.basedevice.corebase.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmCdGrp;
import kr.co.basedevice.corebase.repository.cm.querydsl.CmCdGrpRepositoryQuerydsl;

public interface CmCdGrpRepository extends JpaRepository<CmCdGrp, String>, CmCdGrpRepositoryQuerydsl{

}
