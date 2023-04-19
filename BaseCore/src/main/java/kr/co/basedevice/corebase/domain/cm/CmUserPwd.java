package kr.co.basedevice.corebase.domain.cm;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_USER_PWD")
@SequenceGenerator(name = "SEQGEN_CM_USER_PWD", sequenceName = "SEQ_CM_USER_PWD", initialValue = 1000, allocationSize = 1)
public class CmUserPwd {

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
	
	@Column(name = "DEL_YN", length = 1)
	private String delYn;

	@Column(name = "CREATOR_SEQ", updatable = false)
	private Long creatorSeq;

	@Column(name = "CRE_DT", updatable = false)
	private LocalDateTime creDt;

	@Column(name = "UPDATOR_SEQ")
	private Long updatorSeq;

	@Column(name = "UPD_DT")
	private LocalDateTime updDt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "USER_SEQ", updatable = false, insertable = false)
	private CmUser cmUser;
}
