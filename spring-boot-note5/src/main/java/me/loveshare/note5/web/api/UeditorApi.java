package me.loveshare.note5.web.api;

import me.loveshare.note5.data.thirdparty.baidu.ueditor.UeditorTemplate;
import me.loveshare.note5.data.thirdparty.baidu.ueditor.assist.AppInfo;
import me.loveshare.note5.data.thirdparty.baidu.ueditor.assist.BaseState;
import me.loveshare.note5.data.thirdparty.baidu.ueditor.assist.UeditorArgs;
import me.loveshare.note5.data.util.StringUtils;
import me.loveshare.note5.data.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Tony on 2017/1/9.
 */
@RestController
@RequestMapping("ueditor")
public class UeditorApi extends BaseApi {

    private static final Logger LOG = LoggerFactory.getLogger(UeditorApi.class);

    @Autowired
    private UeditorTemplate ueditorService;

    private static final String UPLOADIMAGE = "uploadimage";
    private static final String UPLOADSCRAWL = "uploadscrawl";
    private static final String ACTION = "action";
    private static final String CALLBACK = "callback";
    private static final String UPFILE = "upfile";
    private static final String LIST = "list";
    private static final String UTF_8 = "UTF-8";
    private static final String DISKNAME = "resources/ueditor/";

    private static final String START = "start";
    private static final String SIZE = "size";

    private static final int DEF_START = 0;
    private static final int DEF_SIZE = 20;

    private static final String CX1 = "%3D";
    private static final String CX2 = "%2B";

    private static final String E_SCRAWL = "_scrawl.jpg";

    private static final String ALLOW_1K = "Access-Control-Allow-Origin";
    private static final String ALLOW_1V = "*";
    private static final String ALLOW_2K = "Access-Control-Allow-Methods";
    private static final String ALLOW_2V = "GET, POST, OPTIONS";
    private static final String ALLOW_3K = "Access-Control-Allow-Headers";
    private static final String ALLOW_3V = "X-Requested-With,X_Requested_With";

    @RequestMapping(value = "interface", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8", method = {RequestMethod.GET, RequestMethod.POST})
//    public String execC(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) MultipartFile upfile) {
    public MappingJacksonValue execC(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) MultipartFile upfile) {

        String action = request.getParameter(ACTION);
        String callback = request.getParameter(CALLBACK);

        LOG.info("Ueditor---> action: " + action + ",callback: " + callback);

        InputStream is = null;
        String fileName = null;
        String randomEid = UUIDUtils.uuid();
        String prefixDir = ""; //暂时未定
        long fileSize = 0;
        UeditorArgs args = args = new UeditorArgs(action, callback);
        args.setDiskName(DISKNAME);
        if (UPLOADIMAGE.equals(action)) {
            try {
                is = upfile.getInputStream();
                fileName = upfile.getOriginalFilename();
                fileSize = upfile.getSize();
                args.setIs(is);
                args.setFileSize(fileSize);
                args.setOrgFileName(fileName);
                args.setNewFileName(randomEid + fileName);
            } catch (IOException e) {
                LOG.error("上传文件IO异常" + e.getMessage(), e);
//                return new BaseState(false, AppInfo.IO_ERROR).toJSONString();
                return string2Jackson(new BaseState(false, AppInfo.IO_ERROR).toJSONString(), callback);
            }
        }
        if (UPLOADSCRAWL.equals(action)) {
            try {
                String base64String = request.getParameter(UPFILE);
                if (CX1.contains(base64String) || CX2.contains(base64String)) {
                    base64String = URLDecoder.decode(base64String, UTF_8);
                }
                args.setBase64String(base64String);
                args.setNewFileName(randomEid + E_SCRAWL);
            } catch (UnsupportedEncodingException e) {
                LOG.error("涂鸦上传异常" + e.getMessage(), e);
//                return new BaseState(false, AppInfo.IO_ERROR).toJSONString();
                return string2Jackson(new BaseState(false, AppInfo.IO_ERROR).toJSONString(), callback);
            }
        }
        if (action != null && action.startsWith(LIST)) {
            String start = request.getParameter(START);
            String size = request.getParameter(SIZE);
            int starti = StringUtils.isNotBlank(start) && StringUtils.isNumeric(start) ? Integer.parseInt(start) : DEF_START;
            int sizei = StringUtils.isNotBlank(size) && StringUtils.isNumeric(size) ? Integer.parseInt(size) : DEF_SIZE;
            args.setDelimiter(null);
            args.setPrefix(prefixDir);
            args.setIndex(starti);
            args.setCount(sizei);
        }

        //跨域时需要设置http头信息以返回参数给源地址
        response.addHeader(ALLOW_1K, ALLOW_1V);
        response.addHeader(ALLOW_2K, ALLOW_2V);
        response.addHeader(ALLOW_3K, ALLOW_3V);

        String resJson = ueditorService.exec(args);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(resJson);
        mappingJacksonValue.setJsonpFunction(callback);

//        return callback + "(" + resJson + ");";

        return string2Jackson(resJson, callback);

    }

    private MappingJacksonValue string2Jackson(String json, String callback) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(json);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

}
