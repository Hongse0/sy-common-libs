package com.sy.side.common.error;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCodeImpl implements ErrorCode {
    SUCCESS("000000", HttpStatus.OK, List.of("성공")),
    INVALID_ACCESS_TOKEN("900201", HttpStatus.UNAUTHORIZED, List.of("유효하지 않은 토큰입니다.")),
    INVALID_REFRESH_TOKEN("900202", HttpStatus.UNAUTHORIZED, List.of("유효하지 않은 토큰입니다.")),
    BAD_PARAMETER("900103", HttpStatus.BAD_REQUEST, List.of("잘못된 요청입니다.")),
    NOT_FOUND("900104", HttpStatus.NOT_FOUND, List.of("자원을 찾지 못했습니다.")),
    INTERNAL_SERVER_ERROR("900105", HttpStatus.INTERNAL_SERVER_ERROR, List.of("Internal Server Error")),
    INSUFFICIENT_PERMISSION ("900105", HttpStatus.FORBIDDEN, List.of("권한이 없습니다."))
    ;

    private final String code;
    private final HttpStatus status;
    private final List<String> messages;
}
