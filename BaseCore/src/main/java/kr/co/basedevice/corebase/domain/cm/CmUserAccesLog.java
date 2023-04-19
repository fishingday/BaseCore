package kr.co.basedevice.corebase.domain.cm;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_USER_ACCES_LOG")
@SequenceGenerator(name = "SEQGEN_CM_USER_ACCES_LOG", sequenceName = "SEQ_CM_USER_ACCES_LOG", initialValue = 1000, allocationSize = 1)
public class CmUserAccesLog {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_USER_ACCES_LOG")
	@Column(name = "USER_ACCES_LOG_SEQ", nullable = false)
	private Long userAccesLogSeq;
	
	@Column(name = "REQ_URL", length = 255, nullable = false)
	private String reqUrl;
	
	@Column(name = "REQ_IP", length = 20, nullable = false)
	private String reqIp;
	
	@Column(name = "HTTP_METHOD_CD", length = 255, nullable = false)
	private String httpMethodCd;
	
	@Column(name = "REQ_CONT", length = 2000)
	private String reqCont;	
	
	@Column(name = "SESS_ID", length = 50)
	private String sessId;
	
	@Column(name = "ACCES_LOG_TYP_CD", length = 255)
	private String accesLogTypCd;
	
	@Column(name = "USER_AGENT", length = 255)
	private String userAgent;
	
	@Column(name = "REFERER", length = 255)
	private String referer;
	
	@Column(name = "ACCEPT", length = 128)
	private String accept;
	
	@Column(name = "ACCEPT_CHARSET", length = 128)
	private String acceptCharset;
	
	@Column(name = "HTTP_STAT_NO", length = 20)
	private String httpStatNo;

	@Column(name = "CRE_DT", updatable = false)
	private LocalDateTime creDt;
}
