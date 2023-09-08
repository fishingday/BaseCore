package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmUserAlowIp;
import kr.co.basedevice.corebase.domain.code.Yn;

public interface CmUserAlowIpRepository extends JpaRepository<CmUserAlowIp, Long> {

	List<CmUserAlowIp> findByUserSeqAndDelYn(Long userSeq, Yn delYn);

}
