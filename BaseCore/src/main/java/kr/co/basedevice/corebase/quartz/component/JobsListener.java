package kr.co.basedevice.corebase.quartz.component;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobsListener implements JobListener{

	@Override
	public String getName() {
		return "GlobalJob";
	}
	
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		JobKey jobKey = context.getJobDetail().getKey();
		log.info("############################################### Job Begin.");
		log.info("jobToBeExecuted (수행되기전):: jobKey : {}", jobKey);
	}
 
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		JobKey jobKey = context.getJobDetail().getKey();
		log.info("jobExecutionVetoed (중단):: jobKey : {}", jobKey);
	}
 
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		JobKey jobKey = context.getJobDetail().getKey();
		log.info("jobWasExecuted (완료후) :: jobKey : {}", jobKey);
		log.info("############################################### Job End.");
	}
}
