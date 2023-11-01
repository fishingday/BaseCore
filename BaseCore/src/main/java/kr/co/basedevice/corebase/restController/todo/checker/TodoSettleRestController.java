package kr.co.basedevice.corebase.restController.todo.checker;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.td.TdWork;
import kr.co.basedevice.corebase.dto.todo.GetSettelDto;
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkerSettleInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchSettle;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.SettleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checker/settle")
@RequiredArgsConstructor
public class TodoSettleRestController {
	
	final private SettleService settleService;
	
	/**
	 * 정산 목록 with 사용자별 미정산 현황 
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
		searchSettle.setAcountSeq(cmUser.getUserSeq());
		
		Page<SettleInfoDto> pageSettleInfo = settleService.pageSettleInfo(searchSettle, page);
		
		return ResponseEntity.ok(pageSettleInfo);
	}
	
	
	/**
	 * 정산 상세 조회
	 * 
	 * @param setleSeq
	 * @return
	 */
	@GetMapping("/get_settle_info.json")
	public ResponseEntity<Map<String,Object>> getSettleInfo(Long setleSeq){
				  
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		GetSettelDto getSettelDto = new GetSettelDto();
		getSettelDto.setSetleSeq(setleSeq);
		getSettelDto.setAcountSeq(cmUser.getUserSeq());
		
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
	
	/**
	 * 정산 하기
	 * 
	 * @param settleInfoDto
	 * @return
	 */
	@PutMapping("/save_settle.json")
	public ResponseEntity<Boolean> saveSettle(SettleInfoDto settleInfoDto){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		settleInfoDto.setAcountSeq(cmUser.getUserSeq());
		boolean isSave = settleService.saveTdSetle(settleInfoDto);
		
		return ResponseEntity.ok(isSave);
	}
	
	/**
	 * 작업자별 정산 현황
	 * - 대시보드용
	 * 
	 * @param searchSettle
	 * @param page
	 * @return
	 */
	@GetMapping("/list_worker_settle_info.json")
	public ResponseEntity<List<WorkerSettleInfoDto>> listWorkerSettleInfo(){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		List<WorkerSettleInfoDto> listWorkerSettleInfoDto = settleService.listWorkerSettleInfo(cmUser.getUserSeq());
		
		return ResponseEntity.ok(listWorkerSettleInfoDto);
	}
}
