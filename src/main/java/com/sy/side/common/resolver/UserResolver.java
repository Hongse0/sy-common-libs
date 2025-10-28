package com.sy.side.common.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sy.side.common.annotation.UserParam;
import com.sy.side.common.entity.UserSession;
import com.sy.side.common.entity.UserSession.UserType;
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
public class UserResolver implements HandlerMethodArgumentResolver {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isAnnotation = parameter.hasParameterAnnotation(UserParam.class);
        boolean isType = parameter.getParameterType().equals(UserSession.class);
        return isAnnotation && isType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String userStr = webRequest.getHeader("user-session");
        if (userStr == null) {
            return UserSession.builder()
                    .userType(UserType.GUEST)
                    .build();
        }
        return this.objectMapper.readValue(userStr, UserSession.class);
    }
}
