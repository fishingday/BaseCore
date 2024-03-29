package kr.co.basedevice.corebase.controller.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.security.service.AccountContext;
import lombok.RequiredArgsConstructor;

/**
 * 대시보드
 * - 역할에 따라 대시보드를 
 * 
 * 
 */
@RequiredArgsConstructor
@Controller
public class DashBoardController {
	
	@GetMapping(value={"/", "/dashboard/init.html"})
	public String routeDashBoard() {
		
		String rolePage = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication != null && !"anonymousUser".equals(authentication.getPrincipal())) {			
			CmRole cmRole = ((AccountContext) authentication.getPrincipal()).getCurrRole();
			rolePage =  cmRole.getDefPage();			
		}else{
			rolePage = "/dashboard/default";
		}
		
		return rolePage;
	}
}
