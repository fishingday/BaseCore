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

import kr.co.basedevice.corebase.domain.code.WriteMakrCd;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_IMPORTANT_LOG")
@SequenceGenerator(name = "SEQGEN_CM_IMPORTANT_LOG", sequenceName = "SEQ_CM_IMPORTANT_LOG", initialValue = 1000, allocationSize = 1)
public class CmImprtantLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_IMPORTANT_LOG")
	@Column(name = "IMPORTANT_LOG_SEQ", nullable = false)
	private Long immportantLogSeq;
	
	// 기록 제조 코드
	@Column(name = "WRITE_MAKR_CD", length = 255, nullable = false)
	@Enumerated(EnumType.STRING)
	private WriteMakrCd writeMakrCd;
	
	@Column(name = "REQ_IP", length = 20, nullable = false)
	private String reqIp;
	
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
	
	@Column(name = "PARAM", length = 4000)
	private String param;

	@Column(name = "CRE_DT", nullable = false, updatable = false)
	private LocalDateTime creDt;
}
