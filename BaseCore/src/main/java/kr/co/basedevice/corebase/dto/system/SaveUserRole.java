package kr.co.basedevice.corebase.dto.system;

import java.util.List;

import lombok.Data;

@Data
public class SaveUserRole {
	
	private List<Long> userSeqList; 
	private List<Long> roleSeqList;
}
