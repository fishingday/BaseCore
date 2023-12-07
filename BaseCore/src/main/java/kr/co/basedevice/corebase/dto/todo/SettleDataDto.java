package kr.co.basedevice.corebase.dto.todo;

import java.util.List;

import lombok.Data;

@Data
public class SettleDataDto {
	private String workerNm;
	private Long workerSeq;
	private Integer sumPoint;
	
	private List<Long> listWorkSeq;
}
