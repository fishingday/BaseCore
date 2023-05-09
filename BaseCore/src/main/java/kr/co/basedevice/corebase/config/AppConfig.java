package kr.co.basedevice.corebase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;
import kr.co.basedevice.corebase.security.service.SecurityResourceService;

@Configuration
public class AppConfig {

    @Bean
    SecurityResourceService securityResourceService(CmMenuRepository cmMenuRepository) {
        SecurityResourceService SecurityResourceService = new SecurityResourceService(cmMenuRepository);
        return SecurityResourceService;
    }
}
