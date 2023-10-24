package kr.co.basedevice.corebase.service.todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.domain.td.TdSetle;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.domain.td.TdWorkSetleMap;
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
	public SettleInfoDto getSettleInfo(Long setleSeq) {
		SettleInfoDto settleInfoDto = setleRepository.getSettleInfo(setleSeq);
		return settleInfoDto;
	}

	
	public List<TdWork> findByWork4SetleSeq(Long setleSeq) {
		List<TdWork> listTdWork = workRepository.findByWork4SetleSeq(setleSeq);
		return listTdWork;
	}

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

}
