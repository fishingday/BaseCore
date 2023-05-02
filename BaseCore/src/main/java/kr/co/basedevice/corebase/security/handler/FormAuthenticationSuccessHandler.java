package kr.co.basedevice.corebase.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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

import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.repository.cm.CmRoleRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;
import kr.co.basedevice.corebase.service.common.LoggingService;
import kr.co.basedevice.corebase.util.RequestUtil;

@Component
public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Value("${login.defaulturl:/dashboard/init.html}")
	private String defaultUrl;
	
	@Autowired
	private CmUserRepository cmUserRepository;

	@Autowired
	private CmRoleRepository cmRoleRepository;
	
	@Autowired
	private LoggingService loggingService;
	
	
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
        cmUser.setLastLoginIp(RequestUtil.getClientIp(request));
        cmUser.setUpdDt(LocalDateTime.now());
        cmUser.setUpdatorSeq(cmUser.getUserSeq());
        
        cmUserRepository.save(cmUser);
        
        List<CmRole> cmRoleList = cmRoleRepository.findByUserSeq(cmUser.getUserSeq());
        cmUser.setCurrRole(cmRoleList.get(0));
        
        // 로깅..
        loggingService.writeImportantLog(request, WriteMakrCd.LOGIN_SUCCESS_FORM, cmUser.getUserSeq());

        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }
    }
}