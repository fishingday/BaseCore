package kr.co.basedevice.corebase.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.security.service.common.CmImprtantLogService;
import kr.co.basedevice.corebase.util.WebUtil;

@Component
public class FormAccessDeniedHandler implements AccessDeniedHandler {

	private String errorPage;

	private ObjectMapper mapper = new ObjectMapper();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	private CmImprtantLogService cmImprtantLogService;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

		// 사용자 정보는...
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CmUser cmUser = (CmUser) authentication.getPrincipal();		
        
        cmImprtantLogService.logging(request, WriteMakrCd.ACCESS_DENIED, cmUser != null ? cmUser.getUserSeq() : null);
		
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
	
	public void setCmImprtantLogService(CmImprtantLogService cmImprtantLogService) {
		this.cmImprtantLogService = cmImprtantLogService;
	}

}
