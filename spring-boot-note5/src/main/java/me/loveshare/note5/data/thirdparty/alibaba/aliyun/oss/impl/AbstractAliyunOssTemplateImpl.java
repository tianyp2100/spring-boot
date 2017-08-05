package me.loveshare.note5.data.thirdparty.alibaba.aliyun.oss.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import me.loveshare.note5.data.entity.bo.OSSBatchUploadArgs;
import me.loveshare.note5.data.exception.TSException;
import me.loveshare.note5.data.thirdparty.alibaba.aliyun.oss.AliyunOssTemplateI;
import me.loveshare.note5.data.util.CollectionUtils;
import me.loveshare.note5.data.util.InputStreamUtils;
import me.loveshare.note5.data.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 2017/6/7.
 * 阿里云OSS操作抽象父类
 */
public class AbstractAliyunOssTemplateImpl implements AliyunOssTemplateI {
    protected String getFLAG() {
        return null;
    }

    protected OSSClient getOSSClient() {
        return null;
    }

    protected Logger getLogger() {
        return null;
    }

    @Override
    public boolean createBucket(String bucketName) {
        try {
            Bucket bucket = getOSSClient().createBucket(bucketName);
            getLogger().info("[" + getFLAG() + "]:Aliyun Oss public create bucket successful.bucketName:" + bucketName);
            bucketName.equals(bucket.getName());
            return true;
        } catch (Exception e) {
            getLogger().error("[" + getFLAG() + "]:Aliyun Oss public create bucket exception.bucketName:" + bucketName);
            return false;
        }
    }

    @Override
    public boolean deleteBucket(String bucketName) {
        try {
            getOSSClient().deleteBucket(bucketName);
            getLogger().info("[" + getFLAG() + "]:Aliyun Oss public delete bucket successful.bucketName:" + bucketName);
            return true;
        } catch (Exception e) {
            getLogger().error("[" + getFLAG() + "]:Aliyun Oss public delete bucket exception.bucketName:" + bucketName);
            return false;
        }
    }

    @Override
    public String upload(String bucketName, String diskName, String fileName, byte[] byteArys) throws TSException {
        return uploadTemp(bucketName, diskName, fileName, InputStreamUtils.toInputStream(byteArys));
    }

    @Override
    public String upload(String bucketName, String diskName, String fileName, InputStream is) throws TSException {
        return uploadTemp(bucketName, diskName, fileName, is);
    }

    @Override
    public List<String> batchupload(List<OSSBatchUploadArgs> args) throws TSException {
        if (CollectionUtils.isEmptyList(args)) return null;
        List<String> res = new ArrayList<String>();

        getLogger().info("[" + getFLAG() + "]:Aliyun Oss batch upload start...");
        OSSClient client = getOSSClient();
        for (int i = 0, len = args.size(); i < len; i++) {
            getLogger().info("[" + getFLAG() + "]:Aliyun Oss batch upload start.index[" + i + "]...");
            OSSBatchUploadArgs arg = args.get(i);
            InputStream is = arg.getByteArys() == null ? arg.getIs() : InputStreamUtils.toInputStream(arg.getByteArys());
            res.add(putObjectTemp(client, arg.getBucketName(), arg.getDiskName(), arg.getFileName(), is));
        }
        getLogger().info("[" + getFLAG() + "]:Aliyun Oss batch upload end, successful.");
        return res;
    }

    @Override
    public boolean isExist(String bucketName, String diskName, String fileName) {
        return getOSSClient().getObject(bucketName, diskName + fileName) != null;
    }

    @Override
    public String copy(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        String resultStr = null;
        if (StringUtils.isEmpty(srcBucketName) || StringUtils.isEmpty(srcKey) || StringUtils.isEmpty(destBucketName) || StringUtils.isEmpty(destKey)) {
            return resultStr;
        }
        CopyObjectResult putResult;
        try {
            putResult = getOSSClient().copyObject(srcBucketName, srcKey, destBucketName, destKey);
            resultStr = putResult.getETag();
        } catch (Exception e) {
            getLogger().error("[" + getFLAG() + "]:Aliyun Oss copy exception: " + e.getMessage(), e);
        }
        return resultStr;
    }

    private String uploadTemp(String bucketName, String diskName, String fileName, InputStream is) throws TSException {
        getLogger().info("[" + getFLAG() + "]:Aliyun Oss upload start...");
        String resultStr = null;
        if (verifyParams(bucketName, diskName, fileName, is)) {
            return resultStr;
        }
        getLogger().info("[" + getFLAG() + "]:Aliyun Oss params check ok...");
        resultStr = putObjectTemp(getOSSClient(), bucketName, diskName, fileName, is);
        getLogger().info("[" + getFLAG() + "]:Aliyun Oss upload end, successful.");
        return resultStr;
    }

    @Override
    public byte[] download(String bucketName, String diskName, String fileName) {
        try {
            return IOUtils.toByteArray(getOSSClient().getObject(bucketName, diskName + fileName).getObjectContent());
        } catch (IOException e) {
            getLogger().error("[" + getFLAG() + "]:Aliyun Oss download byte array exception: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public InputStream downloadIs(String bucketName, String diskName, String fileName) {
        return getOSSClient().getObject(bucketName, diskName + fileName).getObjectContent();
    }

    @Override
    public boolean delete(String bucketName, String diskName, String fileName) {
        try {
            getOSSClient().deleteObject(bucketName, diskName + fileName);
            return true;
        } catch (Exception e) {
            getLogger().error("[" + getFLAG() + "]:Aliyun Oss file delete exception: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<String> listObject(String bucketName, String delimiter, String prefix, Integer pageIndex, Integer pageSize) {

        if (StringUtils.isEmpty(bucketName)) return null;

        pageIndex = pageIndex == 0 ? 1 : pageIndex;
        pageSize = pageSize == 0 ? 10 : pageSize;

        // 是否循环的标识
        boolean hasNext = false;
        // 设定结果从Marker之后按字母排序的第一个开始返回
        String marker = "";
        //
        // ObjectListing listing = new ObjectListing();
        List<String> filePathList = new ArrayList<String>();
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        // 是一个用于对Object名字进行分组的字符。所有名字包含指定的前缀且第一次出现Delimiter字符之间的object作为一组元素:
        // CommonPrefixes
        listObjectsRequest.setDelimiter(delimiter);
        // 限定此次返回object的最大数，如果不设定，默认为100，MaxKeys取值不能大于1000
        listObjectsRequest.setMaxKeys(pageSize);
        // 限定返回的object key必须以Prefix作为前缀。注意使用prefix查询时，返回的key中仍会包含Prefix
        listObjectsRequest.setPrefix(prefix);

        int count = 1;
        do {
            // 设定结果从Marker之后按字母排序的第一个开始返回
            listObjectsRequest.setMarker(marker);
            // 获取指定bucket下的所有Object信息
            ObjectListing sublisting = getOSSClient().listObjects(listObjectsRequest);
            // 如果Bucket中的Object数量大于100，则只会返回100个Object， 且返回结果中 IsTruncated
            // 为false
            if (sublisting.isTruncated()) {
                hasNext = true;
                marker = sublisting.getNextMarker();
            } else {
                hasNext = false;
                marker = "";
            }
            if (pageIndex == count) {
                // // 遍历所有Object
                for (OSSObjectSummary objectSummary : sublisting.getObjectSummaries()) {
                    // System.out.println(objectSummary.getKey());
                    if (objectSummary.getKey().endsWith("/")) continue;  //去除文件夹的显示
                    filePathList.add(objectSummary.getKey());
                }
                return filePathList;
            }
            count++;
        } while (hasNext);

        return null;
    }

    @Override
    public String sign(String owenedUrl, String bucketName, String key, Integer minutes) {
        return null;
    }

    @Override
    public String sign(String url, Integer minutes) {
        return null;
    }

    protected String putObjectTemp(OSSClient client, String bucketName, String diskName, String fileName, InputStream is) throws TSException {
        try {
            int len = is.available();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(len);
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
//        metadata.setContentType(getContentType(fileName));  //<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
            metadata.setContentType(null);
//        metadata.setContentDisposition("attachment;filename="+fileName);  //Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + len + "Byte.");
            //Content-Language 响应体的语言 <meta http-equiv="Content-Language" content="zh-cn">
            //Expires 响应过期的日期和时间
            //上传文件 --解析结果
            return client.putObject(bucketName, diskName + fileName, is, metadata).getETag();
        } catch (Exception e) {
            throw new TSException("阿里云对象存储资源数据上传失败：" + e.getMessage(), e);
        }
    }

    protected boolean verifyParams(String bucketName, String diskName, String fileName, InputStream is) {
        return is == null || StringUtils.isEmpty(bucketName) || StringUtils.isEmpty(diskName) || StringUtils.isEmpty(fileName);
    }
}
