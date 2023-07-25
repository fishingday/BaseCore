package kr.co.basedevice.corebase.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.querydsl.CmUserRepositoryQuerydsl;

public interface CmUserRepository extends JpaRepository<CmUser, Long>, CmUserRepositoryQuerydsl{

	/**
	 * 로그인 아이디로 사용자 검색
	 * 
	 * @param loginId
	 * @return
	 */
	CmUser findByLoginIdAndDelYn(String loginId, Yn yn);

}
