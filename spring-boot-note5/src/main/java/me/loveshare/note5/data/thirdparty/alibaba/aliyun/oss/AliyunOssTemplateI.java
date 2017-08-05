package me.loveshare.note5.data.thirdparty.alibaba.aliyun.oss;

import me.loveshare.note5.data.entity.bo.OSSBatchUploadArgs;
import me.loveshare.note5.data.exception.TSException;

import java.io.InputStream;
import java.util.List;


/**
 * 阿里云存储服务接口
 * <blockquote><pre>
 *    client 阿里云对象存储操作对象
 *    bucketName bucket名称
 *    diskName 上传文件的目录  --bucket下文件的路径
 *    fileName 文件名称
 *    is 文件流
 *    byteArys 文件字节
 * </pre></blockquote>
 *
 * @author Tony 2017-03-07
 */
public interface AliyunOssTemplateI {
    /**
     * 创建oss存储节点Bucket
     */
    boolean createBucket(String bucketName);

    /**
     * 删除oss存储节点Bucket
     */
    boolean deleteBucket(String bucketName);

    /**
     * 对象存储上传文件  --上传和更新是一样的操作，自动替换全路径相同的文件
     *
     * @param bucketName bucket名称
     * @param diskName   上传文件的目录  --bucket下文件的路径
     * @param fileName   文件名称
     * @param byteArys   上传字节流     注：InputStream is：dubbo-hession协议io流必须为最后一个参数 ,直接传递为0，则转换byte数组传递
     *                   注：转换: see class com.hxwisec.util.io.InputStreamUtils
     * @return String 唯一MD5数字签名  null上传失败
     */
    String upload(String bucketName, String diskName, String fileName, byte[] byteArys) throws TSException;

    /**
     * 对象存储上传文件  --上传和更新是一样的操作，自动替换全路径相同的文件
     *
     * @param bucketName bucket名称
     * @param diskName   上传文件的目录  --bucket下文件的路径
     * @param fileName   文件名称
     * @param is         上传输入流
     * @return String 唯一MD5数字签名  null上传失败
     */
    String upload(String bucketName, String diskName, String fileName, InputStream is) throws TSException;

    /**
     * 批量上传
     *
     * @param args 所有参数为必须项
     * @tips 参数请使用分页(分片)，实现多批量上传，不可一次上传太多
     */
    List<String> batchupload(List<OSSBatchUploadArgs> args) throws TSException;

    /**
     * 查看文件是否存在
     *
     * @param bucketName bucket名称
     * @param diskName   上传文件的目录  --bucket下文件的路径
     * @param fileName   文件名称
     */
    boolean delete(String bucketName, String diskName, String fileName);

    /**
     * 下载oss存储的文件
     *
     * @param bucketName bucket名称
     * @param diskName   上传文件的目录  --bucket下文件的路径
     * @param fileName   文件名称
     */
    byte[] download(String bucketName, String diskName, String fileName);

    /**
     * 下载oss存储的文件
     *
     * @param bucketName bucket名称
     * @param diskName   上传文件的目录  --bucket下文件的路径
     * @param fileName   文件名称
     */
    InputStream downloadIs(String bucketName, String diskName, String fileName);

    /**
     * 查看文件是否存在
     *
     * @param bucketName bucket名称
     * @param diskName   上传文件的目录  --bucket下文件的路径
     * @param fileName   文件名称
     * @return true 存在
     */
    boolean isExist(String bucketName, String diskName, String fileName);

    /**
     * 拷贝/重命名   --源文件不删除
     *
     * @param srcBucketName  源bucket名称
     * @param srcKey         源文件全路径
     * @param destBucketName 目标bucket名称
     * @param destKey        true 目标文件全路径
     */
    String copy(String srcBucketName, String srcKey, String destBucketName, String destKey);

    /**
     * 获取私有Bucket中的数据的URL.<br>
     *
     * @param owenedUrl  资源的url, 默认为私有的BucketName.  url不为空，bucketName和key可不填写
     * @param bucketName bucket名称 eg: prihxza
     * @param key        = diskName + fileName eg: exam_liberary/exam_images/9892611323232/answer.jpg
     * @param minutes    签名url的有效时长, 单位: min
     * @return sign_url 签名url
     */
    String sign(String owenedUrl, String bucketName, String key, Integer minutes);

    /**
     * 获取私有Bucket中的数据的URL.<br>
     *
     * @param url     资源的url --并且为阿里云默认的url地址.  url不为空
     * @param minutes 签名url的有效时长, 单位: min
     */
    String sign(String url, Integer minutes);

    /**
     * 列出Object  --实现同条件下分页 <br>
     *
     * @param bucketName bucket名称
     * @param delimiter  delimiter是一个用于对Object名字进行分组的字符。
     * @param prefix     prefix限定返回的object key必须以prefix作为前缀。
     * @param pageIndex  页码。
     * @param pageSize   页面大小。
     * @return List 文件的名称列表
     */
    List<String> listObject(String bucketName, String delimiter, String prefix, Integer pageIndex, Integer pageSize);
}
