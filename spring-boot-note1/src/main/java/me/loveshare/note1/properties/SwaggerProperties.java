package me.loveshare.note1.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Tony on 2017/8/1.
 * api docs配置参数.
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String api;
    private String title;
    private String description;
    private String version;
    private String author;
    private String gmt;
}
