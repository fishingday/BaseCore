package kr.co.basedevice.corebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CoreBaseApplication {

    public static void main(String[] args) {

        SpringApplication.run(CoreBaseApplication.class, args);
    }

}
