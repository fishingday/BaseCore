package kr.co.basedevice.corebase.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.LogMakrCd;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.LoggingService;
import kr.co.basedevice.corebase.service.common.UserService;
import kr.co.basedevice.corebase.util.RequestUtil;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper = new ObjectMapper();
	
    @Value("${login.defaulturl:/dashboard/init.html}")
	private String defaultUrl;
	

	@Autowired
	private UserService userService;
	
	
	@Autowired
	private LoggingService loggingService;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
    	
    	AccountContext account = (AccountContext) authentication.getPrincipal();
        CmUser cmUser = account.getCmUser();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                
        // 로그인에 성공 했다면 ...
        // 로그인 실패 카운트 0 
		cmUser.setLoginFailCnt(0);
		cmUser.setLoginDt(LocalDateTime.now());
        cmUser.setLastLoginIp(RequestUtil.getClientIp(request));
        
        userService.saveCmUser(cmUser);
        
        // 로깅..
        loggingService.writeCriticalLog(request, LogMakrCd.LOGIN_SUCCESS_FORM, cmUser.getUserSeq());
        
        mapper.writeValue(response.getWriter(), cmUser);
    }
}
