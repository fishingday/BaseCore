package kr.co.basedevice.corebase.dto.system;

import java.util.List;

import lombok.Data;

@Data
public class SaveUserPwd {
	private List<Long> userSeqList; 
	private String chgPwd;
}
