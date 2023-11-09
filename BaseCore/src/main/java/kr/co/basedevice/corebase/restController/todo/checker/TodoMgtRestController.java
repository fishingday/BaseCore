package kr.co.basedevice.corebase.restController.todo.checker;

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
import kr.co.basedevice.corebase.dto.todo.TodoMgtDto;
import kr.co.basedevice.corebase.search.todo.SearchTodoMgt;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checker/todo_mgt")
@RequiredArgsConstructor
public class TodoMgtRestController {
	
	final private TodoService todoService;
	
	/** 
	 * 등록된 할일 목록	
	 * - 단순히 등록된 할일 목록을 보여준다.
	 * 
	 * @param searchTodoMgt
	 * @return
	 */
	@GetMapping("/todo_mgt_list.json")
	public ResponseEntity<Page<TodoMgtDto>> findByTodoList(SearchTodoMgt searchTodoMgt, Pageable page){
		if(page == null) {
			page = PageRequest.of(0, 10);
		}
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		searchTodoMgt.setCheckerSeq(cmUser.getUserSeq());
		
		Page<TodoMgtDto> pageTodoMgtDto = todoService.pageTodoMgtList(searchTodoMgt, page);
				
		return ResponseEntity.ok(pageTodoMgtDto);
	}
	
	
	/**
	 * 할일 상세 보기
	 * 
	 * 
	 * @param todoSeq
	 * @return
	 */
	@GetMapping("/get_todo.json")
	public ResponseEntity<TodoMgtDto> getTodo(Long todoSeq){
		
		// 할일 및 작업 대상자, 확인자 목록
		TodoMgtDto todoDetailDto = todoService.getTdTodoDetail(todoSeq);
		
		return ResponseEntity.ok(todoDetailDto);
	}
	
	
	/**
	 * 할일 저장
	 * 
	 * @param todoDetailDto
	 * @return
	 */
	@PutMapping("/save_todo.json")
	public ResponseEntity<Boolean> saveTodo(TodoMgtDto todoDetailDto){
		
		boolean isSave = todoService.saveTodo(todoDetailDto);
		
		return ResponseEntity.ok(isSave); 
	}
	
}
