package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.basedevice.corebase.domain.BaseEntity;
import kr.co.basedevice.corebase.domain.code.SendMediaTypCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CM_NOTI_USER_MAP")
public class CmNotiUserMap  extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -3683964229919126606L;

	@Id
	@Tsid
	@Column(name = "NOTI_USER_MAP_SEQ", nullable = false)
	private Long menuSeq;
	
	@Column(name = "USER_SEQ", nullable = false)
	private Long userSeq;
	
	@Column(name = "NOTI_SEQ", nullable = false)
	private Long notiSeq;
	
	@Column(name = "SEND_MEDIA_TYP_CD", length = 35, nullable = false)
	@Enumerated(EnumType.STRING)
	private SendMediaTypCd sendMediaTypCd;
	
	@Column(name = "VIEW_CNT", nullable = false)
	private Integer viewCnt;
	
	@Column(name = "CLOSE_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn closeYn;
	
	@Column(name = "QRY_DT")
	private LocalDateTime qryDt;

	@Column(name = "DEL_YN", nullable = false, columnDefinition = "VARCHAR(1)")
	@Enumerated(EnumType.STRING)
	private Yn delYn;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "NOTI_SEQ", updatable = false, insertable = false)
	private CmNoti cmNoti;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "USER_SEQ", updatable = false, insertable = false)
	private CmUser cmUser;

}
