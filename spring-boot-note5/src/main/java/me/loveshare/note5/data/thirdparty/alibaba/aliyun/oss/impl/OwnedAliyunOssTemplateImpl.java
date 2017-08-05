package me.loveshare.note5.data.thirdparty.alibaba.aliyun.oss.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import me.loveshare.note5.data.util.DBUtils;
import me.loveshare.note5.data.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云私有存储服务类  --私有
 */
@Component("ownedOSSTemplate")
public class OwnedAliyunOssTemplateImpl extends AbstractAliyunOssTemplateImpl {
    //log
    private static final Logger LOG = LoggerFactory.getLogger(OwnedAliyunOssTemplateImpl.class);
    public static final String FLAG = "Owned";

    @Resource(name = "ownedOSS")
    private OSSClient ownedOSS;

    public OSSClient getOSSClient() {
        return ownedOSS;
    }

    public Logger getLogger() {
        return LOG;
    }

    protected String getFLAG() {
        return FLAG;
    }

    @Override
    public String sign(String owenedUrl, String bucketName, String key, Integer minutes) {
        if (StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(key)) return null;
        String resUrl = generateASignURL(bucketName, key, minutes);
        return resUrl.replace(resUrl.substring(0, resUrl.indexOf(".com/") + 5), owenedUrl);
    }

    @Override
    public String sign(String url, Integer minutes) {
        minutes = DBUtils.numberBlank(minutes) ? 1 : minutes;
        String bucketName = url.substring(url.indexOf("://") + 3);
        bucketName = bucketName.substring(0, bucketName.indexOf("."));
        String resUrl = generateASignURL(bucketName, url.substring(url.indexOf(".com/") + 5), minutes);
        url = resUrl.replace(resUrl.substring(0, resUrl.indexOf(".com/") + 5), resUrl.substring(0, resUrl.indexOf(".com/") + 5));
        return url;
    }

    private String generateASignURL(String bucketName, String key, Integer minutes) {
        Date expires = new Date(new Date().getTime() + 1000 * 60 * minutes);
        if (key.startsWith("/")) key = key.substring(1);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);
        generatePresignedUrlRequest.setExpiration(expires);
        URL url = getOSSClient().generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}
