package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.time.LocalDate;

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

import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_USER_PWD")
@SequenceGenerator(name = "SEQGEN_CM_USER_PWD", sequenceName = "SEQ_CM_USER_PWD", initialValue = 1000, allocationSize = 1)
public class CmUserPwd extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8303293010611426407L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_MENU_DTL")
	@Column(name = "USER_PWD_SEQ", nullable = false)
	private Long userPwdSeq;
	
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;
	
	@Column(name = "USER_PWD", length = 256, nullable = false)
	private String userPwd;

	@Column(name = "PWD_EXP_DT")
	private LocalDate pwdExpDt;
	
	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "USER_SEQ", updatable = false, insertable = false)
	private CmUser cmUser;
}
