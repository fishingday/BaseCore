package kr.co.basedevice.corebase.restController.system;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.system.MenuInfoDto;
import kr.co.basedevice.corebase.dto.system.ParentMenuDto;
import kr.co.basedevice.corebase.dto.system.SaveMenuInfo;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.MenuService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system/menu_mgt")
@RequiredArgsConstructor
public class MenuMgtRestController {
	
	final private MenuService menuService;

	/**
	 * 메뉴 목록
	 * - 조회 조건이 들어가면 복잡하다.
	 * - 모든 메뉴를 조회하는 방법으로 .. 
	 * 
	 * @return
	 */
	@GetMapping("/menu_info_list.json")
	public ResponseEntity<List<MenuInfoDto>> findByMenuList(Long roleSeq){
		
		List<MenuInfoDto> cmMenuList = menuService.findByRoleSeq(roleSeq);
		
		return ResponseEntity.ok(cmMenuList);
	}
	
	/**
	 * 하위 메뉴가 있는 메뉴이거나 메뉴호출 경로가 없는 목록
	 * 
	 * @return
	 */
	@GetMapping("/parent_menu_list.json")
	public ResponseEntity<List<ParentMenuDto>> findByParentMenuList(){
		
		List<ParentMenuDto> cmMenuList = menuService.findByParentMenuList();
		
		return ResponseEntity.ok(cmMenuList);
	}
	
	/**
	 * 메뉴 삭제
	 * - 하위 메뉴가 있으면 삭제가 되지 않는다.
	 * 
	 * @param menuSeq
	 * @return
	 */
	@DeleteMapping("/remove_menu.json")
	public ResponseEntity<Boolean> removeMenu(Long menuSeq){
				
		boolean isRemove = menuService.removeMenu(menuSeq);
				
		return ResponseEntity.ok(isRemove);
	}
	
	
	/**
	 * 메뉴 추가
	 * 
	 * @param menuDto
	 * @return
	 */
	@PutMapping("/save_menu.json")
	public ResponseEntity<Boolean> saveMenu(SaveMenuInfo saveMenuInfo){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		boolean isSave = menuService.saveCmMenu(saveMenuInfo, cmUser.getUserSeq());
		
		return ResponseEntity.ok(isSave);
	}
		
}
