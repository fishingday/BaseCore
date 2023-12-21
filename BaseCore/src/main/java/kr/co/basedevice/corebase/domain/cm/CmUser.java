package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.id.Tsid;
import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.UserStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

//@Convert(converter = CryptoConverter.class, attributeName = "userTelNo") // 클래스 레벨에 적용
@Getter
@Setter
@Entity
@Table(name = "CM_USER", uniqueConstraints = {@UniqueConstraint(name = "UK_CM_USER_LOGIN_ID", columnNames = { "LOGIN_ID" }) })
//@SequenceGenerator(name = "SEQGEN_CM_USER", sequenceName = "SEQ_CM_USER", initialValue = 1000, allocationSize = 1)
public class CmUser extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 8563132967658504534L;
		
	@Id
	@Tsid//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_USER")
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;

	@Column(name = "LOGIN_ID", nullable = false, unique = true)
	private String loginId;
	
	@Column(name = "USER_NM", nullable = false, length = 30)
	private String userNm;

	//@Convert(converter = CryptoConverter.class)
	@Column(name = "USER_TEL_NO", length = 20)
	private String userTelNo;

	@Column(name = "LOGIN_FAIL_CNT", nullable = false)
	private Integer loginFailCnt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@Column(name = "LOGIN_DT")
	private LocalDateTime loginDt;

	@Column(name = "LAST_LOGIN_IP", length = 23)
	private String lastLoginIp;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "ACUNT_EXP_DT", nullable = false, length = 8)
	private LocalDate acuntExpDt;

	@Column(name = "USER_STAT_CD", nullable = false, length = 35)
	@Enumerated(EnumType.STRING)
	private UserStatCd userStatCd;
	
	@Column(name = "DEL_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn delYn;
	
	@OneToMany(mappedBy = "cmUser", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmUserRoleMap> cmUserRoleMapList = new ArrayList<>(1);

	@OneToMany(mappedBy = "cmUser", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmNotiUserMap> cmNotiUserMapList = new ArrayList<>(1);
	
	@OneToMany(mappedBy = "cmUser", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmUserAlowIp> cmUserAlowIpList = new ArrayList<>(1);
	
	@OneToMany(mappedBy = "cmUser", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmOrgUserMap> cmOrgUserMapList = new ArrayList<>(1);
	
}
