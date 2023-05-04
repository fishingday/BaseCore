package kr.co.basedevice.corebase.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PageErrorController implements ErrorController {
	
    @GetMapping(value = "/error/404.html", produces = MediaType.TEXT_HTML_VALUE)
    public String notFoundError(HttpServletRequest request) {
    	 Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
         // error로 들어온 에러의 status를 불러온다 (ex:404,500,403...)
         
         if(status != null){
             int statusCode = Integer.valueOf(status.toString());
             
             if(statusCode == HttpStatus.NOT_FOUND.value()) {
                 return "common/404";
             } else {
                 return "error";
             }
         }

        return "common/404";
    }
}