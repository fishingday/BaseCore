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
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.security.service.common.CmImprtantLogService;

@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Autowired
	private CmImprtantLogService cmImprtantLogService;
 
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication) throws IOException, ServletException {         

		CmUser cmUser = (CmUser) authentication.getPrincipal();
                 
        Long userSeq = cmUser != null ? cmUser.getUserSeq() : null;
        
        cmImprtantLogService.logging(request, WriteMakrCd.LOGOUT_SUCCESS, userSeq);
         
        super.onLogoutSuccess(request, response, authentication);
    }  
}
