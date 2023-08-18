package kr.co.basedevice.corebase.dto.system;

import java.util.List;

import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "cmRoleList")
@NoArgsConstructor
public class MenuInfoDto {
	private Long menuSeq;

	private Long upMenuSeq;
	
	private String menuPath;
	
	private String menuNm;
	
	private String menuDesc;

	private String iConInfo;
	
	private Yn prntYn;
		
	private Yn cmScrenYn;
	
	private Integer prntOrd;
	
	private boolean leaf;
	
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
