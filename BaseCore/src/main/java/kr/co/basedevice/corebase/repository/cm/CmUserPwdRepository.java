package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmUserPwd;
import kr.co.basedevice.corebase.domain.code.Yn;

public interface CmUserPwdRepository extends JpaRepository<CmUserPwd, Long>{

	/**
	 * 최근 순의 패스워드 목록
	 * 
	 * @param userSeq
	 * @param yn
	 * @return
	 */
	List<CmUserPwd> findByUserSeqAndDelYnOrderByUserPwdSeqDesc(Long userSeq, Yn yn);

	/**
	 * 살아있는 패스워드가 여러개면... 오류
	 * 
	 * @param userSeq
	 * @param n
	 * @return
	 */
	CmUserPwd findOneUserSeqAndDelYn(Long userSeq, Yn n);


}
