package me.loveshare.note1.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by tony_tian on 2017/7/8.
 * Druid数据库连接池(数据源)配置参数
 */
@Data
@ConfigurationProperties(prefix = "datasource")
public class DataSourceProperties {

    // 数据库驱动配置信息
    private String type;
    private String url;
    private String username;
    private String password;
    private String driver;

    // 数据库连接池配置信息
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private long maxEvictableIdleTimeMillis;
    private boolean removeAbandoned;
    private long removeAbandonedTimeoutMillis;
    private long timeBetweenConnectErrorMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxOpenPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private boolean logAbandoned;
    private String filters;
    private String connectionProperties;

    //连接池监控配置
    private String allow;
    private String deny;
    private String loginUsername;
    private String loginPassword;
    private String resetEnable;
}
