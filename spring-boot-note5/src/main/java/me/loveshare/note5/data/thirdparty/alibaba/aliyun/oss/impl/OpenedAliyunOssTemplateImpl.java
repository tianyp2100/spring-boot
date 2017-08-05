package me.loveshare.note5.data.thirdparty.alibaba.aliyun.oss.impl;

import com.aliyun.oss.OSSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 阿里云公有存储服务类 --公开
 */
@Component("openedOSSTemplate")
public class OpenedAliyunOssTemplateImpl extends AbstractAliyunOssTemplateImpl {

    //log
    private static final Logger LOG = LoggerFactory.getLogger(OpenedAliyunOssTemplateImpl.class);
    public static final String FLAG = "Opened";

    @Resource(name = "openedOSS")
    private OSSClient openedOSS;

    public OSSClient getOSSClient(){
        return openedOSS;
    }

    public Logger getLogger(){
        return LOG;
    }

    protected String getFLAG(){
        return FLAG;
    }
}
