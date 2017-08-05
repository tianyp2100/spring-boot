package me.loveshare.note5.data.entity.bo.common;

/**
 * 业务或数据处理结果结果json格式封装传递实体类的辅助类
 *
 * @author Tony
 * @time 2017-07-03 18:03:04
 */
public enum JsonResultEnum {

    //业务code和信息定义
    SUCCESS(200, "操作成功"),
    ERROR(500, "系统错误，请联系客服处理"),
    WARMTIPS(800, "温馨提示，操作失败"),
    UNLOGIN(801, "您还未登录"),
    UNAUTHORIZED(802, "您的权限不足"),
    INVALID_DATA(803, "数据为空或非法"),
    NON_DATA(804, "暂时无数据"),
    ILLLEGAL(805, "非法操作");

    /**
     * 获取JsonResult的方法，默认提示
     */
    public JsonResult get() {
        return new JsonResult(this.getCode(), this.getMessage());
    }

    /**
     * 获取JsonResult的方法，自定义提示
     */
    public JsonResult get(String message) {
        return new JsonResult(this.getCode(), message);
    }

    private int code;
    private String message;

    private JsonResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
