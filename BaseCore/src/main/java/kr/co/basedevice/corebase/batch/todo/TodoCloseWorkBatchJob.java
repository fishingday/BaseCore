package kr.co.basedevice.corebase.batch.todo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kr.co.basedevice.corebase.quartz.job.TodoCloseWorkJob;
import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * 종료되지 않은 작업 목록을 조회하여 종료 처리한다.  
 * 
 * @author fishingday
 *
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TodoCloseWorkBatchJob extends DefaultBatchConfiguration {
	
    private final TodoService todoService;
    
    public final static int chunkSize = 10;

	@Bean(name = TodoCloseWorkJob.JOB_NAME)
    Job createWorkTodoBatchJob(JobRepository jobRepository, Step stepCloseWorkBatch4Todo){
		// TodoCloseWorkJob
        return new JobBuilder(TodoCloseWorkJob.JOB_NAME, jobRepository) 
                .start(stepCloseWorkBatch4Todo)
                .build();
    }

	@Bean
	@JobScope
    Step stepCloseWorkBatch4Todo(
    		JobRepository jobRepository, 
    		Tasklet  taskletCloseWorkTodo,
    		PlatformTransactionManager platformTransactionManager, 
    		@Value("#{jobParameters[" + TodoCloseWorkJob.CLOSE_DATE_KEY + "]}") String closeDate) {
		
		return new StepBuilder("stepCloseWorkBatch4Todo", jobRepository)
				.tasklet(taskletCloseWorkTodo, platformTransactionManager)
                .build();				
    }
	
	@Bean
	@StepScope
	Tasklet taskletCloseWorkTodo(@Value("#{jobParameters["+ TodoCloseWorkJob.CLOSE_DATE_KEY +"]}") String closeDate) {
		
		return (contribution, chunkContext) -> {
			
			// 오늘 이전에 셋팅된 작업에 대하여...
			LocalDateTime closeDateTime = LocalDateTime.parse(closeDate, TodoCloseWorkJob.formatter)
					.truncatedTo(ChronoUnit.DAYS);
			
			int closeWorkItems = todoService.closeWorkItems(closeDateTime);
			log.info("Close Work Items Count : {}", closeWorkItems);
			return RepeatStatus.FINISHED;
		};
	}
	
}