package kr.co.basedevice.corebase.dto.todo;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.basedevice.corebase.domain.td.TdWork;
import lombok.Data;

@Data
public class SettleInfoDto {
	
	private Long setleSeq;
	
	private Long workerSeq;
	
	private Long acountSeq;
	
	private Integer totalSetlePoint;
	
	private String setleDesc;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime setleDt;
	
	private String workerNm;
	
	private String loginId;
	
	private List<TdWork> listTdWork;	
}
