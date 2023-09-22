package kr.co.basedevice.corebase.restController.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/chg_pwd")
@RequiredArgsConstructor
public class ChgPwdRestController {
	
	final private UserService userService;

	@PutMapping("/chg_user_info.json")
	public ResponseEntity<Boolean> chgUserInfo(String userPwd, String newPwd) {		

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userSeq = ((AccountContext) authentication.getPrincipal()).getCmUser().getUserSeq();
		
		boolean isSave = userService.verifyUserPwd(userSeq, userPwd);
		
		if(isSave) {
			userService.chgUserPwd(userSeq, newPwd);
		}
		
		return ResponseEntity.ok(isSave);
	}
}
