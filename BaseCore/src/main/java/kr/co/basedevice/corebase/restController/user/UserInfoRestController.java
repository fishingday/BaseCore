package kr.co.basedevice.corebase.restController.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.cm.CmUserAlowIp;
import kr.co.basedevice.corebase.dto.common.UserInfoDto;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/user_info")
@RequiredArgsConstructor
public class UserInfoRestController {
	
	final private UserService userService;
	
	/**
	 * 사용자 정보 조회
	 * 
	 * @return
	 */
	@GetMapping("/user_info.json")
	public ResponseEntity<UserInfoDto> getUserInfo(){
		// 변하지 않는 것은 userSeq 뿐...
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userSeq = ((AccountContext) authentication.getPrincipal()).getCmUser().getUserSeq();
				
		// 자신이 변경할 수 없는 정보
		List<CmRole> cmRoleList =  ((AccountContext) authentication.getPrincipal()).getAuthRoleList();		
		List<CmOrg> cmOrgList = ((AccountContext) authentication.getPrincipal()).getOrgList();
		
		// 자신이 변경할 수 있는 정보
		CmUser cmUser = userService.findByCmUser(userSeq);
		List<CmUserAlowIp> cmUserAlowIpList = userService.findByUserSeq4CmUserAlowIp(userSeq);
		
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setCmUser(cmUser);
		userInfoDto.setCmRoleList(cmRoleList);
		userInfoDto.setCmOrgList(cmOrgList);
		userInfoDto.setCmRoleList(cmRoleList);
		userInfoDto.setCmUserAlowIpList(cmUserAlowIpList);
				
		return ResponseEntity.ok(userInfoDto);
	}
		
	/**
	 * 사용자 정보 저장 
	 * 
	 * @param cmUser
	 * @return
	 */
	@PutMapping("/save_user.json")
	public ResponseEntity<Boolean> saveUserInfo(CmUser cmUser, String userPwd){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userSeq = ((AccountContext) authentication.getPrincipal()).getCmUser().getUserSeq();		
		cmUser.setUserSeq(userSeq);
		
		if(userService.verifyUserPwd(userSeq, userPwd)) {
			userService.saveCmUser(cmUser);
		}		
		
		return ResponseEntity.ok(true);
	}
	
	@PutMapping("/save_allow_ip.json")
	public ResponseEntity<Boolean> saveAllowIpList(CmUserAlowIp cmUserAlowIp, String userPwd){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userSeq = ((AccountContext) authentication.getPrincipal()).getCmUser().getUserSeq();
		
		cmUserAlowIp.setUserSeq(userSeq);
		
		boolean isSave = false;
		if(userService.verifyUserPwd(userSeq, userPwd)) {
			isSave = userService.saveUserAllowIp(cmUserAlowIp);
		}
		
		return ResponseEntity.ok(isSave);
	}
	
	/**
	 * 사용자 ID 존재 여부
	 * 
	 * @param loginId
	 * @return
	 */
	@GetMapping("/exists_login_id.json")
	public ResponseEntity<Boolean> existsLoginId(String loginId){
		
		boolean exists = userService.existsLoginId(loginId);
		
		return ResponseEntity.ok(exists);
	}

}
