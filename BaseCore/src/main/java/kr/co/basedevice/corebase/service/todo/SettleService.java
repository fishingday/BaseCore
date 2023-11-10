package kr.co.basedevice.corebase.service.todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.dto.todo.GetSettelDto;
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkerSettleInfoDto;
import kr.co.basedevice.corebase.repository.td.TdPointUseRepository;
import kr.co.basedevice.corebase.repository.td.TdSetleRepository;
import kr.co.basedevice.corebase.repository.td.TdWorkRepository;
import kr.co.basedevice.corebase.search.todo.SearchSettle;
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
		
		if(!pageSettleInfo.isEmpty() && !pageSettleInfo.getContent().isEmpty()) {
			for(SettleInfoDto settleInfo : pageSettleInfo.getContent()) {
				List<TdWork> listTdWork = workRepository.findByWork4SetleSeq(settleInfo.getSetleSeq());
				settleInfo.setListTdWork(listTdWork);
			}
		}
		
		
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
//		TdSetle tdSetle = new TdSetle();
//		BeanUtils.copyProperties(settleInfoDto, tdSetle);
//		
//		List<WorkStatCd> listWorkStatCd = Arrays.asList(WorkStatCd.DONE, WorkStatCd.FAIL);
//		
//		List<TdWork> listTdWork = workRepository.findByDelYnAndSetleYnAndWorkerSeqAndWorkSeqInAndWorkStatCdIn(Yn.N, Yn.N, 
//				settleInfoDto.getWorkerSeq(), settleInfoDto.getWorkSeqList(), listWorkStatCd);
//
//		if(settleInfoDto.getWorkSeqList() != null && settleInfoDto.getWorkSeqList().size() > 0 && 
//				listTdWork.size() != settleInfoDto.getWorkSeqList().size()) {
//			throw new IllegalArgumentException("정산 대상의 상태가 올바르지 않습니다.");
//		}
//		
//		List<TdWorkSetleMap> listTdWorkSetleMap = new ArrayList<>(listTdWork.size());
//		int totalSetlePoint = 0;
//		for(TdWork tdWork : listTdWork) {
//			totalSetlePoint += tdWork.getGainPoint();
//			tdWork.setSetleYn(Yn.Y); // 정산 한거다~ 표시
//			
//			TdWorkSetleMap tdWorkSetleMap = new TdWorkSetleMap();
//			tdWorkSetleMap.setWorkSeq(tdWork.getWorkSeq());
//			tdWorkSetleMap.setDelYn(Yn.N);
//			listTdWorkSetleMap.add(tdWorkSetleMap);
//		}
//		
//		tdSetle.setAcountSeq(settleInfoDto.getAcountSeq());
//		tdSetle.setSetleDt(LocalDateTime.now());
//		tdSetle.setTotalSetlePoint(totalSetlePoint);
//		tdSetle.setTdWorkSetleMapList(listTdWorkSetleMap);
//		tdSetle.setDelYn(Yn.N);
//		
//		workRepository.saveAll(listTdWork);
//		setleRepository.save(tdSetle);
//				
		return true;
	}
	
	

	/**
	 * 작업자별 포인트 현황 목록
	 * 
	 * @param searchTodo
	 * @return
	 */
	public List<WorkerSettleInfoDto> getWorkerSettleInfo(Long workerSeq) {
		
		StringBuilder sb = new StringBuilder("SELECT Z.USER_SEQ, Z.USER_NM, X.SETTLE_POINTS, V.USE_POINTS, Y.UNSETTLE_POINTS ")
				.append("  FROM CM_USER Z LEFT JOIN ")
				.append("      (SELECT A.WORKER_SEQ, SUM(A.TOTAL_SETLE_POINT) as SETTLE_POINTS ")
				.append("          FROM TD_SETLE A ")
				.append("         WHERE A.DEL_YN = 'N' ")
				.append("           AND A.WORKER_SEQ = ? ")
				.append("         GROUP BY A.WORKER_SEQ ")
				.append("       ) X ON (Z.USER_SEQ = X.WORKER_SEQ) LEFT JOIN ")
				.append("       (SELECT A.WORKER_SEQ, SUM(A.GAIN_POINT) as UNSETTLE_POINTS ")
				.append("          FROM TD_WORK A  ")
				.append("         WHERE A.DEL_YN = 'N' ")
				.append("           AND A.WORK_STAT_CD = 'DONE' ")
				.append("           AND A.WORKER_SEQ = ? ")
				.append("           AND A.SETLE_YN = 'N' ")
				.append("         GROUP BY A.WORKER_SEQ ")
				.append("       ) Y ON (Z.USER_SEQ = Y.WORKER_SEQ) LEFT JOIN")
				.append("       (SELECT A.USER_SEQ, SUM(A.USE_POINT) AS USE_POINTS ")
				.append("          FROM TD_POINT_USE  A  ")
				.append("         WHERE A.DEL_YN = 'N' ")
				.append("           AND A.USER_SEQ  = ? ")
				.append("         GROUP BY A.USER_SEQ ")
				.append("       ) V ON (Z.USER_SEQ = V.USER_SEQ)")				
				.append(" WHERE Z.DEL_YN = 'N' ")
		        .append("   AND Z.USER_SEQ = ? ");
		
		List<WorkerSettleInfoDto> listWorkerSettleInfoDto =  JdbcTemplate.query(
				sb.toString()
				,new RowMapper<WorkerSettleInfoDto>() {
					@Override
					public WorkerSettleInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException{
						WorkerSettleInfoDto workerSettleInfoDto 
						= new WorkerSettleInfoDto(
							 rs.getLong("USER_SEQ")
							,rs.getString("USER_NM")
							,rs.getLong("SETTLE_POINTS")
							,rs.getLong("USE_POINTS")
							,rs.getLong("UNSETTLE_POINTS")
							,LocalDateTime.now()
						);						
						return workerSettleInfoDto;
					}
				}
				,workerSeq, workerSeq, workerSeq, workerSeq
			);

		return listWorkerSettleInfoDto;
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

	/**
	 * 작업자별 정산 현황
	 * - 대시보드용
	 * 
	 * @param userSeq
	 * @return
	 */
	public List<WorkerSettleInfoDto> listWorkerSettleInfo(Long checkerSeq) {
		StringBuilder sb = new StringBuilder("SELECT Z.USER_SEQ, Z.USER_NM, X.SETTLE_POINTS, Y.UNSETTLE_POINTS ")
				.append("  FROM CM_USER Z LEFT JOIN ")
				.append("      (SELECT A.WORKER_SEQ, SUM(A.TOTAL_SETLE_POINT) as SETTLE_POINTS ")
				.append("          FROM TD_SETLE A ")
				.append("         WHERE A.DEL_YN = 'N' ")
				.append("           AND A.ACOUNT_SEQ = ? ")
				.append("         GROUP BY A.WORKER_SEQ ")
				.append("       ) X ON (Z.USER_SEQ = X.WORKER_SEQ) LEFT JOIN ")
				.append("       (SELECT A.WORKER_SEQ, SUM(A.GAIN_POINT) as UNSETTLE_POINTS ")
				.append("          FROM TD_WORK A  ")
				.append("         WHERE A.DEL_YN = 'N' ")
				.append("           AND A.WORK_STAT_CD = 'DONE' ")
				.append("           AND A.SETLE_YN = 'N' ")
				.append("           AND A.CHECKER_SEQ = ? ")
				.append("         GROUP BY A.WORKER_SEQ ")
				.append("       ) Y ON (Z.USER_SEQ = Y.WORKER_SEQ)")
				.append(" WHERE Z.DEL_YN = 'N' ")
		        .append("   AND Z.USER_SEQ IN (SELECT K.TARGETER_SEQ FROM CM_USER_RELAT K WHERE K.DEL_YN = 'N' AND K.TARGETER_AGRE_YN = 'Y' AND K.RELATOR_SEQ = ? ) ");
				
		List<WorkerSettleInfoDto> listWorkerSettleInfoDto =  JdbcTemplate.query(
				sb.toString()
				,new RowMapper<WorkerSettleInfoDto>() {
					@Override
					public WorkerSettleInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException{
						WorkerSettleInfoDto workerSettleInfoDto 
						= new WorkerSettleInfoDto(
						     rs.getLong("USER_SEQ")
							,rs.getString("USER_NM")
							,rs.getLong("SETTLE_POINTS")
							,0L
							,rs.getLong("UNSETTLE_POINTS")
							,LocalDateTime.now()
						);						
						return workerSettleInfoDto;
					}
				}
				,checkerSeq ,checkerSeq ,checkerSeq 
			);
		return listWorkerSettleInfoDto;
	}
}
