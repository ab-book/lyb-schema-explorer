package http;


import java.io.Serializable;

/**
 * 通用的返回结果类
 *
 * @author liyebing created on 15/9/6.
 * @version $Id$
 */
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = -2465140747749709626L;

    //请求是否成功 true/false
    private boolean success;
    //错误码
    private int code;
    //错误信息
    private String errMsg;
    //返回业务数据
    private T data;

    public static <T> ApiResponse<T> buildSuccess(T date) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setData(date);
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> buildSuccess() {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure() {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(false);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure(String errorMsg) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(false);
        response.setCode(0);
        response.setErrMsg(errorMsg);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure(int errorCode, String errorMsg) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(false);
        response.setCode(errorCode);
        response.setErrMsg(errorMsg);
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}