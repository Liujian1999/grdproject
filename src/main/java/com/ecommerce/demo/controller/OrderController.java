package com.ecommerce.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.ecommerce.demo.entity.model.OrderInfo;
import com.ecommerce.demo.service.OrderService;
import com.ecommerce.demo.utils.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public JSONObject orderCreate(@RequestBody OrderInfo orderInfo) throws Exception {
        if (Objects.isNull(orderInfo)){
            return  ResponseJson.buildFail("参数不能为空");
        }
        orderService.createOrder(orderInfo);
        return ResponseJson.buildSuccess("成功！");
    }

    @PostMapping("/pay")
    public JSONObject payMent(@RequestBody OrderInfo orderInfo) {
        if (Objects.isNull(orderInfo)){
            return  ResponseJson.buildFail("参数不能为空");
        }
     String result = orderService.aliPay(orderInfo);
     return ResponseJson.buildSuccess("true",result) ;
    }
}
