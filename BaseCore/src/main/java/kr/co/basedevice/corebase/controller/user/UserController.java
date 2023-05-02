package kr.co.basedevice.corebase.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping(value={"/user_info", "/user_info/init.html"})
	public String viewUserInfo() {
		return "/user/user_info/user_info.html";
	}
	
	@GetMapping(value={"/bookmark", "/bookmark/init.html"})
	public String viewBookmark() {
		return "/user/bookmark/bookmark.html";
	}
	
	@GetMapping(value={"/chg_pwd", "/chg_pwd/init.html"})
	public String viewChgPwd() {
		return "/user/chg_pwd/chg_pwd.html";
	}
	
	@GetMapping(value={"/allow_ip", "/allow_ip/init.html"})
	public String viewAllowIp() {
		return "/user/allow_ip/allow_ip.html";
	}
}
