package kr.co.basedevice.corebase.dto.system;

import java.util.List;

import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.exception.OperationException;
import lombok.Data;

@Data
public class OrgInfoDto {
	private Long orgSeq;
	
	private Long upOrgSeq;
	
	private String orgNm;
	
	private String orgDesc;
	
	private Integer prntOrd;
	
	private OrgInfoDto parentOrgInfo;
	
	private List<OrgInfoDto> subOrgInfoList;
	
	public OrgInfoDto(CmOrg cmOrg) {
		if(cmOrg == null) {
			throw new OperationException("올바른 조직정보가 아닙니다.");
		}
		
		this.orgSeq = cmOrg.getOrgSeq();		
		this.upOrgSeq = cmOrg.getUpOrgSeq();		
		this.orgNm = cmOrg.getOrgNm();		
		this.orgDesc = cmOrg.getOrgDesc();		
		this.prntOrd = cmOrg.getPrntOrd();
	}
	
	public String getFullOrgNm() {
		StringBuilder sb = new StringBuilder();
		
		if(this.parentOrgInfo == null) {
			sb.append(this.orgNm);
		}else {
			if(parentOrgInfo.parentOrgInfo != null) {
				String upOrgNm = parentOrgInfo.getFullOrgNm();
				sb.append(upOrgNm).append(">").append(this.orgNm);
			}else {
				sb.append(parentOrgInfo.getOrgNm()).append(">").append(this.orgNm);
			}
		}
		
		return sb.toString();
	}
}
