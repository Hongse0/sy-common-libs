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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod handlerMethod)){
            return true;
        }

        Class<?> controllerClass = handlerMethod.getBeanType();
        CheckPermission controllerClassAnnotation = controllerClass.getAnnotation(CheckPermission.class);
        CheckPermission methodAnnotation = handlerMethod.getMethodAnnotation(CheckPermission.class);

        if (controllerClassAnnotation != null || methodAnnotation != null) {
            CheckPermission checkPermission = (methodAnnotation != null) ? methodAnnotation: controllerClassAnnotation;
            var role = checkPermission.value();

            String memberStr = request.getHeader("member-session");
            MemberSession memberSession = null;

            if(memberStr == null || memberStr.trim().equals("")) {
                throw new BizException(ErrorCodeImpl.INSUFFICIENT_PERMISSION);
            }

            try{
                memberSession = this.objectMapper.readValue(memberStr, MemberSession.class);
            }catch (Exception ex) {
                log.error("", ex);
                throw new BizException(ErrorCodeImpl.INSUFFICIENT_PERMISSION);
            }

            if(!memberSession.getRole().contains(role)) {
                throw new BizException(ErrorCodeImpl.INSUFFICIENT_PERMISSION);
            }
        }


        return true;
    }
}
