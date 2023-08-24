package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

	/**
	 * 역할별 사용자 목록
	 * 
	 * @param roleSeq
	 * @return
	 */
	@Query("select m from CmUser m inner join CmUserRoleMap n on (m.userSeq = n.userSeq) where m.delYn = 'N' and n.delYn = 'N' and n.roleSeq = ?1 order by m.userSeq asc")
	List<CmUser> findByRoleSeq(Long roleSeq);

	/**
	 * 특정 역할 제외 사용자 목록
	 * 
	 * @param roleSeq
	 * @return
	 */
	@Query("select m from CmUser m inner join CmUserRoleMap n on (m.userSeq = n.userSeq) where m.delYn = 'N' and n.delYn = 'N' and n.roleSeq != ?1 order by m.userSeq asc")
	List<CmUser> findByExcludeRoleSeq(Long roleSeq);

}
