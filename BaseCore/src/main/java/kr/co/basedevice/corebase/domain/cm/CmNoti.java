package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.code.NotiSendGrdCd;
import kr.co.basedevice.corebase.domain.code.SendMediaTypCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_NOTI")
@SequenceGenerator(name = "SEQGEN_CM_NOTI", sequenceName = "SEQ_CM_NOTI", initialValue = 1000, allocationSize = 1)
public class CmNoti implements Serializable{

	private static final long serialVersionUID = -4615576439921001498L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_MENU")
	@Column(name = "NOTI_SEQ", nullable = false)
	private Long notiSeq;
	
	@Column(name = "NOTI_TITL", length = 200, nullable = false)
	private String menuNm;
	
	@Column(name = "NOTI_CONT", length = 2000, nullable = false)
	private String notiCont;

	@Column(name = "NOTI_LNK_ADDR", length = 300)
	private String notiLnkAddr;

	@Column(name = "NOTI_SEND_GRD_CD", length = 35, nullable = false)
	@Enumerated(EnumType.STRING)
	private NotiSendGrdCd notiSendGrdCd;

	@Column(name = "NOTI_SEND_DT", length = 14, nullable = false)
	private String notiSendDt;

	@Column(name = "NOTI_END_DT", length = 14, nullable = false)
	private String notiEndDt;

	@Column(name = "SEND_MEDIA_TYP_CD", length = 35, nullable = false)
	@Enumerated(EnumType.STRING)
	private SendMediaTypCd sendMediaTypCd;	

	@Column(name = "SEND_YN", length = 1, nullable = false)
	@Enumerated(EnumType.STRING)
	private Yn sendYn;

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

	@OneToMany(mappedBy = "cmNoti", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CmNotiUserMap> cmNotiUserMapList = new ArrayList<>(1);
}
