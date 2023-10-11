package kr.co.basedevice.corebase.quartz.job;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleJob extends QuartzJobBean implements InterruptableJob {
    private volatile boolean isJobInterrupted = false;
    private int MAX_SLEEP_IN_SECONDS = 5;

    private volatile Thread currThread;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        if (!isJobInterrupted) {
            currThread = Thread.currentThread();
            log.info("============================================================================");
            log.info("SimpleJob started :: jobKey : {} - {}", jobKey, currThread.getName());

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
            log.info("interrupting - {}", currThread.getName());
            currThread.interrupt();
        }
    }
}
