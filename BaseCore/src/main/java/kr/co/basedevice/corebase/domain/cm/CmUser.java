package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_USER", uniqueConstraints = {@UniqueConstraint(name = "UK_CM_USER_LOGIN_ID", columnNames = { "LOGIN_ID" }) })
@SequenceGenerator(name = "SEQGEN_CM_USER", sequenceName = "SEQ_CM_USER", initialValue = 1000, allocationSize = 1)
public class CmUser implements Serializable{

	private static final long serialVersionUID = 8563132967658504534L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_USER")
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;

	@Column(name = "LOGIN_ID", nullable = false, unique = true)
	private String loginId;

	@Column(name = "USER_NM", length = 30)
	private String userNm;

	@Column(name = "USER_TEL_NO", length = 20)
	private String userTelNo;

	@Column(name = "LOGIN_FAIL_CNT")
	private Integer loginFailCnt;

	@Column(name = "LOGIN_DT")
	private LocalDateTime loginDt;

	@Column(name = "LAST_LOGIN_IP", length = 23)
	private String lastLoginIp;

	@Column(name = "USER_STAT_CD", length = 35)
	private String userStatCd;

	@Column(name = "ACUNT_EXP_DT", length = 8)
	private String acuntExpDt;

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
	
	@OneToMany(mappedBy = "cmUser", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmUserRoleMap> cmUserRoleMapList = new ArrayList<>(1);

	@OneToMany(mappedBy = "cmUser", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmNotiUserMap> cmNotiUserMapList = new ArrayList<>(1);
	
	@OneToMany(mappedBy = "cmUser", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmUserAlowIp> cmUserAlowIpList = new ArrayList<>(1);
}
