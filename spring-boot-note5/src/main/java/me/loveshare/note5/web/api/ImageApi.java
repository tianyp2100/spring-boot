package me.loveshare.note5.web.api;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.note5.data.util.InputStreamUtils;
import me.loveshare.note5.data.util.StringUtils;
import me.loveshare.note5.data.util.image.CompressImgUtils;
import me.loveshare.note5.data.util.image.QRcodeUtils;
import me.loveshare.note5.data.util.image.ValidateCodeImgUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by tony on 2017/8/6.
 * 图片处理
 */
@Slf4j
@Controller
public class ImageApi {

    /**
     * 获取图片验证码
     */
    @RequestMapping(value = "img-verified", produces = "application/json; charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    public void imgVerified(HttpServletResponse response) {

        Map<String, Object> map = ValidateCodeImgUtils.drawVerificationCodeImage();
        Integer validateCode = (Integer) map.get("code");
        BufferedImage validateCodeImg = (BufferedImage) map.get("image");
        String vcodeVal = validateCode.toString();
        log.info("图片验证码生成成功: image verified code：" + vcodeVal);

        outImage(response, validateCodeImg);
    }

    /**
     * 通过URL获取二维码
     */
    @RequestMapping(value = "img-qrcode", produces = "application/json; charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    public void imgQRCode(@RequestParam("url") String url, HttpServletResponse response) {

        if (StringUtils.isEmpty(url)) url = "https://loveshare.me";

        outImage(response, QRcodeUtils.encodeImg(url));
    }

    /**
     * 图片压缩  --未测试
     */
    @RequestMapping(value = "img-compress", produces = "application/json; charset=UTF-8", method = {RequestMethod.GET, RequestMethod.POST})
    public void imgCompress(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        try {

            byte[] reqByteAray = InputStreamUtils.toByteArray(file.getInputStream());
            byte[] resByteAray = CompressImgUtils.compressImg2Byte(reqByteAray, 0, 0, true);

            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes()));
            response.addHeader("Content-Length", "" + file.getSize());
            OutputStream ous = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");

            ous.write(resByteAray);
            ous.flush();
            ous.close();
        } catch (Exception e) {
            log.error("获取图片验证码IO写入相应流失败:" + e.getMessage(), e);
        }
    }

    private void outImage(HttpServletResponse response, BufferedImage image) {

        //设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //设置输出的内容的类型为JPEG图像  --最小图片传输
        response.setContentType("image/jpeg");

        try {
            //输出图片到请求
            ImageIO.write(image, ValidateCodeImgUtils.JPEG, response.getOutputStream());
        } catch (IOException e) {
            log.error("获取图片验证码IO写入相应流失败:" + e.getMessage(), e);
        }
    }

}
