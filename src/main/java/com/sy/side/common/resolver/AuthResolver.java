package com.sy.side.common.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sy.side.common.annotation.AuthParam;
import com.sy.side.common.entity.MemberSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private static final String H_MEMBER_ID  = "X-MEMBER-ID";
    private static final String H_LOGIN_TYPE = "X-LOGIN-TYPE";
    private static final String H_SNS_TYPE   = "X-SNS-TYPE";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthParam.class)
                && MemberSession.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        String memberIdStr = webRequest.getHeader(H_MEMBER_ID);
        if (memberIdStr == null || memberIdStr.isBlank()) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        long memberId = Long.parseLong(memberIdStr);

        return MemberSession.builder()
                .memberId(memberId)
                .loginType(webRequest.getHeader(H_LOGIN_TYPE))
                .snsType(webRequest.getHeader(H_SNS_TYPE))
                .build();
    }
}

