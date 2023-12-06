package kr.co.basedevice.corebase.dto.todo;

import java.util.List;

import lombok.Data;

@Data
public class SettleDataDto {
	private String workerNm;
	private String workerSeq;
	private String sumPoint;
	
	private List<Long> listWorkSeq;
}
