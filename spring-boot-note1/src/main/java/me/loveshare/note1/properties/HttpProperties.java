package me.loveshare.note1.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Tony on 2017/7/28.
 */
@Data
@ConfigurationProperties(prefix = "http")
public class HttpProperties {

    private Integer port;
}
