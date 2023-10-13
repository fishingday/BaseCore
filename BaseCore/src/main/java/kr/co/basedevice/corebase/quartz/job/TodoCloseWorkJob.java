package kr.co.basedevice.corebase.quartz.job;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import kr.co.basedevice.corebase.domain.cm.CmQuartzLog;
import kr.co.basedevice.corebase.domain.code.QuartzLogTypCd;
import kr.co.basedevice.corebase.service.common.LoggingService;
import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TodoCloseWorkJob extends QuartzJobBean implements InterruptableJob {
    private volatile boolean isJobInterrupted = false;
    private volatile Thread currThread;

    @Autowired
    private LoggingService loggingService;

    @Autowired
    private TodoService todoService;

    
    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	JobKey jobKey = context.getJobDetail().getKey();
    	
        if (!isJobInterrupted) {
            currThread = Thread.currentThread();
            int closeItemCnt  = todoService.closeWorkItems();
            log.info("TodoCloseWorkJob :: jobKey : {} - {} : result count = {} ", jobKey, currThread.getName(), closeItemCnt);
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