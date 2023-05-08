package kr.co.basedevice.corebase.dto;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyMentDto {
	
	private List<MenuDto> topMenuList = new LinkedList<>();
	
	private MenuDto currMenu;
}
