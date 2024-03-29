package kr.co.basedevice.corebase.restController.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.domain.cm.CmUserAlowIp;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/allow_ip")
@RequiredArgsConstructor
public class AllowIpRestController {
	
	final private UserService userService;
	
	/** 
	 * 허용 IP 목록
	 * 
	 * @return
	 */
	@GetMapping("/get_allow_ip_list.json")
	public ResponseEntity<List<CmUserAlowIp>> getAllowIpList(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userSeq = ((AccountContext) authentication.getPrincipal()).getCmUser().getUserSeq();
				
		List<CmUserAlowIp>  cmUserAlowIpList = userService.findByUserSeq4CmUserAlowIp(userSeq);
		
		return ResponseEntity.ok(cmUserAlowIpList);
	}
	
	/** 
	 * 허용 IP 저장
	 * 
	 * @param cmUserAlowIp
	 * @return
	 */
	@PutMapping("/add_allow_ip.json")
	public ResponseEntity<Boolean> addAllowIpList(CmUserAlowIp cmUserAlowIp, String userPwd){

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
	 * 허용 IP 
	 * 
	 * @param userAlowIpSeq
	 * @return
	 */
	@DeleteMapping("/remove_allow_ip.json")
	public ResponseEntity<Boolean> removeAllowIpList(Long userAlowIpSeq, String userPwd){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userSeq = ((AccountContext) authentication.getPrincipal()).getCmUser().getUserSeq();
				
		boolean isSave = false;
		if(userService.verifyUserPwd(userSeq, userPwd)) {
			isSave = userService.removeUserAllowIp(userAlowIpSeq);
		}
		
		return ResponseEntity.ok(isSave);
	}
	
	
}
