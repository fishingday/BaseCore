package kr.co.basedevice.corebase.repository.td;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdTodo;

public interface TdTodoRepository extends JpaRepository<TdTodo, Long> {

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

}
