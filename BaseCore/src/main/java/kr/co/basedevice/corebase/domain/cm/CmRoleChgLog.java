package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
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

import kr.co.basedevice.corebase.domain.code.RoleChgCd;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_ROLE_CHG_LOG")
@SequenceGenerator(name = "SEQGEN_CM_ROLE_CHG_LOG", sequenceName = "SEQ_CM_ROLE_CHG_LOG", initialValue = 1000, allocationSize = 1)
public class CmRoleChgLog implements Serializable {
	
	private static final long serialVersionUID = 3245653795178558196L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_ROLE_CHG_LOG")
	@Column(name = "ROLE_CHG_LOG_SEQ", nullable = false)
	private Long roleChgLogSeq;

	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;
	
	@Column(name = "ROLE_SEQ", nullable = false)
	private Long roleSeq;
	
	@Column(name = "ROLE_CHG_CONT", length = 2000, nullable = false)
	private String roleChgCont;
	
	@Column(name = "ROLE_CHG_REASON", length = 2000, nullable = false)
	private String roleChgReason;
		
	@Column(name = "ROLE_CHG_CD", length = 35, nullable = false)
	@Enumerated(EnumType.STRING)
	private RoleChgCd roleChgCd;
	
	@Column(name = "EXCUTOR_SEQ", nullable = false)
	private Long excutorSeq;
	
	@Column(name = "ROLE_CHG_DT")
	private LocalDateTime roleChgDt;
}
