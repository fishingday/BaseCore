package kr.co.basedevice.corebase.quartz.component;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

@Component
public class JobsListener implements JobListener{

	@Override
	public String getName() {
		return "GlobalJob";
	}
	
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		// nothing
	}
 
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// nothing
	}
 
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// nothing
	}
}
