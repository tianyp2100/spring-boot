package me.loveshare.data.entity.bo.common;

/**
 * Created by Tony on 2017/7/25.
 * 业务或数据处理结果结果json格式封装传递实体类的辅助类
 */
public class JsonResultMethod {

    /**
     * 请求成功
     */
    public static JsonResult code_200(String message) {
        return code(JsonResultEnum.SUCCESS, message);
    }

    /**
     * 请求成功
     */
    public static JsonResult code_200(String message, Object data) {
        return code(JsonResultEnum.SUCCESS, message, data);
    }

    /**
     * 系统错误
     */
    public static JsonResult code_500() {
        return code(JsonResultEnum.ERROR);
    }

    public static JsonResult code_500(String message) {
        return code(JsonResultEnum.ERROR, message + "，" + JsonResultEnum.ERROR.getMessage());
    }

    /**
     * 温馨提示
     */
    public static JsonResult code_800(String message) {
        return new JsonResult(JsonResultEnum.WARMTIPS.getCode(), message);
    }

    /**
     * 您还未登录
     */
    public static JsonResult code_801() {
        return code(JsonResultEnum.UNLOGIN);

    }

    /**
     * 您的权限不足
     */
    public static JsonResult code_802() {
        return code(JsonResultEnum.UNAUTHORIZED);
    }

    /**
     * 数据为空或格式不合法
     */
    public static JsonResult code_803() {
        return code(JsonResultEnum.INVALID_DATA);
    }

    /**
     * 暂时无数据
     */
    public static JsonResult code_804() {
        return code(JsonResultEnum.NON_DATA);
    }

    /**
     * 非法操作
     */
    public static JsonResult code_805() {
        return code(JsonResultEnum.ILLLEGAL);
    }


    public static JsonResult code(JsonResultEnum jsonResultEnum) {
        return new JsonResult(jsonResultEnum.getCode(), jsonResultEnum.getMessage());
    }

    public static JsonResult code(JsonResultEnum jsonResultEnum, String message) {
        return new JsonResult(jsonResultEnum.getCode(), message);
    }

    public static JsonResult code(JsonResultEnum jsonResultEnum, String message, Object data) {
        return new JsonResult(jsonResultEnum.getCode(), message, data);
    }
}
