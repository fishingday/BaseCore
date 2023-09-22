package kr.co.basedevice.corebase.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.LogMakrCd;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.LoggingService;

@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Autowired
	private LoggingService loggingService;
 
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication) throws IOException, ServletException {         

    	Long userSeq = null;
    	
    	if(authentication != null && authentication.getPrincipal() != null) {
    		CmUser cmUser = ((AccountContext) authentication.getPrincipal()).getCmUser();
    		userSeq =  cmUser != null ? cmUser.getUserSeq() : null;
    	}
        
        loggingService.writeCriticalLog(request, LogMakrCd.LOGOUT_SUCCESS, userSeq);
         
        super.onLogoutSuccess(request, response, authentication);
    }  
}
