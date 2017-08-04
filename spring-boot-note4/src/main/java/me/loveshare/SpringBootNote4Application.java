package me.loveshare;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = "me.loveshare")
public class SpringBootNote4Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNote4Application.class, args);
        log.info("\n--------------------------------Spring boot note4 application start successful.--------------------------------\n");
    }
}
