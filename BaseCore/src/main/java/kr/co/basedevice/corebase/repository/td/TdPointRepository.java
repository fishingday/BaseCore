package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.td.TdPoint;

public interface TdPointRepository  extends JpaRepository<TdPoint, Long> {

	/**
	 * 사용한 포인트
	 * 
	 * @param userSeq
	 * @return
	 */
	@Query("select m.lastPoint from TdPoint m where m.pointSeq = (select MAX(n.pointSeq) from TdPoint n where n.delYn = 'N' and n.userSeq = ?1 ) ")
	int getUsePoint4Worker(Long userSeq);

}
