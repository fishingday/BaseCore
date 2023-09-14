package kr.co.basedevice.corebase.dto.user;

import java.util.List;

import lombok.Data;

@Data
public class UserAllowIpDto {
	
	private List<String> allowIpList;
	private String userPwd;

}
