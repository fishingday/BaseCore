package kr.co.basedevice.corebase.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import kr.co.basedevice.corebase.security.common.FormWebAuthenticationDetailsSource;
import kr.co.basedevice.corebase.security.configs.AjaxLoginConfigurer;
import kr.co.basedevice.corebase.security.factory.UrlResourcesMapFactoryBean;
import kr.co.basedevice.corebase.security.handler.AjaxAuthenticationFailureHandler;
import kr.co.basedevice.corebase.security.handler.AjaxAuthenticationSuccessHandler;
import kr.co.basedevice.corebase.security.handler.FormAccessDeniedHandler;
import kr.co.basedevice.corebase.security.handler.LogoutSuccessHandler;
import kr.co.basedevice.corebase.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import kr.co.basedevice.corebase.security.provider.AjaxAuthenticationProvider;
import kr.co.basedevice.corebase.security.provider.FormAuthenticationProvider;
import kr.co.basedevice.corebase.security.service.SecurityResourceService;
import kr.co.basedevice.corebase.security.voter.IpAddressVoter;
import kr.co.basedevice.corebase.service.common.LoggingService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	
	@Value("${login.set.login-page:/common/login.html}")
	private String loginPage;
	
	@Value("${login.set.login-login-processing-url:/common/login_proc.html}")
	private String loginProcessingUrl;
	
	@Value("${login.set.access-denied-page:/common/denied.html}")
	private String accessDeniedPage;
	
	@Value("${login.set.logout-page:/common/logout.html}")
	private String logoutPage;
	
	@Value("${login.username-parameter:username}")
	private String usernameParameter;
	
	@Value("${login.password-parameter:password}")
	private String passwordParameter;
	
	final private LoggingService cmImprtantLogService;
	final private FormWebAuthenticationDetailsSource formWebAuthenticationDetailsSource;
	final private AuthenticationSuccessHandler formAuthenticationSuccessHandler;
	final private AuthenticationFailureHandler formAuthenticationFailureHandler;
	final private LogoutSuccessHandler logoutSuccessHandler;
	final private UserDetailsService userDetailsService;
	final private SecurityResourceService securityResourceService;
	final private PasswordEncoder passwordEncoder;
	final private AuthenticationConfiguration authenticationConfiguration;
	final private AuthenticationManagerBuilder authenticationManagerBuilder;
	    
//    @Bean 
//    AuthenticationManagerBuilder authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider(passwordEncoder));
//        auth.authenticationProvider(ajaxAuthenticationProvider(passwordEncoder));
//        return auth;
//    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	authenticationManagerBuilder.authenticationProvider(authenticationProvider(passwordEncoder));
    	authenticationManagerBuilder.authenticationProvider(ajaxAuthenticationProvider(passwordEncoder));
    	
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
        .and()
                .formLogin()
                .loginPage(this.loginPage)
                .usernameParameter(this.usernameParameter)
                .passwordParameter(this.passwordParameter)
                .loginProcessingUrl(this.loginProcessingUrl)
                .authenticationDetailsSource(formWebAuthenticationDetailsSource) // 추가 파라메터
                .successHandler(formAuthenticationSuccessHandler)
                .failureHandler(formAuthenticationFailureHandler)
                .permitAll()
        .and()
        		.logout()
        		.logoutUrl(logoutPage)
        		.logoutSuccessHandler(logoutSuccessHandler)
        .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(this.loginPage))
                .accessDeniedPage(this.accessDeniedPage)
                .accessDeniedHandler(accessDeniedHandler())
        .and()
        		.addFilterBefore(customFilterSecurityInterceptor(),  FilterSecurityInterceptor.class);
        
/*        
        http.sessionManagement()
   	 		.maximumSessions(1)                 // 최대 허용 가능 세션 수 , -1 : 무제한 로그인 세션 허용
   	 		.maxSessionsPreventsLogin(false) 	// 동시 로그인 차단함,  false : 기존 세션 만료(default)
   	 		.invalidSessionUrl("/invalid")      // 세션이 유효하지 않을 때 이동 할 페이지
   	 		.expiredUrl("/expired ");  	        // 세션이 만료된 경우 이동 할 페이지
*/

        http.rememberMe() // rememberMe 기능 작동함
        	.rememberMeParameter("remember-me") // default: remember-me, checkbox 등의 이름과 맞춰야함
        	//.tokenValiditySeconds(86400 * 30) // 쿠키의 만료시간 설정(초), default: 14일
        	.alwaysRemember(false) // 사용자가 체크박스를 활성화하지 않아도 항상 실행, default: false
        	.userDetailsService(userDetailsService) // 기능을 사용할 때 사용자 정보가 필요함. 반드시 이 설
        	.authenticationSuccessHandler(formAuthenticationSuccessHandler);
        
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        
        customConfigurer(http);
        
        return http.build();
    }
    
    /*
     * 참고 : https://github.com/spring-projects/spring-security/issues/10938
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
    	//... /css/**, /js/**,/images/**,/webjars,/favicon.*,/*/icon-*
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    private void customConfigurer(HttpSecurity http) throws Exception {
        http
                .apply(new AjaxLoginConfigurer<>())
                .successHandlerAjax(ajaxAuthenticationSuccessHandler())
                .failureHandlerAjax(ajaxAuthenticationFailureHandler())
                .loginProcessingUrl("/api/login")
                .setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
    }

    @Bean
    AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder){
        return new FormAuthenticationProvider(passwordEncoder);
    }
    
    @Bean
    AuthenticationProvider ajaxAuthenticationProvider(PasswordEncoder passwordEncoder){
        return new AjaxAuthenticationProvider(passwordEncoder);
    }

    @Bean
    AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler(){
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler(){
        return new AjaxAuthenticationFailureHandler();
    }

    public AccessDeniedHandler accessDeniedHandler() {
        FormAccessDeniedHandler commonAccessDeniedHandler = new FormAccessDeniedHandler();
        commonAccessDeniedHandler.setErrorPage(accessDeniedPage);
        commonAccessDeniedHandler.setLoggingService(cmImprtantLogService);
        return commonAccessDeniedHandler;
    }
    
    @Bean
    AccessDecisionManager affirmativeBased() {
        AffirmativeBased accessDecisionManager = new AffirmativeBased(getAccessDecisionVoters());
        return accessDecisionManager;
    }
    
    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        return Arrays.asList(new IpAddressVoter(), new RoleVoter());
    }
    
    @Bean
    FilterInvocationSecurityMetadataSource urFilterInvocationSecurityMetadataSource() {
    	return new UrlFilterInvocationSecurityMetadataSource(urlResourcesMapFactoryBean().getObject());
    }
    
    @Bean
    UrlResourcesMapFactoryBean urlResourcesMapFactoryBean(){
        UrlResourcesMapFactoryBean urlResourcesMapFactoryBean = new UrlResourcesMapFactoryBean();
        urlResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);
        return urlResourcesMapFactoryBean;
    }
    
    @Bean
    FilterSecurityInterceptor customFilterSecurityInterceptor() {
    	
    	FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
    	filterSecurityInterceptor.setSecurityMetadataSource(urFilterInvocationSecurityMetadataSource());
    	filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
    	
		return filterSecurityInterceptor;    	
    }
}