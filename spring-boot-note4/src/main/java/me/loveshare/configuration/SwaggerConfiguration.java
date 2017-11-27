package me.loveshare.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
 * 查看文档地址: http://yourdomain/swagger-ui.html
 */
@Data
@Slf4j
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfiguration {

    @Bean
    @Primary
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(api))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfo api = new ApiInfoBuilder()
                .title(title)
                .description(description())
                .version(version)
                .build();
        log.info("\n*** Initialize Swagger2 RESTful API successful: " + this.api);
        return api;
    }

    private String description() {
        StringBuilder su = new StringBuilder("温馨提示：");
        su.append(description).append("，作者：").append(author).append("，创建时间：").append(gmt).append("(●'◡'●)。\n").append("服务器业务状态码归总：").append("http://git.hxwise.com/hxwisec/docs/blob/ishare/biz-rule/JsonResultBizCode.md");
        return su.toString();
    }

    private String api;
    private String title;
    private String description;
    private String version;
    private String author;
    private String gmt;
}
