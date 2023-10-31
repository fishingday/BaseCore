package kr.co.basedevice.corebase.repository.td;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.repository.td.querydsl.TdTodoRepositoryQuerydsl;

public interface TdTodoRepository extends JpaRepository<TdTodo, Long>, TdTodoRepositoryQuerydsl {

	/**
	 * 일자 기반 할일 조회
	 * 
	 * @param delYn
	 * @param todoCreCdList
	 * @param postBeginDate
	 * @param postEndDate
	 * @return
	 */
	List<TdTodo> findByDelYnAndTodoCreCdInAndPostBeginDateLessThanEqualAndPostEndDateGreaterThanEqual(Yn delYn,
			List<TodoCreCd> todoCreCdList, LocalDate postBeginDate, LocalDate postEndDate);

	/**
	 * 할당된 할일
	 * 
	 * @param userSeq
	 * @return
	 */
	@Query("select m from TdTodo m inner join TdWorkerMap n on (m.todoSeq = n.todoSeq) where m.delYn = 'N' and n.delYn = 'N' and n.workerSeq = ?1  ORDER BY m.todoSeq ASC")
	List<TdTodo> findByAgreeTodo(Long userSeq);

	/**
	 * 미할당된 할일
	 * 
	 * @param userSeq
	 * @return
	 */
	@Query("select m from TdTodo m inner join TdWorkerMap n on (m.todoSeq = n.todoSeq) where m.delYn = 'N' and n.delYn = 'N' and n.workerSeq = ?1  ORDER BY m.todoSeq ASC")
	List<TdTodo> findByUnAgreeTodo(Long userSeq);

}
