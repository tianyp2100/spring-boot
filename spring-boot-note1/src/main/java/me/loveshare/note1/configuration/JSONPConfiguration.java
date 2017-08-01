package me.loveshare.note1.configuration;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Created by Tony on 2017/8/1.
 * Spring Boot支持跨域请求的JSONP数据.
 */
@ControllerAdvice(basePackages = {"me.loveshare.note1.api"})
public class JSONPConfiguration extends AbstractJsonpResponseBodyAdvice {

    public JSONPConfiguration() {
        super("callback", "jsonp");
    }
}