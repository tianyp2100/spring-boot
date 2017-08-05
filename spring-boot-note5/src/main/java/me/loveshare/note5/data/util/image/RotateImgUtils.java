package me.loveshare.note5.data.util.image;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;
import me.loveshare.note5.data.exception.TSEDictionary;
import me.loveshare.note5.data.exception.TSException;
import me.loveshare.note5.data.util.InputStreamUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * 图片旋转.<br>
 * 由于iphone和高版本的android照片，上传后选择解决
 */
public class RotateImgUtils {

    //图片格式
    private static final String JPEG = "jpeg";

    /**
     * 选择图片
     *
     * @param fileName 上传的文件
     */
    public static final InputStream rotateImg(MultipartFile fileName) throws TSException {
        if (fileName == null) return null;
        CommonsMultipartFile cf = (CommonsMultipartFile) fileName;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        //获取旋转角度
        int angle = getRotateAngle(file);
        //选择图片
        return rotatePhonePhoto(file, angle);
    }

    /**
     * 选择图片
     *
     * @param is 上传的文件流
     */
    public static final InputStream rotateImg(InputStream is) throws TSException {
        if (is == null) return null;
        int angle = getRotateAngle(is);
        return rotatePhonePhoto(is, angle);
    }

    /**
     * 选择图片
     *
     * @param url 文件的url
     */
    public static final InputStream rotateImg(URL url) throws TSException {
        if (url == null || url.toString().equals("")) return null;
        try {
            BufferedImage src = ImageIO.read(url);
            InputStream is = InputStreamUtils.bufferedImage2InputStream(src, JPEG);
            int angle = getRotateAngle(is);
            return rotatePhonePhoto(is, angle);
        } catch (IOException e) {
            throw new TSException(TSEDictionary.IO_EXCEPTION.getCode(), "通过url加载图片失败");
        }
    }

    /**
     * 图片iphone图片上传时候，对图片进行旋转
     *
     * @param file  图片
     * @param angle 旋转角度
     * @return InputStream 旋转后的图片流
     * @author Tony_tian
     */
    private static final InputStream rotatePhonePhoto(File file, int angle) throws TSException {
        BufferedImage src = null;
        InputStream res_is = null;
        try {
            src = ImageIO.read(file);
            int src_width = src.getWidth(null);
            int src_height = src.getHeight(null);

            Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angle);

            BufferedImage res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = res.createGraphics();

            g2.translate((rect_des.width - src_width) / 2,
                    (rect_des.height - src_height) / 2);
            g2.rotate(Math.toRadians(angle), src_width / 2, src_height / 2);

            g2.drawImage(src, null, null);
            //文件流转换
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(res, JPEG, os);// 输出到文件流
            res_is = new ByteArrayInputStream(os.toByteArray());

        } catch (IOException e) {
            throw new TSException(TSEDictionary.IO_EXCEPTION.getCode(), "上传中iphone图片服务器旋转异常：" + e.getMessage());
        }
        return res_is;
    }

    /**
     * 图片iphone图片上传时候，对图片进行旋转
     *
     * @param is    图片流
     * @param angle 旋转角度
     * @return InputStream 旋转后的图片流
     * @author Tony_tian
     */
    private static final InputStream rotatePhonePhoto(InputStream is, int angle) throws TSException {
        BufferedImage src = null;
        InputStream res_is = null;
        try {
            src = ImageIO.read(is);
            int src_width = src.getWidth(null);
            int src_height = src.getHeight(null);

            Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angle);

            BufferedImage res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = res.createGraphics();

            g2.translate((rect_des.width - src_width) / 2,
                    (rect_des.height - src_height) / 2);
            g2.rotate(Math.toRadians(angle), src_width / 2, src_height / 2);

            g2.drawImage(src, null, null);
            //文件流转换
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(res, JPEG, os);// 输出到文件流
            res_is = new ByteArrayInputStream(os.toByteArray());

        } catch (IOException e) {
            throw new TSException(TSEDictionary.IO_EXCEPTION.getCode(), "上传中iphone图片服务器旋转异常");
        }
        return res_is;
    }

    /**
     * 获取图片正确显示需要旋转的角度（顺时针）
     */
    private static final int getRotateAngle(InputStream is) throws TSException {
        int angle = 0;
        Metadata metadata;
        try {
            metadata = JpegMetadataReader.readMetadata(is);
            Directory directory = metadata.getDirectory(ExifDirectory.class);
            if (directory.containsTag(ExifDirectory.TAG_ORIENTATION)) {
                // Exif信息中方向　　
                int orientation = directory.getInt(ExifDirectory.TAG_ORIENTATION);
                // 原图片的方向信息
                if (6 == orientation) {
                    //6旋转90
                    angle = 90;
                } else if (3 == orientation) {
                    //3旋转180
                    angle = 180;
                } else if (8 == orientation) {
                    //8旋转90
                    angle = 270;
                }
            }
        } catch (Exception e) {
            throw new TSException(TSEDictionary.UNDEFINED_FAIL.getCode(), "iphone图片服务器旋转异常");
        }
        return angle;
    }

    /**
     * 获取图片正确显示需要旋转的角度（顺时针）
     *
     * @return
     * @throws Exception
     */
    private static final int getRotateAngle(File file) throws TSException {
        int angle = 0;
        Metadata metadata;
        try {
            metadata = JpegMetadataReader.readMetadata(file);
            Directory directory = metadata.getDirectory(ExifDirectory.class);
            if (directory.containsTag(ExifDirectory.TAG_ORIENTATION)) {
                // Exif信息中方向　　
                int orientation = directory.getInt(ExifDirectory.TAG_ORIENTATION);
                // 原图片的方向信息
                if (6 == orientation) {
                    //6旋转90
                    angle = 90;
                } else if (3 == orientation) {
                    //3旋转180
                    angle = 180;
                } else if (8 == orientation) {
                    //8旋转90
                    angle = 270;
                }
            }
        } catch (Exception e) {
            throw new TSException(TSEDictionary.UNDEFINED_FAIL.getCode(), "iphone图片服务器旋转异常");
        }
        return angle;
    }

    private static final Rectangle CalcRotatedSize(Rectangle src, int angel) {
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }
}
