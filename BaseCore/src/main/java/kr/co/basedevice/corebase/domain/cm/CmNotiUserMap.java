package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.time.LocalDateTime;

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

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_NOTI_USER_MAP")
@SequenceGenerator(name = "SEQGEN_CM_NOTI_USER_MAP", sequenceName = "SEQ_CM_NOTI_USER_MAP", initialValue = 1000, allocationSize = 1)
public class CmNotiUserMap implements Serializable{

	private static final long serialVersionUID = -3683964229919126606L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_NOTI_USER_MAP")
	@Column(name = "NOTI_USER_MAP_SEQ", nullable = false)
	private Long menuSeq;
	
	@Column(name = "USER_SEQ")
	private Long userSeq;
	
	@Column(name = "NOTI_SEQ")
	private Long notiSeq;
	
	@Column(name = "SEND_MEDIA_TYP_CD", length = 1, nullable = false)
	private String sendMediaTypCd;
	
	@Column(name = "VIEW_CNT", nullable = false)
	private Integer viewCnt;
	
	@Column(name = "CLOSE_YN", length = 1, nullable = false)
	private String closeYn;
	
	@Column(name = "QRY_DT")
	private LocalDateTime qryDt;

	@Column(name = "DEL_YN", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Yn delYn;

	@Column(name = "CREATOR_SEQ", updatable = false, nullable = false)
	private Long creatorSeq;

	@Column(name = "CRE_DT", updatable = false, nullable = false)
	private LocalDateTime creDt;

	@Column(name = "UPDATOR_SEQ", nullable = false)
	private Long updatorSeq;

	@Column(name = "UPD_DT", nullable = false)
	private LocalDateTime updDt;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "NOTI_SEQ", updatable = false, insertable = false)
	private CmNoti cmNoti;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "USER_SEQ", updatable = false, insertable = false)
	private CmUser cmUser;

}
