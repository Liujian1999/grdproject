package com.ecommerce.demo.service;


import com.ecommerce.demo.entity.model.AuthCodeInfo;
import com.ecommerce.demo.entity.model.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author liujian
 */
public interface UserService {

    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    boolean registUser(UserInfo userInfo);

    /**
     * 通过用户名查找用户
     * @param userName
     * @return
     */
    boolean findUserByUserName(String userName);

    /**
     * 校验账号是否正确
     * @param user
     * @return
     */
    boolean findUserByUserNameAndPwd(UserInfo user);

    /**
     * 通过手机号查找用户
     * @param userPhone
     * @return
     */
    boolean findUserByPhone(String userPhone);

    /**
     * 获得手机验证码
     * @param userPhone
     * @return
     */
    Integer getAuthCode(@Param("userPhone")String userPhone);

    /**
     * 储存验证码
     * @param authCodeInfo
     */
    void  storeAuthCode(AuthCodeInfo authCodeInfo);
}
