package com.census.support.helper.response;

import lombok.Data;
@Data
public class BaseResponse {

    private boolean status;
    private String message;
    private Integer code;
    // array list of data
    private Object data;


    public BaseResponse(boolean status, String message,Integer code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public BaseResponse(boolean status, String message, Integer code, Object data) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public BaseResponse() {
    }


}
