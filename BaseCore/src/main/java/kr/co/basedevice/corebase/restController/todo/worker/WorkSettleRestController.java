package kr.co.basedevice.corebase.restController.todo.worker;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.dto.todo.GetSettelDto;
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.dto.todo.PointSummaryDto;
import kr.co.basedevice.corebase.search.todo.SearchSettle;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.SettleService;
import lombok.RequiredArgsConstructor;

/**
 * 포인트 정산 조회
 * 
 * @author fishingday
 *
 */
@RestController
@RequestMapping("/worker/settle")
@RequiredArgsConstructor
public class WorkSettleRestController {
	
	final private SettleService settleService;

	/**
	 * 포인트 정산 목록 조회
	 * 
	 * @param searchSettle
	 * @param page
	 * @return
	 */
	@GetMapping("/page_settle_info.json")
	public ResponseEntity<Page<SettleInfoDto>> pageSettleInfo(SearchSettle searchSettle, Pageable page){
		if(page == null) {
			page = PageRequest.of(0, 10);
		}
		
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		searchSettle.setWorkerSeq(cmUser.getUserSeq());
		
		Page<SettleInfoDto> pageSettleInfo = settleService.pageSettleInfo(searchSettle, page);
		
		return ResponseEntity.ok(pageSettleInfo);
	}
	
	
	/**
	 * 정산 별 상세 조회
	 * 
	 * @param setleSeq
	 * @return
	 */
	@GetMapping("/get_settle_info.json")
	public ResponseEntity<Map<String,Object>> getSettleInfo(Long setleSeq){
		
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		GetSettelDto getSettelDto = new GetSettelDto();
		getSettelDto.setSetleSeq(setleSeq);
		getSettelDto.setWorkerSeq(cmUser.getUserSeq());

		SettleInfoDto settleInfoDto = settleService.getSettleInfo(getSettelDto);
		
		Map<String,Object> retMap = new HashMap<>();
		retMap.put("settleInfo", settleInfoDto);
		
		// 정산 상세 조회
		if(!ObjectUtils.isEmpty(settleInfoDto)) { // 작업이 있는 곳에 계획이 있음
			// 할일 조회
			List<TdWork> listTdWork = settleService.findByWork4SetleSeq(setleSeq);
			retMap.put("listTdWork", listTdWork);
		}
		
		return ResponseEntity.ok(retMap);
	}
	
	// 보유 포인트 현황
	@GetMapping("/get_have_point.json")
	public ResponseEntity<PointSummaryDto> getHavePoint(){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		LocalDate toDay = LocalDate.now();
		
		int possPoint = settleService.possPoint4Worker(cmUser.getUserSeq()); // 적립가능포인트
		int usePoint  = settleService.usePoint4Worker(cmUser.getUserSeq());  // 사용 포인트
		int accuPoint = settleService.accuPoint4Worker(cmUser.getUserSeq()); // 누적 포인트
		
		int availPoint =  accuPoint - usePoint; // 가용 포인트
		PointSummaryDto todoSummaryDto = new PointSummaryDto(
			toDay, cmUser.getUserSeq(), cmUser.getUserNm(),
			possPoint,
			availPoint,
			usePoint,
			accuPoint
		);
		return ResponseEntity.ok(todoSummaryDto);
	}
}
