package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.cm.CmOrg;

public interface CmOrgRepository extends JpaRepository<CmOrg, Long> {

	@Query("select m from CmOrg m inner join CmOrgUserMap n on (m.orgSeq = n.orgSeq) where m.delYn = 'N' and n.delYn = 'N' and n.userSeq = ?1 order by m.orgSeq asc")
	List<CmOrg> findByUserSeq(Long userSeq);

}
