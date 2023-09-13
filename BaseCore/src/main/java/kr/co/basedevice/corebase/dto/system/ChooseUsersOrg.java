package kr.co.basedevice.corebase.dto.system;

import java.util.List;

import lombok.Data;

@Data
public class ChooseUsersOrg {

	private Long orgSeq;
	
	private List<Long> userSeqList;

}
