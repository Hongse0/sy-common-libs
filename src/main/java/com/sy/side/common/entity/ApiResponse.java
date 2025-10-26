package com.sy.side.common.entity;

import com.sy.side.common.error.ErrorCode;
import com.sy.side.common.error.ErrorCodeImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    private String code;
    private List<String> messages;
    private T result;

    /**
     * 성공 시
     */
    public ApiResponse(T result) {
        this.result = result;
        this.code = ErrorCodeImpl.SUCCESS.getCode();
        this.messages = ErrorCodeImpl.SUCCESS.getMessages();
    }

    /**
     * 예외 발생 시
     */
    public ApiResponse(ErrorCode errorCode) {
        this.result = null;
        this.code = errorCode.getCode();
        this.messages = errorCode.getMessages();
    }

    /**
     * 예외 발생 시, 메시지 추가가 필요할 때.
     */
    public ApiResponse(ErrorCode errorCode, List<String> messages) {
        this.result = null;
        this.code = errorCode.getCode();
        this.messages = Stream
                .concat(errorCode.getMessages().stream(), messages.stream())
                .collect(Collectors.toList());
    }

    /**
     * 예외 발생 시, 메시지 추가가 필요할 때.
     */
    public ApiResponse(ErrorCode errorCode, T result) {
        this.result = result;
        this.code = errorCode.getCode();
        this.messages = errorCode.getMessages();
    }
}
