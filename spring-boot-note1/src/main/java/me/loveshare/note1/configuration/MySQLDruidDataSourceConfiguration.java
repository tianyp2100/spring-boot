package me.loveshare.note1.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.note1.properties.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by tony_tian on 2017/7/8.
 * MySQL数据库驱动配置信息.<br/>
 * Druid连接池(数据源)配置信息.<br/>
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class MySQLDruidDataSourceConfiguration {

    @Autowired
    private DataSourceProperties properties;

    @Bean
    @Primary
    public DataSource dataSource() {
        // 数据库驱动配置信息
        DruidDataSource datasource = new DruidDataSource();
        datasource.setDbType(properties.getType());
        datasource.setUrl(properties.getUrl());
        datasource.setUsername(properties.getUsername());
        datasource.setPassword(properties.getPassword());
        datasource.setDriverClassName(properties.getDriver());

        // 数据库连接池配置信息
        datasource.setMaxWait(properties.getMaxWait());
//        datasource.setInitialSize(properties.getInitialSize());  //配置后, junit将不能执行
        datasource.setMinIdle(properties.getMinIdle());
        datasource.setMaxActive(properties.getMaxActive());
        datasource.setMaxWait(properties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        datasource.setMaxEvictableIdleTimeMillis(properties.getMaxEvictableIdleTimeMillis());
        datasource.setRemoveAbandoned(properties.isRemoveAbandoned());
        datasource.setRemoveAbandonedTimeoutMillis(properties.getRemoveAbandonedTimeoutMillis());
        datasource.setTimeBetweenConnectErrorMillis(properties.getTimeBetweenConnectErrorMillis());
        datasource.setValidationQuery(properties.getValidationQuery());
        datasource.setTestWhileIdle(properties.isTestWhileIdle());
        datasource.setTestOnBorrow(properties.isTestOnBorrow());
        datasource.setTestOnReturn(properties.isTestOnReturn());
        datasource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        datasource.setMaxOpenPreparedStatements(properties.getMaxOpenPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
        datasource.setLogAbandoned(properties.isLogAbandoned());
        try {
            datasource.setFilters(properties.getFilters());
        } catch (SQLException e) {
            log.error("Druid setting filters exception: " + e.getMessage(), e);
        }
        datasource.setConnectionProperties(properties.getConnectionProperties());
        log.info("\n*** Initialize MySQL Druid datasource successful." + properties.getUrl());
        return datasource;
    }
}
