package kr.co.basedevice.corebase.repository.td;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdWorkerMap;
import kr.co.basedevice.corebase.domain.td.TdWorkerMapId;

public interface TdWorkerMapRepository extends JpaRepository<TdWorkerMap, TdWorkerMapId> {

	/**
	 * 작업 할당 동의자 목록
	 * 
	 * @param todoSeq
	 * @param y
	 * @param n
	 * @return
	 */
	@Query("select m from TdWorkerMap m inner join CmUser n on (m.workerSeq = n.userSeq) where m.delYn = 'N' and n.delYn = 'N' and m.workerAgreYn = 'Y' and m.todoSeq = ?1 ")
	List<TdWorkerMap> findByWorkerAgre(Long todoSeq);

	/**
	 * 할당된 할일 목록
	 * 
	 * @param workerSeq
	 * @param listTodoSeq
	 * @param n
	 * @return
	 */
	List<TdWorkerMap> findByWorkerSeqAndTodoSeqInAndDelYn(Long workerSeq, List<Long> listTodoSeq, Yn agreeYn);

}
