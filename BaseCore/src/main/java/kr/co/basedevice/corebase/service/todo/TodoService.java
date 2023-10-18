package kr.co.basedevice.corebase.service.todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.TodoStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.domain.td.TdWorkerMap;
import kr.co.basedevice.corebase.dto.todo.TodayPlanDto;
import kr.co.basedevice.corebase.dto.todo.TodayTodoDto;
import kr.co.basedevice.corebase.dto.todo.TodoDetailDto;
import kr.co.basedevice.corebase.dto.todo.TodoSummaryDto;
import kr.co.basedevice.corebase.repository.td.TdTodoRepository;
import kr.co.basedevice.corebase.repository.td.TdWorkRepository;
import kr.co.basedevice.corebase.repository.td.TdWorkerMapRepository;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import kr.co.basedevice.corebase.search.todo.SearchTodoMgt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class TodoService {
	
	final private TdTodoRepository tdTodoRepository;
	final private TdWorkRepository tdWorkRepository;
	final private TdWorkerMapRepository tdWorkerMapRepository;
	
	/**
	 * 확인자 용 할일 목록
	 * 
	 * @param searchTodo
	 * @return
	 */
	public List<TodayPlanDto> findByTodayPlanList4Checker(SearchTodo searchTodo) {
		// TODO 해당일에 생성된 확인자의 할일/작업 목록
		
		return null;
	}

	public TdWork getTdWork(Long workSeq) {
		// TODO 작업 조회
		return null;
	}

	public TdTodo getTdTodo(Long todoSeq) {
		// TODO 할일 조회 
		return null;
	}

	public List<TodoSummaryDto> findByPointSummary4Checker(SearchTodo searchTodo) {
		// TODO 확인자의 할일을 완룐한 수행자의 해당일 획득 포인트와 지급예정포인트 
		log.info("############################ 한번은 똮!");
		return null;
	}

	public List<TodoDetailDto> findByTodoList(SearchTodoMgt searchTodoMgt) {
		
		// TODO 확인자가 생성한 할일 목록 조회
		return null;
	}

	public TodoDetailDto getTdTodoDetail(Long todoSeq) {
		// TODO 할일, 작업자, 확인자
		return null;
	}

	public List<TodayTodoDto> findByTodayPlanList4Worker(SearchTodo searchTodo) {
		
		
		
		return null;
	}

	/**
	 * 한방에 하려면 
	 * 
	 * @param searchTodo
	 * @return
	 */
	public List<TodoSummaryDto> findByPointSummary4Worker(SearchTodo searchTodo) {
		List<TodoSummaryDto> todoSummaryList = tdWorkRepository.findByUserSummary(searchTodo);
		
		return todoSummaryList;
	}

	/**
	 * 작업 내역 저장
	 * 
	 * @param tdWork
	 */
	public void saveTdWork(TdWork tdWork) {		
		tdWorkRepository.save(tdWork);
	}

	/**
	 * 생성이 필요한 할일 조회 
	 * 
	 * @param createDate
	 * @return
	 */
	public List<TdTodo> findByTargetTodoItem(LocalDate createDate) {
		
		List<TdTodo> tdTodoList = tdTodoRepository
				.findByDelYnAndTodoCreCdInAndPostBeginDateLessThanEqualAndPostEndDateGreaterThanEqual(
						Yn.N, 
						Arrays.asList(TodoCreCd.DAILY, TodoCreCd.WEEK, TodoCreCd.MONTH),
						createDate, 
						createDate);
		
		return tdTodoList;
	}

	/**
	 * 할일에 대한 작업을 생성
	 * 
	 * @param createDate
	 * @param todoSeq
	 * @return
	 */
	public int createWorkItems(LocalDate createDate, Long todoSeq) {
		List<TdWorkerMap> listTdWorkerMap = tdWorkerMapRepository.findByWorkerAgre(todoSeq);

		int createSize = 0;
		if(listTdWorkerMap != null && !listTdWorkerMap.isEmpty()) {
			Optional<TdTodo> tdTodo = tdTodoRepository.findById(todoSeq);
			List<TdWork> listTdWork = new ArrayList<>();
			for(TdWorkerMap tdWorkerMap : listTdWorkerMap) {
				TdWork tdWork = new TdWork();
				tdWork.setTodoSeq(todoSeq);
				tdWork.setWorkerSeq(tdWorkerMap.getWorkerSeq());
				tdWork.setTodoStatCd(TodoStatCd.READY);
				tdWork.setGainPoint(tdTodo.get().getTodoPoint());
				tdWork.setWorkTitl(new StringBuilder(tdTodo.get().getTodoTitl()).append("(")
						.append(createDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
						.append(")").toString());
				tdWork.setSetleYn(Yn.N);
				tdWork.setDelYn(Yn.N);
				listTdWork.add(tdWork);
			}
			tdWorkRepository.saveAll(listTdWork);
			createSize = listTdWork.size();
		}		
		
		return createSize;
	}

	/**
	 * 아무것도 하지 않은 작업에 대하여 
	 * 실패 처리를 한다.
	 * 
	 * @param closeDateTime
	 * @return
	 */
	public int closeWorkItems(LocalDateTime closeDateTime) {
		return tdWorkRepository.updateFailWorks(closeDateTime);
	}

}
