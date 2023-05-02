package kr.co.basedevice.corebase.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import kr.co.basedevice.corebase.domain.cm.CmImprtantLog;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.repository.cm.CmImprtantLogRepository;
import kr.co.basedevice.corebase.util.RequestUtil;

@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
 

	@Autowired
	private CmImprtantLogRepository cmImprtantLogRepository;
 
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication) throws IOException, ServletException {         

		CmUser cmUser = (CmUser) authentication.getPrincipal();
         
        CmImprtantLog log = new CmImprtantLog();
        
        log.setWriteMakrCd(WriteMakrCd.LOGOUT_SUCCESS);
        log.setReqIp(RequestUtil.getClientIp(request));
        log.setSessId(request.getSession().getId());
        log.setUserSeq(cmUser.getUserSeq());        
        log.setUserAgent(request.getHeader("user-agent"));
        log.setAccept(request.getHeader("accept"));
        log.setReferer(request.getHeader("referer"));
        log.setAcceptEncoding(request.getHeader("accept-encoding"));
        log.setAcceptLanguage(request.getHeader("accept-language"));
        log.setAcceptCharset(request.getHeader("accept-charset"));
        log.setCreatorDt(LocalDateTime.now());

        cmImprtantLogRepository.save(log);
         
        super.onLogoutSuccess(request, response, authentication);
    }  
}
