package kr.co.basedevice.corebase.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.service.common.LoggingService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {
	
	final private LoggingService loggingService;
	
    @ExceptionHandler(value = Exception.class)
    public String pageException(Exception e, HttpServletRequest request){
    	
    	CmUser cmUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() != null) {
			cmUser = (CmUser) authentication.getPrincipal();	
		}
    	
    	loggingService.writeCriticalLog(request, WriteMakrCd.INTERNAL_SERVER_ERROR, cmUser != null ? cmUser.getUserSeq() : null);
    	
        return "common/error";
    }
}