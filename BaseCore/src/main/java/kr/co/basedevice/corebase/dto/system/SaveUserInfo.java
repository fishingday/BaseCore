package kr.co.basedevice.corebase.dto.system;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import kr.co.basedevice.corebase.domain.code.UserStatCd;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SaveUserInfo {
	
	private Long userSeq;
	private String loginId;
	private String userNm;
	private String userTelNo;
	private Long orgSeq;
	private Integer loginFailCnt;
	private UserStatCd userStatCd;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate acuntExpDt;
	
	private List<Long> roleSeqList;
}
