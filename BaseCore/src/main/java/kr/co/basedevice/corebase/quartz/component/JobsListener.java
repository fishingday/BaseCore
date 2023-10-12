package kr.co.basedevice.corebase.quartz.component;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

import kr.co.basedevice.corebase.domain.cm.CmQuartzLog;
import kr.co.basedevice.corebase.domain.code.QuartzLogTypCd;
import kr.co.basedevice.corebase.service.common.LoggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobsListener implements JobListener{
	final private LoggingService loggingService;

	@Override
	public String getName() {
		return "GlobalJob";
	}
	
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		JobKey jobKey = context.getJobDetail().getKey();

        loggingService.writeBatchLog(new CmQuartzLog(
        		QuartzLogTypCd.JOB_TOBE,
        		jobKey.toString(),
        		context.getJobDetail().getJobDataMap().toString()
        	));
	}
 
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		JobKey jobKey = context.getJobDetail().getKey();
        loggingService.writeBatchLog(new CmQuartzLog(
        		QuartzLogTypCd.JOB_VETO,
        		jobKey.toString(),
        		context.getJobDetail().getJobDataMap().toString()
        	));
	}
 
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		JobKey jobKey = context.getJobDetail().getKey();
        loggingService.writeBatchLog(new CmQuartzLog(
        		QuartzLogTypCd.JOB_WAS,
        		jobKey.toString(),
        		context.getJobDetail().getJobDataMap().toString()
        	));
	}
}
