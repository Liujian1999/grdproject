package com.ecommerce.demo.mapper;

import com.ecommerce.demo.entity.model.AuthCodeInfo;
import com.ecommerce.demo.entity.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    int userRegist(@Param("userInfo") UserInfo userInfo);
    /**
     * 通过用户名查找用户
     * @param userName
     * @return
     */
    int findUserByUserName(@Param("userName")String userName);
    /**
     * 通过用户名和密码查找用户
     * @param userInfo
     * @return
     */
    int findUserByUserNameAndPwd(@Param("userInfo") UserInfo userInfo);
    /**
     * 通过手机号查找用户
     * @param userPhone
     * @return
     */
    int findUserByUserPhone(@Param("userPhone") String userPhone);

    /**
     * 储存验证码
     * @param authCodeInfo
     */
    void storeAuthCode(@Param("authCodeInfo")AuthCodeInfo authCodeInfo);

    /**
     * 获取验证码
     * @param authCodeInfo
     */
    void getAuthCode(@Param("authCodeInfo")AuthCodeInfo authCodeInfo);
}
