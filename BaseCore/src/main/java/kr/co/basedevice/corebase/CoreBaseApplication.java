package kr.co.basedevice.corebase;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableBatchProcessing
@EnableJpaAuditing
@SpringBootApplication
public class CoreBaseApplication {

    public static void main(String[] args) {

        SpringApplication.run(CoreBaseApplication.class, args);
    }

}
