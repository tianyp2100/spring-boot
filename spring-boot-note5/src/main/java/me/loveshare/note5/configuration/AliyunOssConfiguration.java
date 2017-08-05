package me.loveshare.note5.configuration;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.note5.properties.AliyunOssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 对象存储操作客户端
 */
@Data
@Slf4j
@Configuration
@EnableConfigurationProperties(AliyunOssProperties.class)
public class AliyunOssConfiguration {

    @Autowired
    private AliyunOssProperties properties;

    /**
     * 阿里云私有对象存储
     */
    @Primary
    @Bean(name = "ownedOSS")
    public OSSClient ownedOSS() {
        return new OSSClient(properties.getOwnedEndpoint(), properties.getAccessKeyId(), properties.getAccessKeySecret());
    }

    /**
     * 阿里云公有对象存储
     */
    @Primary
    @Bean(name = "openedOSS")
    public OSSClient openedOSS() {
        log.info("\nOSSClient setting:" + JSON.toJSONString(properties.getOpenedURL1()));
        return new OSSClient(properties.getOpenedEndpoint(), properties.getAccessKeyId(), properties.getAccessKeySecret());
    }
}
