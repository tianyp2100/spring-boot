package me.loveshare.note5.data.util.image;

import me.loveshare.note5.data.exception.TSEDictionary;
import me.loveshare.note5.data.exception.TSException;
import me.loveshare.note5.data.util.StringUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

/**
 * 图片压缩,基于GraphicsMagick和im4java.<br>
 * 使用说明:
 * <p><pre><blockquote>
 * 		url:http://www.imagemagick.org/script/index.php / http://www.imagemagick.org/download/
 *          http://www.graphicsmagick.org/
 * 		1.先安装libpng-1.6.2rc02.tar.gz（为了支持png图片）
 * 		2.然后安装GraphicsMagick-1.3.18.tar.gz和ImageMagick-7.0.2-5.x86_64.rpm
 * 		3.配置环境变量export PATH=$GraphicsMagick_HOME/bin:$PATH ，然用户可以在任意目录运行gm命令
 * 		4.im4java对应的jar文件需要加到os path中
 * 	</pre></blockquote><p>
 */
public class MagickImgUtils {

    /**
     * 压缩图片   ---暂时不使用
     *
     * @param orgImg            图片源地址 local or network url.
     * @param destImg           图片输入地址
     * @param destWidth         图片目标宽度
     * @param destHeight        图片目标高度
     * @param isDistortion      是否保留视觉比例，true则强行改变宽高尺寸匹配使用给定的宽和高
     * @param methodChoice      如果使用ImageMagick，设为false,使用GraphicsMagick，就设为true，默认为false
     * @param windowsMagickPath 若系统为windows则，必须指定GraphicsMagick的安装路径，例如：S:/Program Files (x86)/GraphicsMagick-1.3.21-Q8，其他系统为空
     * @return void 输出到目标地址
     */
    @Deprecated
    public static final void compressImg(String orgImg, String destImg, int destWidth, int destHeight, boolean isDistortion, boolean methodChoice, String windowsMagickPath) throws TSException {
        try {
            IMOperation imop = new IMOperation();  // or IMOperation GMOperation
            imop.addImage(orgImg);
            //图片压缩比，有效值范围是0.0-100.0，数值越大，缩略图越清晰
            imop.quality(75.0);
            //width 和height可以是原图的尺寸，也可以是按比例处理后的尺寸
            if (isDistortion) {
                imop.addRawArgs("-resize", destWidth + "x" + destHeight + "!");
            } else {
                imop.addRawArgs("-resize", destWidth + "x" + destHeight);
            }
            imop.addRawArgs("-gravity", "center");
            imop.addImage(destImg);
            // 如果使用ImageMagick，设为false,使用GraphicsMagick，就设为true，默认为false
            //环境变量ok
            //未安装装GraphicsMagick java.io.FileNotFoundException: gm
            //未安装了ImageMagick java.io.FileNotFoundException: convert
            ConvertCmd convert = new ConvertCmd(methodChoice);
            // linux下不要设置此值，不然会报错
            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("windows") >= 0) {
                String msg = "GraphicsMagick";
                if (!methodChoice) msg = "ImageMagick";
                if (StringUtils.isEmpty(windowsMagickPath)) {
                    throw new TSException(TSEDictionary.SYSTEM_EXCEPTION.getCode(), "If the system for Windows, " + msg + "'s path cannot be empty.");
                }
                convert.setSearchPath(windowsMagickPath);
            }
            convert.run(imop);
        } catch (Exception e) {
            throw new TSException(TSEDictionary.UNDEFINED_FAIL.getCode(), "Image compress failure." + e.getMessage());
        }
    }
}
