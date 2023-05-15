package kr.co.basedevice.corebase.interceptor;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.basedevice.corebase.security.factory.UrlResourcesMapFactoryBean;
import kr.co.basedevice.corebase.security.service.SecurityResourceService;

/**
 * 1. 사용자가 요청한 URI의 정보를 조회
 * 2. 사용자의 현재 위치 정보 셋팅
 * 3. 로깅!
 *
 */
public class SessionTracerInterceptor implements HandlerInterceptor {
	
	private PathMatcher pathMatcher = new AntPathMatcher();
	private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourcesMap;
	
	
	public SessionTracerInterceptor(LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourcesMap) {
		this.resourcesMap = resourcesMap;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// 요청한 URL을 확인하고....
		String contextRoot = request.getContextPath();
		String url = request.getRequestURI();
		String authUrl = url.substring(url.indexOf(contextRoot) + contextRoot.length());

/*
		
		ComUserAccesLog comUserAccesLog = new ComUserAccesLog();
		comUserAccesLog.setReqUrl(url);
		String remoteIP = request.getRemoteAddr();
		comUserAccesLog.setReqIp(remoteIP);
		String queryString = RequestUtils.getQueryString(request);
		if(queryString != null && queryString.length() > 2000){
			queryString = queryString.substring(0, 2000);
		}
		comUserAccesLog.setDtlCont(queryString);
		comUserAccesLog.setHttpMethodCd(request.getMethod());
		comUserAccesLog.setUserLogTypCd(UserLogTypCd.ACCESS);
		comUserAccesLog.setUserLogRsltCd(UserLogRsltCd.SUCCESS);
		comUserAccesLog.setHttpStatNo(String.valueOf(response.getStatus()));
		
		// 사용자 정보가 있는지...
		SecurityUser securityUser = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			Object anonymousObject = auth.getPrincipal();
			comUserAccesLog.setLogGrdCd(LogGrdCd.INFO); 
			if(anonymousObject instanceof SecurityUser){ // 사용자가 없다면 전부 로그인으로 갈 것이다.
				securityUser = (SecurityUser) anonymousObject;
				// 여기는 당연히 사용자 정보가 있어야 한다.
				if(securityUser != null){
					// URL이 있다면... 페이지 정보를 박아 준다.
					ComMenuMngt comMenuMngt = securityUser.getAuthMenuMngt(authUrl);

					// 온전한 url로 찾은 메뉴가 없으면 queryString이 제외된 url로 메뉴를 찾는다.
					if(comMenuMngt == null) {
						comMenuMngt = securityUser.getAuthMenuMngt(url);
					}
					
					// 그래도 없는 경우는 목록(그리드)에서 선택되어 이동한 페이지로 메뉴로 등록되지 않은 것이다.
					// 이 때는 상위 메뉴(상위 메뉴가 init.ls 임을 가정)를 찾는다.
					// 2019.7.22 emile
					if(comMenuMngt == null && !"/sys/sysAsset/getSysAsset.ls".equals(url)) {
						// /bbs/user/6103/1080/getBbs.ls
						String url_dtl = url.substring(0, url.lastIndexOf("/"));						
						if(url_dtl.contains("/bbs/user/")) { // 게시판 만의 특별함을...
							url_dtl = url_dtl.substring(0, url_dtl.lastIndexOf("/"));
						}
						comMenuMngt = securityUser.getAuthMenuMngt(url_dtl + "/init.ls");
					}
					
					// 그래도 없으면 가장 가까운 것으로 표시한다.
					// 2019.10.30
					if(comMenuMngt == null && !"/sys/sysAsset/getSysAsset.ls".equals(url)) {
						String url2 = url.substring(0, url.lastIndexOf("/")); 
						comMenuMngt = securityUser.getAuthMenuMngtSimilarTo(url2);
					}
					
					if(comMenuMngt != null){
						// 만약 현재 페이지가 중복 접속 금지 페이지라면....
						if(comMenuMngt.getDupAccesBanYn() == Yn.Y){
							if(connectedUserManager.isOccupantUrl(comMenuMngt, securityUser)){
								comUserAccesLog.setUserLogRsltCd(UserLogRsltCd.FAIL);
								comUserAccesLog.setUserLogTypCd(UserLogTypCd.DENIED);
								request.setAttribute(SecurityConstant.UP_LOG_SEQ, userService.writeUserAccessLog(comUserAccesLog));
								response.sendError(
										HttpServletResponse.SC_PRECONDITION_FAILED, 
										messageSourceAccessor.getMessage(
												"framework.common.security.alreadUsedPage", 
												"요청한 서비스는 이미 사용 중입니다. 잠시후에 사용하세요.", 
												(Locale)WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME)
											)
									);
							}
						}
						
						securityUser.setSelectedCurrMenu(comMenuMngt);
					}
					comUserAccesLog.setReqUserMngtSeq(securityUser.getComUserMngt().getUserMngtSeq());
				}
				
				request.setAttribute("locale", WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME)); // 언어 정보를 넣어준다. 무조건~
			}
		}else{// 세션이 없는 사용자
			comUserAccesLog.setLogGrdCd(LogGrdCd.CAUTION);
		}
		
		HttpSession session = request.getSession(false);
		if(session != null){
			comUserAccesLog.setSessId(session.getId());
			session.setAttribute(SessionTracerInterceptor.LAST_ACCESS_TIME, Long.valueOf(System.currentTimeMillis()));  // 접근한 시간을 기록한다.
						
			// IP 처음 접속 IP와 같은가? 
			if(Boolean.valueOf(env.getProperty("security.session.hijacking", "true"))){
				String storeIP = (String) session.getAttribute(SessionTracerInterceptor.FIRST_CONNECTED_IP);
				if(storeIP == null){ // 저장된 IP가 없다면... 로그인 페이지로 보낸다.
					response.sendRedirect(SecurityConfig.URL_LOGIN_FORM);
					return true;
				}
				
				if(!remoteIP.equals(storeIP)){
					comUserAccesLog.setUserLogRsltCd(UserLogRsltCd.FAIL);
					comUserAccesLog.setUserLogTypCd(UserLogTypCd.DENIED);
					request.setAttribute(SecurityConstant.UP_LOG_SEQ, userService.writeUserAccessLog(comUserAccesLog));
					response.sendError(
							HttpServletResponse.SC_CONFLICT, 
							messageSourceAccessor.getMessage(
									"framework.common.security.session.hijacking", 
									"세션 하이젝킹이 의심되어 동작을 정지 합니다. 다시 접속하십시오.", 
									(Locale)WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME)
								)
						);
				}
			}
		}
		request.setAttribute(SecurityConstant.UP_LOG_SEQ, userService.writeUserAccessLog(comUserAccesLog));
*/
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
		// 요청한 URL을 확인하고....
		String contextRoot = request.getContextPath();
		String url = request.getRequestURI();
		String authUrl = url.substring(url.indexOf(contextRoot) + contextRoot.length());

		/*
		if(pathMatcher.match(SecurityConfig.URL_NOTIFICATION, authUrl)){			
			return; // 공지사항에 대한 접근이다. 기록하지 않는다. 
		}
		
		ComUserAccesLog comUserAccesLog = new ComUserAccesLog();
		comUserAccesLog.setReqUrl(url);
		comUserAccesLog.setReqIp(request.getRemoteAddr());
		comUserAccesLog.setHttpMethodCd(request.getMethod());
		comUserAccesLog.setUserLogTypCd(UserLogTypCd.COMPLETE);
		comUserAccesLog.setLogGrdCd(LogGrdCd.INFO);
		comUserAccesLog.setHttpStatNo(String.valueOf(response.getStatus()));
		
		if(response.getStatus() >= 400 && response.getStatus() < 500 && response.getStatus() != 404 ) { // 클라이언트 오류만 상대한다.
			Object errObj = request.getAttribute("javax.servlet.error.message");
			String errMsg = null;
			if(ObjectUtils.isEmpty(errObj)) { // 메시지가 없으면 만들자
				if(HttpServletResponse.SC_BAD_REQUEST == response.getStatus()) { // 400 파라메터 위변조 
					errMsg = "요청이 변조된 것으로 의심되어 서비스를 중지 합니다.";
				}else if(HttpServletResponse.SC_UNAUTHORIZED == response.getStatus()) { // 401 인증되지 않음
					errMsg = "인증된 사용자가 아닙니다.";
				}else if(HttpServletResponse.SC_FORBIDDEN == response.getStatus()) { // 403 접근 급지
					errMsg = "권한이 없는 사용자 입니다.";
				}else if(HttpServletResponse.SC_NOT_ACCEPTABLE == response.getStatus()) { // 406 게시판 패스워드 오류
					errMsg = "패스워드가 올바르지 않습니다.";
				}else if(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED == response.getStatus()) { // 407 각종 로그인 오류
					errMsg = "로그인 시 오류가 발생 했습니다."; // 이거슨 절대 실행 되어서는 안되요.... SecurityAuthenticationFailureHandler 꺼니까요.
				}else if(HttpServletResponse.SC_CONFLICT == response.getStatus()) { // 409 세션 하이젝킹 
					errMsg = "세션 하이젝킹이 의심되어 동작을 정지 합니다. 다시 접속하십시오.";
				}else if(HttpServletResponse.SC_PRECONDITION_FAILED == response.getStatus()) { // 412 단일 사용 페이지 
					errMsg = "요청한 서비스는 이미 사용 중입니다. 잠시후에 사용하세요.";
				}else if(HttpServletResponse.SC_REQUEST_URI_TOO_LONG == response.getStatus()) { // 414 단시간에 보안문서를 많이 받았다.  
					errMsg = "보안문서 다운로드가 많습니다."; // 이거슨 절대 실행되어서는 안되요.... 보안문서 다운로드에서 실행 될 것이니까요. 
				}else if(HttpServletResponse.SC_EXPECTATION_FAILED == response.getStatus()) { // 417 사용자 입력 값 중복 (게정명, 이메일, 군번, 부대명, 부대코드) 
					errMsg = "사용자 입력 값 중복 (게정명, 이메일, 군번, 부대명, 부대코드)";
				}else {
					errMsg = "정의되지 않은 예외 입니다.";
				}
				comUserAccesLog.setStatCont(errMsg);
			}else { // 메시지가 있다면...							
				if(errObj.toString().length() > 100) { // 길이가 넘칠까 두려우니...
					errMsg = errObj.toString().substring(0, 100);
				}else{
					errMsg = errObj.toString();
				}
				comUserAccesLog.setStatCont(errMsg);
			}
		}		
		
		if(exception != null){
        	String msg = exception.getMessage(); // 정적분석 방어용
        	if(msg != null) {
        		msg = msg.replace("\n", "");
        	}
			comUserAccesLog.setUserLogRsltCd(UserLogRsltCd.ERROR);
			comUserAccesLog.setDtlCont("Exception Messgae :  " + msg +"\n" + RequestUtils.getQueryString(request));
		}else{
			comUserAccesLog.setUserLogRsltCd(UserLogRsltCd.SUCCESS);
			String queryString = RequestUtils.getQueryString(request);
			if(queryString != null && queryString.length() > 2000){
				queryString = queryString.substring(0, 2000);
			}
			comUserAccesLog.setDtlCont(queryString);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			Object user = auth.getPrincipal();
			if(user instanceof SecurityUser ){ // 사용자 정보가 있다면 말이야... 
				comUserAccesLog.setReqUserMngtSeq(((SecurityUser)user).getComUserMngt().getUserMngtSeq());
				comUserAccesLog.setUpAccesLogSeq((Long)request.getAttribute(SecurityConstant.UP_LOG_SEQ));
			}
		}
		
		HttpSession session = request.getSession(false);		
		if(session != null){
			comUserAccesLog.setSessId(session.getId());
		}
		
		userService.writeUserAccessLog(comUserAccesLog);
		*/
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
		// 할 일이 없음... 
	}
}
