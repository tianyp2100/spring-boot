package me.loveshare.note5.data.entity.bo;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by Tony on 2016/12/31.
 */
public class OSSBatchUploadArgs implements Serializable {

    private static final long serialVersionUID = -8893131393869061839L;
    /**bucket名称*/
    private String bucketName;
    /**上传文件的目录*/
    private String diskName;
    /**文件名称*/
    private String fileName;
    /**上传文件流
     * 注：InputStream is：dubbo-hession协议io流必须为最后一个参数 ,直接传递为0，则转换byte数组传递
     * 转换:#InputStreamUnit.java
     * */
    private byte[] byteArys;
    private InputStream is;

    public OSSBatchUploadArgs() {
        super();
    }

    public OSSBatchUploadArgs(String bucketName, String diskName, String fileName, byte[] byteArys) {
        super();
        this.bucketName = bucketName;
        this.diskName = diskName;
        this.fileName = fileName;
        this.byteArys = byteArys;
    }

    public OSSBatchUploadArgs(String bucketName, String diskName, String fileName, InputStream is) {
        super();
        this.bucketName = bucketName;
        this.diskName = diskName;
        this.fileName = fileName;
        this.is = is;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getByteArys() {
        return byteArys;
    }

    public void setByteArys(byte[] byteArys) {
        this.byteArys = byteArys;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }
}
