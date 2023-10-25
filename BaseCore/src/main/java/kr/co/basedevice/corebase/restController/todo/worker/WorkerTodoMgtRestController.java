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
import kr.co.basedevice.corebase.dto.todo.SettleInfoDto;
import kr.co.basedevice.corebase.search.todo.SearchSettle;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

/**
 * 할일 할당 및 할일 목록 조회
 * 
 * @author fishingday
 *
 */
@RestController
@RequestMapping("/todo/worker/todo_mgt")
@RequiredArgsConstructor
public class WorkerTodoMgtRestController {
	
	final private TodoService todoService;

	// 할당 할일 목록 조회 with 일/주/월별 예상 포인트
	@GetMapping("/page_settle_info.json")
	public ResponseEntity<Page<SettleInfoDto>> pageSettleInfo(SearchSettle searchSettle, Pageable page){
		if(page == null) {
			page = PageRequest.of(0, 10);
		}
		
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		searchSettle.setWorkerSeq(cmUser.getUserSeq());
		
		return ResponseEntity.ok(null);
	}
	
	// 미할당/할당 작업 목록 조회 with 일/주/월별 예상 포인트
	
	
	// 할당/미할당 할일 저정
}
