package kr.co.basedevice.corebase.quartz.component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class StatusResponse {
	private int numOfAllJobs;
	private int numOfGroups;
	private int numOfRunningJobs;
	private List<JobResponse> jobs;
}