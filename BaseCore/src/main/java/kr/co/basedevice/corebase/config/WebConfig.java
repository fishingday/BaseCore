package kr.co.basedevice.corebase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.basedevice.corebase.interceptor.SessionTracerInterceptor;
import kr.co.basedevice.corebase.security.service.SecurityResourceService;
import kr.co.basedevice.corebase.service.common.LoggingService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer{
	
	private String [] excludePathPatternArray = {"/common/**", "/css/**", "/images/**", "/js/**", "/scheduler/**"};
	
	final private SecurityResourceService securityResourceService;
	
	final private LoggingService loggingService;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionTracerInterceptor(securityResourceService.getMenuMap(), loggingService))
			.addPathPatterns("/**")
			.excludePathPatterns(excludePathPatternArray);
	}
}
