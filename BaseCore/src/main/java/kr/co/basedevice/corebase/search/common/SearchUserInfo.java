package kr.co.basedevice.corebase.search.common;

import lombok.Data;

/**
 * 사용자 관리 조회 조건
 * 
 * @author fishingday
 *
 */
@Data
public class SearchUserInfo {

	private String loginId;
	private String userNm;
	private String userTelNo;
	private Long roleSeq;
	private Long orgSeq;
	
	private String order;
	private String sort;
}
