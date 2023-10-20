package kr.co.basedevice.corebase.repository.td;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.repository.td.querydsl.TdWorkRepositoryQuerydsl;

public interface TdWorkRepository extends JpaRepository<TdWork, Long>, TdWorkRepositoryQuerydsl{

	@Modifying
	@Query("update TdWork a set a.workStatCd = 'FAIL', a.gainPoint= 0, a.updDt = now(), updatorSeq = 0 "
		 + "where a.delYn = 'N' and a.workStatCd = 'READY' and a.workDt is null and creDt < ?1")
	int updateFailWorks(LocalDateTime closeDateTime);

}
