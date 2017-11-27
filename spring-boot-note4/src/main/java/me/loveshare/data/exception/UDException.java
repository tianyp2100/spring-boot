package me.loveshare.data.exception;

/**
 * Created by Tony on 2016/12/17. 2017-10-13 rename.<br>
 * 自定义异常类: ~~~  由:状态码/描述信息 --确定一个异常.<br>
 * Class name's simplify as: User-Defined Exception ---> UDException
 */
public class UDException extends Exception {

    /**
     * 异常状态码
     */
    private int code;
    /**
     * 异常描述信息
     */
    private String message;

    public UDException() {
        super();
    }

    public UDException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public UDException(int code) {
        super();
        this.code = code;
    }

    public UDException(String message) {
        super();
        this.message = message;
    }

    public UDException(String message, Exception e) {
        super(message, e);
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
