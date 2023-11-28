package kr.co.basedevice.corebase.restController.todo.checker;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkerSettleInfoDto;
import kr.co.basedevice.corebase.dto.todo.WorkerWorkDto;
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
	 * 화면이 나오면 가장 최근 순으로 정산 목록을 보여준다.
	 * 정산은 사용자별로 작업목록의 합으로 이루어 진다.	 
	 * 정산목록은 확장할 수 있으며, 확장된 목록에는 정산별 작업목록이 나온다. 
	 * 정산은 본인이 한 정산만 나온다.
	 * 완료된 정산은 수정할 수 없다. 
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
		searchSettle.setAcountSeq(cmUser.getUserSeq()); // 확인자라면..
		
		Page<SettleInfoDto> pageSettleInfo = settleService.pageSettleInfo(searchSettle, page);
		
		return ResponseEntity.ok(pageSettleInfo);
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
	
	/**
	 * 미정산 작업 목록
	 * 
	 * @param listWorkerSeq
	 * @return
	 */
	@GetMapping("/list_worker_work.json")
	public ResponseEntity<List<WorkerWorkDto>> listWorkerWork(List<Long> listWorkerSeq){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		List<WorkerWorkDto> listWorkerWorkDto = settleService.findByTdWork4UnSettle(listWorkerSeq, cmUser.getUserSeq());
		
		return ResponseEntity.ok(listWorkerWorkDto);
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
}
