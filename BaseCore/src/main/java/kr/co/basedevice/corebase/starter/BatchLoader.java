package kr.co.basedevice.corebase.starter;

import java.time.LocalDateTime;

import org.quartz.JobDataMap;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import kr.co.basedevice.corebase.quartz.component.JobRequest;
import kr.co.basedevice.corebase.quartz.job.CronJob;
import kr.co.basedevice.corebase.quartz.job.CronJob2;
import kr.co.basedevice.corebase.quartz.job.SimpleJob;
import kr.co.basedevice.corebase.service.QuartzService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BatchLoader implements ApplicationListener<ContextRefreshedEvent> {

	final private QuartzService scheduleService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (scheduleService.getAllJobs().getNumOfAllJobs() == 0) {
            //simple job 생성
            JobRequest jobRequest = new JobRequest();
            jobRequest.setJobName("simpleJob");
            jobRequest.setStartDateAt(LocalDateTime.now());
            jobRequest.setRepeatCount(50);
            jobRequest.setRepeatIntervalInSeconds(30);
            scheduleService.addJob(jobRequest, SimpleJob.class);

            //cron job 생성
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("jobId", "123456789");
            jobRequest = new JobRequest();
            jobRequest.setJobName("cronJob1");
            jobRequest.setCronExpression("0/10 * * ? * *"); //every min
            jobRequest.setJobDataMap(jobDataMap);
            scheduleService.addJob(jobRequest, CronJob.class);

            jobRequest = new JobRequest();
            jobRequest.setJobName("cronJob2");
            jobRequest.setCronExpression("0 */5 * ? * *"); //every 5 min
            scheduleService.addJob(jobRequest, CronJob2.class);
        }
    }

}