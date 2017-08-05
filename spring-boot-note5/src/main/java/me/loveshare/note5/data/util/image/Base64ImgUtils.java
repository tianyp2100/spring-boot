package me.loveshare.note5.data.util.image;

import me.loveshare.note5.data.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * 图片与base64字符串之间的转换
 * Created by Tony on 2017/4/7.
 */
public class Base64ImgUtils {


    /**
     * 图片转化成base64字符串，将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param imgFile 待处理的图片
     * @return String 返回Base64编码过的字节数组字符串
     */
    public static final String toBase64String(String imgFile) {

        InputStream in = null;
        if(StringUtils.isEmpty(imgFile)) return null;
        byte[] data = null;

        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            System.err.println("图片转化成base64字符串错误:" + e.getMessage());
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * base64字符串转化成图片，对字节数组字符串进行Base64解码并生成图片
     * @param base64String 返回Base64编码过的字节数组字符串
     * @param outImgFilePath 图片文件路径
     * @return true 成功
     * */
    public static final boolean toImage(String base64String, String outImgFilePath) {
        if(StringUtils.isEmpty(base64String)) return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64String);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(outImgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            System.err.println("base64字符串转化成byte数组:" + e.getMessage());
        }
        return false;
    }

    /**
     * base64字符串转化成byte数组
     * @param base64String 返回Base64编码过的字节数组字符串
     * @return true 成功
     * */
    public static final byte[] toByteArray(String base64String) {
        if(StringUtils.isEmpty(base64String)) return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64String);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            return b;
        } catch (IOException e) {
            System.err.println("base64字符串转化成byte数组:" + e.getMessage());
        }
        return null;
    }
}
