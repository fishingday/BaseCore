package kr.co.basedevice.corebase.dto.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.domain.cm.CmUserAlowIp;
import kr.co.basedevice.corebase.domain.code.UserStatCd;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "cmRoleList")
@NoArgsConstructor
public class UserInfoDto {
	private int num;
	
	private Long userSeq;

	private String loginId;

	private String userNm;

	private String userTelNo;

	private Integer loginFailCnt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime loginDt;

	private String lastLoginIp;

	private UserStatCd userStatCd;

	private LocalDate acuntExpDt;

	private Yn delYn;
	
	private Long orgSeq;
	
	private List<CmRole> cmRoleList;
	
	private List<CmOrg> cmOrgList;
	
	private List<CmUserAlowIp> cmUserAlowIpList;
	
	public void setCmUser(CmUser cmUser) {
		this.userSeq = cmUser.getUserSeq();
		this.loginId = cmUser.getLoginId();
		this.userNm = cmUser.getUserNm();
		this.userTelNo = cmUser.getUserTelNo();
		
		this.loginDt = cmUser.getLoginDt();
		this.lastLoginIp = cmUser.getLastLoginIp();
		this.acuntExpDt = cmUser.getAcuntExpDt();
	}
	
	public String getRoleNames() {
		if(this.cmRoleList != null && !cmRoleList.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(CmRole cmRole : this.cmRoleList) {
				if(!sb.isEmpty()) {
					sb.append(",");
				}
				sb.append(cmRole.getRoleNm());
			}
			return sb.toString();			
		}else {
			return null;
		}
	}
	
	public String getOrgNames() {
		if(this.cmOrgList != null && !cmOrgList.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(CmOrg cmOrg : this.cmOrgList) {
				if(!sb.isEmpty()) {
					sb.append(",");
				}
				sb.append(cmOrg.getOrgNm());
			}
			return sb.toString();			
		}else {
			return null;
		}
	}

}
