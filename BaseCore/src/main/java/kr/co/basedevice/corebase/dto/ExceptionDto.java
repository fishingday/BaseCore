package kr.co.basedevice.corebase.dto;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ExceptionDto {
	private HttpStatus httpStatus;
	private String code;
	private String message;
}
