package kr.co.basedevice.corebase.controller.play;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/play/wpi")
public class PlayWpiController {
		
	@GetMapping(value={"/test", "/test/init.html"})
	public String testWpi() {
		
		return "/play/wpi/test";
	}
	
	@GetMapping(value={"/result", "/result/init.html"})
	public String resultWpi() {
		
		return "/play/wpi/result";
	}
}
