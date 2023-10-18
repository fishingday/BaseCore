package kr.co.basedevice.corebase.repository.td;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.repository.td.querydsl.TdWorkRepositoryQuerydsl;

public interface TdWorkRepository extends JpaRepository<TdWork, Long>, TdWorkRepositoryQuerydsl{

	@Modifying
	@Query("update TdWork a set a.todoStatCd = 'FAIL' where a.delYn = 'N' and a.todoStatCd = 'READY' and a.workCont is null and creDt < ?1")
	int updateFailWorks(LocalDateTime closeDateTime);

}
