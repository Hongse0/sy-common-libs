package com.sy.side.common.config;

import com.sy.side.common.interceptor.CheckPermissionInterceptor;
import com.sy.side.common.resolver.AuthResolver;
import com.sy.side.common.resolver.UserResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@ComponentScan("com.sy.side.common")
public class WebConfig implements WebMvcConfigurer {
    private final AuthResolver authResolver;
    private final UserResolver userResolver;
    private final CheckPermissionInterceptor memberRoleCheckInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(this.authResolver);
        resolvers.add(this.userResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.memberRoleCheckInterceptor);
    }
}
