package kr.co.basedevice.corebase.batch.todo;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.quartz.job.TodoCreateWorkJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * 기본 Tasklet 기반의 Batch Job
 * step1이 데이터를 조회하고,
 * step2가 데이터를 기록한다.
 * 
 * @author fishingday
 *
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TodoCreateWorkBatchJob {
	final private JobBuilderFactory jobBuilderFactory;
	final private StepBuilderFactory stepBuilderFactory;

	@Bean(name = TodoCreateWorkJob.JOB_NAME)
    Job createWorkTodoBatchJob(){
        return jobBuilderFactory.get(TodoCreateWorkJob.JOB_NAME)
                .start(step1CreateWorkBatchTodoJob()) // 할일 조회
                .next(step2CreateWorkBatchTodoJob())  // 작업 생성
                .build();

    }

    public Step step1CreateWorkBatchTodoJob() {
        return stepBuilderFactory.get("step1CreateWorkBatchTodoJob")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext context) throws Exception {
                        ExecutionContext jobContext = context.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
                        
                        
                        List<TdTodo> tdTodoList = null;
                        
                        
                        jobContext.put("tdTodoList", tdTodoList);
                    	
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
    
    public Step step2CreateWorkBatchTodoJob() {
        return stepBuilderFactory.get("step2CreateWorkBatchTodoJob")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("########## createJob2_Step1 start!!!!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
