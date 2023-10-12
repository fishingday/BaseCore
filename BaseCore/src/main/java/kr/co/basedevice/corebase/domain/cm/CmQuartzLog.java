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

import org.springframework.data.annotation.CreatedDate;

import kr.co.basedevice.corebase.domain.code.QuartzLogTypCd;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@Entity
@Table(name = "CM_QUARTZ_LOG")
@SequenceGenerator(name = "SEQGEN_CM_QUARTZ_LOG", sequenceName = "SEQ_CM_QUARTZ_LOG", initialValue = 1, allocationSize = 1)
public class CmQuartzLog implements Serializable{

	private static final long serialVersionUID = -185494336443799876L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQGEN_CM_QUARTZ_LOG")
	@Column(name = "QUARTZ_LOG_SEQ", nullable = false)
	private Long quartzlogSeq;

	@CreatedDate
	@Column(name = "CRE_DT", nullable = false, updatable = false)
	private LocalDateTime creDt;
	
	@Column(name = "QUARTZ_LOG_TYP_CD", length = 1, nullable = false)
	@Enumerated(EnumType.STRING)
	private QuartzLogTypCd quartzLogTypCd;
	
	@Column(name = "KEY", length = 400)
	private String key;
	
	@Column(name = "SHORT_DATA", length = 400)
	private String shortData;
		
	public CmQuartzLog(QuartzLogTypCd quartzLogTypCd, String key, String shortData) {
		this.quartzLogTypCd = quartzLogTypCd;
		this.key = key;
		this.shortData = shortData;
	}
}
