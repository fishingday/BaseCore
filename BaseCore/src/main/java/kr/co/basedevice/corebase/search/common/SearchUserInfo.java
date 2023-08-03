package kr.co.basedevice.corebase.search.common;

import kr.co.basedevice.corebase.domain.code.RoleCd;
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
	private String roleCd;
	
	private String order;
	private String sort;
	
	public RoleCd getRoleCd() {
		return this.roleCd != null ? RoleCd.valueOf(this.roleCd) : null;
	}		
}
