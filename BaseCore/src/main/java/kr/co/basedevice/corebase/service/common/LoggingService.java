package kr.co.basedevice.corebase.service.common;

import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmCriticalLog;
import kr.co.basedevice.corebase.domain.cm.CmRoleChgLog;
import kr.co.basedevice.corebase.domain.cm.CmUserAccesLog;
import kr.co.basedevice.corebase.domain.code.AccesLogTypCd;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.repository.cm.CmCriticalLogRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleChgLogRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserAccesLogRepository;
import kr.co.basedevice.corebase.util.RequestUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class LoggingService {

	final private CmCriticalLogRepository criticalLogRepository;
	final private CmUserAccesLogRepository accesLogRepository;
	final private CmRoleChgLogRepository chgLogRepository;
	
	public void writeCriticalLog(HttpServletRequest request, WriteMakrCd writeMakrCd, Long userSeq) {
//		CmCriticalLog log = new CmCriticalLog();
//        
//        log.setWriteMakrCd(writeMakrCd);
//        log.setReqIp(RequestUtil.getClientIp(request));
//        log.setSessId(request.getSession().getId());
//        log.setUserSeq(userSeq);        
//        log.setUserAgent(request.getHeader("user-agent"));
//        log.setAccept(request.getHeader("accept"));
//        log.setReferer(request.getHeader("referer"));
//        log.setAcceptEncoding(request.getHeader("accept-encoding"));
//        log.setAcceptLanguage(request.getHeader("accept-language"));
//        log.setAcceptCharset(request.getCharacterEncoding());
//        log.setReqUri(request.getRequestURI());
//        String param = request.getQueryString();
//        if(param != null && param.length() > 2000) {
//        	param = param.substring(0, 2000);
//        }
//        log.setParam(param);        
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        if(status != null) {
//        	int statusCode = Integer.valueOf(status.toString());
//        	log.setHttpStatus(HttpStatus.valueOf(statusCode));
//        }
//        log.setCreDt(LocalDateTime.now());
//        
//        criticalLogRepository.save(log);
	}
	
	public void writeAccessLog(HttpServletRequest request, HttpServletResponse response, AccesLogTypCd accesLogTypCd, Long userSeq) {
//		CmUserAccesLog log = new CmUserAccesLog();
//		log.setReqUrl(request.getRequestURI());
//		log.setReqIp(RequestUtil.getClientIp(request));
//		log.setHttpMethod(HttpMethod.valueOf(request.getMethod()));
//		String param = request.getQueryString();
//        if(param != null && param.length() > 2000) {
//        	param = param.substring(0, 2000);
//        }
//        log.setParam(param);
//        log.setSessId(request.getSession().getId());
//        log.setAccesLogTypCd(accesLogTypCd);
//        log.setUserAgent(request.getHeader("user-agent"));
//        log.setAccept(request.getHeader("accept"));
//        log.setReferer(request.getHeader("referer"));
//        log.setAcceptEncoding(request.getHeader("accept-encoding"));
//        log.setAcceptLanguage(request.getHeader("accept-language"));
//        log.setAcceptCharset(request.getCharacterEncoding());
//        log.setCreDt(LocalDateTime.now());
//        
//        int statusCode = response.getStatus();
//        log.setHttpStatus(HttpStatus.valueOf(statusCode));
//        
//		accesLogRepository.save(log);
	}
	
	public void writeRoleChgLog(CmRoleChgLog chgLog) {
//		chgLog.setRoleChgDt(LocalDateTime.now());
//		chgLogRepository.save(chgLog);
	}
	
}
