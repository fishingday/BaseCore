package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_USER_ALOW_IP")
@SequenceGenerator(name = "SEQGEN_CM_USER_ALOW_IP", sequenceName = "SEQ_CM_USER_ALOW_IP", initialValue = 1000, allocationSize = 1)
public class CmUserAlowIp extends BaseEntity implements Serializable {	

	private static final long serialVersionUID = 2007929684016838318L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_USER_ALOW_IP")
	@Column(name = "USER_ALOW_IP_SEQ", nullable = false)
	private Long userAlowIpSeq;
	
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;
	
	@Column(name = "ALOW_IP", length = 30, nullable = false)
	private String alowIp;
	
	@Column(name = "ALOW_IP_DESC", length = 2000)
	private String alowIpDesc;

	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "USER_SEQ", updatable = false, insertable = false)
	private CmUser cmUser;
}
