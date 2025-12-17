package com.sy.side.common.resolver;

import com.sy.side.common.annotation.UserParam;
import com.sy.side.common.entity.MemberSession;
import com.sy.side.common.entity.UserSession;
import com.sy.side.common.entity.UserSession.UserType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
public class UserResolver implements HandlerMethodArgumentResolver {

    private static final String H_USER_TYPE  = "X-USER-TYPE";
    private static final String H_MEMBER_ID  = "X-MEMBER-ID";
    private static final String H_LOGIN_TYPE = "X-LOGIN-TYPE";
    private static final String H_SNS_TYPE   = "X-SNS-TYPE";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserParam.class)
                && UserSession.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request =
                webRequest.getNativeRequest(HttpServletRequest.class);

        String memberIdStr = request.getHeader(H_MEMBER_ID);
        if (memberIdStr == null || memberIdStr.isBlank()) {
            // 로그인 안 된 상태
            return UserSession.builder()
                    .userType(UserType.GUEST)
                    .build();
        }

        long memberId = Long.parseLong(memberIdStr);

        MemberSession memberSession = MemberSession.builder()
                .memberId(memberId)
                .loginType(request.getHeader(H_LOGIN_TYPE))
                .snsType(request.getHeader(H_SNS_TYPE))
                .build();

        UserType userType = UserType.MEMBER;
        String userTypeHeader = request.getHeader(H_USER_TYPE);
        if (userTypeHeader != null) {
            try {
                userType = UserType.valueOf(userTypeHeader);
            } catch (Exception ignored) {}
        }

        return UserSession.builder()
                .userType(userType)
                .memberSession(memberSession)
                .build();
    }
}
