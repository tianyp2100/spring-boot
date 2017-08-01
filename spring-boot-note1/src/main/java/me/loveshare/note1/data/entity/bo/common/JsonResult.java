package me.loveshare.note1.data.entity.bo.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 业务或数据处理结果结果json格式封装传递实体类
 *
 * @author Tony
 * @time 2016年10月17日21:25:22
 */
public class JsonResult implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5254390441265937082L;
    /**
     * 业务或数据处理结果返回编码
     */
    private int code;
    /**
     * 业务或数据处理结果提示信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    /**
     * 封装处理结果的返回数据,注解：属性为null不序列化,序列化不会在json中体现，而""会序列化。Include.NON_EMPTY都不序列化
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    //constructor
    public JsonResult() {
        super();
    }

    public JsonResult(int code) {
        super();
        this.code = code;
    }

    public JsonResult(int code, String message) {
        super();
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public JsonResult(int code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    //getter and setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
