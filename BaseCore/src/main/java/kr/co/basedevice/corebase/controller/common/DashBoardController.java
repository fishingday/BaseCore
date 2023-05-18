package kr.co.basedevice.corebase.controller.common;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.basedevice.corebase.security.service.AccountContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class DashBoardController {
	
	@GetMapping(value={"/", "/dashboard/init.html"})
	public String routeDashBoard(Authentication authentication) {
		
		String rolePage = null;
		
		if(authentication != null && authentication.isAuthenticated()) {
			AccountContext account = (AccountContext) authentication.getPrincipal();
			rolePage =  account.getCurrRole().getDefPage();
			
		}else{
			rolePage = "/dashboard/default";
		}
		
		return rolePage;
	}
}
