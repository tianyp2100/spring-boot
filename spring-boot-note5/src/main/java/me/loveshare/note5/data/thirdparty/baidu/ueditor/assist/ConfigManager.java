package me.loveshare.note5.data.thirdparty.baidu.ueditor.assist;

import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置管理器
 */
@Data
@Component
@ConfigurationProperties(prefix = "ueditor")
public final class ConfigManager {

    private String config;

    // 涂鸦上传filename定义
    private final static String SCRAWL_FILE_NAME = "scrawl";
    // 远程图片抓取filename定义
    private final static String REMOTE_FILE_NAME = "remote";

    // 验证配置文件加载是否正确
    public boolean valid() {
        return config != null;
    }

    public JSONObject getAllConfig() {

        return new JSONObject(config);

    }

    public Map<String, Object> getConfig(int type) {

        Map<String, Object> conf = new HashMap<String, Object>();
        String savePath = null;

        JSONObject jsonConfig = new JSONObject(config);

        switch (type) {

            case ActionMap.UPLOAD_FILE:
                conf.put("isBase64", "false");
                conf.put("maxSize", jsonConfig.getLong("fileMaxSize"));
                conf.put("allowFiles", this.getArray("fileAllowFiles"));
                conf.put("fieldName", jsonConfig.getString("fileFieldName"));
                savePath = jsonConfig.getString("filePathFormat");
                break;

            case ActionMap.UPLOAD_IMAGE:
                conf.put("isBase64", "false");
                conf.put("maxSize", jsonConfig.getLong("imageMaxSize"));
                conf.put("allowFiles", this.getArray("imageAllowFiles"));
                conf.put("fieldName", jsonConfig.getString("imageFieldName"));
                savePath = jsonConfig.getString("imagePathFormat");
                break;

            case ActionMap.UPLOAD_VIDEO:
                conf.put("maxSize", jsonConfig.getLong("videoMaxSize"));
                conf.put("allowFiles", this.getArray("videoAllowFiles"));
                conf.put("fieldName", jsonConfig.getString("videoFieldName"));
                savePath = jsonConfig.getString("videoPathFormat");
                break;

            case ActionMap.UPLOAD_SCRAWL:
                conf.put("filename", ConfigManager.SCRAWL_FILE_NAME);
                conf.put("maxSize", jsonConfig.getLong("scrawlMaxSize"));
                conf.put("fieldName", jsonConfig.getString("scrawlFieldName"));
                conf.put("isBase64", "true");
                savePath = jsonConfig.getString("scrawlPathFormat");
                break;

            case ActionMap.CATCH_IMAGE:
                conf.put("filename", ConfigManager.REMOTE_FILE_NAME);
                conf.put("filter", this.getArray("catcherLocalDomain"));
                conf.put("maxSize", jsonConfig.getLong("catcherMaxSize"));
                conf.put("allowFiles", this.getArray("catcherAllowFiles"));
                conf.put("fieldName", jsonConfig.getString("catcherFieldName") + "[]");
                savePath = jsonConfig.getString("catcherPathFormat");
                break;

            case ActionMap.LIST_IMAGE:
                conf.put("allowFiles", this.getArray("imageManagerAllowFiles"));
                conf.put("dir", jsonConfig.getString("imageManagerListPath"));
                conf.put("count", jsonConfig.getInt("imageManagerListSize"));
                break;

            case ActionMap.LIST_FILE:
                conf.put("allowFiles", this.getArray("fileManagerAllowFiles"));
                conf.put("dir", jsonConfig.getString("fileManagerListPath"));
                conf.put("count", jsonConfig.getInt("fileManagerListSize"));
                break;

        }

        conf.put("savePath", savePath);
        conf.put("rootPath", "");

        return conf;

    }

    private String[] getArray(String key) {

        JSONObject jsonConfig = new JSONObject(config);

        JSONArray jsonArray = jsonConfig.getJSONArray(key);
        String[] result = new String[jsonArray.length()];

        for (int i = 0, len = jsonArray.length(); i < len; i++) {
            result[i] = jsonArray.getString(i);
        }

        return result;

    }

    // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
    private String filter(String input) {

        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");

    }

}
