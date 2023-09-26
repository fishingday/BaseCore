package kr.co.basedevice.corebase.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

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
import kr.co.basedevice.corebase.domain.code.LogMakrCd;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.LoggingService;
import kr.co.basedevice.corebase.service.common.UserService;
import kr.co.basedevice.corebase.util.RequestUtil;

@Component
public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Value("${login.defaulturl:/dashboard/init.html}")
	private String defaultUrl;
		
	@Autowired
	private LoggingService loggingService;
	
	@Autowired
	private UserService userService;
	
	
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {

        setDefaultTargetUrl(defaultUrl);
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        // 로그인에 성공 했다면 ...
        // 로그인 실패 카운트 0 
        AccountContext account = (AccountContext) authentication.getPrincipal();
        CmUser cmUser = account.getCmUser();
		cmUser.setLoginFailCnt(0);
		cmUser.setLoginDt(LocalDateTime.now());
        cmUser.setLastLoginIp(RequestUtil.getClientIp(request));
        
        userService.saveCmUser(cmUser);
        
        // 로깅..
        loggingService.writeCriticalLog(request, LogMakrCd.LOGIN_SUCCESS_FORM, cmUser.getUserSeq());

        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }
    }
}