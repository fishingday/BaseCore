package kr.co.basedevice.corebase.restController.todo.worker;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.todo.PointSummaryDto;
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
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
		searchSettle.setWorkerSeq(cmUser.getUserSeq()); // 작업자라면...
		
		Page<SettleInfoDto> pageSettleInfo = settleService.pageSettleInfo(searchSettle, page);
		
		return ResponseEntity.ok(pageSettleInfo);
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
