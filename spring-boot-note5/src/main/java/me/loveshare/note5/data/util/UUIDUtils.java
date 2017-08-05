package me.loveshare.note5.data.util;

import java.util.UUID;

/**
 * Created by Tony on 2017/6/28.
 */
public class UUIDUtils {
    /**
     * 获取uuid
     * eg: 451e92f6-a5cf-4198-a4ee-e0f51562d6e9 = 36位
     *  ---> 451e92f6a5cf4198a4eee0f51562d6e9 = 32位
     * @return 返回uuid字符串
     * */
    public static final String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
