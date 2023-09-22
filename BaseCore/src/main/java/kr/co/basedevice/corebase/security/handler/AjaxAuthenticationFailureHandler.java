package kr.co.basedevice.corebase.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.LogMakrCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;
import kr.co.basedevice.corebase.service.common.LoggingService;

public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Value("${login.username-parameter:username}")
	private String usernameParameter;
	
	@Autowired
	private CmUserRepository cmUserRepository;

	@Autowired
	private LoggingService loggingService;
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		String errorMessage = "Invalid Username or Password";

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		if(exception instanceof BadCredentialsException) {
			errorMessage = "Invalid Username or Password";
		} else if(exception instanceof DisabledException) {
			errorMessage = "Locked";
		} else if(exception instanceof CredentialsExpiredException) {
			errorMessage = "Expired password";
		}
		
        CmUser cmUser = cmUserRepository.findByLoginIdAndDelYn(request.getParameter(this.usernameParameter), Yn.N);
        
        if(cmUser != null) { 
        	// 굳이 실패한 로그인 정보를 꺼내서... 조치를 하는 것은 계정을 보호하기 위한 것이다.  
        	Integer failCnt = cmUser.getLoginFailCnt() + 1;
        	cmUser.setLoginFailCnt(failCnt);
        	
        	cmUserRepository.save(cmUser);     
        }
                
        loggingService.writeCriticalLog(request, LogMakrCd.LOGIN_FAIL_FORM, cmUser != null ? cmUser.getUserSeq() : null);

		mapper.writeValue(response.getWriter(), errorMessage);
	}
}
