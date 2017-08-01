package me.loveshare.note1.configuration;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.note1.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Tony on 2017/2/11.<br/>
 * Swagger2提供了接口页面文档化和强大的页面测试功能来调试每个RESTful API.<br/>
 * 查看: http://yourdomain/swagger-ui.html
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfiguration {

    @Autowired
    private SwaggerProperties properties;

    @Bean
    @Primary
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getApi()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        log.info("\n*** Initialize Swagger RESTful API successful." + properties.getVersion());
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(description())
                .version(properties.getVersion())
                .build();
    }

    private String description() {
        StringBuilder su = new StringBuilder("温馨提示：");
        su.append(properties.getDescription()).append("，作者：").append("创建时间：").append(properties.getGmt()).append("(●'◡'●)。");
        return su.toString();
    }
}
