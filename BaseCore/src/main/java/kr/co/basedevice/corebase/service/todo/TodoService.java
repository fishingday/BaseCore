package kr.co.basedevice.corebase.service.todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.domain.td.TdWorkerMap;
import kr.co.basedevice.corebase.dto.todo.TodayPlanDto;
import kr.co.basedevice.corebase.dto.todo.TodayWorkDto;
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
	
	final private JdbcTemplate JdbcTemplate;
	
	/**
	 * 등록된 할일 목록 조회
	 * 
	 * @param searchTodoMgt
	 * @param pageable
	 * @return
	 */
	public Page<TodoDetailDto> pageTodoDetailInfo(SearchTodoMgt searchTodoMgt, Pageable pageable){
		
		Page<TodoDetailDto> pageTodoDetailInfo =  tdTodoRepository.pageTodoDetailInfo(searchTodoMgt, pageable);
		
		if(pageTodoDetailInfo != null && !pageTodoDetailInfo.isEmpty()) {
			
			for(TodoDetailDto todoDetailDto : pageTodoDetailInfo.getContent()) {
				// 확인자 목록
				todoDetailDto.setCheckerList(tdTodoRepository.getCheckerList(todoDetailDto.getTodoSeq()));
				
				// 작업자 목록
				todoDetailDto.setWorkerList(tdTodoRepository.getWorkerList(todoDetailDto.getTodoSeq()));;
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

	public TodoDetailDto getTdTodoDetail(Long todoSeq) {
		// TODO 할일, 작업자, 확인자
		return null;
	}

	/**
	 * 해당일 작업 목록
	 * 
	 * @param searchTodo
	 * @return
	 */
	public List<TodayWorkDto> findByTodayPlanList4Worker(SearchTodo searchTodo) {
		if(ObjectUtils.isEmpty(searchTodo.getToDay())) {
			searchTodo.setToDay(LocalDate.now());
		}
		
		List<TodayWorkDto> listTodayWorkDto = null;
		
		return listTodayWorkDto;
	}

	/**
	 * 작업자별 포인트 현황 목록
	 * 
	 * @param searchTodo
	 * @return
	 */
	public List<TodoSummaryDto> findByPointSummary4Worker() {
		StringBuilder sb = new StringBuilder("SELECT A.WORKER_SEQ, A.USER_NM ")
				.append("      ,NVL(B.ACCUMULATE_POINT, 0) as ACCU_POINT ")
				.append("      ,NVL(C.USE_POINT, 0) as USE_POINT ")
				.append("      ,NVL(D.POSS_POINT, 0) as POSS_POINT ")
				.append("      ,NVL(B.ACCUMULATE_POINT, 0) - NVL(C.USE_POINT, 0) as AVAIL_POINT ")
				.append("  FROM ( SELECT X.WORKER_SEQ, S.USER_NM ")
				.append("           FROM TD_WORKER_MAP X INNER JOIN CM_USER S ON S.USER_SEQ = X.WORKER_SEQ ")
				.append("          WHERE X.DEL_YN = 'N' ")
				.append("          GROUP BY X.WORKER_SEQ) A ")
				.append("       LEFT OUTER JOIN ( ")
				.append("        SELECT Y.WORKER_SEQ, SUM(Y.TOTAL_SETLE_POINT) as ACCU_POINT ")
				.append("          FROM TD_TODO_SETLE Y ")
				.append("         WHERE Y.DEL_YN = 'N' ")
				.append("         GROUP BY Y.WORKER_SEQ ")
				.append("       ) B ON (A.WORKER_SEQ = B.WORKER_SEQ) ")
				.append("       LEFT OUTER JOIN ( ")
				.append("        SELECT Z.USER_SEQ, SUM(Z.USE_POINT) as USE_POINT ")
				.append("          FROM TD_POINT_USE Z ")
				.append("         WHERE Z.DEL_YN ='N' ")
				.append("         GROUP BY Z.USER_SEQ ")
				.append("       ) C ON (A.WORKER_SEQ = C.USER_SEQ) ")
				.append("       LEFT OUTER JOIN ( ")
				.append("        SELECT V.WORKER_SEQ, SUM(V.GAIN_POINT) as POSS_POINT ")
				.append("          FROM TD_WORK V ")
				.append("         WHERE V.DEL_YN = 'N' ")
				.append("           AND V.WORK_STAT_CD != 'FAIL' ")
				.append("           AND NOT EXISTS ( ")
				.append("           SELECT 'X' FROM TD_TODO_SETLE_DTL W WHERE W.DEL_YN ='N' AND V.WORK_SEQ = W.WORK_SEQ ")
				.append("          ) ")
				.append("         GROUP BY V.WORKER_SEQ ")
				.append("       ) D ON (A.WORKER_SEQ = D.WORKER_SEQ)");
		
		List<TodoSummaryDto> todoSummaryList = JdbcTemplate.query(
				sb.toString()
				,new RowMapper<TodoSummaryDto>() {
					@Override
					public TodoSummaryDto mapRow(ResultSet rs, int rowNum) throws SQLException{
						TodoSummaryDto todoSummaryDto = new TodoSummaryDto(
							 LocalDate.now()
							,rs.getLong("WORKER_SEQ")
							,rs.getString("USER_NM")
							,rs.getInt("POSS_POINT")
							,rs.getInt("AVAIL_POINT")
							,rs.getInt("USE_POINT")
							,rs.getInt("ACCU_POINT")					
						);						
						return todoSummaryDto;
					}
				}			
			);		
		
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
				if(ObjectUtils.isEmpty(tdTodo.get().getExecBeginTm())){
					workPossBeginTime = LocalTime.of(0, 0, 0);
				}else {
					workPossBeginTime = tdTodo.get().getExecBeginTm();
				}
				tdWork.setWorkPossBeginDt(LocalDateTime.of(createDate, workPossBeginTime));
				
				LocalTime workPossEndTime = null;
				if(ObjectUtils.isEmpty(tdTodo.get().getExecEndTm())){
					workPossEndTime = LocalTime.of(23, 59, 59);
				}else {
					workPossEndTime = tdTodo.get().getExecEndTm();
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
}
