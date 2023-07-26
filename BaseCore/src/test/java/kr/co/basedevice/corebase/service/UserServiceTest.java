package kr.co.basedevice.corebase.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import kr.co.basedevice.corebase.dto.MyMenuDto;
import kr.co.basedevice.corebase.dto.common.UserInfoDto;
import kr.co.basedevice.corebase.search.common.SearchUserInfo;
import kr.co.basedevice.corebase.service.common.UserService;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void findRoleMenuWithSetting() {
		
		Long userSeq = 102L;
		Long roleSeq = 104L;
		
		MyMenuDto myMenuDto = userService.findRolesMenuWithSetting(userSeq, roleSeq);
				
		assertNotNull(myMenuDto);
		
		System.err.println(myMenuDto);
	}
	
	@Test
	public void pageUserInfo() {
		SearchUserInfo searchUserInfo = new SearchUserInfo();
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<UserInfoDto> pageUserInfo = userService.pageUserInfo(searchUserInfo, pageable);
		
		assertNotNull(pageUserInfo);
		assertTrue(!pageUserInfo.isEmpty());
				
		System.err.println(pageUserInfo.getNumber());
		System.err.println(pageUserInfo.getNumberOfElements());
		System.err.println(pageUserInfo.getSize());
		for(UserInfoDto userInfoDto : pageUserInfo.getContent()) {
			System.err.println(userInfoDto.toString());
		}
	}
	
	
}
