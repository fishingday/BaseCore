package kr.co.basedevice.corebase.controller.user;


import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.basedevice.corebase.domain.cm.Account;
import kr.co.basedevice.corebase.domain.cm.CmUser;
import kr.co.basedevice.corebase.dto.AccountDto;
import kr.co.basedevice.corebase.security.token.AjaxAuthenticationToken;
//import kr.co.basedevice.corebase.service.UserService;

@Controller
public class UserController {
	
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	//@Autowired
//	//private RoleRepository roleRepository;
//
//	@GetMapping(value="/users")
//	public String createUser() throws Exception {
//
//		return "user/login/register";
//	}
//
//	@PostMapping(value="/users")
//	public String createUser(AccountDto accountDto) throws Exception {
//
//		ModelMapper modelMapper = new ModelMapper();
//		Account account = modelMapper.map(accountDto, Account.class);
//		account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
//
//		userService.createUser(account);
//
//		return "redirect:/";
//	}
//
//	@GetMapping(value="/mypage")
//	public String myPage(@AuthenticationPrincipal CmUser cmUser, Authentication authentication, Principal principal) throws Exception {
//
//		String username1 = cmUser.getUserNm();
//		System.out.println("username1 = " + username1);
//
//		CmUser cmUser2 = (CmUser) authentication.getPrincipal();
//		String username2 = cmUser2.getUserNm();
//		System.out.println("username2 = " + username2);
//
//		CmUser cmUser3 = null;
//		if (principal instanceof UsernamePasswordAuthenticationToken) {
//			cmUser3 = (CmUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
//
//		}else if(principal instanceof AjaxAuthenticationToken){
//			cmUser3 = (CmUser) ((AjaxAuthenticationToken) principal).getPrincipal();
//		}
//
//		String username3 = cmUser3.getUserNm();
//		System.out.println("username3 = " + username3);
//
//		CmUser cmUser4 = (CmUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username4 = cmUser4.getUserNm();
//		System.out.println("username4 = " + username4);
//
//		return "user/mypage";
//	}
}
