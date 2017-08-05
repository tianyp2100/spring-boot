package me.loveshare.note5.data.thirdparty.baidu.ueditor;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.note5.data.constant.SymbolConstants;
import me.loveshare.note5.data.exception.TSException;
import me.loveshare.note5.data.thirdparty.alibaba.aliyun.oss.AliyunOssTemplateI;
import me.loveshare.note5.data.thirdparty.baidu.ueditor.assist.*;
import me.loveshare.note5.data.util.image.Base64ImgUtils;
import me.loveshare.note5.data.util.InputStreamUtils;
import me.loveshare.note5.data.util.StringUtils;
import me.loveshare.note5.properties.AliyunOssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Tony on 2017/1/9.
 */
@Slf4j
@Component
public class UeditorTemplate {

    @Autowired
    private ConfigManager configManager;
    @Autowired
    @Qualifier("openedOSSTemplate")
    private AliyunOssTemplateI openedOSSTemplate;
    @Autowired
    private AliyunOssProperties properties;

    private static final String URL = "url";
    private static final String IMAGE = "image";
    private static final String SCRAWL = "scrawl";
    private static final String VIDEO = "video";
    private static final String FILE = "file";

    public String exec(UeditorArgs args) {

        if (args == null) {
            return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
        }

        String action = args.getAction();
        String callback = args.getCallback();

        if (action == null || !ActionMap.mapping.containsKey(action)) {
            return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
        }

        if (configManager == null || !configManager.valid()) {
            return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
        }
        State state = new BaseState();

        int actionCode = ActionMap.getType(action);

        Map<String, Object> conf = null;

        String type = null;

        switch (actionCode) {
            case ActionMap.CONFIG:
                return this.configManager.getAllConfig().toString();
            case ActionMap.UPLOAD_IMAGE:
                type = IMAGE;
                args.setType(type);
                if (doExec(args)) {
                    state.putInfo("state", State.Success);
                    state.putInfo("name", args.getNewFileName());
                    state.putInfo("original", args.getOrgFileName());
                    state.putInfo("type", args.getType());
                    state.putInfo("size", args.getFileSize());
                    state.putInfo(URL, properties.getOpenedURL1() + args.getDiskName() + type + SymbolConstants.SPRIT + args.getNewFileName());
                } else {
                    return new BaseState(false, AppInfo.FAILED_CREATE_FILE).toJSONString();
                }
                break;
            case ActionMap.UPLOAD_SCRAWL:
                type = SCRAWL;
                args.setType(type);
                if (doExec(args)) {
                    state.putInfo(URL, properties.getOpenedURL1() + args.getDiskName() + type + SymbolConstants.SPRIT + args.getNewFileName());
                } else {
                    return new BaseState(false, AppInfo.FAILED_CREATE_FILE).toJSONString();
                }
                break;
            case ActionMap.UPLOAD_VIDEO:
                type = VIDEO;
                args.setType(type);
                if (doExec(args)) {
                    state.putInfo(URL, properties.getOpenedURL1() + args.getDiskName() + type + SymbolConstants.SPRIT + args.getNewFileName());
                } else {
                    return new BaseState(false, AppInfo.FAILED_CREATE_FILE).toJSONString();
                }
                break;
            case ActionMap.UPLOAD_FILE:
                type = FILE;
                args.setType(type);
                if (doExec(args)) {
                    state.putInfo(URL, properties.getOpenedURL1() + args.getDiskName() + type + SymbolConstants.SPRIT + args.getNewFileName());
                } else {
                    return new BaseState(false, AppInfo.FAILED_CREATE_FILE).toJSONString();
                }
                break;
            case ActionMap.CATCH_IMAGE:
                conf = configManager.getConfig(actionCode);
//                String[] list = this.request.getParameterValues( (String)conf.get( "fieldName" ) );
//                state = new ImageHunter( conf ).capture( list );
                break;
            case ActionMap.LIST_IMAGE:
                args.setPrefix(args.getPrefix() + "eg_image_list/");
                state = listFile(args);
                break;
            case ActionMap.LIST_FILE:
                args.setPrefix(args.getPrefix() + "eg_file_list/");
                state = listFile(args);
                break;

        }

        return state.toJSONString();

    }

    // --TODO 记录=队列+mongodb记录 ，编译搜索
    private boolean doExec(UeditorArgs args) {
        try {
            String type = args.getType();
            String diskName = args.getDiskName() + args.getType() + SymbolConstants.SPRIT;
            if (IMAGE.equals(type)) {
                return StringUtils.isNotEmpty(openedOSSTemplate.upload(properties.getOpenedBucketName1(), diskName, args.getNewFileName(), InputStreamUtils.toByteArray(args.getIs()))) ? true : false;
            }
            if (SCRAWL.equals(type)) {
                byte[] base64Byte = Base64ImgUtils.toByteArray(args.getBase64String());
                String key = openedOSSTemplate.upload(properties.getOpenedBucketName1(), diskName, args.getNewFileName(), base64Byte);
                return StringUtils.isNotEmpty(key) ? true : false;
            }
        } catch (TSException e) {
            log.error("百度UEditor上传文件失败" + e.getMessage(), e);
        }
        return false;
    }

    private State listFile(UeditorArgs args) {
        State state = null;
        List<String> objectList = openedOSSTemplate.listObject(properties.getOpenedBucketName1(), args.getDelimiter(), args.getPrefix(), 1, 50);
        int index = args.getIndex();
        if (index < 0 || index > objectList.size()) {
            state = new MultiState(true);
        } else {
            Object[] fileList = Arrays.copyOfRange(objectList.toArray(), index, index + args.getCount());
            state = getOSSState(fileList);
        }

        state.putInfo("start", index);
        state.putInfo("total", objectList.size());
        return state;
    }

    // 处理ailiyun数据
    private State getOSSState(Object[] files) {

        MultiState state = new MultiState(true);
        BaseState fileState = null;

        for (Object obj : files) {
            if (obj == null) {
                break;
            }

            String key = obj.toString();

            if (StringUtils.isEmpty(key)) continue;
            if (key.endsWith(SymbolConstants.SPRIT)) continue;

            fileState = new BaseState(true);
            fileState.putInfo(URL, properties.getOpenedURL1() + key);
            state.addState(fileState);
        }

        return state;

    }

}
