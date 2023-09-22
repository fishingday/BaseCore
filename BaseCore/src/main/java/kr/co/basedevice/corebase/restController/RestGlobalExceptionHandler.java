package kr.co.basedevice.corebase.restController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.LogMakrCd;
import kr.co.basedevice.corebase.dto.ExceptionDto;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.LoggingService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "kr.co.basedevice.corebase.restController")
public class RestGlobalExceptionHandler {

	final private LoggingService loggingService;

	@ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionDto> jsonException(Exception e, HttpServletRequest request){
		
		ExceptionDto ex = new ExceptionDto();
		ex.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		ex.setCode("500");
		ex.setMessage(e.getMessage());
		
    	CmUser cmUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()) {
			Object o = authentication.getPrincipal();
			if (o instanceof UserDetails) {
				cmUser = ((AccountContext) o).getCmUser();
			}	
		}
    	
    	loggingService.writeCriticalLog(request, LogMakrCd.INTERNAL_SERVER_ERROR, cmUser != null ? cmUser.getUserSeq() : null);
		
		return ResponseEntity.status(ex.getHttpStatus()).body(ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDto> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request){
		ExceptionDto ex = new ExceptionDto();
		ex.setHttpStatus(HttpStatus.BAD_REQUEST);
		ex.setCode("400");
		ex.setMessage(e.getMessage());
		
    	CmUser cmUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()) {
			Object o = authentication.getPrincipal();
			if (o instanceof UserDetails) {
				cmUser = ((AccountContext) o).getCmUser();
			}	
		}
    	
    	loggingService.writeCriticalLog(request, LogMakrCd.INTERNAL_SERVER_ERROR, cmUser != null ? cmUser.getUserSeq() : null);

		
		return ResponseEntity.status(ex.getHttpStatus()).body(ex);
    }
}