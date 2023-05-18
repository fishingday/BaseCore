package kr.co.basedevice.corebase.domain.cm;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_CRITICAL_LOG")
@SequenceGenerator(name = "SEQGEN_CM_CRITICAL_LOG", sequenceName = "SEQ_CM_CRITICAL_LOG", initialValue = 1000, allocationSize = 1)
public class CmCriticalLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_CRITICAL_LOG")
	@Column(name = "CRITICAL_LOG_SEQ", nullable = false)
	private Long criticalLogSeq;
	
	// 기록 제조 코드
	@Column(name = "WRITE_MAKR_CD", length = 255, nullable = false)
	@Enumerated(EnumType.STRING)
	private WriteMakrCd writeMakrCd;
	
	@Column(name = "REQ_IP", length = 20, nullable = false)
	private String reqIp;
	
	@Column(name = "REQ_URI", length = 2000, nullable = false)
	private String reqUri;	
	
	@Column(name = "PARAM", length = 4000)
	private String param;
	
	@Column(name = "SESS_ID", length = 50)
	private String sessId;
	
	@Column(name = "USER_SEQ")
	private Long userSeq;
	
	@Column(name = "USER_AGENT", length = 255)
	private String userAgent;
	
	@Column(name = "ACCEPT", length = 255)
	private String accept;
	
	@Column(name = "REFERER", length = 255)
	private String referer;

	@Column(name = "ACCEPT_ENCODING", length = 255)
	private String acceptEncoding;
	
	@Column(name = "ACCEPT_LANGUAGE", length = 255)
	private String acceptLanguage;
	
	@Column(name = "ACCEPT_CHARSET", length = 255)
	private String acceptCharset;
		
	@Column(name = "HTTP_STATUS", length = 35)
	@Enumerated(EnumType.STRING)
	private HttpStatus HttpStatus;

	@Column(name = "CRE_DT", nullable = false, updatable = false)
	private LocalDateTime creDt;
}
