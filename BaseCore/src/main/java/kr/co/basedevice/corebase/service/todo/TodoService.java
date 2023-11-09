package kr.co.basedevice.corebase.service.todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdCheckerMap;
import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.domain.td.TdWorkerMap;
import kr.co.basedevice.corebase.dto.todo.TodayPlanDto;
import kr.co.basedevice.corebase.dto.todo.PlanWorkInfoDto;
import kr.co.basedevice.corebase.dto.todo.TodoMgtDto;
import kr.co.basedevice.corebase.dto.todo.TodoUserDto;
import kr.co.basedevice.corebase.dto.todo.TodoWorkerInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkerAgreeTodoDto;
import kr.co.basedevice.corebase.repository.td.TdTodoRepository;
import kr.co.basedevice.corebase.repository.td.TdWorkRepository;
import kr.co.basedevice.corebase.repository.td.TdWorkerMapRepository;
import kr.co.basedevice.corebase.search.todo.SearchPlanWork;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import kr.co.basedevice.corebase.search.todo.SearchTodoMgt;
import kr.co.basedevice.corebase.search.todo.SearchTodoWorker;
import kr.co.basedevice.corebase.search.todo.SearchWork;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TodoService {
	
	final private TdTodoRepository tdTodoRepository;
	final private TdWorkRepository tdWorkRepository;
	final private TdWorkerMapRepository tdWorkerMapRepository;
	

	
	/**
	 * 등록된 할일 목록 조회
	 * 
	 * @param searchTodoMgt
	 * @param pageable
	 * @return
	 */
	public Page<TodoMgtDto> pageTodoMgtList(SearchTodoMgt searchTodoMgt, Pageable pageable){
		
		Page<TodoMgtDto> pageTodoDetailInfo =  tdTodoRepository.pageTodoMgtList(searchTodoMgt, pageable);
		
		if(pageTodoDetailInfo != null && !pageTodoDetailInfo.isEmpty()) {
			
			for(TodoMgtDto todoMgt : pageTodoDetailInfo.getContent()) {
				// 확인자 목록
				todoMgt.setCheckerList(tdTodoRepository.getCheckerList(todoMgt.getTodoSeq()));
				
				// 작업자 목록
				todoMgt.setWorkerList(tdTodoRepository.getWorkerList(todoMgt.getTodoSeq()));;
			}
		}
		
		return pageTodoDetailInfo;
	}
	
	
	/**
	 * 확인자 용 할일 목록
	 * 
	 * @param searchTodo
	 * @return
	 */
	public Page<TodayPlanDto> pageTodayPlan(SearchTodo searchTodo, Pageable pageable){
		Page<TodayPlanDto> pageTodayPlanDto =  tdWorkRepository.pageTodayPlan(searchTodo, pageable);
		
		return pageTodayPlanDto;
	}
	
	/**
	 *  할일 저장
	 * 
	 * @param todoDetailDto
	 * @return
	 */
	public boolean saveTodo(TodoMgtDto todoDetailDto) {
		
		TdTodo tdTodo = new TdTodo();
		BeanUtils.copyProperties(todoDetailDto, tdTodo, "checkerList", "workerList");
		
		List <TdCheckerMap> listTdCheckerMap = new ArrayList<>(1);
		if(todoDetailDto.getCheckerList() != null && !todoDetailDto.getCheckerList().isEmpty()) {
			for(TodoUserDto TodoUserDto : todoDetailDto.getCheckerList()){
				TdCheckerMap tdCheckerMap = new TdCheckerMap();
				tdCheckerMap.setCheckerSeq(TodoUserDto.getUserSeq());
				tdCheckerMap.setDelYn(Yn.N);
				listTdCheckerMap.add(tdCheckerMap);
			}
		}
		
		List<TdWorkerMap> listTdWorkerMap = new ArrayList<>(1);
		if(todoDetailDto.getWorkerList() != null && !todoDetailDto.getWorkerList().isEmpty()) {
			for(TodoUserDto TodoUserDto : todoDetailDto.getWorkerList()){
				TdWorkerMap tdWorkerMap = new TdWorkerMap();
				tdWorkerMap.setWorkerSeq(TodoUserDto.getUserSeq());
				tdWorkerMap.setWorkerAgreYn(Yn.N);
				tdWorkerMap.setDelYn(Yn.N);
				listTdWorkerMap.add(tdWorkerMap);
			}
		}
		
		tdTodo.setDelYn(Yn.N);
		tdTodo.setTdCheckerMapList(listTdCheckerMap);
		tdTodo.setTdWorkerMapList(listTdWorkerMap);
		
		tdTodoRepository.save(tdTodo);		
		
		return true;
	}

	/**
	 * 작업 조회
	 * 
	 * @param workSeq
	 * @return
	 */
	public TdWork getTdWork(Long workSeq) {
		Optional<TdWork> optTdWork = tdWorkRepository.findById(workSeq);
				
		return optTdWork.get();
	}

	/**
	 * 할일 조회
	 * 
	 * @param todoSeq
	 * @return
	 */
	public TdTodo getTdTodo(Long todoSeq) {
		Optional<TdTodo> optTdTodo = tdTodoRepository.findById(todoSeq);
		
		return optTdTodo.get();
	}

	public TodoMgtDto getTdTodoDetail(Long todoSeq) {

		Optional<TdTodo> optTdTodo = tdTodoRepository.findById(todoSeq);
		TodoMgtDto todoDetailDto = new TodoMgtDto();
		BeanUtils.copyProperties(optTdTodo.get(), todoDetailDto, "tdWorkList", "tdCheckerMapList", "tdWorkerMapList");
		
		// 확인자 목록
		todoDetailDto.setCheckerList(tdTodoRepository.getCheckerList(todoDetailDto.getTodoSeq()));		
		// 작업자 목록
		todoDetailDto.setWorkerList(tdTodoRepository.getWorkerList(todoDetailDto.getTodoSeq()));;
		
		return todoDetailDto;
	}

	/**
	 * 해당일 작업 목록
	 * 
	 * @param searchTodo
	 * @return
	 */
	public List<PlanWorkInfoDto> findByTodayPlanList4Worker(SearchWork searchWork) {
		
		List<PlanWorkInfoDto> listTodayWorkDto = tdWorkRepository.findByTodayPlanList4Worker(searchWork);
		
		return listTodayWorkDto;
	}


	/**
	 * 작업 내역 저장
	 * 
	 * @param tdWork
	 */
	public boolean saveTdWork(TdWork tdWork) {		
		tdWorkRepository.save(tdWork);
		
		return true;
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
// H2 database 
//		INSERT INTO TD_WORK (WORK_SEQ, TODO_SEQ, WORKER_SEQ, WORK_STAT_CD,  WORK_POSS_BEGIN_DT, WORK_POSS_END_DT , GAIN_POINT, WORK_TITL, SETLE_YN, DEL_YN, CRE_DT, CREATOR_SEQ, UPD_DT, UPDATOR_SEQ) 
//		SELECT NEXTVAL('SEQ_TD_WORK') as WORK_SEQ
//		      ,A.TODO_SEQ as TODO_SEQ
//		      ,B.WORKER_SEQ as WORKER_SEQ
//		      ,'READY' as TODO_STAT_CD
//            ,FORMATDATETIME(NOW(),   'yyyy-MM-dd') || ' 00:00:00'
//            ,FORMATDATETIME(NOW(),   'yyyy-MM-dd') || ' 23:59:59'
//		      ,A.TODO_POINT as GAIN_POINT
//		      ,A.TODO_TITL || '(' || FORMATDATETIME(NOW(),   'yyyy/MM/dd') ||')' as WORK_TITL
//		      ,'N' as SETLE_YN
//		      ,'N' as DEL_YN
//		      ,NOW() as CRE_DT
//		      ,0 as CREATOR_SEQ
//		      ,NOW() as UPD_DT
//		      , 0 as UPDATOR_SEQ
//		  FROM TD_TODO A, TD_WORKER_MAP B
//		 WHERE A.DEL_YN = 'N'
//		   AND A.TODO_CRE_CD IN ('DAILY', 'WEEK', 'MONTH')
//		   AND A.TODO_SEQ = B.TODO_SEQ
//		   AND B.DEL_YN = 'N'
//		   AND B.WORKER_AGRE_YN = 'Y'
//		   AND 1 =(CASE WHEN A.TODO_CRE_CD = 'DAILY' THEN 1
//		                WHEN A.TODO_CRE_CD = 'WEEK' THEN INSTR(A.TODO_CRE_DTL_VAL, DAY_OF_WEEK(NOW()))
//		                WHEN A.TODO_CRE_CD = 'MONTH' AND A.TODO_CRE_DTL_VAL = 'LAST' THEN 
//		                     CASE WHEN DAY_OF_MONTH(NOW()) = day_of_month(dateadd(DAY, -1, dateadd(MONTH,1,date_trunc('MONTH', now())))) THEN 1 ELSE 0 END
//		                WHEN A.TODO_CRE_CD = 'MONTH' AND A.TODO_CRE_DTL_VAL != 'LAST' THEN 
//		                     CASE WHEN DAY_OF_MONTH(NOW()) = A.TODO_CRE_DTL_VAL THEN 1 ELSE 0 END
//		                ELSE 0
//		           END)
		List<TdWorkerMap> listTdWorkerMap = tdWorkerMapRepository.findByWorkerAgre(todoSeq);

		int createSize = 0;
		if(listTdWorkerMap != null && !listTdWorkerMap.isEmpty()) {
			Optional<TdTodo> tdTodo = tdTodoRepository.findById(todoSeq);
			List<TdWork> listTdWork = new ArrayList<>();
			for(TdWorkerMap tdWorkerMap : listTdWorkerMap) {
				TdWork tdWork = new TdWork();
				tdWork.setTodoSeq(todoSeq);
				tdWork.setWorkerSeq(tdWorkerMap.getWorkerSeq());
				tdWork.setWorkStatCd(WorkStatCd.READY);
				
				LocalTime workPossBeginTime = null;
				if(ObjectUtils.isEmpty(tdTodo.get().getLimitBeginTm())){
					workPossBeginTime = LocalTime.of(0, 0, 0);
				}else {
					workPossBeginTime = tdTodo.get().getLimitBeginTm();
				}
				tdWork.setWorkPossBeginDt(LocalDateTime.of(createDate, workPossBeginTime));
				
				LocalTime workPossEndTime = null;
				if(ObjectUtils.isEmpty(tdTodo.get().getLimitEndTm())){
					workPossEndTime = LocalTime.of(23, 59, 59);
				}else {
					workPossEndTime = tdTodo.get().getLimitEndTm();
				}
				tdWork.setWorkPossEndDt(LocalDateTime.of(createDate, workPossEndTime));
								
				tdWork.setGainPoint(tdTodo.get().getTodoPoint());
				tdWork.setWorkTitl(new StringBuilder(tdTodo.get().getTodoTitl()).append("(")
						.append(createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
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


	/**
	 * 작업자 할일 목록 및 예상 적립 금액
	 * 
	 * @param searchTodoWorker
	 * @param page
	 * @return
	 */
	public Page<TodoWorkerInfoDto> pageTodoWorkerInfo(SearchTodoWorker searchTodoWorker, Pageable page) {		
		
		Page<TodoWorkerInfoDto> pageTodoWorkerInfo = tdTodoRepository.pageTodoWorkerInfo(searchTodoWorker, page);
		
		return pageTodoWorkerInfo;
	}

	/**
	 * 동의한 할일 목록
	 * 
	 * @param userSeq
	 * @return
	 */
	public List<TdTodo> findByAgreeTodo(Long userSeq) {
		List<TdTodo> listTdTodo = tdTodoRepository.findByAgreeTodo(userSeq);
				
		return listTdTodo;
	}

	/**
	 * 동의할지 않은 할일 목록
	 * 
	 * @param userSeq
	 * @return
	 */
	public List<TdTodo> findByUnAgreeTodo(Long userSeq) {
		List<TdTodo> listTdTodo = tdTodoRepository.findByUnAgreeTodo(userSeq);
		
		return listTdTodo;
	}

	/**
	 *  할일에 대한 동의/미동의 저장
	 * 
	 * @param workerAgreeTodo
	 * @return
	 */
	public boolean saveWorkerAgreeTodo(WorkerAgreeTodoDto workerAgreeTodo) {
		
		List<TdWorkerMap> listTdWorkerMap = tdWorkerMapRepository.findByWorkerSeqAndTodoSeqInAndDelYn(workerAgreeTodo.getWorkerSeq(), workerAgreeTodo.getListTodoSeq(), Yn.N);
		
		if(listTdWorkerMap != null && !listTdWorkerMap.isEmpty()) {
			for(TdWorkerMap tdWorkerMap : listTdWorkerMap) {
				tdWorkerMap.setWorkerAgreYn(workerAgreeTodo.getAgreeYn());
			}
			tdWorkerMapRepository.saveAll(listTdWorkerMap);
		}
		
		return true;
	}


	/**
	 * 계획 작업 정보
	 * 
	 * @param searchPlanWork
	 * @return
	 */
	public List<PlanWorkInfoDto> listPlanWorkInfo(SearchPlanWork searchPlanWork) {
		
		List<PlanWorkInfoDto> listTodayWorkInfoDto = tdWorkRepository.listPlanWorkInfo(searchPlanWork);
		
		return listTodayWorkInfoDto;
	}

}
