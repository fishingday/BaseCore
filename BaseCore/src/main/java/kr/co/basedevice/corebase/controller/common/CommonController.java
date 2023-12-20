package kr.co.basedevice.corebase.controller.common;

import java.security.Principal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.basedevice.corebase.domain.cm.CmRole;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.MyMenuDto;
import kr.co.basedevice.corebase.security.service.AccountContext;
import kr.co.basedevice.corebase.security.token.AjaxAuthenticationToken;
import kr.co.basedevice.corebase.service.common.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {
	

	final private UserService userService;
	
	@GetMapping("/login.html")
	public String login(@RequestParam(value = "error", required = false) String error,
						@RequestParam(value = "exception", required = false) String exception, Model model){
		model.addAttribute("error",error);
		model.addAttribute("exception",exception);
		return "common/login";
	}

	@GetMapping(value = "/logout.html")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null){
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		return "redirect:/common/login.html";
	}

	@GetMapping(value="/denied.html")
	public String accessDenied(@RequestParam(value = "exception", required = false) String exception, Principal principal, Model model) throws Exception {

		CmUser cmUser = null;

		if (principal instanceof UsernamePasswordAuthenticationToken) {
			cmUser = ((AccountContext) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getCmUser();

		}else if(principal instanceof AjaxAuthenticationToken){
			cmUser = ((AccountContext) ((AjaxAuthenticationToken) principal).getPrincipal()).getCmUser();
		}

		model.addAttribute("username", cmUser.getUserNm());
		model.addAttribute("exception", exception);

		return "/common/denied.html";
	}
	
	@GetMapping("/join_us.html")
	public String joinUs() {
		
		return "/common/register.html";
	}
	
	/**
	 * 역할 변경
	 * - 현재 역할 변경
	 * - 역할별 메뉴목록 변경
	 * 
	 * @param roleSeq
	 * @return
	 */
	@GetMapping("/chg_role.html")
	public String chgRole(Long roleSeq){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AccountContext account = ((AccountContext) authentication.getPrincipal());
		
		CmRole currRole = null;
		for(CmRole cmRole : account.getAuthRoleList()) {
			if(cmRole.getRoleSeq() == roleSeq) {
				currRole = cmRole;
				break;
			}
		}
		
		if(currRole == null) {
			throw new IllegalArgumentException("올바른 역할 정보가 아닙니다.");
		}
		account.setCurrRole(currRole);
		
		MyMenuDto myMenu = userService.findRolesMenuWithSetting(account.getCmUser().getUserSeq(), currRole.getRoleSeq());
		
		account.setMyMenu(myMenu);
		
		return "redirect:/";
	}
}
