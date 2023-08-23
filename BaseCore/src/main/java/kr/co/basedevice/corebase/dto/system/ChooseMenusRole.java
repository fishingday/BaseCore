package kr.co.basedevice.corebase.dto.system;

import java.util.List;

import lombok.Data;

@Data
public class ChooseMenusRole {
	private Long roleSeq;
	
	private List<Long> menuSeqList;
}
