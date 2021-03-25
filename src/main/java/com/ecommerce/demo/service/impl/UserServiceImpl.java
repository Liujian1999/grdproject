package com.ecommerce.demo.service.impl;

import com.ecommerce.demo.entity.model.AuthCodeInfo;
import com.ecommerce.demo.entity.model.UserInfo;
import com.ecommerce.demo.mapper.UserMapper;
import com.ecommerce.demo.service.UserService;
import com.ecommerce.demo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘剑
 * @data 2021/03/24
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    @Override
    public boolean registUser(UserInfo userInfo) {
        String password = MD5Utils.MD5(userInfo.getPassWord());
        userInfo.setPassWord(password);
        int row = userMapper.userRegist(userInfo);
        if (row == 1) {
            return true;
        }
        return false;
    }

    /**
     * 通过用户名查找用户
     * @param userName
     * @return
     */
    @Override
    public boolean findUserByUserName(String userName) {
        int row = userMapper.findUserByUserName(userName);
        if (row == 0) {
            return true;
        }
        return false;
    }

    /**
     * 通过用户名和密码查找用户
     * @param userInfo
     * @return
     */
    @Override
    public boolean findUserByUserNameAndPwd(UserInfo userInfo) {
        userInfo.setPassWord(MD5Utils.MD5(userInfo.getPassWord()));
        int row = userMapper.findUserByUserNameAndPwd(userInfo);
        if (row == 1) {
            return true;
        }
        return false;
    }

    /**
     * 通过手机号查找用户
     * @param userPhone
     * @return
     */
    @Override
    public boolean findUserByPhone(String userPhone) {
        int row = userMapper.findUserByUserPhone(userPhone);
        if (row==1){
            return true;
        }
        return false;
    }
    /**
     * 获取验证码
     * @param userPhone
     */
    @Override
    public Integer getAuthCode (String userPhone){
      Integer codeNumber = userMapper.getAuthCode(userPhone);
      if (codeNumber == null) {
         return -1;
      }
        return codeNumber;
    }

    /**
     * 存储验证码
     * @param authCodeInfo
     */
    @Override
    public void storeAuthCode(AuthCodeInfo authCodeInfo) {
        System.out.println(authCodeInfo.getCodeNumber());
        userMapper.storeAuthCode(authCodeInfo);
    }
}
