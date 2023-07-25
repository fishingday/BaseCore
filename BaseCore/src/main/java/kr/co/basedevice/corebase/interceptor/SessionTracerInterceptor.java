package kr.co.basedevice.corebase.interceptor;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.basedevice.corebase.domain.code.AccesLogTypCd;
import kr.co.basedevice.corebase.dto.common.MenuDto;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.service.common.LoggingService;

/**
 * 1. 사용자가 요청한 URI의 정보를 조회
 * 2. 사용자의 현재 위치 정보 셋팅
 * 3. 로깅!
 *
 */
public class SessionTracerInterceptor implements HandlerInterceptor {
	private LinkedHashMap<RequestMatcher, MenuDto> menuMap;
	
	private LoggingService loggingService;
		
	public SessionTracerInterceptor(LinkedHashMap<RequestMatcher, MenuDto> map, LoggingService loggingService) {
		this.menuMap = map;
		this.loggingService = loggingService;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// 메뉴 정보는 인증된 사용자만 제공한다.
		Long userSeq = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Object o = authentication.getPrincipal();
			if (o instanceof UserDetails) {
				MenuDto currMenu = null;	        	
	            for (Map.Entry<RequestMatcher, MenuDto> entry : menuMap.entrySet()) {            	
	                RequestMatcher matcher = entry.getKey();
	                if (matcher.matches(request)) {
	                	currMenu = entry.getValue();
	                    break;
	                }
	            }
	            userSeq = ((AccountContext) o).getCmUser().getUserSeq();
	            ((AccountContext) o).setCurrMenu(currMenu);
			}
        }
		
		loggingService.writeAccessLog(request, response, AccesLogTypCd.IN, userSeq);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
		Long userSeq = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Object o = authentication.getPrincipal();
			if (o instanceof UserDetails) {
				userSeq = ((AccountContext) o).getCmUser().getUserSeq();
			}
        }
		
		loggingService.writeAccessLog(request, response, AccesLogTypCd.OUT, userSeq);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
		// 할 일이 없음... 
	}
}
