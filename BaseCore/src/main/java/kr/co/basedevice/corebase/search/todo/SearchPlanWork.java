package kr.co.basedevice.corebase.search.todo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import kr.co.basedevice.corebase.domain.code.WorkStatCd;
import lombok.Data;

@Data
public class SearchPlanWork {
	
	private Long checkerSeq;

	private String workTitl;
	private String workerNm;
	private WorkStatCd workStatCd;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate workDate;
	private LocalDateTime workBeginDt;
	private LocalDateTime workEndDt;

	private String order;
	private String sort;
}
