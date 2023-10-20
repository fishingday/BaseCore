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
import kr.co.basedevice.corebase.dto.todo.TodoDetailDto;
import kr.co.basedevice.corebase.search.todo.SearchTodoMgt;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo/checker/todo_mgt")
@RequiredArgsConstructor
public class CheckerTodoMgtRestController {
	
	final private TodoService todoService;
	
	/** 
	 * 등록된 할일 목록	
	 * - 단순히 등록된 할일 목록을 보여준다.
	 * 
	 * @param searchTodoMgt
	 * @return
	 */
	@GetMapping("/get_todo_list.json")
	public ResponseEntity<Page<TodoDetailDto>> findByTodoList(SearchTodoMgt searchTodoMgt, Pageable page){
		if(page == null) {
			page = PageRequest.of(0, 10);
		}
				
		Page<TodoDetailDto> pageTodoDetailDto = todoService.pageTodoDetailInfo(searchTodoMgt, page);
				
		return ResponseEntity.ok(pageTodoDetailDto);
	}
	
	
	/**
	 * 할일 상세 보기
	 * 
	 * 
	 * @param todoSeq
	 * @return
	 */
	@GetMapping("/get_todo.json")
	public ResponseEntity<TodoDetailDto> getTodo(Long todoSeq){
		
		// 할일 및 작업 대상자, 확인자 목록
		TodoDetailDto todoDetailDto = todoService.getTdTodoDetail(todoSeq);
		
		return ResponseEntity.ok(todoDetailDto);
	}
	
	
	/**
	 * 할일 저장
	 * 
	 * @param todoDetailDto
	 * @return
	 */
	@PutMapping("/save_todo.json")
	public ResponseEntity<Boolean> saveTodo(TodoDetailDto todoDetailDto){
		
		
		return ResponseEntity.ok(true); 
	}
	
}
