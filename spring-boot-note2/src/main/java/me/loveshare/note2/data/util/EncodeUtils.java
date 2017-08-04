package me.loveshare.note2.data.util;

import me.loveshare.note2.data.constant.EncodingConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Tony on 2017/6/21.
 * 编码工具类
 */
public class EncodeUtils {

    /**
     * 中文参数乱码转换：
     * jquery:  encodeURIComponent("preString") .<br>
     * java ： 如下
     *
     * @throws UnsupportedEncodingException
     */
    public static final String decodeStr(String preString) throws UnsupportedEncodingException {
        if (StringUtils.isNotEmpty(preString)) {
            return URLDecoder.decode(preString, EncodingConstants.UTF_8);
        }
        return null;
    }
}
