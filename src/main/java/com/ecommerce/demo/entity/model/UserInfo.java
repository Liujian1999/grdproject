package com.ecommerce.demo.entity.model;

import lombok.Data;

@Data
public class UserInfo {

    /**
     * 用户Id
     */
    private Integer  id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 验证码
     */
    private String code;
}
