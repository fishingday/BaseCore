package kr.co.basedevice.corebase.dto.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import kr.co.basedevice.corebase.domain.cm.CmRole;
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

	private LocalDateTime loginDt;

	private String lastLoginIp;

	private UserStatCd userStatCd;

	private LocalDate acuntExpDt;

	private Yn delYn;
	
	private List<CmRole> cmRoleList;
	
	
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

}
