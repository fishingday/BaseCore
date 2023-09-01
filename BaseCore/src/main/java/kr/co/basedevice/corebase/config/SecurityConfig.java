package kr.co.basedevice.corebase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import kr.co.basedevice.corebase.security.common.FormWebAuthenticationDetailsSource;
import kr.co.basedevice.corebase.security.configs.AjaxLoginConfigurer;
import kr.co.basedevice.corebase.security.handler.AjaxAuthenticationFailureHandler;
import kr.co.basedevice.corebase.security.handler.AjaxAuthenticationSuccessHandler;
import kr.co.basedevice.corebase.security.handler.FormAccessDeniedHandler;
import kr.co.basedevice.corebase.security.handler.LogoutSuccessHandler;
import kr.co.basedevice.corebase.security.provider.AjaxAuthenticationProvider;
import kr.co.basedevice.corebase.security.provider.FormAuthenticationProvider;
import kr.co.basedevice.corebase.service.common.LoggingService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
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
	
	@Autowired
	private LoggingService cmImprtantLogService;

    @Autowired
    private FormWebAuthenticationDetailsSource formWebAuthenticationDetailsSource;
    
    @Autowired
    private AuthenticationSuccessHandler formAuthenticationSuccessHandler;
    
    @Autowired
    private AuthenticationFailureHandler formAuthenticationFailureHandler;
    
    @Autowired
    private FilterSecurityInterceptor customFilterSecurityInterceptor;
    
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        auth.authenticationProvider(ajaxAuthenticationProvider());
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
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
                .authenticationDetailsSource(formWebAuthenticationDetailsSource)
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
                .addFilterAt(customFilterSecurityInterceptor, FilterSecurityInterceptor.class);
/*        
        http.sessionManagement()
   	 		.maximumSessions(1)                 // 최대 허용 가능 세션 수 , -1 : 무제한 로그인 세션 허용
   	 		.maxSessionsPreventsLogin(false) 	// 동시 로그인 차단함,  false : 기존 세션 만료(default)
   	 		.invalidSessionUrl("/invalid")      // 세션이 유효하지 않을 때 이동 할 페이지
   	 		.expiredUrl("/expired ");  	        // 세션이 만료된 경우 이동 할 페이지
*/

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        customConfigurer(http);
    }

    private void customConfigurer(HttpSecurity http) throws Exception {
        http
                .apply(new AjaxLoginConfigurer<>())
                .successHandlerAjax(ajaxAuthenticationSuccessHandler())
                .failureHandlerAjax(ajaxAuthenticationFailureHandler())
                .loginProcessingUrl("/api/login")
                .setAuthenticationManager(authenticationManagerBean());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        return new FormAuthenticationProvider(passwordEncoder());
    }

    @Bean
    AuthenticationProvider ajaxAuthenticationProvider(){
        return new AjaxAuthenticationProvider(passwordEncoder());
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
}
