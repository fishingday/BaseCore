package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.cm.CmRole;

public interface CmRoleRepository extends JpaRepository<CmRole, Long>{

	@Query("select m from CmRole m inner join CmUserRoleMap n on (m.roleSeq = n.roleSeq) where m.delYn = 'N' and n.delYn = 'N' and n.userSeq = ?1  ORDER BY n.prntOrd ASC")
	List<CmRole> findByUserSeq(Long userSeq);

}
