package kr.co.basedevice.corebase.repository.td;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdSetle;
import kr.co.basedevice.corebase.repository.td.querydsl.TdSetleRepositoryQueryDsl;

public interface TdSetleRepository extends JpaRepository<TdSetle, Long>, TdSetleRepositoryQueryDsl{

	/**
	 * 누적 포인트
	 * 
	 * @param userSeq
	 * @return
	 */
	@Query("select SUM(m.totalSetlePoint) from TdSetle m where m.delYn = 'N' and m.workerSeq = ?1 ")
	int accuPoint4Worker(Long userSeq);

	/**
	 * 마지막 정산을 정보를 가져 온다.
	 * 
	 * @param workerSeq
	 * @param acountSeq
	 * @param delYn
	 * @return
	 */
	List<TdSetle> findByWorkerSeqAndAcountSeqAndDelYnOrderByCreDtDesc(Long workerSeq, Long acountSeq, Yn n);

}
