package kr.co.basedevice.corebase.restController.system;

import org.springframework.security.core.context.SecurityContextHolder;

import kr.co.basedevice.corebase.domain.cm.CmUser;

public class UserMgtRestController {

	
	public CmUser sssd() {
		CmUser cmUser = (CmUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return cmUser;
	}
}
