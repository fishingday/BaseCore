package kr.co.basedevice.corebase.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass 
@EntityListeners(AuditingEntityListener.class) 
public abstract class BaseEntity {
	
	@CreatedBy
	@Column(name = "CREATOR_SEQ", nullable = false, updatable = false)
	private Long creatorSeq;

	@CreatedDate
	@Column(name = "CRE_DT", nullable = false, updatable = false)
	private LocalDateTime creDt;

	@LastModifiedBy
	@Column(name = "UPDATOR_SEQ", nullable = false)
	private Long updatorSeq;

	@LastModifiedDate
	@Column(name = "UPD_DT", nullable = false)
	private LocalDateTime updDt;
}
