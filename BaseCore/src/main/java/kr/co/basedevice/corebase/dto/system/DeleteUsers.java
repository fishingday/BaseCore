package kr.co.basedevice.corebase.dto.system;

import java.util.List;

import lombok.Data;

@Data
public class DeleteUsers {	
	private List<Long> userSeqList;
	private String reason;
}
