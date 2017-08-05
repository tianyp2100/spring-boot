package me.loveshare.note5.data.entity.dbo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tony on 2017/5/19.
 * 资源数据记录实体类 : 云资源url: @see me.loveshare.note5.data.util.ResourceUtils.url(ossUrl, diskName, fileName)
 */
@Data
public class ResourceData implements Serializable {


    private static final long serialVersionUID = 4756936157843165789L;

    /**
     * MongoDB文档数据库(String)/MySQL关系型数据库(Long) : 主键id
     */
    private String id;
    /**
     * 资源文件code
     */
    private String code;
    /**
     * 文件原名
     */
    private String name;
    /**
     * 文件描述
     */
    private String desc;
    /**
     * 文件的平台来源(1.家庭 2.学校 3.公司 4.户外)
     */
    private Byte source;
    /**
     * 文件的类型(1.视频 2.音频 3.图片 4.压缩文件 5:办公文件)
     */
    private Byte type;
    /**
     * 云域名url
     */
    private String url;
    /**
     * 对象存储OSS的BucketName
     */
    private String bucketName;
    /**
     * 对象存储OSS的BucketName下的路径diskName
     */
    private String diskName;
    /**
     * 对象存储OSS的BucketName下的路径下的文件名fileName
     */
    private String fileName;
    /**
     * 文件md5值
     */
    private String md5;
    /**
     * 文件的大小
     */
    private Long size;
    /**
     * 公开(0)或私有(1)
     */
    private Byte isOpened;
    /**
     * 平台上传用户id
     */
    private Integer author;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
    /**
     * 逻辑删除：删除(1)或正常(0)
     */
    private Byte isDeleted;
}
