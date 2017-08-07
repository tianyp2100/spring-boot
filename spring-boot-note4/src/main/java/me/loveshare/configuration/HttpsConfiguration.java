package me.loveshare.configuration;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.properties.HttpsProperties;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
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
@EnableConfigurationProperties(HttpsProperties.class)
public class HttpsConfiguration {

    @Autowired
    private HttpsProperties properties;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.setUriEncoding(Charset.forName("UTF-8"));
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        log.info("\n*** Tomcat SSL setting successful." + properties.getPort());
        return tomcat;
    }

    public Connector createSslConnector() {

        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();

        connector.setPort(properties.getPort());
        connector.setScheme("https");
        connector.setSecure(true);
        protocol.setSSLEnabled(true);
        protocol.setClientAuth("false");
        protocol.setSSLProtocol("TLSv1+TLSv1.1+TLSv1.2");
        protocol.setKeystoreFile(properties.getKeystoreFile());
        protocol.setKeystorePass(properties.getKeystorePassword());
        return connector;
    }
}
