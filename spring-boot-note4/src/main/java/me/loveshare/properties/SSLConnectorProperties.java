package me.loveshare.properties;

import lombok.Data;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.nio.charset.Charset;

/**
 * Created by Tony on 2017/7/28.
 */
@Data
@ConfigurationProperties(prefix = "https")
public class SSLConnectorProperties {

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.setUriEncoding(Charset.forName("UTF-8"));
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    public Connector createSslConnector() {

        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();

        connector.setPort(port);
        connector.setScheme("https");
        connector.setSecure(true);
        protocol.setSSLEnabled(true);
        protocol.setClientAuth("false");
        protocol.setSSLProtocol("TLSv1+TLSv1.1+TLSv1.2");
        protocol.setKeystoreFile(keystoreFile);
        protocol.setKeystorePass(keystorePassword);
        return connector;
    }

    private Integer port;
    private String keystoreFile;
    private String keystorePassword;
}
