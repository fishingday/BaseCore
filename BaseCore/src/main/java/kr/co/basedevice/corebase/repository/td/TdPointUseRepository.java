package kr.co.basedevice.corebase.repository.td;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.td.TdPointUse;

public interface TdPointUseRepository  extends JpaRepository<TdPointUse, Long> {

	/**
	 * 사용한 포인트
	 * 
	 * @param userSeq
	 * @return
	 */
	@Query("select SUM(m.usePoint) from TdPointUse m where m.delYn = 'N' and m.userSeq = ?1 ")
	int getUsePoint4Worker(Long userSeq);

}
