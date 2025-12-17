package com.sy.side.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sy.side.common.annotation.CheckPermission;
import com.sy.side.common.auth.constants.MemberRoles;
import com.sy.side.common.entity.MemberSession;
import com.sy.side.common.error.ErrorCodeImpl;
import com.sy.side.common.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckPermissionInterceptor implements HandlerInterceptor {

    private static final String H_MEMBER_ID = "X-MEMBER-ID";
    private static final String H_USER_TYPE = "X-USER-TYPE";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        CheckPermission methodAnn =
                handlerMethod.getMethodAnnotation(CheckPermission.class);
        CheckPermission classAnn =
                handlerMethod.getBeanType().getAnnotation(CheckPermission.class);

        if (methodAnn == null && classAnn == null) {
            return true;
        }

        // 로그인 여부
        String memberIdStr = request.getHeader(H_MEMBER_ID);
        if (memberIdStr == null || memberIdStr.isBlank()) {
            throw new BizException(ErrorCodeImpl.INSUFFICIENT_PERMISSION);
        }

        // 역할 체크 (예시)
        String userType = request.getHeader(H_USER_TYPE);
        CheckPermission permission =
                (methodAnn != null) ? methodAnn : classAnn;

        if (!permission.value().name().equals(userType)) {
            throw new BizException(ErrorCodeImpl.INSUFFICIENT_PERMISSION);
        }

        return true;
    }
}

