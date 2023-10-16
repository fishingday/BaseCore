package kr.co.basedevice.corebase.quartz;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import kr.co.basedevice.corebase.domain.cm.CmQuartzLog;
import kr.co.basedevice.corebase.domain.code.QuartzLogTypCd;
import kr.co.basedevice.corebase.quartz.component.JobRequest;
import kr.co.basedevice.corebase.quartz.job.TodoCloseWorkJob;
import kr.co.basedevice.corebase.quartz.job.TodoCreateWorkJob;
import kr.co.basedevice.corebase.service.QuartzService;
import kr.co.basedevice.corebase.service.common.LoggingService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BatchLoader implements ApplicationListener<ContextRefreshedEvent> {

	final private QuartzService scheduleService;
	final private LoggingService loggingService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        loggingService.writeBatchLog(new CmQuartzLog(
        		QuartzLogTypCd.STARTED,
        		null,
        		null
        	));
    	
        if (scheduleService.getAllJobs().getNumOfAllJobs() == 0) {
        	
        	//cron job 생성
            JobRequest createWorkJobRequest = new JobRequest();
            createWorkJobRequest.setJobGroup("TODO");
            createWorkJobRequest.setJobName("TodoCreateWorkJob");
            createWorkJobRequest.setCronExpression("0 0 0 ? * *");
            scheduleService.addJob(createWorkJobRequest, TodoCreateWorkJob.class);
            
            JobRequest closeWorkJobRequest = new JobRequest();
            closeWorkJobRequest.setJobGroup("TODO");
            closeWorkJobRequest.setJobName("TodoCloseWorkJob");
            closeWorkJobRequest.setCronExpression("0 0 0 ? * *");
            scheduleService.addJob(closeWorkJobRequest, TodoCloseWorkJob.class);
            
        }

        // 처음 실행 시 한번은 실행하게 하자.
        JobRequest nowCreateWorkJobRequest = new JobRequest();
        nowCreateWorkJobRequest.setJobGroup("INIT");
        nowCreateWorkJobRequest.setJobName("TodoCreateWorkJob");
        nowCreateWorkJobRequest.setStartDateAt(LocalDateTime.now());
        scheduleService.addJob(nowCreateWorkJobRequest, TodoCreateWorkJob.class);
    }

}