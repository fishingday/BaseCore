package kr.co.basedevice.corebase.restController;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice(annotations = RestController.class)
public class RestGlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e){
    	
    	System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    	
        
        return "errorView";
    }
}