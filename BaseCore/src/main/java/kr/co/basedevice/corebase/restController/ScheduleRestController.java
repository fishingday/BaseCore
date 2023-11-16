package kr.co.basedevice.corebase.restController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.quartz.component.ApiResponse;
import kr.co.basedevice.corebase.quartz.component.JobRequest;
import kr.co.basedevice.corebase.quartz.component.StatusResponse;
import kr.co.basedevice.corebase.quartz.job.TodoCreateWorkJob;
import kr.co.basedevice.corebase.quartz.job.SimpleJob;
import kr.co.basedevice.corebase.service.QuartzService;

@Slf4j
@RestController
@RequestMapping("/system/schedule_mgt")
@RequiredArgsConstructor
public class ScheduleRestController {
	final private QuartzService scheduleService;
	
	/**
	 * 일정(작업) 추가
	 * 
	 * @param jobRequest
	 * @return
	 */
    @PostMapping("/job.json")
    public ResponseEntity<?> addScheduleJob(@ModelAttribute JobRequest jobRequest) {
        log.debug("add schedule job :: jobRequest : {}", jobRequest);
        if (jobRequest.getJobName() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Require jobName"),
                    HttpStatus.BAD_REQUEST);
        }

        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (!scheduleService.isJobExists(jobKey)) {
            if (jobRequest.isJobTypeSimple()) {
                scheduleService.addJob(jobRequest, SimpleJob.class);
            } else {
                scheduleService.addJob(jobRequest, TodoCreateWorkJob.class);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job already exits"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job created successfully"), HttpStatus.CREATED);
    }

    /**
     * 일정(작업) 삭제
     * 
     * @param jobRequest
     * @return
     */
    @DeleteMapping("/job")
    public ResponseEntity<?> deleteScheduleJob(@ModelAttribute JobRequest jobRequest) {
        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (scheduleService.isJobExists(jobKey)) {
            if (!scheduleService.isJobRunning(jobKey)) {
                scheduleService.deleteJob(jobKey);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Job already in running state"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job deleted successfully"), HttpStatus.OK);
    }

    /**
     * 일정(작업) 수정
     * 
     * @param jobRequest
     * @return
     */
    @PutMapping("/job/update")
    public ResponseEntity<?> updateScheduleJob(@ModelAttribute JobRequest jobRequest) {
        log.debug("update schedule job :: jobRequest : {}", jobRequest);
        if (jobRequest.getJobName() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Require jobName"),
                    HttpStatus.BAD_REQUEST);
        }

        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (scheduleService.isJobExists(jobKey)) {
            if (jobRequest.isJobTypeSimple()) {
                scheduleService.updateJob(jobRequest);
            } else {
                scheduleService.updateJob(jobRequest);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job updated successfully"), HttpStatus.OK);
    }

    /**
     * 일정(작업) 목록
     * 
     * @return
     */
    @GetMapping("/jobs")
    public StatusResponse getAllJobs() {
    	// 상태값 : NONE, NORMAL, PAUSED, COMPLETE, ERROR, BLOCKED
    	
        return scheduleService.getAllJobs();
    }

    /**
     * 일정(작업) 일단 멈춤
     * 
     * @param jobRequest
     * @return
     */
    @PutMapping("/job/pause")
    public ResponseEntity<?> pauseJob(@ModelAttribute JobRequest jobRequest) {
        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (scheduleService.isJobExists(jobKey)) {
            if (!scheduleService.isJobRunning(jobKey)) {
                scheduleService.pauseJob(jobKey);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Job already in running state"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job paused successfully"), HttpStatus.OK);
    }

    /**
     * 작업 재개
     * 
     * @param jobRequest
     * @return
     */
    @PutMapping("/job/resume")
    public ResponseEntity<?> resumeJob(@ModelAttribute JobRequest jobRequest) {
        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (scheduleService.isJobExists(jobKey)) {
            String jobState = scheduleService.getJobState(jobKey);

            if (jobState.equals("PAUSED")) {
                scheduleService.resumeJob(jobKey);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Job is not in paused state"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job resumed successfully"), HttpStatus.OK);
    }

    /**
     * 일정(작업) 중지
     * 
     * @param jobRequest
     * @return
     */
    @PutMapping("/job/stop")
    public ResponseEntity<?> stopJob(@ModelAttribute JobRequest jobRequest) {
        JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
        if (scheduleService.isJobExists(jobKey)) {
            if (scheduleService.isJobRunning(jobKey)) {
                scheduleService.stopJob(jobKey);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Job is not in running state"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Job does not exits"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Job stopped successfully"), HttpStatus.OK);
    }
}
