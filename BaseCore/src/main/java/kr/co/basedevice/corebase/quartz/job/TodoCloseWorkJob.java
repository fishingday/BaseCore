package kr.co.basedevice.corebase.quartz.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.ObjectUtils;

import kr.co.basedevice.corebase.domain.cm.CmQuartzLog;
import kr.co.basedevice.corebase.domain.code.QuartzLogTypCd;
import kr.co.basedevice.corebase.service.common.LoggingService;
import kr.co.basedevice.corebase.util.BeanUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 완료되지 않은 작업을 완료 처리함.
 * 
 * @author fishingday
 *
 */
@Slf4j
public class TodoCloseWorkJob extends QuartzJobBean implements InterruptableJob {
	final static public String JOB_NAME = "TodoCloseWorkJob";
	final static public String CLOSE_DATE_KEY = "closeDate";
	final static public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	
    private volatile boolean isJobInterrupted = false;
    private volatile Thread currThread;

    @Autowired
    private LoggingService loggingService;
    
    @Autowired
    private BeanUtil beanUtil;
    
    @Autowired 
    private JobLauncher jobLauncher;

    @SneakyThrows
    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
		if (!isJobInterrupted) {
			currThread = Thread.currentThread();
			
			LocalDateTime closeDate = null; // 기록용 날짜... 의미 없음.
			if(jobDataMap != null && ObjectUtils.isEmpty(jobDataMap.getString(TodoCloseWorkJob.CLOSE_DATE_KEY))) {
				closeDate = LocalDateTime.now();
			}else {
				closeDate = LocalDateTime.parse(jobDataMap.getString(TodoCloseWorkJob.CLOSE_DATE_KEY), formatter);
			}
			
	        //전달받은 JodDataMap에서 Job이름을 꺼내오고 그 Job이름으로 context에서 bean을 가져온다
	        Job job = (Job) beanUtil.getBean(TodoCreateWorkJob.JOB_NAME);
	        JobParameters jobParameters = new JobParametersBuilder()
	        		.addString("QuartzJobGroup", jobKey.getGroup())
	        		.addString("QuartzJobName", jobKey.getName())
	                .addString("closeDate", closeDate.format(formatter))
	                .toJobParameters();

	        jobLauncher.run(job, jobParameters);
	        log.info("Quartz Job Run - {} :: JobKey={} - ThreadName : {} - JobParameters : {} ", 
	        		this.getClass().getName(), jobKey, currThread.getName(), jobParameters.toString());
		}
    }

    @Override
    public void interrupt() {
        isJobInterrupted = true;
        if (currThread != null) {        	
            loggingService.writeBatchLog(new CmQuartzLog(
            		QuartzLogTypCd.INTERRUPTED,
            		this.getClass().getName(),
            		currThread.getName()
            	));
            currThread.interrupt();
        }
    }
}