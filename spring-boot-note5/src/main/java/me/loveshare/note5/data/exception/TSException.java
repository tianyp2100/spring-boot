package me.loveshare.note5.data.exception;

/**
 * Created by Tony on 2016/12/17.<br>
 * "爱分享"自定义异常类: ~~~  由:状态码/描述信息 --确定一个异常.<br>
 * Class name's simplify as: TimeSpaceXStarException ---> HException
 */
public class TSException extends Exception {

    /**
     * 异常状态码
     */
    private int code;
    /**
     * 异常描述信息
     */
    private String message;

    public TSException(int code, String s, Exception e) {
        super();
    }

    public TSException(String s, Exception e) {
        super();
    }

    public TSException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public TSException(int code) {
        this.code = code;
    }

    public TSException(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
