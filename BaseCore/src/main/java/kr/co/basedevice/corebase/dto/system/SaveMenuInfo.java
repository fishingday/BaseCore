package kr.co.basedevice.corebase.dto.system;

import java.util.List;

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Data;

@Data
public class SaveMenuInfo {
	
	private Long menuSeq;
	
	private Long upMenuSeq;
	
	private String menuNm;
	
	private String menuPath;
	
	private String menuDesc;

	private String iConInfo;
	
	private Yn prntYn;
		
	private Yn cmScrenYn;
	
	private Integer prntOrd;
	
	private List<Long> roleSeqList;
	
}
