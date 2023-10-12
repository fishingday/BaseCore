package kr.co.basedevice.corebase.quartz.component;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

import kr.co.basedevice.corebase.domain.cm.CmQuartzLog;
import kr.co.basedevice.corebase.domain.code.QuartzLogTypCd;
import kr.co.basedevice.corebase.service.common.LoggingService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TriggersListener  implements TriggerListener {
	final private LoggingService loggingService;
	
	
	@Override	
	public String getName() {
		return "globalTrigger";
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {        
        loggingService.writeBatchLog(new CmQuartzLog(
        		QuartzLogTypCd.TRIGGER_FIRE,
        		trigger.getJobKey().toString(),
        		context.getJobDetail().getJobDataMap().toString()
        	));
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		loggingService.writeBatchLog(new CmQuartzLog(
    		QuartzLogTypCd.TRIGGER_MIS,
    		trigger.getJobKey().toString(),
    		null
    	));
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
        loggingService.writeBatchLog(new CmQuartzLog(
        		QuartzLogTypCd.TRIGGER_COMPLET,
        		trigger.getJobKey().toString(),
        		context.getJobDetail().getJobDataMap().toString()
        	));
	} 
  
}
