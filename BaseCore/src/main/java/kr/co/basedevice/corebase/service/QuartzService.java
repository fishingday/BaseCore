package kr.co.basedevice.corebase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.quartz.QuartzJobUtils;
import kr.co.basedevice.corebase.quartz.component.JobRequest;
import kr.co.basedevice.corebase.quartz.component.JobResponse;
import kr.co.basedevice.corebase.quartz.component.StatusResponse;
import kr.co.basedevice.corebase.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Quartz 작업 관리
 * 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class QuartzService {
    final private SchedulerFactoryBean schedulerFactoryBean;

    final private ApplicationContext context;
    
    /**
     * 일정(작업) 추가
     * 
     * @param jobRequest
     * @param jobClass
     * @return
     */
    public boolean addJob(JobRequest jobRequest, Class<? extends Job> jobClass) {
        JobKey jobKey = null;
        JobDetail jobDetail;
        Trigger trigger;

        try {
            trigger = QuartzJobUtils.createTrigger(jobRequest);
            jobDetail = QuartzJobUtils.createJob(jobRequest, jobClass, context);
            jobKey = JobKey.jobKey(jobRequest.getJobName(), jobRequest.getJobGroup());

            Date dt = schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, trigger);
            log.info("Add Job with jobKey : {} scheduled successfully at date : {}", jobDetail.getKey(), dt);
            return true;
        } catch (SchedulerException e) {
            log.error("error occurred while scheduling with jobKey : {}", jobKey, e);
        }
        return false;
    }

    /**
     * 일정(작업) 삭제
     * 
     * @param jobKey
     * @return
     */
    public boolean deleteJob(JobKey jobKey) {
        log.debug("[schedulerdebug] deleting job with jobKey : {}", jobKey);
        try {
            return schedulerFactoryBean.getScheduler().deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error occurred while deleting job with jobKey : {}", jobKey, e);
        }
        return false;
    }

    /**
     * 일정(작업) 수정
     * 
     * @param jobRequest
     * @return
     */
    public boolean updateJob(JobRequest jobRequest) {
        JobKey jobKey = null;
        Trigger newTrigger;

        try {
            newTrigger = QuartzJobUtils.createTrigger(jobRequest);
            jobKey = JobKey.jobKey(jobRequest.getJobName(), jobRequest.getJobGroup());

            Date dt = schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobRequest.getJobName()), newTrigger);
            log.debug("Job with jobKey : {} rescheduled successfully at date : {}", jobKey, dt);
            return true;
        } catch (SchedulerException e) {
            log.error("error occurred while scheduling with jobKey : {}", jobKey, e);
        }
        return false;
    }

    /**
     * 일정(작업) 일단멈춤
     * 
     * @param jobKey
     * @return
     */
    public boolean pauseJob(JobKey jobKey) {
        log.debug("[schedulerdebug] pausing job with jobKey : {}", jobKey);
        try {
            schedulerFactoryBean.getScheduler().pauseJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error occurred while deleting job with jobKey : {}", jobKey, e);
        }
        return false;
    }

    /**
     * 일정(작업) 재개 
     * 
     * @param jobKey
     * @return
     */
    public boolean resumeJob(JobKey jobKey) {
        log.debug("[schedulerdebug] resuming job with jobKey : {}", jobKey);
        try {
            schedulerFactoryBean.getScheduler().resumeJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error occurred while resuming job with jobKey : {}", jobKey, e);
        }
        return false;
    }

    /**
     * 일정(작업) 중지
     * 
     * @param jobKey
     * @return
     */
    public boolean stopJob(JobKey jobKey) {
        log.debug("[schedulerdebug] stopping job with jobKey : {}", jobKey);
        try {
            return schedulerFactoryBean.getScheduler().interrupt(jobKey);
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error occurred while stopping job with jobKey : {}", jobKey, e);
        }
        return false;
    }

    /**
     * 일정(작업) 목록
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public StatusResponse getAllJobs() {
        JobResponse jobResponse;
        List<JobResponse> jobs = new ArrayList<>();
        int numOfRunningJobs = 0;
        int numOfGroups = 0;
        int numOfAllJobs = 0;

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            for (String groupName : scheduler.getJobGroupNames()) {
                numOfGroups++;
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

                    jobResponse = JobResponse.builder()
                            .jobName(jobKey.getName())
                            .groupName(jobKey.getGroup())
                            .scheduleTime(DateTimeUtils.toString(triggers.get(0).getStartTime()))
                            .lastFiredTime(DateTimeUtils.toString(triggers.get(0).getPreviousFireTime()))
                            .nextFireTime(DateTimeUtils.toString(triggers.get(0).getNextFireTime()))
                            .build();

                    if (isJobRunning(jobKey)) {
                        jobResponse.setJobStatus("RUNNING");
                        numOfRunningJobs++;
                    } else {
                        String jobState = getJobState(jobKey);
                        jobResponse.setJobStatus(jobState);
                    }
                    numOfAllJobs++;
                    jobs.add(jobResponse);
                }
            }
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error while fetching all job info", e);
        }

        StatusResponse statusResponse = StatusResponse.builder()
        .numOfAllJobs(numOfAllJobs)
        .numOfRunningJobs(numOfRunningJobs)
        .numOfGroups(numOfGroups)
        .jobs(jobs)
        .build();
        return statusResponse;
    }

    /**
     * 일정(작업) 목록
     * 
     * @param jobKey
     * @return
     */
    public boolean isJobRunning(JobKey jobKey) {
        try {
            List<JobExecutionContext> currentJobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
            if (currentJobs != null) {
                for (JobExecutionContext jobCtx : currentJobs) {
                    if (jobKey.getName().equals(jobCtx.getJobDetail().getKey().getName())) {
                        return true;
                    }
                }
            }
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error occurred while checking job with jobKey : {}", jobKey, e);
        }
        return false;
    }

    /**
     * 작업 존재 여부
     * 
     * @param jobKey
     * @return
     */
    public boolean isJobExists(JobKey jobKey) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if (scheduler.checkExists(jobKey)) {
                return true;
            }
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] error occurred while checking job exists :: jobKey : {}", jobKey, e);
        }
        return false;
    }

    /**
     * 작업 상태 조회
     * 
     * @param jobKey
     * @return
     */
    public String getJobState(JobKey jobKey) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());

            if (triggers != null && triggers.size() > 0) {
                for (Trigger trigger : triggers) {
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    return triggerState.name();
                }
            }
        } catch (SchedulerException e) {
            log.error("[schedulerdebug] Error occurred while getting job state with jobKey : {}", jobKey, e);
        }
        return null;
    }
}
