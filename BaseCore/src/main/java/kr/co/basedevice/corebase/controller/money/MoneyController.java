package kr.co.basedevice.corebase.controller.money;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/money")
public class MoneyController {	
	
	@GetMapping(value={"/active_item", "/active_item/init.html"})
	public String activeItem() {
		
		return "/money/active_item";
	}
	
	@GetMapping(value={"/exchange", "/exchange/init.html"})
	public String exchange() {
		
		return "/money/exchange";
	}
	
	@GetMapping(value={"/history", "/history/init.html"})
	public String history() {
		
		return "/money/history";
	}
	
	@GetMapping(value={"/item_mgt", "/item_mgt/init.html"})
	public String itemMgt() {
		
		return "/money/item_mgt";
	}
		
	@GetMapping(value={"/payment", "/payment/init.html"})
	public String payment() {
		
		return "/money/payment";
	}	
	
	@GetMapping(value={"/calhistory", "/calhistory/init.html"})
	public String calHistory() {
		
		return "/money/calhistory";
	}
}
