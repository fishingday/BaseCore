package kr.co.basedevice.corebase.service;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.basedevice.corebase.dto.MyMenuDto;
import kr.co.basedevice.corebase.service.common.UserService;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void findRoleMenuWithSetting() {
		
		Long userSeq = 101L;
		Long roleSeq = 100L;
		
		MyMenuDto myMenuDto = userService.findRoleMenuWithSetting(userSeq, roleSeq);
				
		assertNotNull(myMenuDto);
		
		System.err.println(myMenuDto);
	}	
}
