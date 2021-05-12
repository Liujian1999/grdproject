package com.ecommerce.demo.config;

import com.ecommerce.demo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    LoginInterceptor loginInterceptor (){
        return new LoginInterceptor();
    }
    @Override
    /**
     * 设置拦截接口
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/userLogin","/user/userRegist","/user/phone/code","/user/managerLogin",
        "/imge/**","user/update");
    }

    /**
     * 赋予前端获取图片文件
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imge/**").addResourceLocations("file:/usr/local/www/img/");
    }
}
