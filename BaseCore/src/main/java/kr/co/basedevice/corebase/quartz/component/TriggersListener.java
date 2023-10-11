package kr.co.basedevice.corebase.quartz.component;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.springframework.stereotype.Component;
import org.quartz.TriggerListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TriggersListener  implements TriggerListener {
	
	@Override	
	public String getName() {
		return "globalTrigger";
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
        JobKey jobKey = trigger.getJobKey();
        log.info("================================================ Trigger Begin.");
        log.info("triggerFired at {} :: jobKey : {}", trigger.getStartTime(), jobKey);
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
        JobKey jobKey = trigger.getJobKey();
        log.info("triggerMisfired at {} :: jobKey : {}", trigger.getStartTime(), jobKey);		
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
        JobKey jobKey = trigger.getJobKey();
        log.info("triggerComplete at {} :: jobKey : {}", trigger.getStartTime(), jobKey);
        log.info("================================================ Trigger End.");
	} 
  
}
