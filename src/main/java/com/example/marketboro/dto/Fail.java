package com.example.marketboro.dto;

import com.example.marketboro.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Fail {
    private String result = "fail";
    private String msg;
    private int status;
    private String code;

    public Fail(final ErrorCode errorCode){
        this.msg = errorCode.getMessage();
        this.status = errorCode.getStatusCode();
        this.code = errorCode.getErrorCode();
    }

    public Fail(final String msg){
        this.msg = msg;
    }
}
