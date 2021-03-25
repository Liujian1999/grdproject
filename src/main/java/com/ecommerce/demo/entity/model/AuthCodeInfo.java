package com.ecommerce.demo.entity.model;

import lombok.Data;


@Data
public class AuthCodeInfo {
    /**
     *验证码id
     */
    private Integer id ;

    /**
     * 验证码
     */
    private Integer codeNumber;

    /**
     *用户手机号
     */
    private String userPhone;

    /**
     * 验证码发送时间
     */
    private long sendTime;
}
