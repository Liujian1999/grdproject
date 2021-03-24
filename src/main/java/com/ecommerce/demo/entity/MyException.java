package com.ecommerce.demo.entity;

import lombok.Data;

@Data
/**
 * 设置自定义异常
 */
public class MyException extends RuntimeException{
    /**
     * 自定义错误码
     */
    private  String code;
    /**
     * 自定义错误消息
     */
    private  String msg;
    public MyException(String msg,String code){
        this.code = code;
        this.msg=msg;
    }
}
