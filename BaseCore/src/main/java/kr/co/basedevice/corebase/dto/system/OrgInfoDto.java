package kr.co.basedevice.corebase.dto.system;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.basedevice.corebase.domain.cm.CmOrg;
import kr.co.basedevice.corebase.exception.OperationException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "parentOrgInfo")
@Data
public class OrgInfoDto implements Comparable<OrgInfoDto>{
	private Long orgSeq;
	
	private Long upOrgSeq;
	
	private String orgNm;
	
	private String orgDesc;
	
	private Integer prntOrd;
	
	private OrgInfoDto parentOrgInfo;
	
	@JsonIgnore
	private List<OrgInfoDto> subOrgInfoList = new ArrayList<>(1);
	
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
	
	public String getSiblingOrder() {
		StringBuilder sb = new StringBuilder();
		
		if(parentOrgInfo == null) {
			sb.append(this.prntOrd);
		}else {
			if(parentOrgInfo.parentOrgInfo != null) {
				String siblingOrder = parentOrgInfo.getSiblingOrder();
				sb.append(siblingOrder).append(">").append(this.prntOrd);
			}else {
				sb.append(parentOrgInfo.getSiblingOrder()).append(">").append(this.prntOrd);
			}
		}
		
		return sb.toString();
	}

	@Override
	public int compareTo(OrgInfoDto target) {
        return this.getSiblingOrder().compareTo(target.getSiblingOrder());
	}
}
