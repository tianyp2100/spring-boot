package me.loveshare.note1.configuration;

import io.undertow.Undertow;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.note1.properties.HttpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tony on 2017/1/14.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(HttpProperties.class)
public class HttpConfiguration {

    @Autowired
    private HttpProperties properties;

    @Bean
    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory undertow = new UndertowEmbeddedServletContainerFactory();
        undertow.addBuilderCustomizers((Undertow.Builder builder) -> {
            builder.addHttpListener(properties.getPort(), "0.0.0.0");
        });
        log.info("\n*** Undertow http setting successful." + properties.getPort());
        return undertow;
    }
}
