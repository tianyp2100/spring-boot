package me.loveshare.note5.data.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Tony on 2017/5/20.
 */
@Data
public class ResourceResult implements Serializable {

    private static final long serialVersionUID = 752416341135947134L;
    /**
     * 资源上传结果编码
     */
    private String code;

    /**
     * 资源上传结果地址
     */
    private String url;
    /**
     * [isOwned=true]私有资源上传结果返显地址
     */
    private String signUrl;


    public ResourceResult() {
        super();
    }

    public ResourceResult(String code, String url) {
        this.code = code;
        this.url = url;
    }

    public ResourceResult(String code, String url, String signUrl) {
        this.code = code;
        this.url = url;
        this.signUrl = signUrl;
    }


    public static ResourceResult create(String code, String url) {
        return new ResourceResult(code, url);
    }

    public static ResourceResult create(String code, String url, String signUrl) {
        return new ResourceResult(code, url, signUrl);
    }
}
