package kr.co.basedevice.corebase.restController.system;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.MenuService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system/menu_mgt")
@RequiredArgsConstructor
public class MenuMgtRestController {
	
	private MenuService menuService;

	/**
	 * 메뉴 목록
	 * - 조회 조건이 들어가면 복잡하다.
	 * - 모든 메뉴를 조회하는 방법으로 .. 
	 * 
	 * @return
	 */
	@GetMapping("/menu_info_list.json")
	public ResponseEntity<List<CmMenu>> findByMenuList(){
		
		List<CmMenu> cmMenuList = menuService.findByMenuList();
		
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

		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
				
		boolean isRemove = menuService.removeMenu(menuSeq, cmUser.getUserSeq());
		
		return ResponseEntity.ok(isRemove);
	}
	
	
	/**
	 * 메뉴 추가
	 * 
	 * @param menuDto
	 * @return
	 */
	@PostMapping("/save_menu.json")
	public ResponseEntity<CmMenu> saveMenu(CmMenu cmMenu){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
				
		CmMenu saveMenu = menuService.saveCmMenu(cmMenu, cmUser.getUserSeq());
		
		return ResponseEntity.ok(saveMenu);
	}
	
	/** 
	 * 메뉴 순서 변경
	 * 
	 * @param chgMenuSeq
	 * @param chgOrd
	 * @param tgtMenuSeq
	 * @param tgtOrd
	 * @return
	 */
	@PutMapping("/chg_order_menu.json")
	public ResponseEntity<Boolean> chgOrderMenu(Long chgMenuSeq, Integer chgOrd, Long tgtMenuSeq, Integer tgtOrd){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		boolean isChg = menuService.chgOrderMenu(chgMenuSeq, chgOrd, tgtMenuSeq, tgtOrd, cmUser.getUserSeq());
		
		return ResponseEntity.ok(isChg);
	}
	
	/**
	 * 메뉴별 역할 목록
	 * 
	 * @param menuSeq
	 * @return
	 */
	@GetMapping("/role_info_list.json")
	public ResponseEntity<List<CmRole>> findByRoleList(Long menuSeq){
		
		List<CmRole> cmRoleList = menuService.findByRoleList(menuSeq);
		
		return ResponseEntity.ok(cmRoleList);
	}
	
	/**
	 * 메뉴별 역할 추가
	 * 
	 * @param menuSeq
	 * @param roleSeq
	 * @return
	 */
	@PutMapping("/add_role.json")
	public ResponseEntity<Boolean> addRole(Long menuSeq, Long roleSeq){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		boolean isSuccess = menuService.addRole(menuSeq, roleSeq, cmUser.getUserSeq());
		
		return ResponseEntity.ok(isSuccess);
	}
	
	/**
	 * 메뉴별 역할 삭제
	 * 
	 * @param menuSeq
	 * @param roleSeq
	 * @return
	 */
	public ResponseEntity<Boolean> removeRole(Long menuSeq, Long roleSeq){
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
				
		boolean isSuccess = menuService.removeRole(menuSeq, roleSeq, cmUser.getUserSeq());
		
		return ResponseEntity.ok(isSuccess);
	}
}
