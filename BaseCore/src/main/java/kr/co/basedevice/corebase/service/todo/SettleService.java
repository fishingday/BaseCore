package kr.co.basedevice.corebase.service.todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.dto.todo.GetSettelDto;
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.dto.todo.TodoSummaryDto;
import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdSetle;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.domain.td.TdWorkSetleMap;
import kr.co.basedevice.corebase.repository.td.TdPointUseRepository;
import kr.co.basedevice.corebase.repository.td.TdSetleRepository;
import kr.co.basedevice.corebase.repository.td.TdWorkRepository;
import kr.co.basedevice.corebase.search.todo.SearchSettle;
import kr.co.basedevice.corebase.search.todo.SearchTodo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SettleService {
	private final TdSetleRepository setleRepository;
	private final TdWorkRepository workRepository;
	private final TdPointUseRepository pointUseRepository;
	
	final private JdbcTemplate JdbcTemplate;
		
	/**
	 * 정산 정보 페이지 조회
	 * 
	 * @param searchSettle
	 * @param page
	 * @return
	 */
	public Page<SettleInfoDto> pageSettleInfo(SearchSettle searchSettle, Pageable page) {
	
		Page<SettleInfoDto> pageSettleInfo = setleRepository.pageSettleInfo(searchSettle, page);
		
		return pageSettleInfo;
	}

	/**
	 * 정산 정보 조회
	 * 
	 * @param setleSeq
	 * @return
	 */
	public SettleInfoDto getSettleInfo(GetSettelDto getSettelDto) {
		SettleInfoDto settleInfoDto = setleRepository.getSettleInfo(getSettelDto);
		return settleInfoDto;
	}

	/**
	 * 정산별 작업 목록
	 * 
	 * @param setleSeq
	 * @return
	 */
	public List<TdWork> findByWork4SetleSeq(Long setleSeq) {
		List<TdWork> listTdWork = workRepository.findByWork4SetleSeq(setleSeq);
		return listTdWork;
	}

	/**
	 * 정산 저장
	 * 
	 * @param settleInfoDto
	 * @return
	 */
	public boolean saveTdSetle(SettleInfoDto settleInfoDto) {
		TdSetle tdSetle = new TdSetle();
		BeanUtils.copyProperties(settleInfoDto, tdSetle);
		
		List<WorkStatCd> listWorkStatCd = Arrays.asList(WorkStatCd.DONE, WorkStatCd.FAIL);
		
		List<TdWork> listTdWork = workRepository.findByDelYnAndSetleYnAndWorkerSeqAndWorkSeqInAndWorkStatCdIn(Yn.N, Yn.N, 
				settleInfoDto.getWorkerSeq(), settleInfoDto.getWorkSeqList(), listWorkStatCd);

		if(settleInfoDto.getWorkSeqList() != null && settleInfoDto.getWorkSeqList().size() > 0 && 
				listTdWork.size() != settleInfoDto.getWorkSeqList().size()) {
			throw new IllegalArgumentException("정산 대상의 상태가 올바르지 않습니다.");
		}
		
		List<TdWorkSetleMap> listTdWorkSetleMap = new ArrayList<>(listTdWork.size());
		int totalSetlePoint = 0;
		for(TdWork tdWork : listTdWork) {
			totalSetlePoint += tdWork.getGainPoint();
			tdWork.setSetleYn(Yn.Y); // 정산 한거다~ 표시
			
			TdWorkSetleMap tdWorkSetleMap = new TdWorkSetleMap();
			tdWorkSetleMap.setWorkSeq(tdWork.getWorkSeq());
			tdWorkSetleMap.setDelYn(Yn.N);
			listTdWorkSetleMap.add(tdWorkSetleMap);
		}
		
		tdSetle.setAcountSeq(settleInfoDto.getAcountSeq());
		tdSetle.setSetleDt(LocalDateTime.now());
		tdSetle.setTotalSetlePoint(totalSetlePoint);
		tdSetle.setTdWorkSetleMapList(listTdWorkSetleMap);
		tdSetle.setDelYn(Yn.N);
		
		workRepository.saveAll(listTdWork);
		setleRepository.save(tdSetle);
				
		return true;
	}
	
	

	/**
	 * 작업자별 포인트 현황 목록
	 * 
	 * @param searchTodo
	 * @return
	 */
	public List<TodoSummaryDto> findByPointSummary4Worker(SearchTodo searchTodo) {
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
				.append("          FROM TD_SETLE Y ")
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
				.append("           SELECT 'X' FROM TD_WORK_SETLE_MAP W WHERE W.DEL_YN ='N' AND V.WORK_SEQ = W.WORK_SEQ ")
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
	 * 적립 가능 포인트
	 * 
	 * @param userSeq
	 * @return
	 */
	public int possPoint4Worker(Long userSeq) {
		int possPoint = workRepository.getPossPoint4Worker(userSeq);
		return possPoint;
	}

	/**
	 * 사용 포인트
	 * 
	 * @param userSeq
	 * @return
	 */
	public int usePoint4Worker(Long userSeq) {
		int usePoint = pointUseRepository.getUsePoint4Worker(userSeq);
		return usePoint;
	}

	/**
	 * 누적 포인트
	 * 
	 * @param userSeq
	 * @return
	 */
	public int accuPoint4Worker(Long userSeq) {
		int accuPoint = setleRepository.accuPoint4Worker(userSeq);
		return accuPoint;
	}
}
