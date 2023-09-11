package kr.co.basedevice.corebase.restController.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.basedevice.corebase.service.common.CommonService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/system/org_mgt")
@RequiredArgsConstructor
public class OrgMgtRestController {
	
	final private CommonService commonService;	

	
}
