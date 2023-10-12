package kr.co.basedevice.corebase.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import kr.co.basedevice.corebase.domain.cm.CmQuartzLog;
import kr.co.basedevice.corebase.domain.code.QuartzLogTypCd;
import kr.co.basedevice.corebase.quartz.AutowiringSpringBeanJobFactory;
import kr.co.basedevice.corebase.quartz.component.JobsListener;
import kr.co.basedevice.corebase.quartz.component.TriggersListener;
import kr.co.basedevice.corebase.service.common.LoggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzConfig {
	
	final private DataSource dataSource;
	final private ApplicationContext applicationContext;
	final private PlatformTransactionManager platformTransactionManager;
	
	final private TriggersListener triggersListener;
	final private JobsListener jobsListener;	

	final private LoggingService loggingService;

	/**
	 * Quartz 관련 설정
	 *
	 * @param applicationContext the applicationContext
	 * @return SchedulerFactoryBean
	 */
	@Bean
	SchedulerFactoryBean schedulerFactoryBean() {
	   SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();	 
	   AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory = new AutowiringSpringBeanJobFactory();	   
	   autowiringSpringBeanJobFactory.setApplicationContext(applicationContext);
	   schedulerFactoryBean.setJobFactory(autowiringSpringBeanJobFactory);
	   schedulerFactoryBean.setApplicationContext(applicationContext);
	   
	   schedulerFactoryBean.setDataSource(dataSource);
	   schedulerFactoryBean.setTransactionManager(platformTransactionManager);
	 
	   schedulerFactoryBean.setOverwriteExistingJobs(true);
	   schedulerFactoryBean.setAutoStartup(true);
	   schedulerFactoryBean.setQuartzProperties(quartzProperties());
	   schedulerFactoryBean.setGlobalTriggerListeners(triggersListener);
	   schedulerFactoryBean.setGlobalJobListeners(jobsListener);
	   schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
	   
	   return schedulerFactoryBean;
	}
	
	private Properties quartzProperties() {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
		Properties properties = null;
		
		try {
			propertiesFactoryBean.afterPropertiesSet();
			properties = propertiesFactoryBean.getObject();
		}catch(IOException ie) {
			log.error("quartzProperties parse error : {}", ie);
		}
		
		return properties;
	}
	
	/**
	 * Spring Framework의 Shutdown Hook 설정.
	 * Quartz의 Shutdown 동작을 위임받아 Graceful Shutdown 을 보장.
	 * <p>
	 * https://github.com/kingbbode/spring-batch-quartz/blob/master/src/main/java/com/kingbbode/config/BatchConfiguration.java
	 *
	 * @param schedulerFactoryBean the schedulerFactoryBean
	 * @return SmartLifecycle
	 */
	@Bean
	SmartLifecycle gracefulShutdownHookForQuartz(@Qualifier("schedulerFactoryBean") SchedulerFactoryBean schedulerFactoryBean) {
		return new SmartLifecycle() {
			private boolean isRunning = false;

			@Override
			public boolean isAutoStartup() {
				return true;
			}

			@Override
			public void stop(Runnable callback) {
				stop();
				log.info("Spring container is shutting down.");
				callback.run();
			}

			@Override
			public void start() {
				log.info("Quartz Graceful Shutdown Hook started.");
				isRunning = true;
			}

			@Override
			public void stop() {
				isRunning = false;

				try {
			        loggingService.writeBatchLog(new CmQuartzLog(
			        		QuartzLogTypCd.SHUTDOWN,
			        		null,
			        		null
			        	));
					log.info("Quartz Graceful Shutdown...");
					interruptJobs(schedulerFactoryBean);
					schedulerFactoryBean.destroy();
				} catch (SchedulerException e) {
					try {
						log.info("Error shutting down Quartz: ", e);
						schedulerFactoryBean.getScheduler().shutdown(false);
					} catch (SchedulerException ex) {
						log.error("Unable to shutdown the Quartz scheduler.", ex);
					}
				}
			}

			@Override
			public boolean isRunning() {
				return isRunning;
			}

			@Override
			public int getPhase() {
				return Integer.MAX_VALUE;
			}
		};
	}

	private void interruptJobs(SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		for (JobExecutionContext jobExecutionContext : scheduler.getCurrentlyExecutingJobs()) {
			final JobDetail jobDetail = jobExecutionContext.getJobDetail();
			log.info("interrupting job :: jobKey : {}", jobDetail.getKey());
			scheduler.interrupt(jobDetail.getKey());
		}
	}
}
