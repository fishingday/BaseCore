package kr.co.basedevice.corebase.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.basedevice.corebase.domain.cm.CmImprtantLog;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.repository.cm.CmImprtantLogRepository;
import kr.co.basedevice.corebase.util.RequestUtil;
import kr.co.basedevice.corebase.util.WebUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class FormAccessDeniedHandler implements AccessDeniedHandler {

	private String errorPage;

	private ObjectMapper mapper = new ObjectMapper();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	

	@Autowired
	private CmImprtantLogRepository cmImprtantLogRepository;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

		// 사용자 정보는...
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CmUser cmUser = null;
		if(authentication != null) {
			cmUser = (CmUser) authentication.getPrincipal();
		}		
		
		CmImprtantLog log = new CmImprtantLog();
		
        log.setWriteMakrCd(WriteMakrCd.LOGIN_SUCCESS_FORM);
        log.setReqIp(RequestUtil.getClientIp(request));
        log.setSessId(request.getSession().getId());
        log.setUserSeq(cmUser != null ? cmUser.getUserSeq() : null);        
        log.setUserAgent(request.getHeader("user-agent"));
        log.setAccept(request.getHeader("accept"));
        log.setReferer(request.getHeader("referer"));
        log.setAcceptEncoding(request.getHeader("accept-encoding"));
        log.setAcceptLanguage(request.getHeader("accept-language"));
        log.setAcceptCharset(request.getHeader("accept-charset"));        
        String param = request.getQueryString();
        if(param != null && param.length() > 2000) {
        	param = param.substring(0, 2000);
        }
        log.setParam(param);
        log.setCreatorDt(LocalDateTime.now());
        
        cmImprtantLogRepository.save(log);
		
		if (WebUtil.isAjax(request)) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(this.mapper.writeValueAsString(ResponseEntity.status(HttpStatus.FORBIDDEN)));

		} else {
			String deniedUrl = errorPage + "?exception=" + accessDeniedException.getMessage();
			redirectStrategy.sendRedirect(request, response, deniedUrl);
		}
	}
	
	public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }

        this.errorPage = errorPage;
    }

}
