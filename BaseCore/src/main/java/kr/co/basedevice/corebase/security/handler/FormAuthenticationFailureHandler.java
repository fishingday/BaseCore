package kr.co.basedevice.corebase.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import kr.co.basedevice.corebase.repository.cm.CmUserRepository;
import kr.co.basedevice.corebase.security.service.common.CmImprtantLogService;

@Component
public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


	@Value("${login.username-parameter:username}")
	private String usernameParameter;
	
	@Autowired
	private CmUserRepository cmUserRepository;
	
	@Autowired
	private CmImprtantLogService cmImprtantLogService;
	
    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Invalid Username or Password";

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
        	cmUser.setUpdDt(LocalDateTime.now());
        	cmUser.setUpdatorSeq(0L);
        	
        	cmUserRepository.save(cmUser);     
        }
        
        cmImprtantLogService.logging(request, WriteMakrCd.LOGIN_FAIL_FORM, cmUser != null ? cmUser.getUserSeq() : null);
        
        setDefaultFailureUrl("/common/login.html?error=true&exception=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);

    }
}