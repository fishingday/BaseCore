package kr.co.basedevice.corebase.repository.cm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.domain.code.Yn;

public interface CmOrgRepository extends JpaRepository<CmOrg, Long> {

	@Query("select m from CmOrg m inner join CmOrgUserMap n on (m.orgSeq = n.orgSeq) where m.delYn = 'N' and n.delYn = 'N' and n.userSeq = ?1 order by m.orgSeq asc")
	List<CmOrg> findByUserSeq(Long userSeq);

	List<CmOrg> findByDelYn(Yn delYn);

	@Query("select m from CmOrg m where m.delYn = 'N' and m.orgSeq in (select n.upOrgSeq from CmOrg n where n.delYn = 'N')")
	List<CmOrg> findByParentOrg();

	List<CmOrg> findByUpOrgSeqIsNullAndDelYn(Yn delYn);

	List<CmOrg> findByUpOrgSeqAndDelYn(Long orgSeq, Yn delYn);

}
