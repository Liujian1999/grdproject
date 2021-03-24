package com.ecommerce.demo.service;


import com.ecommerce.demo.entity.model.AuthCodeInfo;
import com.ecommerce.demo.entity.model.UserInfo;

public interface UserService {


    boolean registUser(UserInfo userInfo);


    boolean findUserByUserName(String userName);


    boolean findUserByUserNameAndPwd(UserInfo user);


    boolean findUserByPhone(String userPhone);


    void  storeAuthCode(AuthCodeInfo authCodeInfo);
}
