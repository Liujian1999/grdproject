package com.ecommerce.demo.utils;

import com.ecommerce.demo.entity.model.AuthCodeInfo;
import com.ecommerce.demo.service.impl.UserServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author 刘剑
 * @content 验证码工具类
 * @date 20210324
 */
@Component
public class AuthCodeUtils implements ApplicationContextAware {//调用ApplicationContextAware接口将工具类装入spring容器

    private static ApplicationContext applicationContext = null;

    /**
     * @author 刘剑
     * 存储验证码
     * @param authCodeInfo
     * @date 20210324
     */
    public static void  storeAuthCode(AuthCodeInfo authCodeInfo){
        AuthCodeUtils.getBean(new UserServiceImpl().getClass()).storeAuthCode(authCodeInfo);
    }

    /**
     * @author 刘剑
     * 获取验证码
     * @param userPhone
     * @return
     */
    public static Integer getAuthCode(String userPhone){
       return AuthCodeUtils.getBean(new UserServiceImpl().getClass()).getAuthCode(userPhone);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AuthCodeUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

}
