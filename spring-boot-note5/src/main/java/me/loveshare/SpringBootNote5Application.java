package me.loveshare;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = "me.loveshare")
public class SpringBootNote5Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNote5Application.class, args);
        log.info("\n--------------------------------Spring boot note5 application start successful.--------------------------------\n");
    }
}
