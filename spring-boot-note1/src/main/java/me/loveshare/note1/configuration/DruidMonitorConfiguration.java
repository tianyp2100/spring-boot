package me.loveshare.note1.configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.note1.properties.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tony on 2017/3/22.
 * Druid数据库连接池(数据源)监控配置.<br/>
 * 查看: http://yourdomain/druid/index.html
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DruidMonitorConfiguration {

    @Autowired
    private DataSourceProperties properties;

    /**
     * 监控配置
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //白名单：
        servletRegistrationBean.addInitParameter("allow", properties.getAllow());
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", properties.getDeny());
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", properties.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", properties.getLoginPassword());
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", properties.getResetEnable());
        log.info("\n*** Initialize Druid monitor successful." + properties.getAllow());
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
