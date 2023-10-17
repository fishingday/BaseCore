package kr.co.basedevice.corebase.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class SpringBatchConfig {

	final private DataSource dataSource;
	final private PlatformTransactionManager platformTransactionManager;
	
	@Bean
	JobRepository jobRepository() throws Exception {		
	    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
	    factory.setDataSource(dataSource);
	    factory.setTransactionManager(platformTransactionManager);
	    factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
	    return factory.getObject();
	}
}
