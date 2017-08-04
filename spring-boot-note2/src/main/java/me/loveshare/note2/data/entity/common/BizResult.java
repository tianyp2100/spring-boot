package me.loveshare.note2.data.entity.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Tony on 2017/1/14.
 * 业务对象，标识封装
 */
public class BizResult<T> implements Serializable {


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7000988068495275468L;

    /**
     * 业务执行成功与否的标识 true，则成功
     */
    private boolean flag;

    /**
     * required assign , else java.lang.NullPointerException: null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public BizResult() {
        super();
    }

    public BizResult(boolean flag) {
        super();
        this.flag = flag;
        this.result = null;
    }

    public BizResult(boolean flag, T result) {
        super();
        this.flag = flag;
        this.result = result;
    }

    /**
     * New instance class type T.
     */
    public static final <T> BizResult<T> create(Class<T> tC) {
        try {
            return new BizResult<T>(false, tC.newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Create BizResult<T> is null.");
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
