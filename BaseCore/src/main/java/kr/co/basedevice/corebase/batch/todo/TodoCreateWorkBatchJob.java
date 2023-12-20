package kr.co.basedevice.corebase.batch.todo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import kr.co.basedevice.corebase.domain.code.TodoCreCd;
import kr.co.basedevice.corebase.domain.td.TdTodo;
import kr.co.basedevice.corebase.quartz.job.TodoCreateWorkJob;
import kr.co.basedevice.corebase.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * 할일을 목록을 조회 하여 작업 목록을 생성
 * 
 * @author fishingday
 *
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TodoCreateWorkBatchJob {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final TodoService todoService;
    
    public final static int chunkSize = 10;

	@Bean(name = TodoCreateWorkJob.JOB_NAME)
    Job createWorkTodoBatchJob(){
        return jobBuilderFactory.get(TodoCreateWorkJob.JOB_NAME)
                .start(createWorkBatch4TodoReader(null)) // 할일 조회
                .build();
    }

	@Bean
	@JobScope
    Step createWorkBatch4TodoReader(@Value("#{jobParameters["+TodoCreateWorkJob.CREATE_DATE_KEY+"]}") String createDate) {
		return stepBuilderFactory.get("createWorkBatch4TodoReader")
				.<TdTodo, TdTodo>chunk(chunkSize)
                .reader(jpaPagingItemReaderTodo(null))
                .writer(itemWriterWork(null))
                .build();
				
    }
	
    @Bean
    @StepScope
    JpaPagingItemReader<TdTodo> jpaPagingItemReaderTodo(@Value("#{jobParameters["+TodoCreateWorkJob.CREATE_DATE_KEY+"]}") String createDate) {
    	
    	Map<String, Object> paramValues = new HashMap<>();
    	log.debug("jpaPagingItemReaderTodo.#jobParameters[{}= {}", TodoCreateWorkJob.CREATE_DATE_KEY, createDate);
        paramValues.put(TodoCreateWorkJob.CREATE_DATE_KEY, LocalDate.parse(createDate, TodoCreateWorkJob.formatter)); // 데이터를 준 놈이 포맷을 알고 있음
    	
        return new JpaPagingItemReaderBuilder<TdTodo>()
                .name("jpaPagingItemReaderTodo")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT t FROM TdTodo t WHERE t.delYn = 'N' and t.todoCreCd in ('DAILY', 'WEEK', 'MONTH') and :createDate between t.postBeginDate and t.postEndDate ")
                .parameterValues(paramValues)
                .build();
    }
    
    @Bean
    @StepScope
    ItemWriter<TdTodo> itemWriterWork(@Value("#{jobParameters["+TodoCreateWorkJob.CREATE_DATE_KEY+"]}") String createDate) {
        return list -> {
            for (TdTodo tdTodo: list) {
            	LocalDate toDay = LocalDate.parse(createDate, TodoCreateWorkJob.formatter); // 데이터를 준 놈이 포맷을 알고 있음
            	
            	if(tdTodo.getTodoCreCd() == TodoCreCd.MONTH) {
            		// 상세 값이 없으면 실행 할 수 없다.
            		if(ObjectUtils.isEmpty(tdTodo.getTodoCreDtlVal())) {
            			continue;
            		}
            		
            		// LAST이면 마지막 날을 의미, 오늘 날짜와 같으면 OK!
            		boolean isToday = false;
            		String [] monthDay = tdTodo.getTodoCreDtlVal().split(",");
            		for(String day : monthDay) {
            			if("LAST".equals(day)) { // 월의 마지막 날인지...
            				if(toDay.getDayOfMonth() == toDay.withDayOfMonth(toDay.lengthOfMonth()).getDayOfMonth()) {
            					isToday = true;
            					break;
            				}            				
            			}else{ // 오늘 맞니?
            				String dayOfMonth = String.valueOf(toDay.getDayOfMonth());
            				if(dayOfMonth.equals(day)) {
            					isToday = true;
            					break;
            				}
            			}
            		}
            		
            		if(!isToday) {
            			continue;
            		}
            		
            	}else if(tdTodo.getTodoCreCd() == TodoCreCd.WEEK) {
            		// 상세 값이 없으면 실행 할 수 없다.
            		if(ObjectUtils.isEmpty(tdTodo.getTodoCreDtlVal())) {
            			continue;
            		}
            		DayOfWeek dayOfWeek = toDay.getDayOfWeek();
            		
            		boolean isToday = false;
            		String [] weeks = tdTodo.getTodoCreDtlVal().split(",");
            		for(String week : weeks) {
            			if(dayOfWeek.toString().equals(week)) {
            				isToday = true;
        					break;
            			}
            		}
            		if(!isToday) {
            			continue;
            		}            		
            	}
            	
            	todoService.createWorkItems(toDay, tdTodo.getTodoSeq());
                log.info("Current TdTodo={}", tdTodo);
            }
        };
    }
}