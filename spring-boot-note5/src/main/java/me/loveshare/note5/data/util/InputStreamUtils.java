package me.loveshare.note5.data.util;

import me.loveshare.note5.data.exception.TSEDictionary;
import me.loveshare.note5.data.exception.TSException;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;

public class InputStreamUtils {

    /**
     * 输入流转字节流
     */
    public static final byte[] toByteArray(InputStream is) throws TSException {
        byte[] byteArys = null;
        try {
            byteArys = IOUtils.toByteArray(is);
        } catch (Exception e) {
            throw new TSException(TSEDictionary.BYE_2_STREAM_FAIL.getCode(), "InputStream转换byte数组失败");
        }
        return byteArys;
    }

    /**
     * 输入流转字节流
     */
    public static final byte[] toByteArray1(InputStream is) throws TSException {
        byte[] byteArys = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = is.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byteArys = output.toByteArray();
        } catch (IOException e) {
            throw new TSException(TSEDictionary.STREAM_2_BYE_FAIL.getCode(), "InputStream转换byte数组失败");
        }
        return byteArys;
    }

    /**
     * 字节流转输入流
     */
    public static final InputStream toInputStream(byte[] byteArys) {
        return new ByteArrayInputStream(byteArys);
    }

    /**
     * BufferedImage转换InputStream
     *
     * @param bi      BufferedImage对象
     * @param jpgType 输出流图片类型：png，bmp，jpeg，jpg，默认jpeg.
     */
    public static final InputStream bufferedImage2InputStream(BufferedImage bi, String jpgType) throws TSException {
        if (StringUtils.isEmpty(jpgType)) jpgType = "jpeg";
        if (bi == null) return null;
        InputStream is = null;
        try {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bi, jpgType, imOut);
            is = new ByteArrayInputStream(bs.toByteArray());
        } catch (IOException e) {
            throw new TSException(TSEDictionary.IMG_2_STREAM_FAIL.getCode(), "BufferedImage转换InputStream异常");
        }
        return is;
    }

    /**
     * InputStream转换为File
     *
     * @param outputFilePath 输出文件全路径名
     * @param is             输入流
     */
    public static final void inputStream2File(String outputFilePath, InputStream is) throws TSException {
        OutputStream os = null;
        try {
            File file = new File(outputFilePath);
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new TSException(TSEDictionary.STREAM_2_FILE_FAIL.getCode(), "InputStream转换为File异常");
        } finally {
            try {
                if (os != null) os.close();
                if (is != null) is.close();
            } catch (IOException e) {
                throw new TSException(TSEDictionary.IO_EXCEPTION.getCode(), "InputStream转换为File关闭流异常");
            }
        }
    }
}
