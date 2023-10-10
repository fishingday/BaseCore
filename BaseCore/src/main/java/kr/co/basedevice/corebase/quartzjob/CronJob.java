package kr.co.basedevice.corebase.quartzjob;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CronJob extends QuartzJobBean {
	
	private int MAX_SLEEP_IN_SECONDS = 5;
	private volatile Thread currThread;
 
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
      JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
      int jobId = jobDataMap.getInt("jobId");
      JobKey jobKey = context.getJobDetail().getKey();
      currThread = Thread.currentThread();
 
      IntStream.range(0, 5).forEach(i -> {
         log.info("SimpleJob Counting - {}", i);
         try {
            TimeUnit.SECONDS.sleep(MAX_SLEEP_IN_SECONDS);
         } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
         }
      });
   }
}
