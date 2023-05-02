package kr.co.basedevice.corebase.service.common;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmImprtantLog;
import kr.co.basedevice.corebase.domain.cm.CmRoleChgLog;
import kr.co.basedevice.corebase.domain.cm.CmUserAccesLog;
import kr.co.basedevice.corebase.domain.code.AccesLogTypCd;
import kr.co.basedevice.corebase.domain.code.HttpMethodCd;
import kr.co.basedevice.corebase.domain.code.RoleChgCd;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.repository.cm.CmImprtantLogRepository;
import kr.co.basedevice.corebase.repository.cm.CmRoleChgLogRepository;
import kr.co.basedevice.corebase.repository.cm.CmUserAccesLogRepository;
import kr.co.basedevice.corebase.util.RequestUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoggingService {

	final private CmImprtantLogRepository imprtantLogRepository;
	final private CmUserAccesLogRepository accesLogRepository;
	final private CmRoleChgLogRepository chgLogRepository;
	
	public void writeImportantLog(HttpServletRequest request, WriteMakrCd writeMakrCd, Long userSeq) {
		CmImprtantLog log = new CmImprtantLog();
        
        log.setWriteMakrCd(writeMakrCd);
        log.setReqIp(RequestUtil.getClientIp(request));
        log.setSessId(request.getSession().getId());
        log.setUserSeq(userSeq);        
        log.setUserAgent(request.getHeader("user-agent"));
        log.setAccept(request.getHeader("accept"));
        log.setReferer(request.getHeader("referer"));
        log.setAcceptEncoding(request.getHeader("accept-encoding"));
        log.setAcceptLanguage(request.getHeader("accept-language"));
        log.setAcceptCharset(request.getCharacterEncoding());        
        String param = request.getQueryString();
        if(param != null && param.length() > 2000) {
        	param = param.substring(0, 2000);
        }
        log.setParam(param);
        log.setCreDt(LocalDateTime.now());
        
        imprtantLogRepository.save(log);
	}
	
	public void writeRoleChgLog(HttpServletRequest request, AccesLogTypCd accesLogTypCd, Long userSeq) {
		CmUserAccesLog log = new CmUserAccesLog();
		log.setReqUrl(request.getRequestURI());
		log.setReqIp(RequestUtil.getClientIp(request));
		log.setHttpMethodCd(HttpMethodCd.valueOf(request.getMethod().toUpperCase()));
		String param = request.getQueryString();
        if(param != null && param.length() > 2000) {
        	param = param.substring(0, 2000);
        }
        log.setParam(param);
        log.setSessId(request.getSession().getId());
        log.setAccesLogTypCd(accesLogTypCd);
        log.setUserAgent(request.getHeader("user-agent"));
        log.setAccept(request.getHeader("accept"));
        log.setReferer(request.getHeader("referer"));
        log.setAcceptCharset(request.getCharacterEncoding());
        log.setCreDt(LocalDateTime.now());
		
		accesLogRepository.save(log);
	}
	
	public void writeAccessLog(CmRoleChgLog chgLog) {
		chgLog.setRoleChgDt(LocalDateTime.now());
		chgLogRepository.save(chgLog);
	}
	
}
