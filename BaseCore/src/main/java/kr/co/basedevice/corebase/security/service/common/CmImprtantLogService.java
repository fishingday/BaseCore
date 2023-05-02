package kr.co.basedevice.corebase.security.service.common;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.domain.cm.CmImprtantLog;
import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import kr.co.basedevice.corebase.repository.cm.CmImprtantLogRepository;
import kr.co.basedevice.corebase.util.RequestUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CmImprtantLogService {

	final private CmImprtantLogRepository cmImprtantLogRepository;
	
	
	public void logging(HttpServletRequest request, WriteMakrCd writeMakrCd, Long userSeq) {
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
        log.setAcceptCharset(request.getHeader("accept-charset"));        
        String param = request.getQueryString();
        if(param != null && param.length() > 2000) {
        	param = param.substring(0, 2000);
        }
        log.setParam(param);
        log.setCreatorDt(LocalDateTime.now());
        
        cmImprtantLogRepository.save(log);
	}
}
