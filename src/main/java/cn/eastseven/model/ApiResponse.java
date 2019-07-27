package cn.eastseven.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author d7
 */
@Getter
@Setter
@ToString
@ApiModel("API响应")
public class ApiResponse implements Serializable {

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    private ApiResponse() {
        code = 20000;
        success = true;
        msg = "success";
    }

    private ApiResponse(Object data) {
        this();
        this.data = data;
    }

    public static ApiResponse of() {
        return new ApiResponse();
    }

    public static ApiResponse of(Object data) {
        return new ApiResponse(data);
    }

    public static ApiResponse ofError(int code, String msg) {
        ApiResponse response = new ApiResponse();
        response.setCode(code);
        response.setMsg(msg);
        response.setSuccess(false);
        return response;
    }

    public static ApiResponse ofError(int code, String msg, Object data) {
        ApiResponse response = ofError(code, msg);
        response.setData(data);
        return response;
    }
}
