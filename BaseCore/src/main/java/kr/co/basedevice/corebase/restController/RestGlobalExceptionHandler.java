package kr.co.basedevice.corebase.restController;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "kr.co.basedevice.corebase.restController")
public class RestGlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e){
    	
    	System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	
        
        return "errorView";
    }
}