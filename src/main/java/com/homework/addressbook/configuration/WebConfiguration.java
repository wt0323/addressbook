package com.homework.addressbook.configuration;


import com.homework.addressbook.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *   拦截器配置类
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 不需要限制的页面 添加白名单。不会进行拦截
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new LoginInterceptor());
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.order(0);
        interceptorRegistration.excludePathPatterns("/");
        interceptorRegistration.excludePathPatterns("/login");
        interceptorRegistration.excludePathPatterns("/admins");
        interceptorRegistration.excludePathPatterns("/index");
        interceptorRegistration.excludePathPatterns("/student/index");
        interceptorRegistration.excludePathPatterns("/student/register");
        interceptorRegistration.excludePathPatterns("/backstage");
        interceptorRegistration.excludePathPatterns("/student/backstage");
        interceptorRegistration.excludePathPatterns("/static/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    // 这个方法是用来配置静态资源的，比如html，js，css，等等  设置允许访问
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/static/**").addResourceLocations("classpath:/resources/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
