package kr.co.basedevice.corebase.quartz.job;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.quartz.QuartzJobBean;

import kr.co.basedevice.corebase.domain.cm.CmQuartzLog;
import kr.co.basedevice.corebase.domain.code.QuartzLogTypCd;
import kr.co.basedevice.corebase.dto.common.UserInfoDto;
import kr.co.basedevice.corebase.search.common.SearchUserInfo;
import kr.co.basedevice.corebase.service.common.LoggingService;
import kr.co.basedevice.corebase.service.common.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * 스프링 빈을 사용하기 위해서는 @Autowired를 사용해야 함.
 * 
 * @author fishingday
 *
 */
@Slf4j
public class SimpleJob extends QuartzJobBean implements InterruptableJob {
    private volatile boolean isJobInterrupted = false;
    private int MAX_SLEEP_IN_SECONDS = 5;
    private volatile Thread currThread;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private LoggingService loggingService;
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        if (!isJobInterrupted) {
            currThread = Thread.currentThread();
            log.info("============================================================================");
            log.info("SimpleJob started :: jobKey : {} - {}", jobKey, currThread.getName());
            
            SearchUserInfo searchUserInfo = new SearchUserInfo();
    		Pageable pageable = PageRequest.of(0, 10);
            
            Page<UserInfoDto> pageUserInfo = userService.pageUserInfo(searchUserInfo, pageable);
            
            if(!pageUserInfo.isEmpty()) {
            	for(UserInfoDto userInfoDto : pageUserInfo.getContent()) {
        			log.info("################# : " + userInfoDto.toString());
        		}
            }

            IntStream.range(0, 2).forEach(i -> {
                log.info("SimpleJob Counting - {}", i);
                try {
                    TimeUnit.SECONDS.sleep(MAX_SLEEP_IN_SECONDS);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            });

            log.info("SimpleJob ended :: jobKey : {} - {}", jobKey, currThread.getName());
            log.info("============================================================================");
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
