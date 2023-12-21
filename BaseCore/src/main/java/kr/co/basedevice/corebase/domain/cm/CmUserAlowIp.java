package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.id.Tsid;
import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_USER_ALOW_IP")
//@SequenceGenerator(name = "SEQGEN_CM_USER_ALOW_IP", sequenceName = "SEQ_CM_USER_ALOW_IP", initialValue = 1000, allocationSize = 1)
public class CmUserAlowIp extends BaseEntity implements Serializable {	

	private static final long serialVersionUID = 2007929684016838318L;

	@Id
	@Tsid//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_USER_ALOW_IP")
	@Column(name = "USER_ALOW_IP_SEQ", nullable = false)
	private Long userAlowIpSeq;
	
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;
	
	@Column(name = "ALOW_IP", length = 30, nullable = false)
	private String alowIp;
	
	@Column(name = "ALOW_IP_DESC", length = 2000)
	private String alowIpDesc;

	@Column(name = "DEL_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "USER_SEQ", updatable = false, insertable = false)
	private CmUser cmUser;
}
