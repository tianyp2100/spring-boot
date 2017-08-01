package me.loveshare;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication(scanBasePackages = "me.loveshare")
@EnableTransactionManagement
@MapperScan(basePackages = {"me.loveshare.note1.data.dao"})
public class SpringBootNote1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNote1Application.class, args);
        log.info("\n--------------------------------Spring boot note1 application start successful.--------------------------------\n");
    }
}
