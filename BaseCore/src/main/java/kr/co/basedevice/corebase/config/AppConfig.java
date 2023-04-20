package kr.co.basedevice.corebase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.basedevice.corebase.repository.ResourcesRepository;
import kr.co.basedevice.corebase.service.SecurityResourceService;

@Configuration
public class AppConfig {

    @Bean
    SecurityResourceService securityResourceService(ResourcesRepository resourcesRepository) {
        SecurityResourceService SecurityResourceService = new SecurityResourceService(resourcesRepository);
        return SecurityResourceService;
    }
}
