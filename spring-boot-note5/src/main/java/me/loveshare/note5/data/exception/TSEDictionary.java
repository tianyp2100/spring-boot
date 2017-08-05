package me.loveshare.note5.data.exception;


/**
 * Created by Tony on 2016/12/17.<br>
 * 异常:状态码/描述信息 --字典类.<br>
 * 后期可实现i18n的国际化.<br>
 * Class name's simplify as: TimeSpaceXStarExceptionDictionary ---> TSEDictionary
 */
public enum TSEDictionary {

    JSON_CONVERT_FAIL(1101001, "Json字符串转换异常"),

    IO_EXCEPTION(1101002, "输入输出流异常"),
    IO_NULL(1101003, "输入输出流不能为空"),

    SYSTEM_EXCEPTION(1101004, "系统异常"),
    SYSTEM_ERROR(1101005, "系统错误，请联系客服处理"),
    DATA_ERROR(1101006, "数据错误，请联系客服处理"),

    ARGS_FAIL(1101007, "范围不合法"),
    ARGS_FAULTINESS(1101008, "参数未设置或设置不全"),
    RECORD_DATA_FAIL(1101009, "数据写入异常"),


    FILE_NOT_EXIST(1102001, "文件不存在"),
    FILE_NOT_KNOWN(1102002, "未知文件"),
    FILE_COMPRESS_FAIL(1102003, "文件压缩失败"),
    FILE_OUT_DISK_FAIL(1102004, "文件写入磁盘失败"),
    FILE_OUT_STREAM_FAIL(1102005, "文件输出流失败"),

    BYE_2_STREAM_FAIL(1102006, "字节转换流失败"),
    STREAM_2_BYE_FAIL(1102007, "流转换字节失败"),
    IMG_2_STREAM_FAIL(1102008, "图片转换流失败"),
    STREAM_2_FILE_FAIL(1102009, "流转换文件失败"),

    SERIALIZE_FAIL(1102010, "序列化失败"),
    DESERIALIZE_FAIL(1102011, "反序列化失败"),


    SECRET_DECRYPT_FAIL(1103001, "解密失败"),
    SECRET_ENCRYPT_FAIL(1103002, "加密失败"),

    ENCODING_FAIL(1103003, "编码失败"),
    DECODE_FAIL(1103004, "解码失败"),
    SECRET_ENCODING_FAIL(1103005, "加密编码失败"),


    UNDEFINED_FAIL(1103001, "未定义异常"),

    AOSS2TQVOD_FAIL(1104001, "未定义异常"),;

    private int code;
    private String message;

    private TSEDictionary(int code, String message) {
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
