package kr.co.basedevice.corebase.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping(value={"/", "/dashboard/init.html"})
	public String home() throws Exception {
		return "home";
	}

}
