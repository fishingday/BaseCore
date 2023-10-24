package kr.co.basedevice.corebase.repository.td;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.repository.td.querydsl.TdWorkRepositoryQuerydsl;

public interface TdWorkRepository extends JpaRepository<TdWork, Long>, TdWorkRepositoryQuerydsl{

	/**
	 * 작업 실패 처리
	 * 
	 * @param closeDateTime
	 * @return
	 */
	@Modifying
	@Query("update TdWork a set a.workStatCd = 'FAIL', a.gainPoint= 0, a.updDt = now(), updatorSeq = 0 "
		 + "where a.delYn = 'N' and a.workStatCd = 'READY' and a.workDt is null and creDt < ?1")
	int updateFailWorks(LocalDateTime closeDateTime);

	/**
	 * 정산별 작업 목록
	 * 
	 * @param setleSeq
	 * @return
	 */
	@Query("select m from TdWork m inner join TdWorkSetleMap n on (m.workSeq = n.workSeq) where m.delYn = 'N' and n.delYn = 'N' and n.setleSeq = ?1 order by m.workSeq asc")
	List<TdWork> findByWork4SetleSeq(Long setleSeq);

	/**
	 * 정산되지 않은 작업 목록 조회
	 * 
	 * @param delYn 삭제 여부
	 * @param setleYn 정산 여부
	 * @param WorkerSeq (대상)작업자순번
	 * @param workSeqList 대상작업번호목록
	 * @return
	 */
	List<TdWork> findByDelYnAndSetleYnAndWorkerSeqAndWorkSeqInAndWorkStatCdIn(Yn delYn, Yn setleYn, Long workerSeq, List<Long> workSeqList, List<WorkStatCd> workStatCdList );

	/**
	 * 적립 가능 포인트
	 * 
	 * @param userSeq
	 * @return
	 */
	@Query("select SUM(m.gainPoint) from TdWork m where m.setleYn = 'N' and m.delYn = 'N' and m.workStatCd != 'FAIL' and m.workerSeq = ?1 ")
	int getPossPoint4Worker(Long userSeq);

}
