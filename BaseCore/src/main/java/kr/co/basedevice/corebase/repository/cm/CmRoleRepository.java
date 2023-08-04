package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.code.RoleCd;
import kr.co.basedevice.corebase.domain.code.Yn;

public interface CmRoleRepository extends JpaRepository<CmRole, Long>{

	/**
	 * 사용자별 역할 목록
	 * 
	 * @param userSeq
	 * @return
	 */
	@Query("select m from CmRole m inner join CmUserRoleMap n on (m.roleSeq = n.roleSeq) where m.delYn = 'N' and n.delYn = 'N' and n.userSeq = ?1  ORDER BY n.prntOrd ASC")
	List<CmRole> findByUserSeq(Long userSeq);

	/**
	 * 역할 목록
	 * 
	 * @param n
	 * @return
	 */
	List<CmRole> findByDelYn(Yn delYn);

	/**
	 * 역할 코드로 역할 조회
	 * 
	 * @param valueOf
	 * @param n
	 * @return
	 */
	List<CmRole> findByRoleCdAndDelYn(RoleCd roleCd, Yn delYn);

}
