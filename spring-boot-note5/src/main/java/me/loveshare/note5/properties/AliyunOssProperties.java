package me.loveshare.note5.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Tony on 2017/8/4.
 */
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssProperties {

    private String accessKeyId;
    private String accessKeySecret;

    private String openedEndpoint;
    private String ownedEndpoint;

    private String ownedBucketName1;
    private String ownedURL1;
    private String openedBucketName1;
    private String openedURL1;
}
