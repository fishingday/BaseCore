package kr.co.basedevice.corebase.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;

@Component
public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Value("${login.defaulturl:/dashboard/init.html}")
	private String defaultUrl;
	
	@Autowired
	private CmUserRepository cmUserRepository;
	
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {

        setDefaultTargetUrl(defaultUrl);
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        // 로그인에 성공 했다면 ...
        // 로그인 실패 카운트 0 
		CmUser cmUser = (CmUser) authentication.getPrincipal();
		cmUser.setLoginFailCnt(0);
		cmUser.setLoginDt(LocalDateTime.now());
        cmUser.setLastLoginIp(getClientIp(request));
        cmUser.setUpdDt(LocalDateTime.now());
        cmUser.setUpdatorSeq(cmUser.getUserSeq());
        
        cmUserRepository.save(cmUser);

        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }
        
//        // 세션 ID
//        request.getScheme();
//        request.getRemoteHost();
//        request.getRemoteAddr();
//        request.getRequestedSessionId();
 
//     
//      Request Name : [user-agent]:[Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36]        
//      Request Name : [accept]:[text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7]
//		Request Name : [referer]:[http://localhost:8080/common/login.html]
//		Request Name : [accept-encoding]:[gzip, deflate, br]
//		Request Name : [accept-language]:[ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7]
        
//        
//        Enumeration<String> eHeader = request.getHeaderNames();
//        while(eHeader.hasMoreElements()) {
//        	String name = eHeader.nextElement();
//        	String value = request.getHeader(name);
//        	
//        	System.err.println("Request Name : [" + name + "]:[" + value + "]" );
//        }
       
    }
    
    private String getClientIp(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (ip == null) ip = req.getRemoteAddr();
        return ip;
    }
}