package com.sy.side.common.exception;

import com.sy.side.common.error.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BizException extends RuntimeException {
    private ErrorCode errorCode;
    public BizException(ErrorCode errorCode) {
        super(errorCode.getCode() + " : " + errorCode.getMessages());
        this.setErrorCode(errorCode);
    }
}
