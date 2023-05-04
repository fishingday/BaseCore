package kr.co.basedevice.corebase.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {
	
    @ExceptionHandler(value = Exception.class)
    public String pageException(Exception e, HttpServletRequest request){
    	
    	System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	
        return "common/error";
    }
}