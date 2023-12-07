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
import org.springframework.util.ObjectUtils;

import kr.co.basedevice.corebase.domain.code.PointAplytoCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdPoint;
import kr.co.basedevice.corebase.domain.td.TdSetle;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.dto.todo.SettleDataDto;
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkerSettleDto;
import kr.co.basedevice.corebase.dto.todo.WorkerSettleInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkerWorkDto;
import kr.co.basedevice.corebase.exception.OperationException;
import kr.co.basedevice.corebase.repository.td.TdPointRepository;
import kr.co.basedevice.corebase.repository.td.TdSetleRepository;
import kr.co.basedevice.corebase.repository.td.TdWorkRepository;
import kr.co.basedevice.corebase.search.todo.SearchSettle;
import kr.co.basedevice.corebase.search.todo.SearchWorker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SettleService {
	private final TdSetleRepository tdSetleRepository;
	private final TdWorkRepository tdWorkRepository;
	private final TdPointRepository tdPointRepository;
	
	final private JdbcTemplate jdbcTemplate;
		
	/**
	 * 정산 정보 페이지 조회
	 * 
	 * @param searchSettle
	 * @param page
	 * @return
	 */
	public Page<SettleInfoDto> pageSettleInfo(SearchSettle searchSettle, Pageable page) {
	
		Page<SettleInfoDto> pageSettleInfo = tdSetleRepository.pageSettleInfo(searchSettle, page);
		
		if(!pageSettleInfo.isEmpty() && !pageSettleInfo.getContent().isEmpty()) {
			
			int num = 1 + (pageSettleInfo.getNumber() * pageSettleInfo.getSize());
			for(int i = 0; i < pageSettleInfo.getContent().size(); i++) {
				SettleInfoDto settleInfo = pageSettleInfo.getContent().get(i);
				settleInfo.setNum(num + i);
				
				List<TdWork> listTdWork = tdWorkRepository.findByWork4SetleSeq(settleInfo.getSetleSeq());
				settleInfo.setListTdWork(listTdWork);
			}
		}
		
		
		return pageSettleInfo;
	}

	/**
	 * 정산별 작업 목록
	 * 
	 * @param setleSeq
	 * @return
	 */
	public List<TdWork> findByWork4SetleSeq(Long setleSeq) {
		List<TdWork> listTdWork = tdWorkRepository.findByWork4SetleSeq(setleSeq);
		return listTdWork;
	}

	/**
	 * 정산 저장
	 * 
	 * @param workerSettle
	 * @return
	 */
	public boolean saveTdSetle(WorkerSettleDto workerSettle) {
		
		for(SettleDataDto settleData :workerSettle.getListSettleData()) {
			
			// 작업자와 계산원의 마지막 정산 정보를 찾아온다.
			List<TdSetle> listSetle = tdSetleRepository.findByWorkerSeqAndAcountSeqAndDelYnOrderByCreDtDesc(
					settleData.getWorkerSeq(),
					workerSettle.getAcountSeq(),
					Yn.N
				);
			
			TdSetle lastSetle = null;
			
			if(listSetle != null && !listSetle.isEmpty()) {
				lastSetle = listSetle.get(0);
				lastSetle.setDelYn(Yn.Y); // 이건 삭제
			}else {
				lastSetle = new TdSetle();
				lastSetle.setAccumultPoint(0); // 누적된 것이 없으니.. 0
			}
			
			// 정산 만들기
			TdSetle tdSetle = new TdSetle();
			tdSetle.setWorkerSeq(settleData.getWorkerSeq());
			tdSetle.setAcountSeq(workerSettle.getAcountSeq());
			tdSetle.setSetleDesc(settleData.getSumPoint() + "포인트 정산");
			tdSetle.setAccumultPoint(lastSetle.getAccumultPoint() + settleData.getSumPoint());
			tdSetle.setTotalSetlePoint(settleData.getSumPoint());
			tdSetle.setSetleDt(LocalDateTime.now());
			tdSetle.setDelYn(Yn.N);
			
			
			// 작업에 정산 번호 넣기
			TdPoint lastTdPoint = null;
			List<TdPoint> listTdPoint = tdPointRepository.findByUserSeqAndDelYnOrderByCreDtDesc(settleData.getWorkerSeq(), Yn.N);
			if(listTdPoint != null && !listTdPoint.isEmpty()) {
				lastTdPoint = listTdPoint.get(0);
				lastTdPoint.setDelYn(Yn.Y); // 이건 삭제
			}else{
				lastTdPoint = new TdPoint();
				lastTdPoint.setLastPoint(0);
			}
			
			// 작업자 점수 데이터 생성
			TdPoint tdPoint = new TdPoint();
			tdPoint.setUserSeq(settleData.getWorkerSeq());
			tdPoint.setPointAplytoCd(PointAplytoCd.ADD);
			tdPoint.setLastPoint(lastTdPoint.getLastPoint() + settleData.getSumPoint());
			tdPoint.setAplytoPoint(settleData.getSumPoint());
			tdPoint.setDelYn(Yn.N);
			
			
			// 일단, 정산을 저장하자.
			if(!ObjectUtils.isEmpty(lastSetle.getAcountSeq())) {
				tdSetleRepository.save(lastSetle);
			}
			TdSetle aplySetle = tdSetleRepository.save(tdSetle);
			
			List<TdWork> listTdWork = tdWorkRepository.findByWorkSeqIn(settleData.getListWorkSeq());
			int sumPoint = 0;
			
			if(listTdWork != null && !listTdWork.isEmpty()) {
				for(TdWork tdWork : listTdWork) {
					tdWork.setSetleSeq(aplySetle.getSetleSeq());
					tdWork.setSetleYn(Yn.Y);
					sumPoint += tdWork.getGainPoint();
				}
			}
			
			// 검증 .... 
			if(sumPoint != settleData.getSumPoint().intValue()){
				throw new OperationException("정산 금액이 잘못 되었습니다.");
			}
			tdWorkRepository.saveAll(listTdWork);
			
			if(!ObjectUtils.isEmpty(lastTdPoint.getPointSeq())) {
				tdPointRepository.save(lastTdPoint);
			}
			tdPoint.setSetleSeq(aplySetle.getSetleSeq());
			tdPointRepository.save(tdPoint);			
		}
			
		return true;
	}
	
	

	/**
	 * 작업자별 포인트 현황 목록
	 * 
	 * @param searchTodo
	 * @return
	 */
	public List<WorkerSettleInfoDto> getWorkerSettleInfo(Long workerSeq) {
		
		StringBuilder sb = new StringBuilder("SELECT Z.USER_SEQ, Z.USER_NM, ")
				.append("  (SELECT SUM(A.TOTAL_SETLE_POINT) FROM TD_SETLE A WHERE A.DEL_YN = 'N' AND A.WORKER_SEQ = Z.USER_SEQ GROUP BY A.WORKER_SEQ ) AS SETTLE_POINTS, ")
				.append("  (SELECT A.LAST_POINT FROM TD_POINT A WHERE A.POINT_SEQ = (SELECT MAX(K.POINT_SEQ) FROM TD_POINT K WHERE K.DEL_YN = 'N' AND K.USER_SEQ = Z.USER_SEQ) ) AS LAST_POINTS, ")
				.append("  (SELECT SUM(A.GAIN_POINT) FROM TD_WORK A WHERE A.DEL_YN = 'N' AND A.WORK_STAT_CD = 'DONE' AND A.WORKER_SEQ = Z.USER_SEQ AND A.SETLE_YN = 'N' GROUP BY A.WORKER_SEQ ) AS UNSETTLE_POINTS ")
				.append("  FROM CM_USER Z ")	
				.append(" WHERE Z.DEL_YN = 'N' ")
		        .append("   AND Z.USER_SEQ = ? ");
		
		List<WorkerSettleInfoDto> listWorkerSettleInfoDto =  jdbcTemplate.query(
				sb.toString()
				,new RowMapper<WorkerSettleInfoDto>() {
					@Override
					public WorkerSettleInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException{
						WorkerSettleInfoDto workerSettleInfoDto 
						= new WorkerSettleInfoDto(
							 rs.getLong("USER_SEQ")
							,rs.getString("USER_NM")
							,rs.getLong("SETTLE_POINTS")
							,rs.getLong("LAST_POINTS")
							,rs.getLong("UNSETTLE_POINTS")
							,LocalDateTime.now()
						);						
						return workerSettleInfoDto;
					}
				}
				,workerSeq
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
		int possPoint = tdWorkRepository.getPossPoint4Worker(userSeq);
		return possPoint;
	}

	/**
	 * 사용 포인트
	 * 
	 * @param userSeq
	 * @return
	 */
	public int usePoint4Worker(Long userSeq) {
		int usePoint = tdPointRepository.getUsePoint4Worker(userSeq);
		return usePoint;
	}

	/**
	 * 누적 포인트
	 * 
	 * @param userSeq
	 * @return
	 */
	public int accuPoint4Worker(Long userSeq) {
		int accuPoint = tdSetleRepository.accuPoint4Worker(userSeq);
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
				.append("      (SELECT A.WORKER_SEQ, ACCUMULT_POINT AS SETTLE_POINTS ")
				.append("		  FROM TD_SETLE A ")
				.append("		 WHERE A.SETLE_SEQ IN ( ")
				.append("		       SELECT MAX(A.SETLE_SEQ) ")
				.append("		         FROM TD_SETLE A ")
  				.append("		        WHERE A.DEL_YN = 'N' ")
   				.append("		          AND A.ACOUNT_SEQ = ? ")
   				.append("		        GROUP BY A.WORKER_SEQ ")
				.append("		      ) ")				
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
				
		List<WorkerSettleInfoDto> listWorkerSettleInfoDto =  jdbcTemplate.query(
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

	/**
	 * 미정산 작업 목록
	 * 
	 * @param listWorkerSeq
	 * @param userSeq
	 * @return
	 */
	public List<WorkerWorkDto> findByTdWork4UnSettle(SearchWorker searchWorker) {
		
		List<WorkerWorkDto> listWorkerWorkDto = tdWorkRepository.findByWork4UnSettle(searchWorker);
		
		return listWorkerWorkDto;
	}
}
