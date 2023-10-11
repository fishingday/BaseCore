package kr.co.basedevice.corebase.restController;

import kr.co.basedevice.corebase.quartz.component.StatusResponse;
import kr.co.basedevice.corebase.quartz.job.SimpleJob;
import kr.co.basedevice.corebase.service.QuartzService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ScheduleRestController.class)
public class ScheduleRestControllerTest {
    private final String BASE_PATH = "/api/scheduler";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuartzService scheduleService;

    @Test
    public void addScheduleJob_simpleJob() throws Exception {
        given(scheduleService.addJob(any(), eq(SimpleJob.class))).willReturn(true);
        given(scheduleService.isJobExists(any())).willReturn(false);

        mvc.perform(post(BASE_PATH + "/job")
                .param("jobName", "job1")
                .param("groupName", "testGroup"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)));

        verify(scheduleService).addJob(any(), eq(SimpleJob.class));
        verify(scheduleService).isJobExists(any());
    }

    @Test
    public void deleteScheduleJob() throws Exception {
        given(scheduleService.deleteJob(any())).willReturn(true);
        given(scheduleService.isJobExists(any())).willReturn(true);
        given(scheduleService.isJobRunning(any())).willReturn(false);

        mvc.perform(delete(BASE_PATH + "/job")
                .param("jobName", "job1")
                .param("groupName", "testGroup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));

        verify(scheduleService).deleteJob(any());
        verify(scheduleService).isJobExists(any());
        verify(scheduleService).isJobRunning(any());
    }

    @Test
    public void updateScheduleJob() throws Exception {
        given(scheduleService.updateJob(any())).willReturn(true);
        given(scheduleService.isJobExists(any())).willReturn(true);

        mvc.perform(put(BASE_PATH + "/job/update")
                .param("jobName", "job1")
                .param("groupName", "testGroup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));

        verify(scheduleService).updateJob(any());
        verify(scheduleService).isJobExists(any());
    }

    @Test
    public void getAllJobs() throws Exception {
        StatusResponse statsResponse = StatusResponse.builder()
                .numOfAllJobs(1)
                .build();

        given(scheduleService.getAllJobs()).willReturn(statsResponse);

        mvc.perform(get(BASE_PATH + "/jobs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numOfAllJobs", is(1)));

        verify(scheduleService).getAllJobs();
    }

    @Test
    public void pauseJob() throws Exception {
        given(scheduleService.pauseJob(any())).willReturn(true);
        given(scheduleService.isJobExists(any())).willReturn(true);
        given(scheduleService.isJobRunning(any())).willReturn(false);

        mvc.perform(put(BASE_PATH + "/job/pause")
                .param("jobName", "job1")
                .param("groupName", "testGroup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));

        verify(scheduleService).pauseJob(any());
        verify(scheduleService).isJobExists(any());
        verify(scheduleService).isJobRunning(any());
    }

    @Test
    public void resumeJob() throws Exception {
        given(scheduleService.resumeJob(any())).willReturn(true);
        given(scheduleService.isJobExists(any())).willReturn(true);
        given(scheduleService.getJobState(any())).willReturn("PAUSED");

        mvc.perform(put(BASE_PATH + "/job/resume")
                .param("jobName", "job1")
                .param("groupName", "testGroup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));

        verify(scheduleService).resumeJob(any());
        verify(scheduleService).isJobExists(any());
        verify(scheduleService).getJobState(any());
    }
}
