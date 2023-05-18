package kr.co.basedevice.corebase.restController.system;

import org.springframework.security.core.context.SecurityContextHolder;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.security.service.AccountContext;

public class UserMgtRestController {

	
	public CmUser sssd() {
		CmUser cmUser = ((AccountContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCmUser();
		
		return cmUser;
	}
}
