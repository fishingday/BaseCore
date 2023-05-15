package kr.co.basedevice.corebase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.basedevice.corebase.interceptor.SessionTracerInterceptor;
import kr.co.basedevice.corebase.security.service.SecurityResourceService;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	private String [] excludePathPatternArray = {"/common/**", "/css/**", "/images/**", "/js/**"};
	
	@Autowired private SecurityResourceService securityResourceService;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionTracerInterceptor(securityResourceService.getResourceList()))
			.addPathPatterns("/**")
			.excludePathPatterns(excludePathPatternArray);
	}
}
