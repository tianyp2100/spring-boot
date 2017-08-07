package me.loveshare.note5.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Tony on 2017/7/28.
 */
@Data
@ConfigurationProperties(prefix = "https")
public class HttpsProperties {

    private Integer port;
    private String keystoreFile;
    private String keystorePassword;
}
