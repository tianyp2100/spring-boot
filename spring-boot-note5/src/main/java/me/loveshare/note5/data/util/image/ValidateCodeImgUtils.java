package me.loveshare.note5.data.util.image;

import me.loveshare.note5.data.exception.TSEDictionary;
import me.loveshare.note5.data.exception.TSException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * ClassName: ValidateCodeImg. <br/>
 * Description: creat verification code image.<br/>
 * Date: 2015-09-02 00:51:22 <br/>
 *
 * @author Tony_tian. first re made<br />
 * @version 1.0.0<br/>
 */
public final class ValidateCodeImgUtils {

    // verification code image width
    private static final int IMG_WIDTH = 130;
    // verification code image height
    private static final int IMG_HEIGHT = 38;
    // The number of interference lines
    private static final int DISTURB_LINE_SIZE = 15;
    // generate a random number
    private static final Random random = new Random();
    //transfer style
    public static final String JPEG = "JPEG";
    // Chinese Numbers
    // private static final String [] CNUMBERS =
    // "零,一,二,三,四,五,六,七,八,九,十".split(",");
    // 零一二三四五六七八九十乘除加减
    // Here, must be java Unicode code
    private static final String CVCNUMBERS = "\u96F6\u4E00\u4E8C\u4E09\u56DB\u4E94\u516D\u4E03\u516B\u4E5D\u5341\u4E58\u9664\u52A0\u51CF";
    // Definition of drawings in the captcha characters font, font name, font
    // style, font size
    // static final font : In Chinese characters garbled
    private static final Font font = new Font("黑体", Font.BOLD, 18);
    // data operator
    private static final Map<String, Integer> OPMap = new HashMap<String, Integer>();

    static {
        OPMap.put("*", 11);
        OPMap.put("/", 12);
        OPMap.put("+", 13);
        OPMap.put("-", 14);
    }

    /**
     * Get validate code image and validate code.
     *
     * @return map.key="validateCode" value="(int)validate code result." <br />
     * map.key="validateCodeImg" value="(BufferedImage)validate image."
     */
    public static Map<String, Object> drawVerificationCodeImage() {
        //map use save validate code and image
        Map<String, Object> map = new HashMap<String, Object>();
        // image
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        // In memory to create a brush
        Graphics g = image.getGraphics();
        // Set the brush color
        // g.setColor(getRandomColor(200,250));
        g.setColor(Color.WHITE);
        // Fill the background color, using the current brush colour changing
        // background color images
        // The meaning of the four parameters respectively, starting x
        // coordinates, starting y, width, height.
        // image background
        g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
        // Set the brush color
//		g.setColor(getRandomColor(200, 250));
        Color bordercolor = new Color(216, 216, 216);
        g.setColor(bordercolor);
        // image border
        g.drawRect(0, 0, IMG_WIDTH - 1, IMG_HEIGHT - 1);
        // Set disturb line color
        g.setColor(getRandomColor(110, 133));
        // Generate random interference lines
        for (int i = 0; i < DISTURB_LINE_SIZE; i++) {
            drawDisturbLine1(g);
            drawDisturbLine2(g);
        }
        // Generate a random number, set return data
        int validateCode = 0;
        // Randomly generated number 0 to 10
        int xx = random.nextInt(10);
        int yy = random.nextInt(10);
        // save getRandomString
        StringBuffer sfChinese = new StringBuffer();
        // random 0,1,2
        int Randomoperands = (int) Math.round(Math.random() * 2);
        // multiplication
        if (Randomoperands == 0) {
            validateCode = yy * xx;
            // suChinese.append(CNUMBERS[yy]);
            sfChinese.append(yy);
            sfChinese.append("*");
            sfChinese.append(xx);
            // division, divisor cannot be zero, Be divisible
        } else if (Randomoperands == 1) {
            if (!(xx == 0) && yy % xx == 0) {
                validateCode = yy / xx;
                sfChinese.append(yy);
                sfChinese.append("/");
                sfChinese.append(xx);
            } else {
                validateCode = yy + xx;
                sfChinese.append(yy);
                sfChinese.append("+");
                sfChinese.append(xx);
            }
            // subtraction
        } else if (Randomoperands == 2) {
            validateCode = yy - xx;
            sfChinese.append(yy);
            sfChinese.append("-");
            sfChinese.append(xx);
            // add
        } else {
            validateCode = yy + xx;
            sfChinese.append(yy);
            sfChinese.append("+");
            sfChinese.append(xx);
        }
        String validateCodeTempString = sfChinese.toString();
        // The generated random string used to save the system
        StringBuffer logsu = new StringBuffer();
        for (int j = 0, k = validateCodeTempString.length(); j < k; j++) {
            int chid = 0;
            if (j == 1) {
                chid = OPMap.get(String.valueOf(validateCodeTempString.charAt(j)));
            } else {
                chid = Integer.parseInt(String.valueOf(validateCodeTempString.charAt(j)));
            }
            String ch = String.valueOf(CVCNUMBERS.charAt(chid));
            logsu.append(ch);
            drawRandomString((Graphics2D) g, ch, j);
        }
        // = ?
        drawRandomString((Graphics2D) g, "\u7B49\u4E8E\uFF1F", 3);
        logsu.append("\u7B49\u4E8E \uFF1F");
/*		LOG.info("验证码 : " + validateCodeTempString);
        LOG.info("验证码结果 : " + validateCode);
		LOG.info("汉字验证码 : " + logsu);*/
        // Release the brush object
        g.dispose();
        //save validate img and code.
        map.put("code", validateCode);
        map.put("image", image);
        return map;
    }

    /**
     * Draw a random string
     *
     * @param g          Graphics
     * @param randomvcch random string
     * @param i          the random number of characters
     */
    public static void drawRandomString(Graphics2D g, String randomvcch, int i) {
        // Set the string font style
        g.setFont(font);
        // Set the color string
        int rc = random.nextInt(255);
        int gc = random.nextInt(255);
        int bc = random.nextInt(255);
        g.setColor(new Color(rc, gc, bc));
        // random string
        // Set picture in the picture of the text on the x, y coordinates,
        // random offset value
        int x = random.nextInt(3);
        int y = random.nextInt(2);
        g.translate(x, y);
        // Set the font rotation angle
        int degree = new Random().nextInt() % 15;
        // Positive point of view
        g.rotate(degree * Math.PI / 180, 5 + i * 25, 20);
        // Character spacing is set to 15 px
        // Using the graphics context of the current font and color rendering by
        // the specified string for a given text.
        // The most on the left side of the baseline of the characters in the
        // coordinate system of the graphics context (x, y) location
        // str- to draw string.x - x coordinate.y - y coordinate.
        g.drawString(randomvcch, 5 + i * 20, 25);
        // Reverse Angle
        g.rotate(-degree * Math.PI / 180, 5 + i * 25, 20);
    }

    /**
     * For random color
     *
     * @param fc set param1
     * @param bc set param2
     * @return color random color
     */
    public static Color getRandomColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        // Generate random RGB trichromatic
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * Draw line interference 1
     *
     * @param g Graphics
     */
    public static void drawDisturbLine1(Graphics g) {
        int x1 = random.nextInt(IMG_WIDTH);
        int y1 = random.nextInt(IMG_HEIGHT);
        int x2 = random.nextInt(13);
        int y2 = random.nextInt(15);
        // x1 - The first point of the x coordinate.
        // y1 - The first point of the y coordinate
        // x2 - The second point of the x coordinate.
        // y2 - The second point of the y coordinate.
        // X1 and x2 is the starting point coordinates, x2 and y2 is end
        // coordinates.
        g.drawLine(x1, y1, x1 + x2, y1 + y2);
    }

    /**
     * Draw line interference 2
     *
     * @param g Graphics
     */
    public static void drawDisturbLine2(Graphics g) {
        int x1 = random.nextInt(IMG_WIDTH);
        int y1 = random.nextInt(IMG_HEIGHT);
        int x2 = random.nextInt(13);
        int y2 = random.nextInt(15);
        // x1 - The first point of the x coordinate.
        // y1 - The first point of the y coordinate
        // x2 - The second point of the x coordinate.
        // y2 - The second point of the y coordinate.
        // X1 and x2 is the starting point coordinates, x2 and y2 is end
        // coordinates.
        g.drawLine(x1, y1, x1 - x2, y1 - y2);
    }

    /**
     * Output validate image to file.
     *
     * @param format file wirte image format. <br />
     * @param file   write to image file.<br />
     * @return int validateCode validate code's result.
     */
    public static int writeToFile(String format, File file) throws TSException {
        int validateCode = 0;
        try {
            Map<String, Object> map = drawVerificationCodeImage();
            validateCode = (Integer) map.get("code");
            BufferedImage validateCodeImg = (BufferedImage) map.get("image");
            ImageIO.write(validateCodeImg, format, file);
        } catch (Exception e) {
            throw new TSException(TSEDictionary.FILE_OUT_DISK_FAIL.getCode(), "Validate code image write to file failure:" + e.getMessage());
        }
        return validateCode;
    }

    /**
     * Output validate code to stream.
     *
     * @param format       file wirte image format. <br />
     * @param outputStream write to image output stream.<br />
     * @return int validateCode validate code's result.
     */
    public static int writeToStream(String format, OutputStream outputStream) throws TSException {
        int validateCode = 0;
        try {
            Map<String, Object> map = drawVerificationCodeImage();
            validateCode = (Integer) map.get("code");
            BufferedImage validateCodeImg = (BufferedImage) map.get("image");
            ImageIO.write(validateCodeImg, format, outputStream);
        } catch (Exception e) {
            throw new TSException(TSEDictionary.FILE_OUT_STREAM_FAIL.getCode(), "Validate code image write to output stream failure:" + e.getMessage());
        }
        return validateCode;
    }
}