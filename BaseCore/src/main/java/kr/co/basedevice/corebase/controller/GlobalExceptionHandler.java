package kr.co.basedevice.corebase.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.LogMakrCd;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.LoggingService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice(basePackages = "kr.co.basedevice.corebase.controller")
public class GlobalExceptionHandler {
	
	final private LoggingService loggingService;
	
    @ExceptionHandler(value = Exception.class)
    public String pageException(Exception e, HttpServletRequest request){
    	
    	CmUser cmUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()) {
			Object o = authentication.getPrincipal();
			if (o instanceof UserDetails) {
				cmUser = ((AccountContext) o).getCmUser();
			}	
		}
    	
    	loggingService.writeCriticalLog(request, LogMakrCd.INTERNAL_SERVER_ERROR, cmUser != null ? cmUser.getUserSeq() : null);
    	
        return "common/error";
    }
}