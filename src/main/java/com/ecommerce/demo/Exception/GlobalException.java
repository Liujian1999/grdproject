package com.ecommerce.demo.Exception;

import com.alibaba.fastjson.JSONObject;
import com.ecommerce.demo.entity.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalException {
    private static  final  String MESSAGE="msg";
    private static  final  String CODE = "code";

    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =MyException.class)
   public JSONObject MyException(MyException e){
        JSONObject data = new JSONObject();
        data.put(MESSAGE,e.getMsg());
        data.put(CODE,e.getCode());
        e.printStackTrace();
        log.info(new Exception().getMessage());
        return data;
    }

    /**
     * 捕获所有异常
     * @param e
     * @return
     */
    @ExceptionHandler(value=Exception.class)
    public JSONObject Exception(Exception e){
        JSONObject data = new JSONObject();
        data.put(MESSAGE,"出现未知异常，请联系管理员！");
        data.put(CODE,"100");
        e.printStackTrace();
        log.info(e.getMessage());
        return data;
    }
}
