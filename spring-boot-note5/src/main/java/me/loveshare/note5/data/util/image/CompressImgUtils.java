package me.loveshare.note5.data.util.image;

import me.loveshare.note5.data.exception.TSEDictionary;
import me.loveshare.note5.data.exception.TSException;
import me.loveshare.note5.data.util.DBUtils;
import me.loveshare.note5.data.util.InputStreamUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * java图片压缩
 */
public class CompressImgUtils {

    private static final String JPEG = "JPEG";
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 300;

    /**
     * 压缩图片  --输出到目标文件
     *
     * @param orgImg     图片源地址 local or network url.
     * @param destImg    图片输入地址
     * @param destWidth  图片目标宽度
     * @param destHeight 图片目标高度
     * @param proportion 是否是等比缩放，true为是
     * @return void 输出到目标文件
     */
    public static final void compressImg2File(String orgImg, String destImg, int destWidth, int destHeight, boolean proportion) throws TSException {
        FileOutputStream out = null;
        // 获得源文件
        File file = new File(orgImg);
        if (!file.exists()) {
            throw new TSException(TSEDictionary.FILE_NOT_EXIST.getCode(), "文件不存在");
        }
        try {
            Image img = ImageIO.read(file);
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                throw new TSException(TSEDictionary.FILE_NOT_KNOWN.getCode(), "未知的图片文件");
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) destWidth + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) destHeight + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = destWidth; // 输出的图片宽度
                    newHeight = destHeight; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
                /*
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                File outputFile = new File(destImg);
                if (!outputFile.exists()) outputFile.createNewFile();
                out = new FileOutputStream(outputFile);
                // JPEGImageEncoder可适用于其他图片类型的转换
//				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//				encoder.encode(tag);
                ImageIO.write(tag, JPEG, out);
            }
        } catch (Exception e) {
            throw new TSException(TSEDictionary.FILE_COMPRESS_FAIL.getCode(), "图片压缩失败", e);
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                throw new TSException(TSEDictionary.IO_EXCEPTION.getCode(), "图片压缩输出流关闭失败", e);
            }
        }
    }

    /**
     * 压缩图片  --流输出
     *
     * @param is         输入流
     * @param destWidth  图片目标宽度
     * @param destHeight 图片目标高度
     * @param proportion 是否是等比缩放，true为是
     * @return void 输出到目标文件
     */
    public static final byte[] compressImg2Byte(InputStream is, int destWidth, int destHeight, boolean proportion) throws TSException {
        ByteArrayOutputStream out = null;
        byte[] resByte = null;
        try {
            if (is.available() == 0) {
                throw new TSException(TSEDictionary.IO_NULL.getCode(), "输入流不能为空");
            }
            Image img = ImageIO.read(is);
            if (img == null) {
                throw new TSException(TSEDictionary.IO_NULL.getCode(), "输入流不能为空");
            }
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                throw new TSException(TSEDictionary.FILE_NOT_KNOWN.getCode(), "未知的图片文件");
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) destWidth + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) destHeight + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = destWidth; // 输出的图片宽度
                    newHeight = destHeight; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
                /*
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                out = new ByteArrayOutputStream();
                // JPEGImageEncoder可适用于其他图片类型的转换
//				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//				encoder.encode(tag);
                ImageIO.write(tag, JPEG, out);
                resByte = out.toByteArray();
            }
        } catch (Exception e) {
            throw new TSException(TSEDictionary.FILE_COMPRESS_FAIL.getCode(), "图片压缩失败", e);
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                throw new TSException(TSEDictionary.IO_EXCEPTION.getCode(), "图片压缩输出流关闭失败", e);
            }
        }
        return resByte;
    }

    public static final byte[] compressImg2Byte(byte[] byteArys, int destWidth, int destHeight, boolean proportion) throws TSException {
        ByteArrayOutputStream out = null;
        byte[] resByte = null;
        InputStream is = InputStreamUtils.toInputStream(byteArys);
        try {
            destWidth = DBUtils.numberBlank(destWidth) ? DEFAULT_WIDTH : destWidth;
            destHeight = DBUtils.numberBlank(destHeight) ? DEFAULT_HEIGHT : destHeight;
            if (is.available() == 0) {
                throw new TSException(TSEDictionary.IO_NULL.getCode(), "输入流不能为空");
            }
            Image img = ImageIO.read(is);
            if (img == null) {
                throw new TSException(TSEDictionary.IO_NULL.getCode(), "输入流不能为空");
            }
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                throw new TSException(TSEDictionary.FILE_NOT_KNOWN.getCode(), "未知的图片文件");
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) destWidth + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) destHeight + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                    System.out.println("6:" + newHeight);
                    System.out.println("7:" + newWidth);
                } else {
                    newWidth = destWidth; // 输出的图片宽度
                    newHeight = destHeight; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
                /*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                out = new ByteArrayOutputStream();
                // JPEGImageEncoder可适用于其他图片类型的转换
//				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//				encoder.encode(tag);
                ImageIO.write(tag, JPEG, out);
                resByte = out.toByteArray();
            }
        } catch (Exception e) {
            throw new TSException(TSEDictionary.FILE_COMPRESS_FAIL.getCode(), "图片压缩失败", e);
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                throw new TSException(TSEDictionary.IO_EXCEPTION.getCode(), "图片压缩输出流关闭失败", e);
            }
        }
        return resByte;
    }

    public static final InputStream compressImg2IS(InputStream is, int destWidth, int destHeight, boolean proportion) throws TSException {
        InputStream resIs = null;
        try {
            if (is.available() == 0) {
                throw new TSException(TSEDictionary.IO_NULL.getCode(), "输入流不能为空");
            }
            Image img = ImageIO.read(is);
            if (img == null) {
                throw new TSException(TSEDictionary.IO_NULL.getCode(), "输入流不能为空");
            }
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                throw new TSException(TSEDictionary.FILE_NOT_KNOWN.getCode(), "未知的图片文件");
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) destWidth + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) destHeight + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = destWidth; // 输出的图片宽度
                    newHeight = destHeight; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                // JPEGImageEncoder可适用于其他图片类型的转换
//				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//				encoder.encode(tag);
                ImageIO.write(tag, JPEG, out);
                resIs = new ByteArrayInputStream(out.toByteArray());
            }
        } catch (Exception e) {
            throw new TSException(TSEDictionary.FILE_COMPRESS_FAIL.getCode(), "图片压缩失败", e);
        } finally {
            try {
                if (resIs != null) resIs.close();
            } catch (IOException e) {
                throw new TSException(TSEDictionary.IO_EXCEPTION.getCode(), "图片压缩输出流关闭失败", e);
            }
        }
        return resIs;
    }
}
