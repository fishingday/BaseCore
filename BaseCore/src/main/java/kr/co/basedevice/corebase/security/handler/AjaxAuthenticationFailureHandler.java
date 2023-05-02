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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.basedevice.corebase.domain.cm.CmImprtantLog;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.CmImprtantLogRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;
import kr.co.basedevice.corebase.util.RequestUtil;

public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Value("${login.username-parameter:username}")
	private String usernameParameter;
	
	@Autowired
	private CmUserRepository cmUserRepository;
	
	@Autowired
	private CmImprtantLogRepository cmImprtantLogRepository;
    
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
        
        CmImprtantLog log = new CmImprtantLog();
        if(cmUser != null) { 
        	// 굳이 실패한 로그인 정보를 꺼내서... 조치를 하는 것은 계정을 보호하기 위한 것이다.  
        	Integer failCnt = cmUser.getLoginFailCnt() + 1;
        	cmUser.setLoginFailCnt(failCnt);
        	cmUser.setUpdDt(LocalDateTime.now());
        	cmUser.setUpdatorSeq(0L);
        	
        	cmUserRepository.save(cmUser);
        	
            log.setUserSeq(cmUser.getUserSeq());        
        }
                
        log.setWriteMakrCd(WriteMakrCd.LOGIN_FAIL_AJAX);
        log.setReqIp(RequestUtil.getClientIp(request));
        log.setSessId(request.getSession().getId());
        log.setUserAgent(request.getHeader("user-agent"));
        log.setAccept(request.getHeader("accept"));
        log.setReferer(request.getHeader("referer"));
        log.setAcceptEncoding(request.getHeader("accept-encoding"));
        log.setAcceptLanguage(request.getHeader("accept-language"));
        String param = request.getQueryString();
        if(param != null && param.length() > 2000) {
        	param = param.substring(0, 2000);
        }
        log.setCreatorDt(LocalDateTime.now());
        
        cmImprtantLogRepository.save(log);

		mapper.writeValue(response.getWriter(), errorMessage);
	}
}
