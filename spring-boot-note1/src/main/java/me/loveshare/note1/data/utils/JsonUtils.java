package me.loveshare.note1.data.utils;

import com.alibaba.fastjson.JSON;

/**
 * Created by Tony on 2017/6/29.
 * Json操作类
 */
public final class JsonUtils {

    /**
     * 字符串转Java bean对象
     * */
    public static final <T> T str2obj(String json, Class<T> tC){
        return JSON.parseObject(json, tC);
    }

    /**
     * Java bean对象转换字符串
     * */
    public static final <T> String obj2str(T t){
        return JSON.toJSONString(t);
    }
}
