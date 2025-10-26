package com.sy.side.common.error;

import java.util.List;
import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getCode();
    HttpStatus getStatus();
    List<String> getMessages();
}
