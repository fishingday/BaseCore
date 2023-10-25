package kr.co.basedevice.corebase.restController.todo.worker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.todo.WorkDetailInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchWork;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.WorkService;
import lombok.RequiredArgsConstructor;

/**
 * 작업 이력 조회
 * 
 * @author fishingday
 *
 */
@RestController
@RequestMapping("/todo/worker/work_history")
@RequiredArgsConstructor
public class WorkHistoryRestController {
	
	final private WorkService workService;

	// 작업 이력 조회
	@GetMapping("/page_work_history.json")
	public ResponseEntity<Page<WorkDetailInfoDto>> pageWorkHistory(SearchWork searchWork, Pageable page){
		if(page == null) {
			page = PageRequest.of(0, 10);
		}
		
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		searchWork.setWorkerSeq(cmUser.getUserSeq());
		
		Page<WorkDetailInfoDto> pageWorkInfo = workService.pageWorkHistory(searchWork, page);
		
		return ResponseEntity.ok(pageWorkInfo);
	}
	
	
	// 작업 상세 조회
	@GetMapping("/get_work_info.json")
	public ResponseEntity<WorkDetailInfoDto> getWorkInfo(Long workSeq){
				
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		WorkDetailInfoDto workInfoDto = workService.getWorkInfo(workSeq, cmUser.getUserSeq());
		
		return ResponseEntity.ok(workInfoDto);
	}
	
}
