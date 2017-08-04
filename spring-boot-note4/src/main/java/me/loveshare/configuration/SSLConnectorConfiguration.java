package me.loveshare.configuration;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.properties.SSLConnectorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

/**
 * Created by Tony on 2017/1/14.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SSLConnectorProperties.class)
public class SSLConnectorConfiguration {

    @Autowired
    private SSLConnectorProperties properties;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.setUriEncoding(Charset.forName("UTF-8"));
        tomcat.addAdditionalTomcatConnectors(properties.createSslConnector());
        log.info("\n*** Tomcat SSL setting successful." + properties.getPort());
        return tomcat;
    }
}
