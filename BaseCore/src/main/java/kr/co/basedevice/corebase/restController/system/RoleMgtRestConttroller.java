package kr.co.basedevice.corebase.restController.system;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.system.ChooseMenusRole;
import kr.co.basedevice.corebase.dto.system.ChooseUsersRole;
import kr.co.basedevice.corebase.dto.system.ParentMenuDto;
import kr.co.basedevice.corebase.service.common.MenuService;
import kr.co.basedevice.corebase.service.common.RoleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system/role_mgt")
@RequiredArgsConstructor
public class RoleMgtRestConttroller {
	
	final private RoleService roleService; 
	final private MenuService menuService;
	
	/**
	 * 역할 목록
	 * 
	 * @return
	 */
	@GetMapping("/role_list.json")
	public ResponseEntity<List<CmRole>> findByRoleList(){		
		List<CmRole> cmRoleList = roleService.findByRoleList();
		
		return ResponseEntity.ok(cmRoleList);
	}
	
	
	/**
	 * 역할 상세
	 * 
	 * @param roleSeq
	 * @return
	 */
	@GetMapping("/get_role.json")
	public ResponseEntity<CmRole> getCmCdGrp(Long roleSeq){
		
		CmRole cmRole = roleService.findById(roleSeq);
		
		if(cmRole != null) {
			return ResponseEntity.ok(cmRole);
		}else{
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * 역할 추가
	 * 
	 * @param cmRole
	 * @return
	 */
	@PostMapping("/save_role.json")
	public ResponseEntity<CmRole> saveCdGrp(CmRole cmRole){
		CmRole saveRole = roleService.saveCmRole(cmRole);
		
		return ResponseEntity.ok(saveRole);
	}
	
	/**
	 * 역할 삭제
	 * 
	 * @param roleSeq
	 * @return
	 */
	@DeleteMapping("/remove_role.json")
	public ResponseEntity<Boolean> removeCdGrp(Long roleSeq){
		CmRole cmRole = roleService.removeCmRole(roleSeq);
		
		if(cmRole != null) {
			return ResponseEntity.ok(true);
		}else{
			return ResponseEntity.ok(false);
		}
	}
	
	/**
	 * 역할별 사용자 목록
	 * 
	 * @param roleSeq
	 * @return
	 */
	@GetMapping("/role_user_list.json")
	public ResponseEntity<List<CmUser>> findByUserList(Long roleSeq){		
		List<CmUser> cmUserList = null;
		
		if(roleSeq != null) {
			cmUserList = roleService.findByCmUser(roleSeq);
		}
		return ResponseEntity.ok(cmUserList);
	}
		
	/**
	 * 특정 역할 제외 사용자 목록
	 * 
	 * @param roleSeq
	 * @return
	 */
	@GetMapping("/exclude_user_list.json")
	public ResponseEntity<List<CmUser>> findByExcludeUserList(Long roleSeq){
		List<CmUser> cmUserList = null;
		
		if(roleSeq != null) {
			cmUserList = roleService.findByExcludeCmUser(roleSeq);
		}
		
		return ResponseEntity.ok(cmUserList);
	}
	
	/** 
	 * 역할 별 사용자 추가
	 * 
	 * @param chooseUsers
	 * @return
	 */
	@PutMapping("/add_users.json")
	public ResponseEntity<Integer> addUsers(ChooseUsersRole chooseUsers){
		int applyCnt = roleService.addUsers(chooseUsers);
		
		return ResponseEntity.ok(applyCnt);
	}
	
	/** 
	 * 역할 별 사용자 제거
	 * 
	 * @param chooseUsers
	 * @return
	 */
	@DeleteMapping("/remove_users.json")
	public ResponseEntity<Integer> removeUsers(ChooseUsersRole chooseUsers){
		int applyCnt = roleService.removeUsers(chooseUsers);
		
		return ResponseEntity.ok(applyCnt);
		
	}	
	
	/** 
	 * 역할 별 메뉴 목록
	 * 
	 * @param roleSeq
	 * @return
	 */
	@GetMapping("/role_menu_list.json")
	public ResponseEntity<List<ParentMenuDto>> findByMenuList(Long roleSeq){
		List<ParentMenuDto> parentMenuList = null;
		if(roleSeq != null) {
			List<CmMenu> cmMenuList = roleService.findByCmMenu(roleSeq);			
			parentMenuList = menuService.makeParentMenuList(cmMenuList);
		}
		
		return ResponseEntity.ok(parentMenuList);
	}
	
	/** 
	 * 역할 별 메뉴 목록
	 * 
	 * @param roleSeq
	 * @return
	 */
	@GetMapping("/exclude_menu_list.json")
	public ResponseEntity<List<ParentMenuDto>> findByExcludeMenuList(Long roleSeq){
		List<ParentMenuDto> parentMenuList = null;
		
		if(roleSeq != null) {
			List<CmMenu> cmMenuList = roleService.findByExcludeCmMenu(roleSeq);			
			parentMenuList = menuService.makeParentMenuList(cmMenuList);
		}
		
		return ResponseEntity.ok(parentMenuList);
	}
		
	/** 
	 * 역할 별 메뉴 추가
	 * 
	 * @param chooseMenus
	 * @return
	 */
	@PutMapping("/add_menus.json")
	public ResponseEntity<Integer> addMenus(ChooseMenusRole chooseMenus){
		int applyCnt = roleService.addMenus(chooseMenus);
		
		return ResponseEntity.ok(applyCnt);
	}
		
	/**
	 * 역할 별 메뉴 삭제
	 * 
	 * @param chooseMenus
	 * @return
	 */
	@DeleteMapping("/remove_menus.json")
	public ResponseEntity<Integer> removeMenus(ChooseMenusRole chooseMenus){
		int applyCnt = roleService.removeMenus(chooseMenus);
		
		return ResponseEntity.ok(applyCnt);
	}
}
