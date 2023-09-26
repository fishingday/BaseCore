package kr.co.basedevice.corebase.restController.common;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmCdDtl;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.dto.MyMenuDto;
import kr.co.basedevice.corebase.dto.system.OrgInfoDto;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.CommonService;
import kr.co.basedevice.corebase.service.common.OrgService;
import kr.co.basedevice.corebase.service.common.RoleService;
import kr.co.basedevice.corebase.service.common.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonRestController {
	
	final private CommonService commonService;
	final private RoleService roleService;
	final private OrgService orgService;
	final private UserService userService;
	
	/**
	 * 역할 목록 조회
	 * 
	 * @return
	 */
	@GetMapping("/role_list.json")
	public ResponseEntity<List<CmRole>> getRoleList(){
		List<CmRole> cmRoleList = roleService.findByRoleList();
		
		return ResponseEntity.ok(cmRoleList);
	}
	
	/**
	 * 코드 상세 목록
	 * 
	 * @param grpCd
	 * @return
	 */
	@GetMapping("/code_dtl_list.json")
	public ResponseEntity<List<CmCdDtl>> getCodeDtlList(String grpCd){
		List<CmCdDtl> cmCdDtlList = commonService.findCmCdDtlByGrpCd(grpCd);
		
		return ResponseEntity.ok(cmCdDtlList);
	}
	
	/**
	 * 조직 목록
	 * 
	 * @return
	 */
	@GetMapping("/org_list.json")
	public ResponseEntity<List<OrgInfoDto>> getOrgList(){
		List<OrgInfoDto> orgInfoDtoList = orgService.findAllOrg();
		
		return ResponseEntity.ok(orgInfoDtoList);
	}
	
	/**
	 * 역할 변경
	 * - 현재 역할 변경
	 * - 역할별 메뉴목록 변경
	 * 
	 * @param roleSeq
	 * @return
	 */
	@GetMapping("/chg_role.json")
	public ResponseEntity<Boolean> chgRole(Long roleSeq){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AccountContext account = ((AccountContext) authentication.getPrincipal());
		
		CmRole currRole = null;
		for(CmRole cmRole : account.getAuthRoleList()) {
			if(cmRole.getRoleSeq() == roleSeq) {
				currRole = cmRole;
				break;
			}
		}
		
		if(currRole == null) {
			throw new IllegalArgumentException("올바른 역할 정보가 아닙니다.");
		}
		account.setCurrRole(currRole);
		
		MyMenuDto myMenu = userService.findRolesMenuWithSetting(account.getCmUser().getUserSeq(), currRole.getRoleSeq());
		
		account.setMyMenu(myMenu);
		
		return ResponseEntity.ok(true);
	}
}
