package kr.co.basedevice.corebase.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system/")
public class SystemController {

	@GetMapping(value={"/user_mgt", "/user_mgt/init.html"})
	public String viewUserMgt() {
		return "/system/user_mgt/user_mgt.html";
	}
	
	@GetMapping(value={"/noti_mgt", "/noti_mgt/init.html"})
	public String viewNotiMgt() {
		return "/system/noti_mgt/noti_mgt.html";
	}
	
	@GetMapping(value={"/role_mgt", "/role_mgt/init.html"})
	public String viewRoleMgt() {
		return "/system/role_mgt/role_mgt.html";
	}
	
	@GetMapping(value={"/menu_mgt", "/menu_mgt/init.html"})
	public String viewMenuMgt() {
		return "/system/menu_mgt/menu_mgt.html";
	}
	
	@GetMapping(value={"/code_mgt", "/code_mgt/init.html"})
	public String viewCodeMgt() {
		return "/system/code_mgt/code_mgt.html";
	}
	
	@GetMapping(value={"/schedule_mgt", "/schedule_mgt/init.html"})
	public String viewScheduleMgt() {
		return "/system/schedule_mgt/schedule_mgt.html";
	}
	
	@GetMapping(value={"/env_mgt", "/env_mgt/init.html"})
	public String viewEnvMgt() {
		return "/system/env_mgt/env_mgt.html";
	}
}
