package kr.co.basedevice.corebase.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "kr.co.basedevice.corebase.controller")
public class GlobalExceptionHandler {
	
    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e,Model model){
    	
    	System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	
        model.addAttribute("errorMessage",e.getMessage());
        return "errorView";
    }
}