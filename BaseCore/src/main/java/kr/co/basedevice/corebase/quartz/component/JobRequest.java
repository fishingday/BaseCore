package kr.co.basedevice.corebase.quartz.component;

import java.time.LocalDateTime;

import org.quartz.JobDataMap;
import org.springframework.format.annotation.DateTimeFormat;

import kr.co.basedevice.corebase.domain.code.BatchJobType;
import kr.co.basedevice.corebase.domain.code.QuartzJobGroupCd;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobRequest {

    private String jobGroup = QuartzJobGroupCd.ONCE.name();
    private String jobName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateAt;
    private long repeatIntervalInSeconds;
    private int repeatCount;

    private String cronExpression;
    private JobDataMap jobDataMap;

    public boolean isJobTypeSimple() {
        return this.cronExpression == null;
    }

    public BatchJobType getCurrentJobType() {
        return isJobTypeSimple() ? BatchJobType.SIMPLE : BatchJobType.CRON;
    }
}