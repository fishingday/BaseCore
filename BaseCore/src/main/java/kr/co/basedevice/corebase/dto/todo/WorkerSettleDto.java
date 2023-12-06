package kr.co.basedevice.corebase.dto.todo;

import java.util.List;

import lombok.Data;

@Data
public class WorkerSettleDto {
	private Long acountSeq;
	private List<SettleDataDto> listSettleData; 
}
