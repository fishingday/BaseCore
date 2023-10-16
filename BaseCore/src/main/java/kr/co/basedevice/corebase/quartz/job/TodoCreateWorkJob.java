package kr.co.basedevice.corebase.quartz.job;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import kr.co.basedevice.corebase.domain.cm.CmQuartzLog;
import kr.co.basedevice.corebase.domain.code.QuartzLogTypCd;
import kr.co.basedevice.corebase.service.common.LoggingService;
import kr.co.basedevice.corebase.util.BeanUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * 오늘의 TODO 생성 
 * 
 * @author fishingday
 *
 */
@Log4j2
public class TodoCreateWorkJob extends QuartzJobBean implements InterruptableJob {
	final static public String JOB_NAME = "TodoCreateWorkJob";
	final static public String CREATE_DATE_KEY = "CreateDate";
	final static public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	
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
	protected void executeInternal(JobExecutionContext context){
		JobKey jobKey = context.getJobDetail().getKey();
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
		if (!isJobInterrupted) {
			currThread = Thread.currentThread();
	        //전달받은 JodDataMap에서 Job이름을 꺼내오고 그 Job이름으로 context에서 bean을 가져온다
	        Job job = (Job) beanUtil.getBean(TodoCreateWorkJob.JOB_NAME);

	        JobParameters jobParameters = new JobParametersBuilder()
	                .addDate("curDate", new Date())
	                .toJobParameters();

	        jobLauncher.run(job, jobParameters);
			
			/*
			// 1. 생성할 날짜가 있나?
			LocalDate createDate = null;
			if(jobDataMap != null && ObjectUtils.isEmpty(jobDataMap.getString(TodoCreateWorkJob.CREATE_DATE_KEY))) {
				createDate = LocalDate.now();
			}else {
				createDate = LocalDate.parse(jobDataMap.getString(TodoCreateWorkJob.CREATE_DATE_KEY), formatter);
			}
			log.info("TodoCreateWorkJob :: STEP1 jobKey : {} - {}", jobKey, currThread.getName());
			
			// 2. 유효한 모든 할일 목록을 조회하여...
			List<TdTodo> tdTodoList = todoService.findByTargetTodoItem(createDate);
			log.info("TodoCreateWorkJob :: STEP2 jobKey : {} - {} : result count = {} ", jobKey, currThread.getName(), tdTodoList.isEmpty() ? 0 : tdTodoList.size());
			
			// 3. 조건에 해당하는 할일의 작업을 생성 
			int creItemCnt  = 0;
			if(tdTodoList != null && !tdTodoList.isEmpty()) {
				for(TdTodo tdTodo : tdTodoList) {
					creItemCnt += todoService.createWorkItems(createDate, tdTodo.getTodoSeq());
				}
			}
			log.info("TodoCreateWorkJob :: STEP3 jobKey : {} - {} : result count = {} ", jobKey, currThread.getName(), creItemCnt);
			*/
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
