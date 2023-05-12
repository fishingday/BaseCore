package kr.co.basedevice.corebase.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.service.common.LoggingService;
import lombok.RequiredArgsConstructor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class PageErrorController implements ErrorController {

	final private LoggingService loggingService;

	@GetMapping(value = "/error/404.html", produces = MediaType.TEXT_HTML_VALUE)
	public String notFoundError(HttpServletRequest request) {

		CmUser cmUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()) {
			Object o = authentication.getPrincipal();
			if (o instanceof UserDetails) {
				cmUser = (CmUser) o;
			}	
		}

		loggingService.writeCriticalLog(request, WriteMakrCd.OTHER_ERRROR, cmUser != null ? cmUser.getUserSeq() : null);

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "common/404";
			} else {
				return "error";
			}
		}

		return "common/404";
	}
}